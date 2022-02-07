import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { server } from 'src/app/app-global';

@Injectable({
  providedIn: 'root',
})
export class ProfilePictureService {
  private baseUrl = server;

  constructor(private http: HttpClient) {}

  getProfilePicture(userId: number): Observable<Blob> {
    return this.http.get(`${this.baseUrl}users/${userId}/profile-picture`, {
      responseType: 'blob',
    });
  }

  postProfilePicture(userId: number, formData: FormData) {
    return this.http.post(
      `${this.baseUrl}users/${userId}/profile-picture`,
      formData
    );
  }
}
