import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { server } from 'src/app/app-global';
import { AuthService } from 'src/app/shared/services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class SuggestionsService {
  url = server + 'suggestions';

  constructor(private _http: HttpClient, private authService: AuthService) {}

  getSuggestions() {
    const headers = this.authService.getHeaders();
    return this._http.get<any>(this.url, {
      headers: headers,
    });
  }
}
