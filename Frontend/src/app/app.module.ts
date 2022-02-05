import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
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
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    ContactsPageComponent,
    PhotosPageComponent,
    LoginComponent,
    RegisterComponent,
    ErrorPageComponent,
    ServiceSelectionComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MaterialModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
