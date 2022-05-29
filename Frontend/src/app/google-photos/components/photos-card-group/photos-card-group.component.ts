import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
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
    this.selectionChanged.emit(this.selected)
  }

  photoSelected(photoSelectDTO: PhotoSelectDTO) {
    this.selectPhotoEvent.emit(photoSelectDTO)
  }

  executeAction(action: string) {
    if (action === "UNSELECT") {
      this.selected = false
    }
  }

}
