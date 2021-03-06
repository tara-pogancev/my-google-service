import { Component, OnInit } from '@angular/core';
import { User } from '../../model/user-model';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user-service';
import { ProfileRefreshService } from '../edit-profile-page/profile-refresh.service';

@Component({
  selector: 'header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  user: User = new User();

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private profileRefreshService: ProfileRefreshService
  ) {
    this.profileRefreshService.doRefreshName$.subscribe(() => {
      this.initUser();
    });
  }

  ngOnInit(): void {
    this.initUser();
  }

  initUser() {
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
}
