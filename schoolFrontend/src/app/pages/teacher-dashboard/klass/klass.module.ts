import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KlassRoutingModule } from './klass-routing.module';
import { KlassComponent } from './klass.component';
import { KlassListModule } from 'src/app/components/klass-list/klass-list.module';
import { AssignmentModalComponent } from 'src/app/components/assignment-modal/assignment-modal.component';
import { ReactiveFormsModule } from '@angular/forms';
import { DeleteAssignmentComponent } from 'src/app/components/delete-assignment/delete-assignment.component';
import { GradeModalComponent } from 'src/app/components/grade-modal/grade-modal.component';
import { PaginatorModule } from 'src/app/components/paginator/paginator.module';
import { SwitchUpcomingModule } from 'src/app/components/switch-upcoming/switch-upcoming.module';


@NgModule({
  declarations: [
    KlassComponent,
    AssignmentModalComponent,
    DeleteAssignmentComponent,
    GradeModalComponent
  ],
  imports: [
    CommonModule,
    KlassRoutingModule,
    KlassListModule,
    ReactiveFormsModule,
    PaginatorModule,
    SwitchUpcomingModule
  ]
})
export class KlassModule { }
