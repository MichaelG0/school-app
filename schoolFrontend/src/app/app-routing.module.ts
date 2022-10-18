import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';
import { IsOnlyGuestGuard } from './auth/is-only-guest.guard';
import { IsOwnerOrStaffGuard } from './auth/is-owner-or-staff.guard';
import { IsStudentGuard } from './auth/is-student.guard';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    loadChildren: () => import('./pages/home/home.module').then(m => m.HomeModule),
  },
  {
    path: 'apply',
    canActivate: [IsOnlyGuestGuard],
    loadChildren: () => import('./pages/apply/apply.module').then(m => m.ApplyModule),
  },
  {
    path: ':token/confirmation',
    loadChildren: () => import('./pages/enrolment-confirmation/enrolment-confirmation.module').then(m => m.EnrolmentConfirmationModule),
  },
  { path: 'management', loadChildren: () => import('./pages/management/management.module').then(m => m.ManagementModule) },
  {
    path: ':id/profile',
    canActivate: [IsOwnerOrStaffGuard],
    loadChildren: () => import('./pages/profile/profile.module').then(m => m.ProfileModule),
  },
  { path: 'academics', loadChildren: () => import('./pages/academics/academics.module').then(m => m.AcademicsModule) },
  {
    path: 'academics/undergraduate',
    loadChildren: () => import('./pages/academics/undergraduate/undergraduate.module').then(m => m.UndergraduateModule),
  },
  { path: 'academics/graduate', loadChildren: () => import('./pages/academics/graduate/graduate.module').then(m => m.GraduateModule) },
  { path: 'academics/:id', loadChildren: () => import('./pages/academics/course/course.module').then(m => m.CourseModule) },
  {
    path: 'student_dashboard',
    canActivate: [IsStudentGuard],
    loadChildren: () => import('./pages/student-dashboard/student-dashboard.module').then(m => m.StudentDashboardModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
