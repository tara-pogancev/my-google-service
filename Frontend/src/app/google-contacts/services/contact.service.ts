import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { server } from 'src/app/app-global';
import { AuthService } from 'src/app/shared/services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class ContactService {
  url = server + 'contacts';

  constructor(private _http: HttpClient, private authService: AuthService) {}

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
}
