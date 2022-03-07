import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActiveUser } from '../../model/active-user';
import { AuthenticationRequest } from '../../model/authentication-request';
import { AuthService } from '../../services/auth.service';
import { EmailDoesntExistValidator } from '../../services/validator.service';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
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
export class LoginComponent implements OnInit {
  loginFormControll = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);
  authRequest = new AuthenticationRequest();
  invalidEmail: boolean = false;
  invalidPassword: boolean = false;
  step: number = 1;
  emailForm: FormGroup = new FormGroup({});
  passwordForm: FormGroup = new FormGroup({});

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.emailForm = new FormGroup({
      email: new FormControl(null, {
        validators: [Validators.required],
        asyncValidators: [EmailDoesntExistValidator(this.authService)],
        updateOn: 'submit',
      }),
    });

    this.passwordForm = new FormGroup({
      password: new FormControl(null, {
        validators: [Validators.required],
        updateOn: 'submit',
      }),
      showPassword: new FormControl(false),
    });
  }

  setStep(step: number) {
    this.step = step;
  }

  checkEmail() {
    if (this.emailForm.valid) {
      this.invalidEmail = false;
      this.authRequest.email = this.emailForm.controls.email.value;
      this.setStep(2);
    }
  }

  login() {
    this.authRequest.password = this.passwordForm.controls.password.value;
    this.authService.login(this.authRequest).subscribe(
      (data) => {
        this.invalidPassword = false;
        this.successfulLogin(data);
      },
      (err) => {
        this.invalidPassword = true;
      }
    );
  }

  successfulLogin(data: ActiveUser) {
    this.authService.loginSetUser(data);
  }

  goBack() {
    this.setStep(1);
  }

  redirectCreateAccount() {
    window.location.href = '/register';
  }
}
