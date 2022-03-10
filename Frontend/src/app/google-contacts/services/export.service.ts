import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { server } from 'src/app/app-global';
import { AuthService } from 'src/app/shared/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ExportService {
  url = server + 'export';

  constructor(private _http: HttpClient, private authService: AuthService) { }
}
