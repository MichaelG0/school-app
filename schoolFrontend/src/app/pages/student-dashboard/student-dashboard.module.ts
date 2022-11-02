import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentDashboardRoutingModule } from './student-dashboard-routing.module';
import { StudentDashboardComponent } from './student-dashboard.component';
import { CircleProgressComponent } from 'src/app/components/circle-progress/circle-progress.component';
import { KlassListModule } from 'src/app/components/klass-list/klass-list.module';
import { CalendarModule } from 'src/app/components/calendar/calendar.module';

@NgModule({
  declarations: [
    StudentDashboardComponent,
    CircleProgressComponent
  ],
  imports: [
    CommonModule,
    StudentDashboardRoutingModule,
    KlassListModule,
    CalendarModule
  ]
})
export class StudentDashboardModule { }
