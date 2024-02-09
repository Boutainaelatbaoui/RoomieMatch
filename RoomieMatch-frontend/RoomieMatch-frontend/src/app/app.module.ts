import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { FooterComponent } from './components/footer/footer.component';
import { RoomateComponent } from './components/roomate/roomate.component';
import { AccessDeniedComponent } from './components/errors/access-denied/access-denied.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpInterceptor } from './helpers/http/http.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    DashboardComponent,
    LoginComponent,
    RegisterComponent,
    FooterComponent,
    RoomateComponent,
    AccessDeniedComponent
  ],
  imports: [
    [BrowserModule, FormsModule, ReactiveFormsModule],
      AppRoutingModule,
      HttpClientModule,
      BrowserAnimationsModule,
    ],
    providers: [
      { provide: HTTP_INTERCEPTORS, useClass: HttpInterceptor, multi: true },
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
