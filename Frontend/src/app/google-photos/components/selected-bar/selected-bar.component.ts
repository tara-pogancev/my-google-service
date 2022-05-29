import { Component, OnInit } from '@angular/core';
import { SelectedService } from '../../services/selected.service';

@Component({
  selector: 'selected-bar',
  templateUrl: './selected-bar.component.html',
  styleUrls: ['./selected-bar.component.scss']
})
export class SelectedBarComponent implements OnInit {

  selected!: string[]
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

  export() {
    this.selectedService.changeAction("EXPORT")
  }


}
