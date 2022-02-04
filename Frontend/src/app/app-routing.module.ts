import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { ContactsPageComponent } from './google-contacts/contacts-page/contacts-page.component';
import { PhotosPageComponent } from './google-photos/photos-page/photos-page.component';
import { ErrorPageComponent } from './shared/error-page/error-page.component';
import { LoginComponent } from './shared/login/login.component';
import { RegisterComponent } from './shared/register/register.component';
import { ServiceSelectionComponent } from './shared/service-selection/service-selection.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'contacts', component: ContactsPageComponent },
  { path: 'photos', component: PhotosPageComponent },
  { path: '', component: ServiceSelectionComponent },
  { path: '**', component: ErrorPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
