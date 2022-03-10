import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { server } from 'src/app/app-global';
import { AuthService } from 'src/app/shared/services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class ExportService {
  url = server + 'export';

  constructor(private _http: HttpClient, private authService: AuthService) {}

  exportAllCsv() {
    const url = this.url + '/csv/all';
    const headers = new HttpHeaders({
      'Content-Type': 'text/csv',
      Authorization: `Bearer ` + this.authService.getCurrentUser().jwt,
    });
    return this._http.get<any>(url, {
      headers: headers,
      observe: 'response',
      responseType: 'blob' as 'json',
    });
  }

  exportSelectedCsv(ids: number[]) {
    const url = this.url + '/csv';
    const headers = new HttpHeaders({
      'Content-Type': 'text/csv',
      Authorization: `Bearer ` + this.authService.getCurrentUser().jwt,
    });
    return this._http.put<any>(url, ids, {
      headers: headers,
      observe: 'response',
      responseType: 'blob' as 'json',
    });
  }
}
