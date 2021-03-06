import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { server } from 'src/app/app-global';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class ProfilePictureService {
  private baseUrl = server;

  constructor(private _http: HttpClient, private authService: AuthService) {}

  getProfilePicture(userId: number): Observable<Blob> {
    const headers = this.authService.getHeaders();
    return this._http.get(`${this.baseUrl}users/${userId}/profile-picture`, {
      responseType: 'blob',
      headers: headers,
    });
  }

  postProfilePicture(userId: number, formData: FormData) {
    const headers = this.authService.getHeadersMultipart();
    return this._http.post(
      `${this.baseUrl}users/${userId}/profile-picture`,
      formData,
      {
        headers: headers,
      }
    );
  }

  deleteProfilePicture() {
    const url = this.baseUrl + 'users/profile-picture';
    const headers = this.authService.getHeaders();
    return this._http.delete(url, {
      headers: headers,
    });
  }
}
