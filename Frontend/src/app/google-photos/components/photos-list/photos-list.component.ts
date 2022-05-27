import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'photos-list',
  templateUrl: './photos-list.component.html',
  styleUrls: ['./photos-list.component.scss']
})
export class PhotosListComponent implements OnInit {

  constructor() { }
  imgUrls = ['https://picsum.photos/200/300', 'https://picsum.photos/200/500']
  date = new Date()

  ngOnInit(): void {
  }

}
