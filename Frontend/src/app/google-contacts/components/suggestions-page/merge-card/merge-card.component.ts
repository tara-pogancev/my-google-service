import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Contact } from 'src/app/google-contacts/model/contact';
import { MergeService } from 'src/app/google-contacts/services/merge.service';
import { RefreshContactsCountService } from '../../contacts-sidebar/refresh-contact-count.service';

@Component({
  selector: 'merge-card',
  templateUrl: './merge-card.component.html',
  styleUrls: ['./merge-card.component.scss'],
})
export class MergeCardComponent implements OnInit {
  @Input() contacts: Contact[] = [];
  @Output() doRemoveSuggestion: EventEmitter<Contact[]> = new EventEmitter();

  constructor(private mergeService: MergeService, 
    private refreshContactsCountService: RefreshContactsCountService) {}

  ngOnInit(): void {}

  excractIds() {
    var ids = [];
    for (let contact of this.contacts) {
      ids.push(contact.id);
    }
    return ids;
  }

  merge() {
    this.mergeService.mergeContacts(this.excractIds()).subscribe((data) => {
      this.refreshContactsCountService.announceRefreshing();
      this.doRemoveSuggestion.emit(this.contacts);
    });
  }
}
