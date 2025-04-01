import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CourseListRoutingModule } from './course-list-routing.module';
import { CourseListComponent } from './course-list.component';

@NgModule({
  imports: [CommonModule, CourseListRoutingModule, CourseListComponent],
})
export class CourseListModule {}
