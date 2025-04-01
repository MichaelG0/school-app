import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TeacherDashboardRoutingModule } from './teacher-dashboard-routing.module';
import { TeacherDashboardComponent } from './teacher-dashboard.component';



@NgModule({
    imports: [
    CommonModule,
    TeacherDashboardRoutingModule,
    TeacherDashboardComponent
]
})
export class TeacherDashboardModule { }
