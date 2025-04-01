import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KlassRoutingModule } from './klass-routing.module';
import { KlassComponent } from './klass.component';

import { AssignmentModalComponent } from 'src/app/components/assignment-modal/assignment-modal.component';
import { ReactiveFormsModule } from '@angular/forms';
import { DeleteAssignmentComponent } from 'src/app/components/delete-assignment/delete-assignment.component';
import { GradeModalComponent } from 'src/app/components/grade-modal/grade-modal.component';




@NgModule({
    imports: [
    CommonModule,
    KlassRoutingModule,
    ReactiveFormsModule,
    KlassComponent,
    AssignmentModalComponent,
    DeleteAssignmentComponent,
    GradeModalComponent
]
})
export class KlassModule { }
