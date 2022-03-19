import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Contact } from '../../model/contact';
import { BinService } from '../../services/bin.service';
import { ContactListService } from '../../services/contact-list.service';
import { ContactService } from '../../services/contact.service';
import { SearchContactsService } from '../contacts-header/search-contacts.service';
import { RefreshContactsCountService } from '../contacts-sidebar/refresh-contact-count.service';
import { ExportSelectedPageComponent } from '../export-selected-page/export-selected-page.component';

@Component({
  selector: 'trash-page',
  templateUrl: './trash-page.component.html',
  styleUrls: ['./trash-page.component.scss'],
})
export class TrashPageComponent implements OnInit {
  allContacts: Contact[] = [];
  contacts: Contact[] = [];
  selectedContacts: number[] = [];
  currentSearch: string = '';

  constructor(
    private binService: BinService,
    private router: Router,
    private refreshContactsCountService: RefreshContactsCountService,
    private searchContactsService: SearchContactsService,
    private snackbar: MatSnackBar,
    private dialog: MatDialog
  ) {
    searchContactsService.doSearch$.subscribe((text) => {
      this.searchContacts(text);
    });
    this.searchContactsService.announceSearchReset('');
  }

  ngOnInit(): void {
    this.refreshContacts();
  }

  refreshContacts() {
    this.binService.getAllBinContacts().subscribe((data) => {
      this.allContacts = this.sortContacts(data);
      this.contacts = this.sortContacts(data);
      this.refreshContactsCountService.announceRefreshing();
      this.searchContacts(this.currentSearch);
    });
  }

  sortContacts(entities: any[]) {
    return entities.sort((e1, e2) => {
      if (e1.fullName > e2.fullName) {
        return 1;
      } else {
        return -1;
      }
    });
  }

  selectContact(checked: boolean, contact: Contact) {
    if (checked) {
      this.selectedContacts.push(contact.id);
    } else {
      this.selectedContacts = this.selectedContacts.filter(
        (idNumber) => idNumber != contact.id
      );
    }
  }

  selectAll() {
    this.selectedContacts = [];
    for (let contact of this.contacts) {
      this.selectedContacts.push(contact.id);
    }
  }

  selectNone() {
    this.selectedContacts = [];
  }

  deleteContactForever(id: number) {
    if (
      confirm(
        'Are you sure you want to delete this contact forever?\nThis action cannot be undone.'
      )
    ) {
      this.binService.deleteBinContactForever(id).subscribe((data) => {
        this.refreshContacts();
        this.snackbar.open('Contact deleted', 'Close', {
          duration: 3000,
        });
      });
    }
  }

  deleteSelectedForever() {
    if (
      confirm(
        'Are you sure you want to delete selected contacts forever?\nThis action cannot be undone.'
      )
    ) {
      this.binService
        .deleteBinContactList(this.selectedContacts)
        .subscribe((data) => {
          this.refreshContacts();
          this.selectNone();
          this.snackbar.open('Contacts deleted', 'Close', {
            duration: 3000,
          });
        });
    }
  }

  recoverContact(id: number) {
    this.binService.recoverBinContact(id).subscribe((data) => {
      this.refreshContacts();
      this.snackbar.open('Contact restored', 'Close', {
        duration: 3000,
      });
    });
  }

  recoverSelected() {
    this.binService
      .recoverBinContactList(this.selectedContacts)
      .subscribe((data) => {
        this.refreshContacts();
        this.selectNone();
        this.snackbar.open('Contacts restored', 'Close', {
          duration: 3000,
        });
      });
  }

  deleteAllForever() {
    if (
      confirm(
        'Are you sure you want to delete all contacts in bin forever?\nThis action cannot be undone.'
      )
    ) {
      this.binService.deleteAllContactsInBin().subscribe((data) => {
        this.refreshContacts();
        this.selectNone();
        this.snackbar.open('Contacts deleted', 'Close', {
          duration: 3000,
        });
      });
    }
  }

  searchContacts(text: string) {
    this.currentSearch = text;
    if (text == '') {
      this.contacts = this.allContacts;
    } else {
      this.selectNone();
      this.contacts = this.searchContactsService.searchContacts(
        this.allContacts,
        text
      );
    }
  }
}
