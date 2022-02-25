import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContactPersonPageComponent } from './google-contacts/components/contact-person-page/contact-person-page.component';
import { ContactsPageComponent } from './google-contacts/components/contacts-page/contacts-page.component';
import { ContactsSidebarComponent } from './google-contacts/components/contacts-sidebar/contacts-sidebar.component';
import { ContactsTableItemComponent } from './google-contacts/components/contacts-table-item/contacts-table-item.component';
import { ContactsTableComponent } from './google-contacts/components/contacts-table/contacts-table.component';
import { CreateNewPageComponent } from './google-contacts/components/create-new-page/create-new-page.component';
import { ExportPageComponent } from './google-contacts/components/export-page/export-page.component';
import { ImportPageComponent } from './google-contacts/components/import-page/import-page.component';
import { SuggestionsPageComponent } from './google-contacts/components/suggestions-page/suggestions-page.component';
import { TrashPageComponent } from './google-contacts/components/trash-page/trash-page.component';
import { PhotosPageComponent } from './google-photos/photos-page/photos-page.component';
import { ConnectionRefusedErrorComponent } from './shared/components/connection-refused-error/connection-refused-error.component';
import { EditProfilePageComponent } from './shared/components/edit-profile-page/edit-profile-page.component';
import { ErrorPageComponent } from './shared/components/error-page/error-page.component';
import { LoginComponent } from './shared/components/login/login.component';
import { RegisterComponent } from './shared/components/register/register.component';
import { ServiceSelectionComponent } from './shared/components/service-selection/service-selection.component';
import { AuthGuard, UnAuthGuard } from './shared/services/auth-guard.service';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [UnAuthGuard] },

  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [UnAuthGuard],
  },

  {
    path: 'edit-profile',
    component: EditProfilePageComponent,
    canActivate: [AuthGuard],
  },

  {
    path: 'contacts',
    component: ContactsPageComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: ContactsTableComponent,
      },
      {
        path: 'new',
        component: CreateNewPageComponent,
      },
      {
        path: 'suggestions',
        component: SuggestionsPageComponent,
      },
      {
        path: 'trash',
        component: TrashPageComponent,
      },
      {
        path: 'import',
        component: ImportPageComponent,
      },
      {
        path: 'export',
        component: ExportPageComponent,
      },
      {
        path: 'person/:id',
        component: ContactPersonPageComponent,
      },
    ],
  },

  {
    path: 'photos',
    component: PhotosPageComponent,
    canActivate: [AuthGuard],
  },

  { path: '', component: ServiceSelectionComponent, canActivate: [AuthGuard] },
  { path: 'api-error', component: ConnectionRefusedErrorComponent },
  { path: '**', component: ErrorPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
