import { HttpClientModule } from '@angular/common/http';
import { ErrorHandler, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContactsHeaderComponent } from './google-contacts/components/contacts-header/contacts-header.component';
import { ContactsPageComponent } from './google-contacts/components/contacts-page/contacts-page.component';
import { ContactsSidebarComponent } from './google-contacts/components/contacts-sidebar/contacts-sidebar.component';
import { ContactsTableComponent } from './google-contacts/components/contacts-table/contacts-table.component';
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
import { RefreshContactsCountService } from './google-contacts/components/contacts-sidebar/refresh-contact-count.service';
import { ContactProfilePictureComponent } from './google-contacts/components/contact-profile-picture/contact-profile-picture.component';
import { SearchContactsService } from './google-contacts/components/contacts-header/search-contacts.service';
import { ExportSelectedPageComponent } from './google-contacts/components/export-selected-page/export-selected-page.component';
import { MergeContactsDialogComponent } from './google-contacts/components/merge-contacts-dialog/merge-contacts-dialog.component';
import { ProfileRefreshService } from './shared/components/edit-profile-page/profile-refresh.service';
import { UploadContactPictureDialogComponent } from './google-contacts/components/create-new-page/upload-contact-picture-dialog/upload-contact-picture-dialog.component';
import { DeleteAccountDialogComponent } from './shared/components/edit-profile-page/delete-account-dialog/delete-account-dialog.component';
import { ViewPersonComponent } from './google-contacts/components/contact-person-page/view-person/view-person.component';
import { EditPersonComponent } from './google-contacts/components/contact-person-page/edit-person/edit-person.component';
import { MergeCardComponent } from './google-contacts/components/suggestions-page/merge-card/merge-card.component';
import { BodyComponent } from './google-contacts/components/contacts-page/body/body.component';
import { SearchDialogComponent } from './google-contacts/components/contacts-header/search-dialog/search-dialog.component';
import { PhotosSidebarComponent } from './google-photos/components/photos-sidebar/photos-sidebar.component';
import { PhotosHeaderComponent } from './google-photos/components/photos-header/photos-header.component';
import { PhotosListComponent } from './google-photos/components/photos-list/photos-list.component';
import { PhotosCardComponent } from './google-photos/components/photos-card/photos-card.component';
import { PhotosCardGroupComponent } from './google-photos/components/photos-card-group/photos-card-group.component';
import { SelectedBarComponent } from './google-photos/components/selected-bar/selected-bar.component';

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
    ContactProfilePictureComponent,
    ExportSelectedPageComponent,
    MergeContactsDialogComponent,
    UploadContactPictureDialogComponent,
    DeleteAccountDialogComponent,
    ViewPersonComponent,
    EditPersonComponent,
    MergeCardComponent,
    BodyComponent,
    SearchDialogComponent,
    PhotosSidebarComponent,
    PhotosHeaderComponent,
    PhotosListComponent,
    PhotosCardComponent,
    PhotosCardGroupComponent,
    SelectedBarComponent,
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
    { provide: ErrorHandler, useClass: MyErrorHandler },
    AuthGuard,
    UnAuthGuard,
    RefreshContactsCountService,
    SearchContactsService,
    ProfileRefreshService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
