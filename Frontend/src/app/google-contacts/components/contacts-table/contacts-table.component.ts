import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Contact } from '../../model/contact';
import { ContactListService } from '../../services/contact-list.service';
import { ContactService } from '../../services/contact.service';
import { SearchContactsService } from '../contacts-header/search-contacts.service';
import { RefreshContactsCountService } from '../contacts-sidebar/refresh-contact-count.service';

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

  constructor(
    private contactListService: ContactListService,
    private router: Router,
    private refreshContactsCountService: RefreshContactsCountService,
    private searchContactsService: SearchContactsService,
    private contactService: ContactService,
    private snackbar: MatSnackBar
  ) {
    searchContactsService.doSearch$.subscribe((text) => {
      this.searchContacts(text);
    });
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
          this.snackbar.open('Contacts deleted', 'Close', {
            duration: 3000,
          });
        });
    }
  }

  exportSelected() {
    //TODO export selected
  }

  searchContacts(text: string) {
    if (text == '') {
      this.contacts = this.allContacts;
      this.setStarredContacts();
    } else {
      this.contacts = this.searchContactsService.searchContacts(
        this.allContacts,
        text
      );
      this.setStarredContacts();
    }
  }
}
