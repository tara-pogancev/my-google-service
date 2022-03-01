import { Component, OnInit } from '@angular/core';
import { SearchContactsService } from '../contacts-header/search-contacts.service';

@Component({
  selector: 'create-new-page',
  templateUrl: './create-new-page.component.html',
  styleUrls: ['./create-new-page.component.scss'],
})
export class CreateNewPageComponent implements OnInit {
  constructor(private searchContactsService: SearchContactsService) {
    this.searchContactsService.announceSearchReset();
  }

  ngOnInit(): void {}

  //TODO add new contact
}
