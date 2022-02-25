import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'contacts-sidebar',
  templateUrl: './contacts-sidebar.component.html',
  styleUrls: ['./contacts-sidebar.component.scss'],
})
export class ContactsSidebarComponent implements OnInit {
  tab: number = 1;

  constructor() {}

  ngOnInit(): void {}
}
