import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, HostListener, OnInit, ViewChild } from '@angular/core';
import { MatDrawer } from '@angular/material/sidenav';
import { DomSanitizer } from '@angular/platform-browser';
import { ProfilePictureService } from 'src/app/shared/services/profile-picture.service';

@Component({
  selector: 'photos-page',
  templateUrl: './photos-page.component.html',
  styleUrls: ['./photos-page.component.scss']
})
export class PhotosPageComponent implements OnInit {

  innerWidth: any;
  @ViewChild(MatDrawer) sidenav!: MatDrawer;

  constructor() {}
  ngAfterViewInit(): void {
    this.setupSidenav();
  }

  ngOnInit() {
    this.innerWidth = window.innerWidth;
  }

  search(searchString: string) {
    alert(searchString);
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    var oldWidth = this.innerWidth;
    this.innerWidth = window.innerWidth;

    if (oldWidth > 768 && this.innerWidth <= 768) {
      this.setupSidenav();
    } else if (oldWidth < 768 && this.innerWidth >= 768) {
      this.setupSidenav();
    }
  }

  setupSidenav() {
    if (this.innerWidth > 768) {
      this.sidenav.mode = 'side';
      this.sidenav.opened = true;
    } else {
      this.sidenav.mode = 'over';
      this.sidenav.opened = false;
    }
  }

  toggleSidenav() {
    this.sidenav.toggle();
  }

}
