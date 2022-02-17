import { aliasTransformFactory } from '@angular/compiler-cli/src/ngtsc/transform';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActiveUser } from '../../model/active-user';
import { User } from '../../model/user-model';
import { AuthService } from '../../services/auth.service';
import { ProfilePictureService } from '../../services/profile-picture.service';
import { UserService } from '../../services/user-service';
import { ProfilePictureComponent } from '../profile-picture/profile-picture.component';

@Component({
  selector: 'edit-profile-page',
  templateUrl: './edit-profile-page.component.html',
  styleUrls: ['./edit-profile-page.component.scss'],
})
export class EditProfilePageComponent implements OnInit {
  phoneContacts: string[] = ['2'];
  canAddNewContact: boolean = true;
  user: ActiveUser = new ActiveUser();

  fileToUpload: File | any = null;
  invalidPicture: boolean = false;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private profilePictureService: ProfilePictureService,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
  }

  addContact() {
    this.phoneContacts.push('3');
    this.canAddNewContact = false;
  }

  setPicture() {
    const formData = new FormData();
    formData.append('file', this.fileToUpload, this.fileToUpload.name);
    this.profilePictureService
      .postProfilePicture(this.user.id, formData)
      .subscribe(
        (data) => {
          this._snackBar.open(
            'Your profile picture has been set. Refresh the page to see changes.',
            'Close'
          );

          this.invalidPicture = false;
          this.fileToUpload = null;
        },
        (err) => {
          this.invalidPicture = true;
        }
      );
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }
}
