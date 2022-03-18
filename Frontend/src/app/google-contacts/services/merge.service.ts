import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { server } from 'src/app/app-global';
import { AuthService } from 'src/app/shared/services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class MergeService {
  url = server + 'merge';

  constructor(private _http: HttpClient, private authService: AuthService) {}

  mergeContacts(ids: number[]) {
    const headers = this.authService.getHeaders();
    return this._http.put<any>(this.url, ids, {
      headers: headers,
    });
  }
}
