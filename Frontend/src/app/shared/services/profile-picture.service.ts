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

  constructor(private http: HttpClient, private authService: AuthService) {}

  getProfilePicture(userId: number): Observable<Blob> {
    const headers = this.authService.getHeaders();
    return this.http.get(`${this.baseUrl}users/${userId}/profile-picture`, {
      responseType: 'blob',
      headers: headers,
    });
  }

  postProfilePicture(userId: number, formData: FormData) {
    const headers = this.authService.getHeaders();
    return this.http.post(
      `${this.baseUrl}users/${userId}/profile-picture`,
      formData,
      {
        headers: headers,
      }
    );
  }
}
