import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/model/user-model';
import { AuthService } from 'src/app/shared/services/auth.service';
import { UserService } from 'src/app/shared/services/user-service';

@Component({
  selector: 'photos-header',
  templateUrl: './photos-header.component.html',
  styleUrls: ['./photos-header.component.scss']
})
export class PhotosHeaderComponent implements OnInit {
  user: User = new User();

  searchBarFocus = false
  searchValue: string = ""
  constructor(private authService: AuthService,
     private userService: UserService) { }

  ngOnInit(): void {
    this.initUser()
  }

  initUser() {
    this.user.id = this.authService.getCurrentUser().id;
    this.userService.getCurrentUser().subscribe((data) => (this.user = data));
  }

  toggleSidebar() {

  }

  refreshPage() {

  }

  search() {

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


  resetSearch() {
    this.searchValue = ""
  }

}
