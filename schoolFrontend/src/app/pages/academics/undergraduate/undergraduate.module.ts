import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UndergraduateRoutingModule } from './undergraduate-routing.module';
import { UndergraduateComponent } from './undergraduate.component';


@NgModule({
  declarations: [
    UndergraduateComponent
  ],
  imports: [
    CommonModule,
    UndergraduateRoutingModule
  ]
})
export class UndergraduateModule { }
