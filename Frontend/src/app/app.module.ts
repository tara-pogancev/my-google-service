import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material-module';
import { ContactsPageComponent } from './google-contacts/contacts-page/contacts-page.component';
import { PhotosPageComponent } from './google-photos/photos-page/photos-page.component';
import { LoginComponent } from './shared/login/login.component';
import { RegisterComponent } from './shared/register/register.component';
import { ErrorPageComponent } from './shared/error-page/error-page.component';
import { ServiceSelectionComponent } from './shared/service-selection/service-selection.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ContactsHeaderComponent } from './google-contacts/contacts-header/contacts-header.component';
import { ContactsSidebarComponent } from './google-contacts/contacts-sidebar/contacts-sidebar.component';
import { ContactsSidebarItemComponent } from './google-contacts/contacts-sidebar-item/contacts-sidebar-item.component';
import { CreateContactButtonComponent } from './google-contacts/create-contact-button/create-contact-button.component';
import { ContactsPageBodyComponent } from './google-contacts/contacts-page-body/contacts-page-body.component';
import { ContactsTableItemComponent } from './google-contacts/contacts-table-item/contacts-table-item.component';
import { ContactsTableComponent } from './google-contacts/contacts-table/contacts-table.component';

@NgModule({
  declarations: [
    AppComponent,
    ContactsPageComponent,
    PhotosPageComponent,
    LoginComponent,
    RegisterComponent,
    ErrorPageComponent,
    ServiceSelectionComponent,
    ContactsHeaderComponent,
    ContactsSidebarComponent,
    ContactsSidebarItemComponent,
    CreateContactButtonComponent,
    ContactsPageBodyComponent,
    ContactsTableItemComponent,
    ContactsTableComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
