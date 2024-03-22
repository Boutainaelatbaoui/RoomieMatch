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
import { MatStepperModule } from '@angular/material/stepper';
import { MatPaginatorModule } from '@angular/material/paginator';
import { SidebarComponent } from './components/dashboard/sidebar/sidebar.component';
import { UpdateQuestionComponent } from './components/dashboard/update-question/update-question.component';
import { CreateQuestionComponent } from './components/dashboard/create-question/create-question.component';
import { RoomateDetailsComponent } from './components/roomate/roomate-details/roomate-details.component';
import {MatChipsModule} from '@angular/material/chips';
import { MemberComponent } from './components/dashboard/member/member.component';
import { QuestionnaireComponent } from './components/questionnaire/questionnaire.component';
import { MatRadioModule } from '@angular/material/radio';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { RequestComponent } from './components/request/request.component';
import {MatTabsModule} from '@angular/material/tabs';
import { UpdateProfileComponent } from './components/roomate/update-profile/update-profile.component';





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
    AccessDeniedComponent,
    SidebarComponent,
    UpdateQuestionComponent,
    CreateQuestionComponent,
    RoomateDetailsComponent,
    MemberComponent,
    QuestionnaireComponent,
    RequestComponent,
    UpdateProfileComponent,
  ],
  imports: [
    [BrowserModule, FormsModule, ReactiveFormsModule],
      AppRoutingModule,
      HttpClientModule,
      MatPaginatorModule,
      MatStepperModule,
      BrowserAnimationsModule,
      MatChipsModule,
      MatRadioModule,
      MatProgressSpinnerModule,
      MatTabsModule
    ],
    providers: [
      { provide: HTTP_INTERCEPTORS, useClass: HttpInterceptor, multi: true }
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
