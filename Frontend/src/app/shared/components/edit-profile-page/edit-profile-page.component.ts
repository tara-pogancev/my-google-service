import { isDtsPath } from '@angular/compiler-cli/src/ngtsc/util/src/typescript';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthenticationRequest } from '../../model/authentication-request';
import { ChangePasswordModel } from '../../model/change-password';
import { CreateUser } from '../../model/create-user';
import { User } from '../../model/user-model';
import { AuthService } from '../../services/auth.service';
import { ProfilePictureService } from '../../services/profile-picture.service';
import { UserService } from '../../services/user-service';
import { ConfirmedValidator } from '../../services/validator.service';

@Component({
  selector: 'edit-profile-page',
  templateUrl: './edit-profile-page.component.html',
  styleUrls: ['./edit-profile-page.component.scss'],
})
export class EditProfilePageComponent implements OnInit {
  phoneContacts: string[] = ['2'];
  canAddNewContact: boolean = true;
  user: User = new User();

  fileToUpload: File | any = null;
  invalidPicture: boolean = false;
  invalidPassword: boolean = false;

  nameChangeForm: FormGroup = new FormGroup({});
  passwordChangeForm: FormGroup = new FormGroup({});
  newMobileNumberForm: FormGroup = new FormGroup({});
  mobileNumberForm: FormGroup[] = [];

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private _snackBar: MatSnackBar,
    private profilePictureService: ProfilePictureService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.nameChangeForm = new FormGroup({
      firstName: new FormControl(this.user.firstName, Validators.required),
      lastName: new FormControl(this.user.lastName, Validators.required),
    });

    this.passwordChangeForm = new FormGroup({
      oldPassword: new FormControl('', Validators.required),
      password: new FormControl('', {
        validators: [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(20),
        ],
        updateOn: 'change',
      }),
      confirmPassword: new FormControl('', {
        validators: [Validators.required],
        updateOn: 'change',
      }),
    });

    this.passwordChangeForm
      .get('confirmPassword')!
      .addValidators(ConfirmedValidator('password'));

    this.user.id = this.authService.getCurrentUser().id;
    this.userService.getCurrentUser().subscribe((data) => {
      this.user = data;

      this.nameChangeForm.controls.firstName.setValue(this.user.firstName);
      this.nameChangeForm.controls.lastName.setValue(this.user.lastName);
    });
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

  changeName() {
    if (this.nameChangeForm.valid) {
      let dto = new CreateUser();
      dto.firstName = this.nameChangeForm.controls.firstName.value;
      dto.lastName = this.nameChangeForm.controls.lastName.value;
      this.userService.changeName(dto).subscribe((data) => {
        this.user.firstName = dto.firstName;
        this.user.lastName = dto.lastName;

        this._snackBar.open(
          'Your name has been changed. Refresh the page to see changes.',
          'Close'
        );
      });
    }
  }

  changePassword() {
    if (this.passwordChangeForm.valid) {
      let dto = new ChangePasswordModel();
      dto.oldPassword = this.passwordChangeForm.controls.oldPassword.value;
      dto.password = this.passwordChangeForm.controls.password.value;

      this.authService
        .login(new AuthenticationRequest(this.user.email, dto.oldPassword))
        .subscribe(
          (data) => {
            this.userService.changePassword(dto).subscribe(
              (data) => {
                this.invalidPassword = false;
                alert(
                  'Your password has been changed. You will now be logged out.'
                );
                this.authService.logout();
              },
              (err) => {
                console.log(err);
              }
            );
          },
          (err) => {
            console.log(
              new AuthenticationRequest(this.user.email, dto.oldPassword)
            );
            this.invalidPassword = true;
          }
        );

      this.userService.changePassword(dto).subscribe(
        (data) => {},
        (err) => {
          this.invalidPassword = true;
        }
      );
    }
  }

  addContact() {
    this.phoneContacts.push('3');
    this.canAddNewContact = false;
  }
}
