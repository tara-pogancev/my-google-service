import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Contact } from '../../model/contact';
import { ContactListService } from '../../services/contact-list.service';
import { ContactService } from '../../services/contact.service';
import { RefreshContactsCountService } from '../contacts-sidebar/refresh-contact-count.service';

@Component({
  selector: 'contacts-table',
  templateUrl: './contacts-table.component.html',
  styleUrls: ['./contacts-table.component.scss'],
})
export class ContactsTableComponent implements OnInit {
  contacts: Contact[] = [];
  starredContacts: Contact[] = [];
  selectedContacts: number[] = [];

  constructor(
    private contactListService: ContactListService,
    private router: Router,
    private refreshContactsCountService: RefreshContactsCountService,
    private contactService: ContactService
  ) {}

  ngOnInit(): void {
    this.refreshContacts();
  }

  refreshContacts() {
    this.contactListService.getAllContacts().subscribe((data) => {
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

  deleteContact(id: number) {
    //TODO delete single comment
    this.refreshContacts();
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

  deleteSelected() {
    //TODO delete selected
  }

  exportSelected() {
    //TODO export selected
  }
}
