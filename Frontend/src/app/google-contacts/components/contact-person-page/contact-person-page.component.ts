import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Contact } from '../../model/contact';
import { ContactService } from '../../services/contact.service';
import { SearchContactsService } from '../contacts-header/search-contacts.service';

@Component({
  selector: 'contact-person-page',
  templateUrl: './contact-person-page.component.html',
  styleUrls: ['./contact-person-page.component.scss'],
})
export class ContactPersonPageComponent implements OnInit {
  contact: Contact = new Contact();

  constructor(
    private searchContactsService: SearchContactsService,
    private route: ActivatedRoute,
    private contactService: ContactService
  ) {
    this.searchContactsService.announceSearchReset();
  }

  ngOnInit(): void {
    this.contact.id = this.route.snapshot.params['id'];
    this.contactService.getContact(this.contact.id).subscribe((data) => {
      this.contact = data;
      console.log(this.contact);
    });
  }
}
