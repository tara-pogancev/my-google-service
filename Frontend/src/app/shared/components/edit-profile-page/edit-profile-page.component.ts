import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthenticationRequest } from '../../model/authentication-request';
import { ChangePasswordModel } from '../../model/change-password';
import { CreateUser } from '../../model/create-user';
import { User } from '../../model/user-model';
import { UserPhoneNumber } from '../../model/user-phone-number';
import { AuthService } from '../../services/auth.service';
import { ProfilePictureService } from '../../services/profile-picture.service';
import { UserService } from '../../services/user-service';
import { ConfirmedValidator } from '../../services/validator.service';
import { DeleteAccountDialogComponent } from './delete-account-dialog/delete-account-dialog.component';
import { ProfileRefreshService } from './profile-refresh.service';

@Component({
  selector: 'edit-profile-page',
  templateUrl: './edit-profile-page.component.html',
  styleUrls: ['./edit-profile-page.component.scss'],
})
export class EditProfilePageComponent implements OnInit {
  user: User = new User();

  fileToUpload: File | any = null;
  invalidPicture: boolean = false;
  invalidPassword: boolean = false;
  userHasProfilePicture: boolean = true;

  phoneNumberMessage: string = '0 phone numbers';

  nameChangeForm: FormGroup = new FormGroup({});
  passwordChangeForm: FormGroup = new FormGroup({});
  newMobileNumberForm: FormGroup = new FormGroup({});
  defaultApplication: FormControl = new FormControl({});

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private snackBar: MatSnackBar,
    private profilePictureService: ProfilePictureService,
    private dialog: MatDialog,
    private profileRefreshService: ProfileRefreshService
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

    this.defaultApplication = new FormControl('', Validators.required);

    this.user.id = this.authService.getCurrentUser().id;
    this.userService.getCurrentUser().subscribe((data) => {
      this.user = data;
      console.log(this.user);

      this.nameChangeForm.controls.firstName.setValue(this.user.firstName);
      this.nameChangeForm.controls.lastName.setValue(this.user.lastName);

      let defaultApplicationValue = this.user.defaultApplication.toLowerCase();
      defaultApplicationValue = defaultApplicationValue.replace('_', '-');
      this.defaultApplication.setValue(defaultApplicationValue);

      this.user.phoneNumbers = this.sortPhoneNumbers(this.user.phoneNumbers);
      this.setPhoneNumberMessage();
      this.checkForProfilePicture();
    });
  }

  checkForProfilePicture() {
    this.profilePictureService
      .getProfilePicture(this.user.id)
      .subscribe((data: Blob) => {
        this.userHasProfilePicture = data.size != 0;
      });
  }

  setPhoneNumberMessage() {
    if (this.user.phoneNumbers.length == 0) {
      this.phoneNumberMessage = '0 phone numbers';
    } else if (this.user.phoneNumbers.length == 1) {
      this.phoneNumberMessage = '1 phone number';
    } else {
      this.phoneNumberMessage =
        this.user.phoneNumbers.length + ' phone numbers';
    }
  }

  setPicture() {
    const formData = new FormData();
    formData.append('file', this.fileToUpload, this.fileToUpload.name);
    this.profilePictureService
      .postProfilePicture(this.user.id, formData)
      .subscribe(
        (data) => {
          this.invalidPicture = false;
          this.fileToUpload = null;
          this.profileRefreshService.announceRefreshImage();
          this.checkForProfilePicture();

          this.snackBar.open('Your profile picture has been set.', 'Close', {
            duration: 3000,
          });
        },
        (err) => {
          this.invalidPicture = true;
        }
      );
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  removeProfilePicture() {
    if (confirm('Are you sure you want to remove your profile picture?')) {
      this.profilePictureService.deleteProfilePicture().subscribe((data) => {
        this.profileRefreshService.announceRefreshImage();
        this.checkForProfilePicture();

        this.snackBar.open('Your profile picture has been removed.', 'Close', {
          duration: 3000,
        });
      });
    }
  }

  changeName() {
    if (this.nameChangeForm.valid) {
      let dto = new CreateUser();
      dto.firstName = this.nameChangeForm.controls.firstName.value;
      dto.lastName = this.nameChangeForm.controls.lastName.value;
      this.userService.changeName(dto).subscribe((data) => {
        this.user.firstName = dto.firstName;
        this.user.lastName = dto.lastName;
        this.profileRefreshService.announceRefreshName();

        this.snackBar.open('Your name has been changed.', 'Close', {
          duration: 3000,
        });
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

  changeDefaultApplication() {
    this.userService
      .changeDefaultApplication(this.defaultApplication.value)
      .subscribe((data) => {
        this.snackBar.open(
          'Your default application has been changed.',
          'Close',
          {
            duration: 3000,
          }
        );
      });
  }

  refreshUser() {
    this.userService.getCurrentUser().subscribe((data) => {
      this.user = data;
      this.setPhoneNumberMessage();
      this.user.phoneNumbers = this.sortPhoneNumbers(this.user.phoneNumbers);
    });
  }

  changeContact() {
    this.refreshUser();
  }

  addContact() {
    const dialogRef = this.dialog.open(CreateNewUserPhoneDialog);
    let phoneNumber = UserPhoneNumber;

    dialogRef.afterClosed().subscribe((result) => {
      this.refreshUser();
    });
  }

  sortPhoneNumbers(entities: any[]) {
    return entities.sort((n1, n2) => {
      if (n1.id < n2.id) {
        return 1;
      } else {
        return -1;
      }
    });
  }

  deleteAccountDialog() {
    const dialogRef = this.dialog.open(DeleteAccountDialogComponent);

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        window.location.href = '/';
      }
    });
  }
}

@Component({
  selector: 'create-new-user-phone-dialog',
  templateUrl: './create-new-user-phone-dialog.html',
})
export class CreateNewUserPhoneDialog implements OnInit {
  mobileNumberForm: FormGroup = new FormGroup({});

  constructor(
    public dialogRef: MatDialogRef<CreateNewUserPhoneDialog>,
    @Inject(MAT_DIALOG_DATA) public data: UserPhoneNumber,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.mobileNumberForm = new FormGroup({
      phoneNumber: new FormControl(null, {
        validators: [Validators.required, Validators.pattern('[0-9-+]+')],
        updateOn: 'change',
      }),
      type: new FormControl('OTHER', {
        validators: [Validators.required],
        updateOn: 'change',
      }),
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  addContact() {
    let phoneNumber = new UserPhoneNumber();
    phoneNumber.phoneNumber = this.mobileNumberForm.controls.phoneNumber.value;
    phoneNumber.type = this.mobileNumberForm.controls.type.value;
    this.userService.addNewUserPhoneNummber(phoneNumber).subscribe((data) => {
      this.dialogRef.close();
    });
  }
}
