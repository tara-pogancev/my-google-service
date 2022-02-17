import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CreateUser } from '../../model/create-user';
import { AuthService } from '../../services/auth.service';
import { RegistrationService } from '../../services/registration.service';
import {
  ConfirmedValidator,
  EmailTakenValidator,
} from '../../services/validator.service';

@Component({
  selector: 'register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  animations: [
    trigger('fade1', [
      state('void', style({ opacity: 0 })),

      transition('void => *', [animate(300)]),
      transition('* => void', [animate(200)]),
    ]),

    trigger('fade2', [
      state('void', style({ opacity: 0 })),

      transition('void => *', [animate(300)]),
      transition('* => void', [animate(200)]),
    ]),
  ],
})
export class RegisterComponent implements OnInit {
  profileForm: FormGroup = new FormGroup({});
  registrationSuccess = false;

  constructor(
    private authService: AuthService,
    private registrationService: RegistrationService
  ) {}

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
      .addValidators(ConfirmedValidator('password'));
  }

  redirectLogin() {
    if (this.profileForm.touched && !this.registrationSuccess) {
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

      this.registrationService.register(user).subscribe(
        (res) => {
          this.registrationSuccess = true;
        },
        (err) => {
          window.location.href = 'error';
          console.log(err);
        }
      );
    }
  }
}
