import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
  Renderer2,
} from '@angular/core';
import { Validators, UntypedFormGroup, UntypedFormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Observable, Subject, take, takeUntil } from 'rxjs';
import { ICompletedAssignment } from 'src/app/interfaces/icompleted-assignment';
import { CompletedAssignmentService } from 'src/app/services/completed-assignment.service';
import { ModalService } from 'src/app/services/modal.service';
import { NgIf, AsyncPipe } from '@angular/common';
declare var bootstrap: any;

@Component({
  selector: 'app-grade-modal',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './grade-modal.component.html',
  styleUrls: ['./grade-modal.component.scss'],
  imports: [ReactiveFormsModule, NgIf, AsyncPipe],
})
export class GradeModalComponent implements OnInit, OnDestroy {
  unsub$ = new Subject<void>();
  @Output() updatedAss = new EventEmitter<void>();
  complAssignment$!: Observable<ICompletedAssignment | null>;
  complAssignment!: ICompletedAssignment | null;
  gradeForm!: UntypedFormGroup;
  loading: boolean = false;

  constructor(
    private complAssSrv: CompletedAssignmentService,
    private mdlSrv: ModalService,
    private fb: UntypedFormBuilder,
    private renderer: Renderer2
  ) {}

  ngOnInit(): void {
    this.complAssignment$ = this.mdlSrv.complAss$;
    this.mdlSrv.complAss$.pipe(takeUntil(this.unsub$)).subscribe(res => {
      this.complAssignment = res;
      if (this.gradeForm && res && res.grade) {
        this.gradeForm.patchValue({
          grade: this.complAssignment!.grade,
        });
        this.renderer.setProperty(document.querySelector('#grade-btn'), 'disabled', false);
      }
    });
    this.setForm();
  }

  setForm() {
    this.gradeForm = this.fb.group({
      grade: ['', [Validators.required, Validators.min(1), Validators.max(10)]],
    });
  }

  onSubmit(form: UntypedFormGroup) {
    if (!form.valid) return;

    this.loading = true;
    const grade: number = form.value.grade;

    this.complAssSrv
      .assignGrade(this.complAssignment!.id, grade)
      .pipe(take(1))
      .subscribe(() => {
        this.updateAssignments();
        const assMdlEl = document.querySelector('#gradeModalToggle');
        const assModal = bootstrap.Modal.getInstance(assMdlEl);
        assModal.hide();
        this.successAlert();
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
      `<div class="alert alert-success" role="alert">Grade issued successfully</div>`
    );
    this.renderer.appendChild(document.body, alert);
  }

  ngOnDestroy(): void {
    this.unsub$.next();
    this.unsub$.complete();
  }
}
