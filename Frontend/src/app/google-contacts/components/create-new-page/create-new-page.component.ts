import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Contact } from '../../model/contact';
import { SearchContactsService } from '../contacts-header/search-contacts.service';
import { UploadContactPictureDialogComponent } from './upload-contact-picture-dialog/upload-contact-picture-dialog.component';

@Component({
  selector: 'create-new-page',
  templateUrl: './create-new-page.component.html',
  styleUrls: ['./create-new-page.component.scss'],
})
export class CreateNewPageComponent implements OnInit {
  contact: Contact = new Contact();
  contactForm: FormGroup = new FormGroup({});
  fileToUpload: File | any = null;

  constructor(
    private searchContactsService: SearchContactsService,
    private dialog: MatDialog
  ) {
    this.searchContactsService.announceSearchReset();
  }

  ngOnInit(): void {
    this.contactForm = new FormGroup({
      firstName: new FormControl(null, {
        validators: [Validators.required],
        updateOn: 'change',
      }),
      lastName: new FormControl(null, {
        validators: [Validators.required],
        updateOn: 'change',
      }),
      emailListForm: new FormArray([]),
      phoneNumberListForm: new FormArray([]),
    });

    this.addNewEmailForm();
    this.addNewPhoneNumberForm();
  }

  get emailListForm() {
    return this.contactForm.controls['emailListForm'] as FormArray;
  }

  get phoneNumberListForm() {
    return this.contactForm.controls['phoneNumberListForm'] as FormArray;
  }

  getEmailListForm(): FormGroup[] {
    return this.emailListForm.controls as FormGroup[];
  }

  getPhoneNumberListForm(): FormGroup[] {
    return this.phoneNumberListForm.controls as FormGroup[];
  }

  addNewEmailForm() {
    const emailForm = new FormGroup({
      email: new FormControl(null, {
        validators: [Validators.email, Validators.maxLength(30)],
        updateOn: 'change',
      }),
      type: new FormControl('OTHER', Validators.required),
    });
    this.emailListForm.push(emailForm);
  }

  addNewPhoneNumberForm() {
    const mobileNumberForm = new FormGroup({
      phoneNumber: new FormControl(null, {
        validators: [Validators.pattern('[0-9-+]+')],
        updateOn: 'change',
      }),
      type: new FormControl('OTHER', {
        validators: [Validators.required],
        updateOn: 'change',
      }),
    });

    this.phoneNumberListForm.push(mobileNumberForm);
  }

  deleteEmail(index: number) {
    this.emailListForm.removeAt(index);
  }

  deletePhoneNumber(index: number) {
    this.phoneNumberListForm.removeAt(index);
  }

  openPictureDialog() {
    const dialogRef = this.dialog.open(UploadContactPictureDialogComponent, {
      data: this.fileToUpload,
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(result.name);
      if (result.size > 0) {
        this.fileToUpload = result;
      }
    });
  }

  removeContactPicture() {
    this.fileToUpload = null;
  }

  redirectBack() {
    if (this.contactForm.touched || this.fileToUpload != null) {
      if (confirm('Are you sure you want to leave this page?')) {
        window.location.href = '/contacts';
      }
    } else {
      window.location.href = '/contacts';
    }
  }

  submitForm() {}
}
