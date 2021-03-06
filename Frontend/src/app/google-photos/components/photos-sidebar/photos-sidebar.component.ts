import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/shared/services/auth.service';
import { PhotoService } from '../../services/photo.service';
import { SelectedService } from '../../services/selected.service';
import { SidebarService } from '../../services/sidebar.service';

@Component({
  selector: 'photos-sidebar',
  templateUrl: './photos-sidebar.component.html',
  styleUrls: ['./photos-sidebar.component.scss']
})
export class PhotosSidebarComponent implements OnInit {

  tab : number = 1
  currentStorage!: number
  maxStorage!: number
  storageValue!: number
  userId!: number
  progressColor!: string
  constructor(private router: Router,
     private photoService: PhotoService,
      private authService: AuthService,
      private sidebarService: SidebarService,
      private selectedService: SelectedService,
      private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.progressColor = 'primary'
    this.userId = this.authService.getCurrentUser().id
    this.photoService.getStorage(this.userId).subscribe((data:any) => {
      this.maxStorage = data.storageCapacity
      this.currentStorage = data.usedStorage
      this.updateStorageValue()
    })
  }

  navigateAccount() {
    this.router.navigate(['/edit-profile/']);
  }


  private updateStorageValue() {
    this.storageValue = (this.currentStorage / this.maxStorage) * 100
    this.progressColor = this.storageValue > 90 ? 'warn' : 'primary'
  }


  favorites() {
    this.tab = 2
    this.sidebarService.changeValue("FAVORITES")
    if (this.router.url.includes('favorites'))
      return
    this.selectedService.changeAction('UNSELECT')
    this.router.navigate(['/photos/favorites/']);
  }

  photos() {
    this.tab = 1
    this.sidebarService.changeValue("")
    if (!this.router.url.includes('favorites'))
      return
    this.selectedService.changeAction('UNSELECT')
    this.router.navigate(['/photos/']);
  }



}
