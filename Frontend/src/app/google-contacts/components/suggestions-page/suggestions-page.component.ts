import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { Contact } from '../../model/contact';
import { Suggestion } from '../../model/suggestion';
import { SuggestionsService } from '../../services/suggestions.service';
import { SearchContactsService } from '../contacts-header/search-contacts.service';
import { RefreshContactsCountService } from '../contacts-sidebar/refresh-contact-count.service';

@Component({
  selector: 'suggestions-page',
  templateUrl: './suggestions-page.component.html',
  styleUrls: ['./suggestions-page.component.scss'],
  animations: [
    trigger('fade1', [
      state('void', style({ opacity: 0 })),

      transition('void => *', [animate(300)]),
      transition('* => void', [animate(200)]),
    ]),
  ],
})
export class SuggestionsPageComponent implements OnInit {
  suggestions: Suggestion[] = [];
  initEmpty: Boolean = true;

  constructor(
    private searchContactsService: SearchContactsService,
    private suggestionService: SuggestionsService
  ) {
    this.searchContactsService.announceSearchReset();
  }

  ngOnInit(): void {
    this.suggestionService.getSuggestions().subscribe((data) => {
      this.suggestions = data;

      if (this.suggestions.length != 0) {
        this.initEmpty = false;
      }
    });
  }

  removeSuggestion(contacts: Contact[]) {
    this.suggestions = this.suggestions.filter(
      (suggestion) => suggestion.contacts != contacts
    );
  }
}
