import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EnrolmentConfirmationRoutingModule } from './enrolment-confirmation-routing.module';
import { EnrolmentConfirmationComponent } from './enrolment-confirmation.component';

@NgModule({
  imports: [CommonModule, EnrolmentConfirmationRoutingModule, EnrolmentConfirmationComponent],
})
export class EnrolmentConfirmationModule {}
