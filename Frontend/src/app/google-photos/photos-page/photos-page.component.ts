import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ProfilePictureService } from 'src/app/shared/services/profile-picture.service';

@Component({
  selector: 'photos-page',
  templateUrl: './photos-page.component.html',
  styleUrls: ['./photos-page.component.scss']
})
export class PhotosPageComponent implements OnInit {


  url: any
  constructor() { }
  ngOnInit(): void {

  }


}
