import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  email: string = 'example@gmail.com';
  password: string = '';
  invalidEmail: boolean = false;
  invalidPassword: boolean = false;
  step: number = 2;

  constructor() {}

  ngOnInit(): void {}

  setStep(step: number) {
    this.step = step;
  }

  checkEmail() {
    this.setStep(2);
  }

  redirectCreateAccount() {
    window.location.href = '/register';
  }
}
