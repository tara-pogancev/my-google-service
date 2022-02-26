import { Component, OnInit } from '@angular/core';
import { Contact } from '../../model/contact';
import { ContactListService } from '../../services/contact-list.service';

@Component({
  selector: 'contacts-table',
  templateUrl: './contacts-table.component.html',
  styleUrls: ['./contacts-table.component.scss'],
})
export class ContactsTableComponent implements OnInit {
  contacts: Contact[] = [];
  starredContacts: Contact[] = [];

  constructor(private contactListService: ContactListService) {}

  ngOnInit(): void {
    this.contactListService.getAllContacts().subscribe((data) => {
      this.contacts = data;
      this.setStarredContacts();
    });
  }

  setStarredContacts() {
    this.starredContacts = this.contacts.filter((contact) => contact.starred);
  }
}
