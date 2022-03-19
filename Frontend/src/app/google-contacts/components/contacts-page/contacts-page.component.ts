import { Component, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'contacts-page',
  templateUrl: './contacts-page.component.html',
  styleUrls: ['./contacts-page.component.scss'],
})
export class ContactsPageComponent implements OnInit {
  innerWidth: any;

  constructor() {}

  ngOnInit() {
    this.innerWidth = window.innerWidth;
  }

  search(searchString: string) {
    alert(searchString);
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.innerWidth = window.innerWidth;
  }
}
