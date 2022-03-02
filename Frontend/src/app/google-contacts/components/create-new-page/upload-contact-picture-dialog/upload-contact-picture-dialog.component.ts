import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ContactPictureService } from 'src/app/google-contacts/services/contact-picture.service';

@Component({
  selector: 'upload-contact-picture-dialog',
  templateUrl: './upload-contact-picture-dialog.component.html',
  styleUrls: ['./upload-contact-picture-dialog.component.scss'],
})
export class UploadContactPictureDialogComponent implements OnInit {
  fileToUpload: File | any = null;
  invalidPicture: boolean = false;

  constructor(
    private contactPictureService: ContactPictureService,
    public dialogRef: MatDialogRef<UploadContactPictureDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: File | any = null
  ) {}

  ngOnInit(): void {}

  setPicture() {
    const formData = new FormData();
    formData.append('file', this.fileToUpload, this.fileToUpload.name);
    this.contactPictureService.checkIfProfilePictureIsValid(formData).subscribe(
      (data) => {
        this.invalidPicture = false;
        this.dialogRef.close(this.fileToUpload);
      },
      (err) => {
        this.invalidPicture = true;
      }
    );
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
