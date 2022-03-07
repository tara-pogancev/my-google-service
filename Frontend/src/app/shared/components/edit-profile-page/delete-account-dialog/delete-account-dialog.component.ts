import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { AuthenticationRequest } from 'src/app/shared/model/authentication-request';
import { AuthService } from 'src/app/shared/services/auth.service';
import { UserService } from 'src/app/shared/services/user-service';

@Component({
  selector: 'delete-account-dialog',
  templateUrl: './delete-account-dialog.component.html',
  styleUrls: ['./delete-account-dialog.component.scss'],
})
export class DeleteAccountDialogComponent implements OnInit {
  passwordForm: FormGroup = new FormGroup({});
  authRequest = new AuthenticationRequest();
  invalidPassword: boolean = false;

  constructor(
    public dialogRef: MatDialogRef<DeleteAccountDialogComponent>,
    private authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.passwordForm = new FormGroup({
      password: new FormControl(null, {
        validators: [Validators.required],
        updateOn: 'submit',
      }),
    });

    this.authRequest.email = this.authService.getCurrentUser().email;
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  deleteAccount() {
    this.authRequest.password = this.passwordForm.controls.password.value;
    this.authService.login(this.authRequest).subscribe(
      (data) => {
        this.invalidPassword = false;
        this.userService.deleteUserAccount().subscribe(
          (data) => {
            this.authService.logout();
          },
          (err) => {
            window.location.href = '/error';
          }
        );
      },
      (err) => {
        this.invalidPassword = true;
        console.log(err);
      }
    );
  }
}
