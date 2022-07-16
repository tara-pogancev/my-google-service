import { Component, OnInit } from '@angular/core';
import { MatGridTileHeaderCssMatStyler } from '@angular/material/grid-list';
import { Photo } from '../../model/Photo';
import { SelectedService } from '../../services/selected.service';

@Component({
  selector: 'selected-bar',
  templateUrl: './selected-bar.component.html',
  styleUrls: ['./selected-bar.component.scss']
})
export class SelectedBarComponent implements OnInit {

  selected!: Photo[]
  constructor(private selectedService: SelectedService) { }

  ngOnInit(): void {
    this.selectedService.currentValue.subscribe(selected => this.selected = selected)
  }

  unselect() {
    this.selectedService.changeAction("UNSELECT")
  }

  deletePhotos() {
    this.selectedService.changeAction("DELETE")
  }

  favorite() {
    this.selectedService.changeAction("FAVORITE")
  }

  unfavorite() {
    this.selectedService.changeAction("UNFAVORITE")
  }

  export() {
    this.selectedService.changeAction("EXPORT")
  }

  rotate() {
    this.selectedService.changeAction("ROTATE")
  }



  isEverySelectedFavorite() {
    return this.selected.every(photo => photo.favorite)
  }



  getFavoriteIcon() {
    if (this.isEverySelectedFavorite())
      return 'star_border'
    return 'star'
  }


}
