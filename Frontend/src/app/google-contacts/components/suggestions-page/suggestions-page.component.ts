import { Component, OnInit } from '@angular/core';
import { SearchContactsService } from '../contacts-header/search-contacts.service';

@Component({
  selector: 'suggestions-page',
  templateUrl: './suggestions-page.component.html',
  styleUrls: ['./suggestions-page.component.scss'],
})
export class SuggestionsPageComponent implements OnInit {
  constructor(private searchContactsService: SearchContactsService) {
    this.searchContactsService.announceSearchReset();
  }

  ngOnInit(): void {}

  //TODO suggestions;
}
