import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApplyRoutingModule } from './apply-routing.module';
import { ApplyComponent } from './apply.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    ApplyComponent
  ],
  imports: [
    CommonModule,
    ApplyRoutingModule,
    ReactiveFormsModule
  ]
})
export class ApplyModule { }
