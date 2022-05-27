import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'photos-card',
  templateUrl: './photos-card.component.html',
  styleUrls: ['./photos-card.component.scss']
})
export class PhotosCardComponent implements OnInit {

  @Input() imgUrl!: string
  selected: boolean = false
  hover: boolean = false
  constructor() { }


  mouseOver() {
    this.hover = true
  }
  mouseOut() {
    this.hover = false
  }
  ngOnInit(): void {
  }

  iconClicked() {
    this.selected = !this.selected
  }

}
