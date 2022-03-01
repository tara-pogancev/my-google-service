import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { server } from 'src/app/app-global';
import { AuthService } from 'src/app/shared/services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class BinService {
  url = server + 'bin';

  constructor(private _http: HttpClient, private authService: AuthService) {}

  getAllBinContacts() {
    const url = this.url + '/all';
    const headers = this.authService.getHeaders();
    return this._http.get<any>(url, {
      headers: headers,
    });
  }

  deleteBinContactForever(id: number) {
    const url = this.url + '/delete/' + id;
    const headers = this.authService.getHeaders();
    return this._http.delete<any>(url, {
      headers: headers,
    });
  }

  deleteBinContactList(idList: number[]) {
    const url = this.url + '/delete';
    const headers = this.authService.getHeaders();
    return this._http.put<any>(url, idList, {
      headers: headers,
    });
  }

  deleteAllContactsInBin() {
    const url = this.url + '/delete-bin';
    const headers = this.authService.getHeaders();
    return this._http.delete<any>(url, {
      headers: headers,
    });
  }

  recoverBinContact(id: number) {
    const url = this.url + '/recover/' + id;
    const headers = this.authService.getHeaders();
    return this._http.put<any>(url, null, {
      headers: headers,
    });
  }

  recoverBinContactList(idList: number[]) {
    const url = this.url + '/recover';
    const headers = this.authService.getHeaders();
    return this._http.put<any>(url, idList, {
      headers: headers,
    });
  }
}
