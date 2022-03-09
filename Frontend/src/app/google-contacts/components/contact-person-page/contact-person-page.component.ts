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
  constructor(private searchContactsService: SearchContactsService) {
    this.searchContactsService.announceSearchReset();
  }

  ngOnInit(): void {}
}
