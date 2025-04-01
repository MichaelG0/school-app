import { Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';
import { IsOnlyGuestGuard } from './auth/is-only-guest.guard';
import { IsOwnerOrStaffGuard } from './auth/is-owner-or-staff.guard';
import { IsStaffGuard } from './auth/is-staff.guard';
import { IsStudentInThisKlassGuard } from './auth/is-student-in-this-klass.guard';
import { IsStudentGuard } from './auth/is-student.guard';
import { IsTeacherInThisKlassGuard } from './auth/is-teacher-in-this-klass.guard';
import { IsTeacherGuard } from './auth/is-teacher.guard';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    loadChildren: () => import('./pages/home/home-routing.module').then(m => m.routes),
  },
  {
    path: 'apply',
    canActivate: [AuthGuard, IsOnlyGuestGuard],
    loadChildren: () => import('./pages/apply/apply-routing.module').then(m => m.routes),
  },
  {
    path: ':token/confirmation',
    loadChildren: () =>
      import('./pages/enrolment-confirmation/enrolment-confirmation-routing.module').then(m => m.routes),
  },
  {
    path: 'management',
    canActivate: [IsStaffGuard],
    loadChildren: () => import('./pages/management/management-routing.module').then(m => m.routes),
  },
  {
    path: ':id/profile',
    canActivate: [IsOwnerOrStaffGuard],
    loadChildren: () => import('./pages/profile/profile-routing.module').then(m => m.routes),
  },
  {
    path: 'academics',
    loadChildren: () => import('./pages/academics/academics-routing.module').then(m => m.routes),
  },
  {
    path: 'academics/:type/:id',
    loadChildren: () => import('./pages/academics/course/course-routing.module').then(m => m.routes),
  },
  {
    path: 'student_dashboard',
    canActivate: [IsStudentGuard],
    loadChildren: () =>
      import('./pages/student-dashboard/student-dashboard-routing.module').then(m => m.routes),
  },
  {
    path: 'academics/:type',
    loadChildren: () =>
      import('./pages/academics/course-list/course-list-routing.module').then(m => m.routes),
  },
  {
    path: ':klassId/assignment/:assignmentId',
    canActivate: [IsStudentInThisKlassGuard],
    loadChildren: () => import('./pages/assignment/assignment-routing.module').then(m => m.routes),
  },
  {
    path: 'teacher_dashboard',
    canActivate: [IsTeacherGuard],
    loadChildren: () =>
      import('./pages/teacher-dashboard/teacher-dashboard-routing.module').then(m => m.routes),
  },
  {
    path: 'teacher_dashboard/:klassId',
    canActivate: [IsTeacherInThisKlassGuard],
    loadChildren: () => import('./pages/teacher-dashboard/klass/klass-routing.module').then(m => m.routes),
  },
];
