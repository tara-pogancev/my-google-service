import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Contact } from 'src/app/google-contacts/model/contact';
import { ContactListService } from 'src/app/google-contacts/services/contact-list.service';
import { ContactService } from 'src/app/google-contacts/services/contact.service';
import { SearchContactsService } from '../../contacts-header/search-contacts.service';

@Component({
  selector: 'view-person',
  templateUrl: './view-person.component.html',
  styleUrls: ['./view-person.component.scss'],
})
export class ViewPersonComponent implements OnInit {
  contact: Contact = new Contact();

  constructor(
    private searchContactsService: SearchContactsService,
    private route: ActivatedRoute,
    private contactService: ContactService,
    private contactListService: ContactListService,
    private router: Router,
    private snackbar: MatSnackBar
  ) {
    this.searchContactsService.announceSearchReset();
  }

  ngOnInit(): void {
    this.contact.id = this.route.snapshot.params['id'];
    this.contactService.getContact(this.contact.id).subscribe((data) => {
      this.contact = data;
    });
  }

  redirectBack() {
    window.location.href = '/contacts';
  }

  starContact() {
    if (this.contact != null && this.contact.starred == false) {
      this.contactListService.starContact(this.contact.id).subscribe((data) => {
        this.contact.starred = true;
      });
    } else if (this.contact != null && this.contact.starred == true) {
      this.contactListService
        .unstarContact(this.contact.id)
        .subscribe((data) => {
          this.contact.starred = false;
        });
    }
  }

  deleteContact() {
    if (confirm('Are you sure you want to delete this contact?')) {
      this.contactListService
        .deleteContact(this.contact.id)
        .subscribe((data) => {
          this.router.navigate(['/contacts']);
          this.snackbar.open('Contact deleted', 'Close', {
            duration: 3000,
          });
        });
    }
  }

  redirectEditProfile() {
    this.router.navigate(['/contacts/person/' + this.contact.id + '/edit']);
  }
}
