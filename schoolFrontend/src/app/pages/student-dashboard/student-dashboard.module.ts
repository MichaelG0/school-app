import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentDashboardRoutingModule } from './student-dashboard-routing.module';
import { StudentDashboardComponent } from './student-dashboard.component';
import { CalendarComponent } from 'src/app/components/calendar/calendar.component';
import { CircleProgressComponent } from 'src/app/components/circle-progress/circle-progress.component';


@NgModule({
  declarations: [
    StudentDashboardComponent,
    CalendarComponent,
    CircleProgressComponent
  ],
  imports: [
    CommonModule,
    StudentDashboardRoutingModule
  ]
})
export class StudentDashboardModule { }
