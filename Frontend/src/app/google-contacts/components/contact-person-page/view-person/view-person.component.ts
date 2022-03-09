import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Contact } from 'src/app/google-contacts/model/contact';
import { ContactListService } from 'src/app/google-contacts/services/contact-list.service';
import { ContactService } from 'src/app/google-contacts/services/contact.service';
import { SearchContactsService } from '../../contacts-header/search-contacts.service';

@Component({
  selector: 'view-person',
  templateUrl: './view-person.component.html',
  styleUrls: ['./view-person.component.scss'],
})
export class ViewPersonComponent implements OnInit {
  contact: Contact = new Contact();

  constructor(
    private searchContactsService: SearchContactsService,
    private route: ActivatedRoute,
    private contactService: ContactService,
    private contactListService: ContactListService
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

  redirectBack() {
    window.location.href = '/contacts';
  }

  starContact() {
    if (this.contact != null && this.contact.starred == false) {
      this.contactListService.starContact(this.contact.id).subscribe((data) => {
        this.contact.starred = true;
      });
    } else if (this.contact != null && this.contact.starred == true) {
      this.contactListService
        .unstarContact(this.contact.id)
        .subscribe((data) => {
          this.contact.starred = false;
        });
    }
  }

  deleteContact() {}
}
