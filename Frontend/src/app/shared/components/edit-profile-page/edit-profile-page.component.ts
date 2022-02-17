import { Component, OnInit } from '@angular/core';
import { ActiveUser } from '../../model/active-user';
import { User } from '../../model/user-model';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user-service';

@Component({
  selector: 'edit-profile-page',
  templateUrl: './edit-profile-page.component.html',
  styleUrls: ['./edit-profile-page.component.scss'],
})
export class EditProfilePageComponent implements OnInit {
  phoneContacts: string[] = ['2'];
  canAddNewContact: boolean = true;
  user: ActiveUser = new ActiveUser();

  constructor(
    private userService: UserService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
  }

  addContact() {
    this.phoneContacts.push('3');
    this.canAddNewContact = false;
  }
}
