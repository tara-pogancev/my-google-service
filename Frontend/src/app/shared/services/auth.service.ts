import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { server } from 'src/app/app-global';
import { ActiveUser } from '../model/active-user';
import { AuthenticationRequest } from '../model/authentication-request';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  url = server + 'login';
  user = new ActiveUser();

  constructor(private _http: HttpClient, private route: Router) {}

  login(request: AuthenticationRequest) {
    return this._http.post<any>(this.url, request);
  }

  loginSetUser(activeUser: ActiveUser) {
    this.user = activeUser;
    localStorage.setItem('currentUser', JSON.stringify(this.user));
    this.redirectUserToDefaultApp(this.user.defaultApplication);
  }

  logout() {
    this.user = new ActiveUser();
    localStorage.setItem('currentUser', JSON.stringify(this.user));
    window.location.href = '/login';
  }

  getCurrentUser(): ActiveUser {
    return JSON.parse(localStorage.getItem('currentUser')!);
  }

  isUserLoggedIn() {
    if (this.getCurrentUser() != null) {
      return this.getCurrentUser().jwt != '';
    } else return false;
  }

  getHeaders() {
    const jwt = this.getCurrentUser().jwt;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ` + jwt,
    });
    return headers;
  }

  getHeadersMultipart() {
    const jwt = this.getCurrentUser().jwt;
    const headers = new HttpHeaders({
      Authorization: `Bearer ` + jwt,
    });
    return headers;
  }

  checkIfEmailExists(request: AuthenticationRequest): Observable<boolean> {
    return this._http.post<boolean>(this.url + '/email-exists', request);
  }

  redirectUserToDefaultApp(deffaultApp: string) {
    if (deffaultApp == 'GOOGLE_CONTACTS') {
      window.location.href = '/contacts';
    } else if (deffaultApp == 'GOOGLE_PHOTOS') {
      window.location.href = '/photos';
    } else {
      window.location.href = '/';
    }
  }
}
