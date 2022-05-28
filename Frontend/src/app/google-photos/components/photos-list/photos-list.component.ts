import { templateSourceUrl } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import * as moment from 'moment';
import { groupBy } from 'rxjs/operators';
import { AuthService } from 'src/app/shared/services/auth.service';
import { PhotoDTO } from '../../DTO/PhotoDTO';
import { Photo } from '../../model/Photo';
import { PhotoService } from '../../services/photo.service';

@Component({
  selector: 'photos-list',
  templateUrl: './photos-list.component.html',
  styleUrls: ['./photos-list.component.scss']
})
export class PhotosListComponent implements OnInit {

  constructor(private photoService: PhotoService, private authService: AuthService, private sanitizer: DomSanitizer) { }
  userId!: number
  photos: Photo[] = []
  photoGroups!: Photo[][]

  ngOnInit(): void {
    this.userId = this.authService.getCurrentUser().id
    this.photoService.getAllPhotos(this.userId).subscribe((data: any) => {
      let photoDTOs: PhotoDTO[] = data

      for (const photoDTO of photoDTOs) {
        this.photoService.getPhotoThumbnail(photoDTO.filename).subscribe((blob: any) => {
          this.photos = [...this.photos, {
            ...photoDTO,
            creationDate: moment(photoDTO.creationDate).toDate(),
            imgUrl: this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob))
          }]

          this.photoGroups = Object.values(this.photos.reduce(function (r, a) {
            let idx = a.creationDate.toISOString().slice(0, 10)
            r[idx] = r[idx] || [];
            r[idx].push(a);
            return r;
          }, Object.create(null)))
          this.photoGroups.sort((a,b) => b[0].creationDate.getTime() - a[0].creationDate.getTime())
          this.photoGroups.forEach(el => el.sort((a,b) => b.creationDate.getTime() - a.creationDate.getTime()))
          this.photoGroups = [... this.photoGroups]

        }, (err: Error) => {
          console.log(err)
        })
      }


    })
  }


  getImgUrlsFromPhotoGroup(photoGroup: any) {
    return photoGroup.map((el: any) => el.imgUrl)
  }

}
