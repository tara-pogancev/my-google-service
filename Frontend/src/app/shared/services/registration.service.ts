import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { server } from 'src/app/app-global';
import { CreateUser } from '../model/create-user';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class RegistrationService {
  url = server + 'register';

  constructor(private _http: HttpClient, private authService: AuthService) {}

  register(newUser: CreateUser) {
    const headers = this.authService.getHeaders();
    return this._http.post<any>(this.url, newUser, { headers: headers });
  }
}
