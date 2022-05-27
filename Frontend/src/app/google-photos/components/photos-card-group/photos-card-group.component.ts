import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'photos-card-group',
  templateUrl: './photos-card-group.component.html',
  styleUrls: ['./photos-card-group.component.scss']
})
export class PhotosCardGroupComponent implements OnInit {


  @Input() imgUrls:string[] = []
  @Input() date!:Date
  constructor() { }

  ngOnInit(): void {
  }


  getDateFormat() : string {
    if (this.date.getFullYear() === new Date().getFullYear())
      return 'E, MMMM d'
    return 'E, MMMM d, y'
  }

}
