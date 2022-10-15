import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    loadChildren: () =>
      import('./pages/home/home.module').then((m) => m.HomeModule),
  },
  {
    path: 'apply',
    canActivate: [AuthGuard],
    loadChildren: () =>
      import('./pages/apply/apply.module').then((m) => m.ApplyModule),
  },
  {
    path: ':token/confirmation',
    loadChildren: () =>
      import(
        './pages/enrolment-confirmation/enrolment-confirmation.module'
      ).then((m) => m.EnrolmentConfirmationModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
