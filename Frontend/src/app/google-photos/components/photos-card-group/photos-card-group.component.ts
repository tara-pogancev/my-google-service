import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatGridTileHeaderCssMatStyler } from '@angular/material/grid-list';
import { forEachChild } from 'typescript';
import { PhotoSelectDTO } from '../../DTO/PhotoSelectDTO';
import { Photo } from '../../model/Photo';
import { SelectedService } from '../../services/selected.service';

@Component({
  selector: 'photos-card-group',
  templateUrl: './photos-card-group.component.html',
  styleUrls: ['./photos-card-group.component.scss']
})
export class PhotosCardGroupComponent implements OnInit {


  @Input() photos!: Photo[]
  @Input() date!:Date
  @Output() selectPhotoEvent = new EventEmitter<PhotoSelectDTO>();

  selectionChanged: EventEmitter<boolean> = new EventEmitter();
  @Input() selected: boolean = false
  selectedChildren: Photo[] = []
  hover: boolean = false
  constructor(private selectedService : SelectedService) { }

  ngOnInit(): void {
    this.selectedService.currentAction.subscribe(action => this.executeAction(action))
  }


  getDateFormat() : string {
    if (this.date.getFullYear() === new Date().getFullYear())
      return 'E, MMMM d'
    return 'E, MMMM d, y'
  }

  mouseOver() {
    this.hover = true
  }
  mouseOut() {
    this.hover = false
  }
  iconClicked() {
    this.selected = !this.selected
    if (this.selected)
      this.selectedChildren = [...this.photos]
    else
      this.selectedChildren = []
    this.selectionChanged.emit(this.selected)
  }

  photoSelected(photoSelectDTO: PhotoSelectDTO) {
    if (photoSelectDTO.selected)
      this.selectedChildren = [...this.selectedChildren, photoSelectDTO.photo]
    else
      this.selectedChildren = this.selectedChildren.filter(el => el.id !== photoSelectDTO.photo.id)
    if (this.selectedChildren.length === this.photos.length)
      this.selected = true
    if (this.selectedChildren.length === 0)
      this.selected = false
    this.selectPhotoEvent.emit(photoSelectDTO)
  }

  executeAction(action: string) {
    if (action === "UNSELECT") {
      this.selected = false
    }
  }

}
