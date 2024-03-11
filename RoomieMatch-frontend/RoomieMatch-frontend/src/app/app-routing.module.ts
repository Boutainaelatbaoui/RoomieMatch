import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { RoomateComponent } from './components/roomate/roomate.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { UpdateQuestionComponent } from './components/dashboard/update-question/update-question.component';
import { CreateQuestionComponent } from './components/dashboard/create-question/create-question.component';
import { RoomateDetailsComponent } from './components/roomate/roomate-details/roomate-details.component';

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'roomates', component: RoomateComponent},
  { path: 'dashboard', component: DashboardComponent},
  { path: 'update-question/:id', component: UpdateQuestionComponent},
  { path: 'create-question', component: CreateQuestionComponent },
  { path: 'roomate-details', component: RoomateDetailsComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
