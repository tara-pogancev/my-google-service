import { Component, OnInit } from '@angular/core';
import { CreateUser } from '../../model/createUser';

@Component({
  selector: 'register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  createUser = new CreateUser();
  invalidEmail: boolean = false;
  invalidPassword: boolean = false;

  constructor() {}

  ngOnInit(): void {}

  redirectLogin() {
    window.location.href = '/login';
  }
}
