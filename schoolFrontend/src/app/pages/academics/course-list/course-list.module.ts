import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CourseListRoutingModule } from './course-list-routing.module';
import { CourseListComponent } from './course-list.component';


@NgModule({
  declarations: [
    CourseListComponent
  ],
  imports: [
    CommonModule,
    CourseListRoutingModule
  ]
})
export class CourseListModule { }
