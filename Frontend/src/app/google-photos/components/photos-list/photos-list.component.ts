import { templateSourceUrl } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { forkJoin, observable } from 'rxjs';
import { groupBy } from 'rxjs/operators';
import { AuthService } from 'src/app/shared/services/auth.service';
import { PhotoDTO } from '../../DTO/PhotoDTO';
import { PhotoSelectDTO } from '../../DTO/PhotoSelectDTO';
import { SearchParametersDTO } from '../../DTO/SearchParametersDTO';
import { Photo } from '../../model/Photo';
import { PhotoService } from '../../services/photo.service';
import { SearchParametersService } from '../../services/search-parameters.service';
import { SelectedService } from '../../services/selected.service';
import { SidebarService } from '../../services/sidebar.service';
import { UploadService } from '../../services/upload.service';

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
    private searchParametersService: SearchParametersService,
    private uploadService: UploadService,
    private route: ActivatedRoute) { }
  userId!: number
  favorites: boolean = false
  photos: Photo[] = []
  photoGroups!: Photo[][]
  selected: Photo[] = []
  searchParameters!: SearchParametersDTO

  ngOnInit(): void {
    this.userId = this.authService.getCurrentUser().id
    this.selectedService.currentAction.subscribe(action => this.executeAction(action))
    this.searchParametersService.currentValue.subscribe(params => {
      this.searchParameters = params
      if (this.searchParameters.action === 'SEARCH') {
        this.searchParameters.action = ''
        this.searchParametersService.changeValue(this.searchParameters)
        this.getPhotos()
      }
    })
    this.uploadService.currentValue.subscribe(action => {
      if (action === "UPLOAD") {
        this.uploadService.changeValue('')
        this.reloadComponent()
      }
    })
    if (this.route.snapshot.url.length > 0) {
      this.favorites = true
    }
    this.getPhotos();
  }

  private getPhotos() {
    this.photos = []
    this.photoService.getAllPhotos(this.userId,
      this.favorites,
      this.searchParameters.searchValue,
      this.searchParameters.beforeDate,
      this.searchParameters.afterDate).subscribe((data: any) => {
        if (data.length == 0){
          this.photos = []
          this.updatePhotoGroups()
          return
        }
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
    if (this.photos.length === 0) {
      this.photoGroups = []
      return
    }
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
      return b.creationDate.getTime() - a.creationDate.getTime()
    }));
    this.photoGroups = [...this.photoGroups];
  }

  executeAction(action: string) {
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
        favoriteObs.push(this.photoService.favoritePhoto(photo.filename, { favorite: true }))
      }
      forkJoin(favoriteObs).subscribe(data => {
        this.selectedService.changeAction('UNSELECT')
        this.reloadComponent()
      })
    }
    else if (action === "UNFAVORITE") {
      let unfavoriteObs = []
      for (const photo of this.selected) {
        unfavoriteObs.push(this.photoService.favoritePhoto(photo.filename, { favorite: false }))
      }
      forkJoin(unfavoriteObs).subscribe(data => {
        this.selectedService.changeAction('UNSELECT')
        this.reloadComponent()
      })
    }
    else if (action === "EXPORT") {
      let selectedFileNames = this.selected.map(el => el.filename)
      this.photoService.getExport(this.userId, selectedFileNames).subscribe((data: any) => {
        const blob = new Blob([data], {
          type: 'application/zip'
        });
        const url = window.URL.createObjectURL(blob);
        window.open(url);
        this.selectedService.changeAction('UNSELECT')
      })
    }
    else if (action === "ROTATE") {
      let rotateObs = []
      for (const photo of this.selected) {
        rotateObs.push(this.photoService.rotatePhoto(photo.filename))
      }
      forkJoin(rotateObs).subscribe(data => {
        this.selectedService.changeAction('UNSELECT')
        this.reloadComponent()
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



  reloadComponent() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['./photos']);
  }

}
