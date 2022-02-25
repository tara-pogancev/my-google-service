import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'contacts-sidebar',
  templateUrl: './contacts-sidebar.component.html',
  styleUrls: ['./contacts-sidebar.component.scss'],
})
export class ContactsSidebarComponent implements OnInit {
  tab: number = 1;
  route: string = '';

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.toggleSelectedMenuItem();
  }

  toggleSelectedMenuItem() {
    switch (this.router.url) {
      case '/contacts/new':
        this.tab = 0;
        break;
      case '/contacts/suggestions':
        this.tab = 2;
        break;
      case '/contacts/import':
        this.tab = 4;
        break;
      case '/contacts/export':
        this.tab = 5;
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
}
