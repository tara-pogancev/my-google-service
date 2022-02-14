import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { server } from 'src/app/app-global';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  url = server + 'users';

  constructor(private _http: HttpClient, private authService: AuthService) {}

  getUser(id: number) {
    const url = this.url + '/' + id;
    const headers = this.authService.getHeaders();
    return this._http.get<any>(url, {
      headers: headers,
    });
  }

  getCurrentUser() {
    const currentUser = this.authService.getCurrentUser().id;
    const url = this.url + '/' + currentUser;
    const headers = this.authService.getHeaders();
    return this._http.get<any>(url, {
      headers: headers,
    });
  }
}
