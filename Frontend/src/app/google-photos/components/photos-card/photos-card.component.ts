import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { PhotoSelectDTO } from '../../DTO/PhotoSelectDTO';
import { Photo } from '../../model/Photo';
import { SelectedService } from '../../services/selected.service';

@Component({
  selector: 'photos-card',
  templateUrl: './photos-card.component.html',
  styleUrls: ['./photos-card.component.scss']
})
export class PhotosCardComponent implements OnInit, OnChanges {
  @Input() photo!: Photo
  @Input() selectionChanged!: EventEmitter<boolean>;
  selected: boolean = false
  @Output() selectPhotoEvent = new EventEmitter<PhotoSelectDTO>();
  hover: boolean = false
  constructor(private selectedService: SelectedService) { }

  ngOnChanges(changes: SimpleChanges): void {
    // console.log(changes)
  }


  mouseOver() {
    this.hover = true
  }
  mouseOut() {
    this.hover = false
  }
  ngOnInit(): void {
    this.selectedService.currentAction.subscribe(action => this.executeAction(action))
    this.selectionChanged.subscribe(selection => {
        if (selection !== this.selected) {
          this.selected = selection
          this.selectPhotoEvent.emit({photo: this.photo, selected: this.selected})}
      })
  }

  iconClicked() {
    this.selected = !this.selected
    this.selectPhotoEvent.emit({photo: this.photo, selected: this.selected})
  }

  getBackground() {
    if (this.selected)
      return 'rgba(232, 240, 254, 1)'
    if (this.hover)
      return  'linear-gradient(rgba(0, 0, 0, .2), rgba(0, 0, 0, .04), rgba(0, 0, 0, .01), rgba(0, 0, 0, 0))'
    return ''
  }

  executeAction(action: string) {
    if (action === "UNSELECT") {
      this.selected = false
    }
  }

}
