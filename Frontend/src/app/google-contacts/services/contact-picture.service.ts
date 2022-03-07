import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { server } from 'src/app/app-global';
import { AuthService } from 'src/app/shared/services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class ContactPictureService {
  private baseUrl = server;

  constructor(private http: HttpClient, private authService: AuthService) {}

  getContactPicture(contactId: number): Observable<Blob> {
    const headers = this.authService.getHeaders();
    return this.http.get(
      `${this.baseUrl}contacts/${contactId}/contact-picture`,
      {
        responseType: 'blob',
        headers: headers,
      }
    );
  }

  checkIfProfilePictureIsValid(formData: FormData) {
    const headers = this.authService.getHeadersMultipart();
    const userId = this.authService.getCurrentUser().id;
    const url = server + 'contacts/contact-picture';
    return this.http.put(url, formData, {
      headers: headers,
    });
  }

  postProfilePicture(contactId: number, formData: FormData) {
    const headers = this.authService.getHeadersMultipart();
    return this.http.post(
      `${this.baseUrl}contacts/${contactId}/contact-picture`,
      formData,
      {
        headers: headers,
      }
    );
  }

  deleteContactPicture(contactId: number) {
    const url = this.baseUrl + 'contacts/' + contactId + '/contact-picture';
    const headers = this.authService.getHeaders();
    return this.http.delete(url, {
      headers: headers,
    });
  }
}
