import { ChangeDetectionStrategy, Component, EventEmitter, OnInit, Output, Renderer2 } from '@angular/core';
import { Observable, take } from 'rxjs';
import { AssignmentService } from 'src/app/services/assignment.service';
import { ModalService } from 'src/app/services/modal.service';
import { NgIf, AsyncPipe } from '@angular/common';
declare var bootstrap: any;

@Component({
  selector: 'app-delete-assignment',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './delete-assignment.component.html',
  styleUrls: ['./delete-assignment.component.scss'],
  standalone: true,
  imports: [NgIf, AsyncPipe],
})
export class DeleteAssignmentComponent implements OnInit {
  @Output() updatedAss = new EventEmitter<void>();
  assingmentId$!: Observable<number | null>;
  loading: boolean = false;

  constructor(
    private assSrv: AssignmentService,
    private mdlSrv: ModalService,
    private renderer: Renderer2
  ) {}

  ngOnInit(): void {
    this.assingmentId$ = this.mdlSrv.assingmentId$;
  }

  deleteAssignment(id: number) {
    this.loading = true;
    this.assSrv
      .delete(id)
      .pipe(take(1))
      .subscribe(res => {
        if (res !== false) {
          this.updateAssignments();
          const assMdlEl = document.querySelector('#deleteAssignmentModal');
          const assModal = bootstrap.Modal.getInstance(assMdlEl);
          assModal.hide();
          this.successAlert();
        }
        this.loading = false;
      });
  }

  updateAssignments() {
    this.updatedAss.emit();
  }

  successAlert() {
    const alert = this.renderer.createElement('div');
    this.renderer.setProperty(
      alert,
      'innerHTML',
      `<div class="alert alert-success" role="alert">Assignment deleted successfully</div>`
    );
    this.renderer.appendChild(document.body, alert);
  }
}
