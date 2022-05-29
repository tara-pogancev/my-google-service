import { templateSourceUrl } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { forkJoin } from 'rxjs';
import { groupBy } from 'rxjs/operators';
import { AuthService } from 'src/app/shared/services/auth.service';
import { PhotoDTO } from '../../DTO/PhotoDTO';
import { PhotoSelectDTO } from '../../DTO/PhotoSelectDTO';
import { Photo } from '../../model/Photo';
import { PhotoService } from '../../services/photo.service';
import { SelectedService } from '../../services/selected.service';

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
    private router: Router) { }
  userId!: number
  photos: Photo[] = []
  photoGroups!: Photo[][]
  selected: string[] = []

  ngOnInit(): void {

    this.userId = this.authService.getCurrentUser().id
    this.selectedService.currentAction.subscribe(action => this.executeAction(action))
    this.photoService.getAllPhotos(this.userId).subscribe((data: any) => {
      let photoDTOs: PhotoDTO[] = data

      for (const photoDTO of photoDTOs) {
        this.photoService.getPhotoThumbnail(photoDTO.filename).subscribe((blob: any) => {
          this.photos = [...this.photos, {
            ...photoDTO,
            creationDate: moment(photoDTO.creationDate).toDate(),
            imgUrl: this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob))
          }]
          this.updatePhotoGroups();

        }, (err: Error) => {
          console.log(err)
        })
      }


    })
  }

  private updatePhotoGroups() {
    this.photoGroups = Object.values(this.photos.reduce(function (r, a) {
      let idx = a.creationDate.toISOString().slice(0, 10);
      r[idx] = r[idx] || [];
      r[idx].push(a);
      return r;
    }, Object.create(null)));
    this.photoGroups.sort((a, b) => b[0].creationDate.getTime() - a[0].creationDate.getTime());
    this.photoGroups.forEach(el => el.sort((a, b) => b.creationDate.getTime() - a.creationDate.getTime()));
    this.photoGroups = [...this.photoGroups];
  }

  executeAction(action:string) {
    if (action === "UNSELECT") {
      this.selected = []
      this.selectedService.changeValue(this.selected)
    }
    else if (action === "DELETE") {
      let deleteObs = []
      for (const filename of this.selected) {
        deleteObs.push(this.photoService.deletePhoto(filename))
      }
      forkJoin(deleteObs).subscribe(data => {
        this.reloadComponent()
      })
    }
    else if (action === "FAVORITE") {
      let favoriteObs = []
      for (const filename of this.selected) {
        favoriteObs.push(this.photoService.favoritePhoto(filename, {favorite: true}))
      }
      forkJoin(favoriteObs).subscribe(data => {
        this.reloadComponent()
      })
    }
    else if (action === "EXPORT") {
      this.photoService.getExport(this.userId, this.selected).subscribe((data:any) => {
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
      this.selected = [...this.selected, photoSelectDTO.photo.filename]
    } else {
      this.selected = this.selected.filter(el => el !== photoSelectDTO.photo.filename)
    }
    this.selectedService.changeValue(this.selected)
  }


  reloadComponent() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['./photos']);
}

}
