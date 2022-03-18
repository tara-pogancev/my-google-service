import { Component, Input, OnInit } from '@angular/core';
import { Contact } from 'src/app/google-contacts/model/contact';

@Component({
  selector: 'merge-card',
  templateUrl: './merge-card.component.html',
  styleUrls: ['./merge-card.component.scss'],
})
export class MergeCardComponent implements OnInit {
  @Input() contacts: Contact[] = [];

  constructor() {}

  ngOnInit(): void {}

  merge() {}
}
