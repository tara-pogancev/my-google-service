import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-service-selection',
  templateUrl: './service-selection.component.html',
  styleUrls: ['./service-selection.component.scss'],
})
export class ServiceSelectionComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {
    //todo: check login status
  }

  redirectPhotos() {
    window.location.href = '/photos';
  }

  redirectContacts() {
    window.location.href = '/contacts';
  }
}
