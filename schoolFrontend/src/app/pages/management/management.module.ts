import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ManagementRoutingModule } from './management-routing.module';
import { ManagementComponent } from './management.component';




@NgModule({
    imports: [
    CommonModule,
    ManagementRoutingModule,
    ManagementComponent
]
})
export class ManagementModule { }
