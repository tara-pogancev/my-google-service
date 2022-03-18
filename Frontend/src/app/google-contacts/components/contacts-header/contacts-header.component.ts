import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { ProfileRefreshService } from 'src/app/shared/components/edit-profile-page/profile-refresh.service';
import { User } from 'src/app/shared/model/user-model';
import { AuthService } from 'src/app/shared/services/auth.service';
import { UserService } from 'src/app/shared/services/user-service';
import { SearchContactsService } from './search-contacts.service';

@Component({
  selector: 'contacts-header',
  templateUrl: './contacts-header.component.html',
  styleUrls: ['./contacts-header.component.scss'],
})
export class ContactsHeaderComponent implements OnInit {
  @Output() doToggleSidebar: EventEmitter<any> = new EventEmitter();
  @Output() doSearch: EventEmitter<string> = new EventEmitter();
  user: User = new User();
  searchValue: string = '';

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private searchContactsService: SearchContactsService,
    public router: Router,
    private profileRefreshService: ProfileRefreshService
  ) {
    this.searchContactsService.doResetSearchValue$.subscribe(() => {
      this.searchValue = '';
    });

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

  search() {
    this.searchContactsService.announceSearch(this.searchValue.trim());
  }

  toggleSidebar() {
    this.doToggleSidebar.emit(null);
  }
}
