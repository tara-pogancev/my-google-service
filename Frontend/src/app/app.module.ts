import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContactsHeaderComponent } from './google-contacts/components/contacts-header/contacts-header.component';
import { ContactsPageBodyComponent } from './google-contacts/components/contacts-page-body/contacts-page-body.component';
import { ContactsPageComponent } from './google-contacts/components/contacts-page/contacts-page.component';
import { ContactsSidebarItemComponent } from './google-contacts/components/contacts-sidebar-item/contacts-sidebar-item.component';
import { ContactsSidebarComponent } from './google-contacts/components/contacts-sidebar/contacts-sidebar.component';
import { ContactsTableItemComponent } from './google-contacts/components/contacts-table-item/contacts-table-item.component';
import { ContactsTableComponent } from './google-contacts/components/contacts-table/contacts-table.component';
import { CreateContactButtonComponent } from './google-contacts/components/create-contact-button/create-contact-button.component';
import { PhotosPageComponent } from './google-photos/photos-page/photos-page.component';
import { MaterialModule } from './material-module';
import { ErrorPageComponent } from './shared/components/error-page/error-page.component';
import { LoginComponent } from './shared/components/login/login.component';
import { RegisterComponent } from './shared/components/register/register.component';
import { ServiceSelectionComponent } from './shared/components/service-selection/service-selection.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MadeByLabelComponent } from './shared/components/made-by-label/made-by-label.component';
import { AuthGuard, UnAuthGuard } from './shared/services/auth-guard.service';
import { EditProfilePageComponent } from './shared/components/edit-profile-page/edit-profile-page.component';
import { HeaderComponent } from './shared/components/header/header.component';
import { GapComponent } from './shared/components/gap/gap.component';

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
    MadeByLabelComponent,
    EditProfilePageComponent,
    HeaderComponent,
    GapComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    
  ],
  providers: [AuthGuard, UnAuthGuard],
  bootstrap: [AppComponent],
})
export class AppModule {}
