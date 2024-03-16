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
import { noAuthGuardGuard } from './helpers/noAuth/no-auth-guard.guard';
import { authGuard } from './helpers/auth/auth.guard';
import { adminGuardGuard } from './helpers/admin/admin-guard.guard';
import { MemberComponent } from './components/member/member.component';

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'login', component: LoginComponent, canActivate: [noAuthGuardGuard]},
  { path: 'register', component: RegisterComponent, canActivate: [noAuthGuardGuard]},
  { path: 'roomates', component: RoomateComponent, canActivate: [authGuard]},
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard, adminGuardGuard]},
  { path: 'member', component: MemberComponent, canActivate: [authGuard, adminGuardGuard]},
  { path: 'update-question/:id', component: UpdateQuestionComponent, canActivate: [authGuard, adminGuardGuard]},
  { path: 'create-question', component: CreateQuestionComponent, canActivate: [authGuard, adminGuardGuard]},
  { path: 'roommate-details/:id', component: RoomateDetailsComponent, canActivate: [authGuard]},
  { path: '**', redirectTo: '', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
