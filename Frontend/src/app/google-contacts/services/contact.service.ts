import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { server } from 'src/app/app-global';
import { AuthService } from 'src/app/shared/services/auth.service';
import { Contact } from '../model/contact';

@Injectable({
  providedIn: 'root',
})
export class ContactService {
  url = server + 'contact';

  constructor(private _http: HttpClient, private authService: AuthService) {}

  createNewContact(contact: Contact) {
    const url = this.url + '/new';
    const headers = this.authService.getHeaders();
    return this._http.post<any>(url, contact, {
      headers: headers,
    });
  }

  getContact(id: number) {
    const url = this.url + '/' + id;
    const headers = this.authService.getHeaders();
    return this._http.get<any>(url, {
      headers: headers,
    });
  }
}
