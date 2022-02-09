import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CreateContactButtonComponent } from 'src/app/google-contacts/components/create-contact-button/create-contact-button.component';
import { CreateUser } from '../../model/create-user';
import { AuthService } from '../../services/auth.service';
import {
  ConfirmedValidator,
  EmailTakenValidator,
} from '../../services/validator.service';

@Component({
  selector: 'register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  profileForm: FormGroup = new FormGroup({});

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.profileForm = new FormGroup({
      firstName: new FormControl(null, Validators.required),
      lastName: new FormControl(null, Validators.required),
      email: new FormControl(null, {
        validators: [
          Validators.required,
          Validators.email,
          Validators.maxLength(30),
        ],
        asyncValidators: [EmailTakenValidator(this.authService)],
        updateOn: 'blur',
      }),
      password: new FormControl(null, [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(20),
      ]),
      confirmPassword: new FormControl(null, [Validators.required]),
    });

    this.profileForm
      .get('confirmPassword')!
      .setValidators(ConfirmedValidator('password'));
  }

  redirectLogin() {
    if (this.profileForm.touched) {
      if (confirm('Are you sure you want to leave this page?')) {
        window.location.href = '/login';
      }
    } else {
      window.location.href = '/login';
    }
  }

  submitForm() {
    if (this.profileForm.valid) {
      let user = new CreateUser(
        this.profileForm.controls.firstName.value,
        this.profileForm.controls.lastName.value,
        this.profileForm.controls.email.value,
        this.profileForm.controls.password.value
      );

      console.log(user);
    }
  }
}
