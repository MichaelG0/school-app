import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GraduateRoutingModule } from './graduate-routing.module';
import { GraduateComponent } from './graduate.component';


@NgModule({
  declarations: [
    GraduateComponent
  ],
  imports: [
    CommonModule,
    GraduateRoutingModule
  ]
})
export class GraduateModule { }
