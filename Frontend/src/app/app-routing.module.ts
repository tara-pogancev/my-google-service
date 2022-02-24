import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContactsPageComponent } from './google-contacts/components/contacts-page/contacts-page.component';
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
