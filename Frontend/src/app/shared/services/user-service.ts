import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { server } from 'src/app/app-global';
import { ChangePasswordModel } from '../model/change-password';
import { CreateUser } from '../model/create-user';
import { UserPhoneNumber } from '../model/user-phone-number';
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

  changeName(dto: CreateUser) {
    const url = this.url + '/change-name';
    const headers = this.authService.getHeaders();
    return this._http.put<any>(url, dto, {
      headers: headers,
    });
  }

  changePassword(dto: ChangePasswordModel) {
    const url = this.url + '/change-password';
    const headers = this.authService.getHeaders();
    return this._http.put<any>(url, dto, {
      headers: headers,
    });
  }

  changeDefaultApplication(app: String) {
    const url = this.url + '/set-default-application/' + app;
    const headers = this.authService.getHeaders();
    return this._http.put<any>(url, null, {
      headers: headers,
    });
  }

  changeUserPhoneNumber(dto: UserPhoneNumber) {
    const url = this.url + '/change-phone-number';
    const headers = this.authService.getHeaders();
    return this._http.put<any>(url, dto, {
      headers: headers,
    });
  }

  addNewUserPhoneNummber(dto: UserPhoneNumber) {
    const url = this.url + '/add-phone-number';
    const headers = this.authService.getHeaders();
    return this._http.post<any>(url, dto, {
      headers: headers,
    });
  }

  deleteUserPhoneNumber(dto: UserPhoneNumber) {
    const url = this.url + '/delete-phone-number';
    const headers = this.authService.getHeaders();
    return this._http.put<any>(url, dto, {
      headers: headers,
    });
  }

  deleteUserAccount() {
    const url = this.url + '/delete-account';
    const headers = this.authService.getHeaders();
    return this._http.delete<any>(url, {
      headers: headers,
    });
  }
}
