import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { take } from 'rxjs';
import { AssignmentService } from 'src/app/services/assignment.service';
import { NgIf } from '@angular/common';
import { DomService } from 'src/app/services/dom.service';
import { SnackBarComponent } from '../snack-bar/snack-bar.component';
declare var bootstrap: any;

@Component({
  selector: 'app-delete-assignment',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './delete-assignment.component.html',
  styleUrls: ['./delete-assignment.component.scss'],
  imports: [NgIf],
})
export class DeleteAssignmentComponent {
  @Input({ required: true }) assignmentId = 0;
  loading: boolean = false;

  constructor(private assSrv: AssignmentService, private domSrv: DomService) {}

  closeModal() {
    this.domSrv.closeModal(DeleteAssignmentComponent, false);
  }

  deleteAssignment(id: number) {
    this.loading = true;
    this.assSrv
      .delete(id)
      .pipe(take(1))
      .subscribe(res => {
        if (res !== false) {
          this.showSuccess();
          this.domSrv.closeModal(DeleteAssignmentComponent, res);
        }

        this.loading = false;
      });
  }

  private showSuccess() {
    this.domSrv.openSnackbar(SnackBarComponent, {
      name: 'data',
      value: {
        type: 'alert-success',
        message: 'Assignment deleted successfully',
        icon: 'bi bi-check-circle-fill',
      },
    });
  }
}
