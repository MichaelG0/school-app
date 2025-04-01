import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AcademicsRoutingModule } from './academics-routing.module';
import { AcademicsComponent } from './academics.component';

@NgModule({
  imports: [CommonModule, AcademicsRoutingModule, AcademicsComponent],
})
export class AcademicsModule {}
