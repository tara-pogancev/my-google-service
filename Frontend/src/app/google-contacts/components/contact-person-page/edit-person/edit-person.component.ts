import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Contact } from 'src/app/google-contacts/model/contact';
import { ContactEmail } from 'src/app/google-contacts/model/contact-email';
import { ContactPhoneNumber } from 'src/app/google-contacts/model/contact-phone-number';
import { ContactPictureService } from 'src/app/google-contacts/services/contact-picture.service';
import { ContactService } from 'src/app/google-contacts/services/contact.service';
import { SearchContactsService } from '../../contacts-header/search-contacts.service';
import { UploadContactPictureDialogComponent } from '../../create-new-page/upload-contact-picture-dialog/upload-contact-picture-dialog.component';

@Component({
  selector: 'edit-person',
  templateUrl: './edit-person.component.html',
  styleUrls: ['./edit-person.component.scss'],
})
export class EditPersonComponent implements OnInit {
  contact: Contact = new Contact();
  contactForm: FormGroup = new FormGroup({});
  fileToUpload: File | any = null;
  hasProfilePicture: boolean = false;

  constructor(
    private searchContactsService: SearchContactsService,
    private dialog: MatDialog,
    private contactService: ContactService,
    private contactPictureService: ContactPictureService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.searchContactsService.announceSearchReset();
  }

  ngOnInit(): void {
    this.contactForm = new FormGroup({
      firstName: new FormControl(this.contact.lastName, {
        validators: [Validators.required],
        updateOn: 'change',
      }),
      lastName: new FormControl(this.contact.lastName, {
        validators: [Validators.required],
        updateOn: 'change',
      }),
      emailListForm: new FormArray([]),
      phoneNumberListForm: new FormArray([]),
    });

    this.contact.id = this.route.snapshot.params['id'];
    this.contactService.getContact(this.contact.id).subscribe((data) => {
      this.contact = data;

      this.contactForm.controls.firstName.setValue(this.contact.firstName);
      this.contactForm.controls.lastName.setValue(this.contact.lastName);

      for (let email of this.contact.emails) {
        const emailForm = new FormGroup({
          email: new FormControl(email.email, {
            validators: [Validators.email, Validators.maxLength(30)],
            updateOn: 'change',
          }),
          type: new FormControl(email.type),
        });
        this.emailListForm.push(emailForm);
      }

      if (this.contact.emails.length == 0) {
        this.addNewEmailForm();
      }

      for (let phoneNumber of this.contact.phoneNumbers) {
        const mobileNumberForm = new FormGroup({
          phoneNumber: new FormControl(phoneNumber.phoneNumber, {
            validators: [Validators.pattern('[0-9-+]+')],
            updateOn: 'change',
          }),
          type: new FormControl(phoneNumber.type),
        });

        this.phoneNumberListForm.push(mobileNumberForm);
      }

      if (this.contact.phoneNumbers.length == 0) {
        this.addNewPhoneNumberForm();
      }
    });

    this.contactPictureService
      .getContactPicture(this.contact.id)
      .subscribe((data) => {
        if (data.size != 0) this.hasProfilePicture = true;
      });
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
  }

  deletePhoneNumber(index: number) {
    this.phoneNumberListForm.removeAt(index);
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
    this.hasProfilePicture = false;
  }

  redirectBack() {
    if (
      confirm(
        'Are you sure you want to leave this page? Your changes will not be saved.'
      )
    ) {
      this.router.navigate(['/contacts/person/' + this.contact.id]);
    }
  }

  submitForm() {
    if (this.contactForm.valid == true) {
      this.contact.firstName = this.contactForm.controls.firstName.value;
      this.contact.lastName = this.contactForm.controls.lastName.value;
      this.contact.emails = [];
      this.contact.phoneNumbers = [];

      for (let emailForm of this.getEmailListForm()) {
        if (emailForm.valid && emailForm.controls.email.value.trim() != '') {
          let email = new ContactEmail();
          email.email = emailForm.controls.email.value.trim();
          email.type = emailForm.controls.type.value;
          this.contact.emails.push(email);
        }
      }

      for (let phoneNumberForm of this.getPhoneNumberListForm()) {
        if (
          phoneNumberForm.valid &&
          phoneNumberForm.controls.phoneNumber.value != ''
        ) {
          let phoneNumber = new ContactPhoneNumber();
          phoneNumber.phoneNumber =
            phoneNumberForm.controls.phoneNumber.value.trim();
          phoneNumber.type = phoneNumberForm.controls.type.value;
          this.contact.phoneNumbers.push(phoneNumber);
        }
      }

      this.contactService.editContact(this.contact).subscribe(
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
          } else if (!this.hasProfilePicture) {
            this.contactPictureService
              .deleteContactPicture(this.contact.id)
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
