import { Component, Input, OnInit, Sanitizer } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ProfilePictureService } from '../../services/profile-picture.service';

@Component({
  selector: 'profile-picture',
  templateUrl: './profile-picture.component.html',
  styleUrls: ['./profile-picture.component.scss'],
})
export class ProfilePictureComponent implements OnInit {
  @Input() size: string = '50';
  @Input() img: SafeResourceUrl = '../../../../assets/missing_profile_picture.png';
  @Input() id: number = 0;

  constructor(private profilePictureService: ProfilePictureService, private _sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.profilePictureService
      .getProfilePicture(this.id)
      .subscribe((data: Blob) => {
        this.img = this._sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(data));
      });
  }
}
