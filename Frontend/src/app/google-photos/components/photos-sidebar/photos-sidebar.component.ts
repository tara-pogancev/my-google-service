import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'photos-sidebar',
  templateUrl: './photos-sidebar.component.html',
  styleUrls: ['./photos-sidebar.component.scss']
})
export class PhotosSidebarComponent implements OnInit {

  tab : number = 1
  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  navigateAccount() {
    this.router.navigate(['/edit-profile/']);
  }

}
