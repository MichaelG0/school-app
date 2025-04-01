import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentDashboardRoutingModule } from './student-dashboard-routing.module';
import { StudentDashboardComponent } from './student-dashboard.component';
import { CircleProgressComponent } from 'src/app/components/circle-progress/circle-progress.component';





@NgModule({
    imports: [
    CommonModule,
    StudentDashboardRoutingModule,
    StudentDashboardComponent,
    CircleProgressComponent
]
})
export class StudentDashboardModule { }
