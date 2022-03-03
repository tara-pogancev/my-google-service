import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { server } from 'src/app/app-global';
import { AuthService } from 'src/app/shared/services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class ContactListService {
  url = server + 'contacts';

  constructor(private _http: HttpClient, private authService: AuthService) {}

  getAllContacts() {
    const url = this.url + '/all';
    const headers = this.authService.getHeaders();
    return this._http.get<any>(url, {
      headers: headers,
    });
  }

  starContact(id: number) {
    const url = this.url + '/star/' + id;
    const headers = this.authService.getHeaders();
    return this._http.put<any>(url, null, {
      headers: headers,
    });
  }

  unstarContact(id: number) {
    const url = this.url + '/unstar/' + id;
    const headers = this.authService.getHeaders();
    return this._http.put<any>(url, null, {
      headers: headers,
    });
  }

  deleteContact(id: number) {
    const url = this.url + '/delete/' + id;
    const headers = this.authService.getHeaders();
    return this._http.delete<any>(url, {
      headers: headers,
    });
  }

  deleteContactList(idList: number[]) {
    const url = this.url + '/delete';
    const headers = this.authService.getHeaders();
    return this._http.put<any>(url, idList, {
      headers: headers,
    });
  }
}
