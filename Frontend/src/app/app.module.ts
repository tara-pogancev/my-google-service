import { HttpClientModule } from '@angular/common/http';
import { ErrorHandler, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContactsHeaderComponent } from './google-contacts/components/contacts-header/contacts-header.component';
import { ContactsPageComponent } from './google-contacts/components/contacts-page/contacts-page.component';
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
import {
  CreateNewUserPhoneDialog,
  EditProfilePageComponent,
} from './shared/components/edit-profile-page/edit-profile-page.component';
import { HeaderComponent } from './shared/components/header/header.component';
import { GapComponent } from './shared/components/gap/gap.component';
import { ProfilePictureComponent } from './shared/components/profile-picture/profile-picture.component';
import { EditUserPhoneNumberComponent } from './shared/components/edit-user-phone-number/edit-user-phone-number.component';
import { ConnectionRefusedErrorComponent } from './shared/components/connection-refused-error/connection-refused-error.component';
import { CreateNewPageComponent } from './google-contacts/components/create-new-page/create-new-page.component';
import { ContactPersonPageComponent } from './google-contacts/components/contact-person-page/contact-person-page.component';
import { TrashPageComponent } from './google-contacts/components/trash-page/trash-page.component';
import { ImportPageComponent } from './google-contacts/components/import-page/import-page.component';
import { ExportPageComponent } from './google-contacts/components/export-page/export-page.component';
import { SuggestionsPageComponent } from './google-contacts/components/suggestions-page/suggestions-page.component';

class MyErrorHandler implements ErrorHandler {
  handleError(error: any): void {
    if (error.status == 0) {
      window.location.href = '/api-error';
    }
  }
}

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
    CreateContactButtonComponent,
    ContactsTableItemComponent,
    ContactsTableComponent,
    MadeByLabelComponent,
    EditProfilePageComponent,
    HeaderComponent,
    GapComponent,
    ProfilePictureComponent,
    EditUserPhoneNumberComponent,
    CreateNewUserPhoneDialog,
    ConnectionRefusedErrorComponent,
    CreateNewPageComponent,
    ContactPersonPageComponent,
    TrashPageComponent,
    ImportPageComponent,
    ExportPageComponent,
    SuggestionsPageComponent,
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
  providers: [
    AuthGuard,
    UnAuthGuard,
    { provide: ErrorHandler, useClass: MyErrorHandler },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
