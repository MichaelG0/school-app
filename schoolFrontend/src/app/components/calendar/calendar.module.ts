import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CalendarComponent } from 'src/app/components/calendar/calendar.component';
import { MonthYearDropdownComponent } from '../month-year-dropdown/month-year-dropdown.component';
import { PureFunctionModule } from 'src/app/pipes/pure-function/pure-function.module';

@NgModule({
  declarations: [
    CalendarComponent,
    MonthYearDropdownComponent,
  ],
  exports: [
    CalendarComponent
  ],
  imports: [
    CommonModule,
    PureFunctionModule,
  ]
})
export class CalendarModule { }
