import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-photos-page',
  templateUrl: './photos-page.component.html',
  styleUrls: ['./photos-page.component.scss']
})
export class PhotosPageComponent implements OnInit {


  url: any
  constructor(private http: HttpClient, private _sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.http.get(`http://localhost:8080/users/1/profile-picture`, { responseType: "blob" }).subscribe((data:any) => {
      this.url = this._sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(data))
    })
  }

  onSelectFile(event: any) { // called each time file input changes
    if (event.target.files && event.target.files[0]) {
      let formData = new FormData();

      let file = event.target.files[0]
      formData.append('file', file)
      this.http.post(`http://localhost:8080/users/1/profile-picture`, formData).subscribe((data: any) => {
        console.log(data);

      })
    }
}

}
