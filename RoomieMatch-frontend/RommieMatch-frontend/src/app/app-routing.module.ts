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
import { MemberComponent } from './components/dashboard/member/member.component';
import { AccessDeniedComponent } from './components/errors/access-denied/access-denied.component';
import { QuestionnaireComponent } from './components/questionnaire/questionnaire.component';
import { RequestComponent } from './components/request/request.component';
import { UpdateProfileComponent } from './components/roomate/update-profile/update-profile.component';
import { UpdateInfoComponent } from './components/roomate/update-info/update-info.component';
import { ChoiceComponent } from './components/dashboard/choice/choice.component';
import { UpdateChoiceComponent } from './components/dashboard/choice/update-choice/update-choice.component';
import { CreateChoiceComponent } from './components/dashboard/choice/create-choice/create-choice.component';
import { PreferenceComponent } from './components/dashboard/preference/preference.component';

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'login', component: LoginComponent, canActivate: [noAuthGuardGuard]},
  { path: 'register', component: RegisterComponent, canActivate: [noAuthGuardGuard]},
  { path: 'roomates', component: RoomateComponent, canActivate: [authGuard]},
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard, adminGuardGuard]},
  { path: 'choice', component: ChoiceComponent, canActivate: [authGuard, adminGuardGuard]},
  { path: 'preference', component: PreferenceComponent, canActivate: [authGuard, adminGuardGuard]},
  { path: 'members', component: MemberComponent, canActivate: [authGuard, adminGuardGuard]},
  { path: 'update-question/:id', component: UpdateQuestionComponent, canActivate: [authGuard, adminGuardGuard]},
  { path: 'update-choice/:id', component: UpdateChoiceComponent, canActivate: [authGuard, adminGuardGuard]},
  { path: 'create-question', component: CreateQuestionComponent, canActivate: [authGuard, adminGuardGuard]},
  { path: 'create-choice', component: CreateChoiceComponent, canActivate: [authGuard, adminGuardGuard]},
  { path: 'roommate-details/:id', component: RoomateDetailsComponent, canActivate: [authGuard]},
  { path: 'profile', component: RoomateDetailsComponent, canActivate: [authGuard] },
  { path: 'update-profile/:id', component: UpdateProfileComponent, canActivate: [authGuard]},
  { path: 'update-info/:id', component: UpdateInfoComponent, canActivate: [authGuard]},
  {path: 'forbidden', component: AccessDeniedComponent, canActivate: [authGuard]},
  {path: 'questionnaire', component: QuestionnaireComponent, canActivate: [authGuard]},
  {path: 'request-match', component: RequestComponent, canActivate: [authGuard]},
  { path: '**', redirectTo: '', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
