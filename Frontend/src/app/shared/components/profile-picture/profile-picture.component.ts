import { Component, Input, OnInit, Sanitizer } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ProfilePictureService } from '../../services/profile-picture.service';
import { ProfileRefreshService } from '../edit-profile-page/profile-refresh.service';

@Component({
  selector: 'profile-picture',
  templateUrl: './profile-picture.component.html',
  styleUrls: ['./profile-picture.component.scss'],
})
export class ProfilePictureComponent implements OnInit {
  @Input() size: string = '50';
  @Input() img: SafeResourceUrl =
    '../../../../assets/missing_profile_picture.png';
  @Input() id: number = 0;

  constructor(
    private profilePictureService: ProfilePictureService,
    private _sanitizer: DomSanitizer,
    private profileRefreshService: ProfileRefreshService
  ) {
    this.profileRefreshService.doRefreshImage$.subscribe(() => {
      this.ngOnInit();
    });
  }

  ngOnInit(): void {
    this.loadProfilePicture();
  }

  loadProfilePicture() {
    this.profilePictureService
      .getProfilePicture(this.id)
      .subscribe((data: Blob) => {
        if (data.size != 0) {
          this.img = this._sanitizer.bypassSecurityTrustResourceUrl(
            window.URL.createObjectURL(data)
          );
        } else {
          this.img = '../../../../assets/missing_profile_picture.png';
        }
      });
  }
}
