import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ContactListService } from '../../services/contact-list.service';
import { ExportPageComponent } from '../export-page/export-page.component';
import { ImportPageComponent } from '../import-page/import-page.component';
import { RefreshContactsCountService } from './refresh-contact-count.service';

@Component({
  selector: 'contacts-sidebar',
  templateUrl: './contacts-sidebar.component.html',
  styleUrls: ['./contacts-sidebar.component.scss'],
})
export class ContactsSidebarComponent implements OnInit {
  tab: number = 1;
  route: string = '';
  contacts: number = 0;

  constructor(
    private router: Router,
    private contactListService: ContactListService,
    private refreshContactsCountService: RefreshContactsCountService,
    private dialog: MatDialog
  ) {
    refreshContactsCountService.refreshContacts$.subscribe(() =>
      this.refreshContactsCount()
    );
  }

  ngOnInit(): void {
    this.toggleSelectedMenuItem();
    this.refreshContactsCount();
  }

  refreshContactsCount() {
    this.contactListService.getAllContacts().subscribe((data) => {
      this.contacts = data.length;
    });
  }

  toggleSelectedMenuItem() {
    switch (this.router.url) {
      case '/contacts/new':
        this.tab = 0;
        break;
      case '/contacts/suggestions':
        this.tab = 2;
        break;
      case '/contacts/trash':
        this.tab = 6;
        break;
      default:
        this.tab = 1;
        break;
    }
  }

  navigateRoute(route: string) {
    this.router.navigate(['/contacts/' + route]);
    setTimeout(() => {
      this.toggleSelectedMenuItem();
    }, 50);
  }

  navigateAccount() {
    this.router.navigate(['/edit-profile/']);
  }

  navigateImport() {
    const dialogRef = this.dialog.open(ImportPageComponent);
  }

  navigateExport() {
    const dialogRef = this.dialog.open(ExportPageComponent);
  }
}
