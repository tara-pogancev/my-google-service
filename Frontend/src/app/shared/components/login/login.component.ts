import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ActiveUser } from '../../model/active-user';
import { AuthenticationRequest } from '../../model/authentication-request';
import { AuthService } from '../../services/auth.service';

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

  constructor(private authService: AuthService) {}

  ngOnInit(): void {}

  setStep(step: number) {
    this.step = step;
  }

  checkEmail() {
    if (this.authRequest.email != '') {
      this.authService
        .checkIfEmailExists(this.authRequest)
        .subscribe((data) => {
          if (data) {
            this.invalidEmail = false;
            this.setStep(2);
          }
        });
    } else {
      this.invalidEmail = true;
    }
  }

  login() {
    this.authService.login(this.authRequest).subscribe(
      (data) => {
        this.successfulLogin(data);
      },
      (err) => {
        this.invalidPassword = true;
        console.log(err);
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
