import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SwitchUpcomingComponent } from './switch-upcoming.component';

@NgModule({
  declarations: [
    SwitchUpcomingComponent
  ],
  exports: [
    SwitchUpcomingComponent
  ],
  imports: [
    CommonModule
  ]
})
export class SwitchUpcomingModule { }
