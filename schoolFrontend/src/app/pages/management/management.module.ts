import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ManagementRoutingModule } from './management-routing.module';
import { ManagementComponent } from './management.component';
import { PaginatorModule } from 'src/app/components/paginator/paginator.module';
import { PureFunctionModule } from 'src/app/pipes/pure-function/pure-function.module';


@NgModule({
  declarations: [
    ManagementComponent
  ],
  imports: [
    CommonModule,
    ManagementRoutingModule,
    PaginatorModule,
    PureFunctionModule
  ]
})
export class ManagementModule { }
