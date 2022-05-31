import { templateSourceUrl } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { forkJoin, observable } from 'rxjs';
import { groupBy } from 'rxjs/operators';
import { AuthService } from 'src/app/shared/services/auth.service';
import { PhotoDTO } from '../../DTO/PhotoDTO';
import { PhotoSelectDTO } from '../../DTO/PhotoSelectDTO';
import { Photo } from '../../model/Photo';
import { PhotoService } from '../../services/photo.service';
import { SelectedService } from '../../services/selected.service';
import { SidebarService } from '../../services/sidebar.service';

@Component({
  selector: 'photos-list',
  templateUrl: './photos-list.component.html',
  styleUrls: ['./photos-list.component.scss']
})
export class PhotosListComponent implements OnInit {

  constructor(private photoService: PhotoService,
    private authService: AuthService,
    private sanitizer: DomSanitizer,
    private selectedService: SelectedService,
    private router: Router,
    private sidebarService: SidebarService) { }
  userId!: number
  favorites: boolean = false
  photos: Photo[] = []
  photoGroups!: Photo[][]
  selected: Photo[] = []

  ngOnInit(): void {

    this.userId = this.authService.getCurrentUser().id
    this.selectedService.currentAction.subscribe(action => this.executeAction(action))
    this.sidebarService.currentValue.subscribe(value => {
      this.favorites = value === 'FAVORITES' ? true : false
      this.showPhotos()
    })


    this.getPhotos();
  }

  private getPhotos() {
    this.photos = []
    this.photoService.getAllPhotos(this.userId, this.favorites).subscribe((data: any) => {
      for (const photoDTO of data) {
        this.photoService.getPhotoThumbnail(photoDTO.filename).subscribe((blob: any) => {
          this.photos = [...this.photos, {
            ...photoDTO,
            creationDate: moment(photoDTO.creationDate).toDate(),
            imgUrl: this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob))
          }];
          this.updatePhotoGroups();

        }, (err: Error) => {
          console.log(err);
        });
      }
    });
  }

  private updatePhotoGroups() {
    this.photoGroups = Object.values(this.photos.reduce(function (r, a) {
      let idx = a.creationDate.toISOString().slice(0, 10);
      r[idx] = r[idx] || [];
      r[idx].push(a);
      return r;
    }, Object.create(null)));
    this.photoGroups.sort((a, b) => b[0].creationDate.getTime() - a[0].creationDate.getTime());
    this.photoGroups.forEach(el => el.sort((a, b) => {
       if (a.creationDate.getTime() === b.creationDate.getTime())
       return ('' + b.filename).localeCompare(a.filename);
      return  b.creationDate.getTime() - a.creationDate.getTime()
      }));
    this.photoGroups = [...this.photoGroups];
  }

  executeAction(action:string) {
    if (action === "UNSELECT") {
      this.selected = []
      this.selectedService.changeValue(this.selected)
    }
    else if (action === "DELETE") {
      let deleteObs = []
      for (const photo of this.selected) {
        deleteObs.push(this.photoService.deletePhoto(photo.filename))
      }
      forkJoin(deleteObs).subscribe(data => {
        this.reloadComponent()
      })
    }
    else if (action === "FAVORITE") {
      let favoriteObs = []
      for (const photo of this.selected) {
        favoriteObs.push(this.photoService.favoritePhoto(photo.filename, {favorite: true}))
      }
      forkJoin(favoriteObs).subscribe(data => {
        this.reloadComponent()
      })
    }
    else if (action === "UNFAVORITE") {
      let unfavoriteObs = []
      for (const photo of this.selected) {
        unfavoriteObs.push(this.photoService.favoritePhoto(photo.filename, {favorite: false}))
      }
      forkJoin(unfavoriteObs).subscribe(data => {
        this.reloadComponent()
      })
    }
    else if (action === "EXPORT") {
      let selectedFileNames = this.selected.map(el => el.filename)
      this.photoService.getExport(this.userId, selectedFileNames).subscribe((data:any) => {
        const blob = new Blob([data], {
          type: 'application/zip'
        });
        const url = window.URL.createObjectURL(blob);
        window.open(url);
        this.selectedService.changeAction('UNSELECT')
      })
    }
  }

  photoSelected(photoSelectDTO: PhotoSelectDTO) {
    if (photoSelectDTO.selected) {
      this.selected = [...this.selected, photoSelectDTO.photo]
    } else {
      this.selected = this.selected.filter(el => el.filename !== photoSelectDTO.photo.filename)
    }
    this.selectedService.changeValue(this.selected)
  }

  showPhotos() {
    this.getPhotos()
  }




  reloadComponent() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['./photos']);
}

}
