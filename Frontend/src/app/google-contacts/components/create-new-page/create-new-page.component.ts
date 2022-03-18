import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Contact } from '../../model/contact';
import { ContactEmail } from '../../model/contact-email';
import { ContactPhoneNumber } from '../../model/contact-phone-number';
import { ContactPictureService } from '../../services/contact-picture.service';
import { ContactService } from '../../services/contact.service';
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
    private dialog: MatDialog,
    private contactService: ContactService,
    private contactPictureService: ContactPictureService
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
      email: new FormControl('', {
        validators: [Validators.email, Validators.maxLength(30)],
        updateOn: 'change',
      }),
      type: new FormControl('OTHER'),
    });
    this.emailListForm.push(emailForm);
  }

  addNewPhoneNumberForm() {
    const mobileNumberForm = new FormGroup({
      phoneNumber: new FormControl('', {
        validators: [Validators.pattern('[0-9-+]+')],
        updateOn: 'change',
      }),
      type: new FormControl('OTHER'),
    });

    this.phoneNumberListForm.push(mobileNumberForm);
  }

  deleteEmail(index: number) {
    this.emailListForm.removeAt(index);
    if (index == 0 && this.getEmailListForm().length == 0) {
      this.addNewEmailForm();
    }
  }

  deletePhoneNumber(index: number) {
    this.phoneNumberListForm.removeAt(index);
    if (index == 0 && this.getPhoneNumberListForm().length == 0) {
      this.addNewPhoneNumberForm();
    }
  }

  openPictureDialog() {
    const dialogRef = this.dialog.open(UploadContactPictureDialogComponent, {
      data: this.fileToUpload,
    });

    dialogRef.afterClosed().subscribe((result) => {
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

  submitForm() {
    if (this.contactForm.valid == true) {
      this.contact = new Contact();
      this.contact.firstName = this.contactForm.controls.firstName.value;
      this.contact.lastName = this.contactForm.controls.lastName.value;

      for (let emailForm of this.getEmailListForm()) {
        if (
          emailForm.valid &&
          emailForm.touched &&
          emailForm.controls.email.value.trim() != ''
        ) {
          let email = new ContactEmail();
          email.email = emailForm.controls.email.value.trim();
          email.type = emailForm.controls.type.value;
          this.contact.emails.push(email);
        }
      }

      if (this.getPhoneNumberListForm().length > 0) {
        for (let phoneNumberForm of this.getPhoneNumberListForm()) {
          if (
            phoneNumberForm.valid &&
            phoneNumberForm.touched &&
            phoneNumberForm.controls.phoneNumber.value != ''
          ) {
            let phoneNumber = new ContactPhoneNumber();
            phoneNumber.phoneNumber =
              phoneNumberForm.controls.phoneNumber.value.trim();
            phoneNumber.type = phoneNumberForm.controls.type.value;
            this.contact.phoneNumbers.push(phoneNumber);
          }
        }
      }

      this.contactService.createNewContact(this.contact).subscribe(
        (data) => {
          let newContactId = data.id;
          if (this.fileToUpload != null) {
            const formData = new FormData();
            formData.append('file', this.fileToUpload, this.fileToUpload.name);
            this.contactPictureService
              .postProfilePicture(data.id, formData)
              .subscribe(
                (data) => {
                  window.location.href = '/contacts/person/' + newContactId;
                },
                (err) => {
                  window.location.href = '/error';
                }
              );
          } else {
            window.location.href = '/contacts/person/' + newContactId;
          }
        },
        (err) => {
          window.location.href = '/error';
        }
      );
    }
  }
}
