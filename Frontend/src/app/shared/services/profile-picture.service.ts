import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProfilePictureService {

  private baseUrl = environment.backend_api;

  constructor(private http: HttpClient) { }

  getProfilePicture(userId: number) : Observable<Blob> {
    return this.http.get(`${this.baseUrl}users/${userId}/profile-picture`, { responseType: "blob" })
  }

  postProfilePicture(userId: number, formData: FormData) {
    return this.http.post(`${this.baseUrl}users/${userId}/profile-picture`, formData)
  }

}
