import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TeacherDashboardRoutingModule } from './teacher-dashboard-routing.module';
import { TeacherDashboardComponent } from './teacher-dashboard.component';
import { CalendarModule } from 'src/app/components/calendar/calendar.module';


@NgModule({
  declarations: [
    TeacherDashboardComponent
  ],
  imports: [
    CommonModule,
    TeacherDashboardRoutingModule,
    CalendarModule
  ]
})
export class TeacherDashboardModule { }
