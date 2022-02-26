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
}
