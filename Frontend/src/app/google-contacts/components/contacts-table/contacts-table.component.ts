import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Contact } from '../../model/contact';
import { ContactListService } from '../../services/contact-list.service';
import { SearchContactsService } from '../contacts-header/search-contacts.service';
import { RefreshContactsCountService } from '../contacts-sidebar/refresh-contact-count.service';
import { ExportSelectedPageComponent } from '../export-selected-page/export-selected-page.component';
import { MergeContactsDialogComponent } from '../merge-contacts-dialog/merge-contacts-dialog.component';

@Component({
  selector: 'contacts-table',
  templateUrl: './contacts-table.component.html',
  styleUrls: ['./contacts-table.component.scss'],
})
export class ContactsTableComponent implements OnInit {
  allContacts: Contact[] = [];
  contacts: Contact[] = [];
  starredContacts: Contact[] = [];
  selectedContacts: number[] = [];
  currentSearch: string = '';

  constructor(
    private contactListService: ContactListService,
    private router: Router,
    private refreshContactsCountService: RefreshContactsCountService,
    private searchContactsService: SearchContactsService,
    private contactService: ContactListService,
    private snackbar: MatSnackBar,
    private dialog: MatDialog
  ) {
    searchContactsService.doSearch$.subscribe((text) => {
      this.searchContacts(text);
    });
    this.searchContactsService.announceSearchReset();
  }

  ngOnInit(): void {
    this.refreshContacts();
  }

  refreshContacts() {
    this.contactListService.getAllContacts().subscribe((data) => {
      this.allContacts = this.sortContacts(data);
      this.contacts = this.sortContacts(data);
      this.setStarredContacts();
      this.refreshContactsCountService.announceRefreshing();
      this.searchContacts(this.currentSearch);
    });
  }

  setStarredContacts() {
    this.starredContacts = this.contacts.filter((contact) => contact.starred);
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

  redirectContact(id: number) {
    this.router.navigateByUrl('/contacts/person/' + id);
  }

  redirectContactEdit(id: number) {
    this.router.navigateByUrl('/contacts/person/' + id + '/edit');
  }

  starContact(id: number) {
    let contact = this.contacts.filter((contact) => {
      return contact.id == id;
    })[0];
    if (contact != null && contact.starred == false) {
      this.contactService.starContact(id).subscribe((data) => {
        this.refreshContacts();
      });
    } else if (contact != null && contact.starred == true) {
      this.contactService.unstarContact(id).subscribe((data) => {
        this.refreshContacts();
      });
    }
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

  deleteContact(id: number) {
    if (confirm('Are you sure you want to delete this contact?')) {
      this.contactService.deleteContact(id).subscribe((data) => {
        this.refreshContacts();
        this.snackbar.open('Contact deleted', 'Close', {
          duration: 3000,
        });
      });
    }
  }

  deleteSelected() {
    if (confirm('Are you sure you want to delete selected contacts?')) {
      this.contactService
        .deleteContactList(this.selectedContacts)
        .subscribe((data) => {
          this.refreshContacts();
          this.selectNone();
          this.snackbar.open('Contacts deleted', 'Close', {
            duration: 3000,
          });
        });
    }
  }

  exportSelected() {
    if (this.selectedContacts.length != 0) {
      const dialogRef = this.dialog.open(ExportSelectedPageComponent, {
        data: this.selectedContacts,
      });
    }
  }

  mergeSelected() {
    if (this.selectedContacts.length != 0) {
      const dialogRef = this.dialog.open(MergeContactsDialogComponent, {
        data: this.selectedContacts,
      });
    }
  }

  searchContacts(text: string) {
    this.currentSearch = text;
    if (text == '') {
      this.contacts = this.allContacts;
      this.setStarredContacts();
    } else {
      this.selectNone();
      this.contacts = this.searchContactsService.searchContacts(
        this.allContacts,
        text
      );
      this.setStarredContacts();
    }
  }
}
