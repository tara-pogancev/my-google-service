import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'edit-profile-page',
  templateUrl: './edit-profile-page.component.html',
  styleUrls: ['./edit-profile-page.component.scss'],
})
export class EditProfilePageComponent implements OnInit {
  phoneContacts: string[] = ['2'];
  canAddNewContact: boolean = true;

  constructor() {}

  ngOnInit(): void {}

  addContact() {
    this.phoneContacts.push('3');
    this.canAddNewContact = false;
  }
}
