import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EnrolmentConfirmationRoutingModule } from './enrolment-confirmation-routing.module';
import { EnrolmentConfirmationComponent } from './enrolment-confirmation.component';


@NgModule({
  declarations: [
    EnrolmentConfirmationComponent
  ],
  imports: [
    CommonModule,
    EnrolmentConfirmationRoutingModule
  ]
})
export class EnrolmentConfirmationModule { }
