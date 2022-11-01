import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';
import { IsOnlyGuestGuard } from './auth/is-only-guest.guard';
import { IsOwnerOrStaffGuard } from './auth/is-owner-or-staff.guard';
import { IsStudentInThisKlassGuard } from './auth/is-student-in-this-klass.guard';
import { IsStudentGuard } from './auth/is-student.guard';
import { IsTeacherGuard } from './auth/is-teacher.guard';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    loadChildren: () => import('./pages/home/home.module').then(m => m.HomeModule),
  },
  {
    path: 'apply',
    canActivate: [AuthGuard, IsOnlyGuestGuard],
    loadChildren: () => import('./pages/apply/apply.module').then(m => m.ApplyModule),
  },
  {
    path: ':token/confirmation',
    loadChildren: () =>
      import('./pages/enrolment-confirmation/enrolment-confirmation.module').then(
        m => m.EnrolmentConfirmationModule
      ),
  },
  {
    path: 'management',
    loadChildren: () => import('./pages/management/management.module').then(m => m.ManagementModule),
  },
  {
    path: ':id/profile',
    canActivate: [IsOwnerOrStaffGuard],
    loadChildren: () => import('./pages/profile/profile.module').then(m => m.ProfileModule),
  },
  {
    path: 'academics',
    loadChildren: () => import('./pages/academics/academics.module').then(m => m.AcademicsModule),
  },
  {
    path: 'academics/:type/:id',
    loadChildren: () => import('./pages/academics/course/course.module').then(m => m.CourseModule),
  },
  {
    path: 'student_dashboard',
    canActivate: [IsStudentGuard],
    loadChildren: () =>
      import('./pages/student-dashboard/student-dashboard.module').then(m => m.StudentDashboardModule),
  },
  {
    path: 'academics/:type',
    loadChildren: () =>
      import('./pages/academics/course-list/course-list.module').then(m => m.CourseListModule),
  },
  {
    path: ':klassId/assignment/:assignmentId',
    canActivate: [IsStudentInThisKlassGuard],
    loadChildren: () => import('./pages/assignment/assignment.module').then(m => m.AssignmentModule),
  },
  {
    path: 'teacher_dashboard',
    canActivate: [IsTeacherGuard],
    loadChildren: () =>
      import('./pages/teacher-dashboard/teacher-dashboard.module').then(m => m.TeacherDashboardModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
