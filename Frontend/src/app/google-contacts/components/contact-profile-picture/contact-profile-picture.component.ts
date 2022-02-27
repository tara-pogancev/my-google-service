import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ContactPictureService } from '../../services/contact-picture.service';

@Component({
  selector: 'contact-profile-picture',
  templateUrl: './contact-profile-picture.component.html',
  styleUrls: ['./contact-profile-picture.component.scss']
})
export class ContactProfilePictureComponent implements OnInit {
  @Input() size: string = '50';
  @Input() img: SafeResourceUrl =
    '../../../../assets/missing_profile_picture.png';
  @Input() id: number = 0;

  constructor(
    private contactPictureService: ContactPictureService,
    private _sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.contactPictureService
      .getContactPicture(this.id)
      .subscribe((data: Blob) => {
        if (data.size != 0)
          this.img = this._sanitizer.bypassSecurityTrustResourceUrl(
            window.URL.createObjectURL(data)
          );
      });
  }

}
