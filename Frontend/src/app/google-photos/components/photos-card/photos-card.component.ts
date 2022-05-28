import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'photos-card',
  templateUrl: './photos-card.component.html',
  styleUrls: ['./photos-card.component.scss']
})
export class PhotosCardComponent implements OnInit {

  @Input() imgUrl!: any
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

  getBackground() {
    if (this.selected)
      return 'rgba(232, 240, 254, 1)'
    if (this.hover)
      return  'linear-gradient(rgba(0, 0, 0, .2), rgba(0, 0, 0, .04), rgba(0, 0, 0, .01), rgba(0, 0, 0, 0))'
    return ''
  }

}
