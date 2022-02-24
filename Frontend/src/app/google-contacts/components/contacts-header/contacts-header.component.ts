import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/model/user-model';
import { AuthService } from 'src/app/shared/services/auth.service';
import { UserService } from 'src/app/shared/services/user-service';

@Component({
  selector: 'contacts-header',
  templateUrl: './contacts-header.component.html',
  styleUrls: ['./contacts-header.component.scss'],
})
export class ContactsHeaderComponent implements OnInit {
  user: User = new User();
  searchValue: string = '';

  constructor(
    private userService: UserService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.user.id = this.authService.getCurrentUser().id;
    this.userService.getCurrentUser().subscribe((data) => (this.user = data));
  }

  redirectContacts() {
    window.location.href = '/contacts';
  }

  redirectPhotos() {
    window.location.href = '/photos';
  }

  redirectEditProfile() {
    window.location.href = '/edit-profile';
  }

  signOut() {
    this.authService.logout();
  }

  search() {}

  toggleSidebar() {}

  
}
