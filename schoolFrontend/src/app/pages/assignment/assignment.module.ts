import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AssignmentRoutingModule } from './assignment-routing.module';
import { AssignmentComponent } from './assignment.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AssignmentComponent
  ],
  imports: [
    CommonModule,
    AssignmentRoutingModule,
    ReactiveFormsModule
  ]
})
export class AssignmentModule { }
