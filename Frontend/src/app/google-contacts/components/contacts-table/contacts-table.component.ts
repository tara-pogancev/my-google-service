import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Contact } from '../../model/contact';
import { ContactListService } from '../../services/contact-list.service';
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
    private refreshContactsCountService: RefreshContactsCountService
  ) {}

  ngOnInit(): void {
    this.contactListService.getAllContacts().subscribe((data) => {
      this.contacts = this.sortContacts(data);
      this.setStarredContacts();
    });
  }

  setStarredContacts() {
    this.starredContacts = this.contacts.filter((contact) => contact.starred);
  }

  sortContacts(entities: any[]) {
    return entities.sort((e1, e2) => {
      if (e1.fullName < e2.fullName) {
        return 1;
      } else {
        return -1;
      }
    });
  }

  redirectContact(id: number) {
    this.router.navigateByUrl('/contacts/person/' + id);
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

  refreshContactCountSidebar() {
    this.refreshContactsCountService.announceRefreshing();
  }
}
