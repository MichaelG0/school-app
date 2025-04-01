import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
  Renderer2,
} from '@angular/core';
import { Validators, UntypedFormGroup, UntypedFormBuilder, ReactiveFormsModule } from '@angular/forms';
import { map, Observable, Subject, take, takeUntil } from 'rxjs';
import { IAssignment } from 'src/app/interfaces/iassignment';
import { IAssignmentDto } from 'src/app/interfaces/iassignment-dto';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { IKlass } from 'src/app/interfaces/iklass';
import { ITeacherMPK } from 'src/app/interfaces/iteacher-mpk';
import { AssignmentService } from 'src/app/services/assignment.service';
import { ModalService } from 'src/app/services/modal.service';
import { TeacherModulePerKlassService } from 'src/app/services/teacher-module-per-klass.service';
import { NgFor, NgIf, AsyncPipe } from '@angular/common';
declare var bootstrap: any;

@Component({
  selector: 'app-assignment-modal',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './assignment-modal.component.html',
  styleUrls: ['./assignment-modal.component.scss'],
  standalone: true,
  imports: [ReactiveFormsModule, NgFor, NgIf, AsyncPipe],
})
export class AssignmentModalComponent implements OnInit, OnDestroy {
  unsub$ = new Subject<void>();
  @Input() klass!: IKlass;
  @Input() loggedUser!: IJwtResponse | null;
  @Output() updatedAss = new EventEmitter<void>();
  modalTitle$!: Observable<string>;
  assToUpdate!: IAssignment | null;
  taughtModules$!: Observable<string[]>;
  assignmentForm!: UntypedFormGroup;
  submissionFailed: boolean = false;
  loading: boolean = false;

  constructor(
    private assSrv: AssignmentService,
    private tcrMPKSrv: TeacherModulePerKlassService,
    private mdlSrv: ModalService,
    private fb: UntypedFormBuilder,
    private renderer: Renderer2
  ) {}

  ngOnInit(): void {
    this.modalTitle$ = this.mdlSrv.assignment$.pipe(map(res => res.modalTitle));
    this.mdlSrv.assignment$.pipe(takeUntil(this.unsub$)).subscribe(res => {
      this.assToUpdate = res.assignment;
      if (res.assignment) {
        this.assignmentForm.patchValue({
          title: this.assToUpdate!.title,
          caption: this.assToUpdate!.caption,
          module: this.assToUpdate!.module,
          due: this.assToUpdate!.dueDate,
        });
        this.renderer.setProperty(document.querySelector('#ass-btn'), 'disabled', false);
      }
    });
    this.taughtModules$ = this.tcrMPKSrv
      .getByTeacherAndKlassIds(this.loggedUser!.user.id, this.klass.id)
      .pipe(map((res: ITeacherMPK) => res.modules));
    this.setForm();
  }

  setForm() {
    this.submissionFailed = false;
    this.assignmentForm = this.fb.group({
      title: ['', [Validators.required, Validators.nullValidator]],
      caption: ['', [Validators.required, Validators.nullValidator]],
      module: ['', [Validators.required, Validators.nullValidator]],
      due: ['', [Validators.required, Validators.nullValidator]],
    });
  }

  onSubmit(form: UntypedFormGroup) {
    if (!form.valid) return;

    this.loading = true;
    const data: IAssignmentDto = {
      title: form.value.title,
      caption: form.value.caption,
      moduleName: form.value.module,
      dueDate: form.value.due,
      klassId: this.klass.id,
      teacherId: this.loggedUser!.user.id,
    };

    if (this.assToUpdate)
      this.assSrv
        .update(this.assToUpdate.id, data)
        .pipe(take(1))
        .subscribe(res => {
          if (res) {
            this.updateAssignments();
            const assMdlEl = document.querySelector('#assignmentModalToggle');
            const assModal = bootstrap.Modal.getInstance(assMdlEl);
            assModal.hide();
            this.successAlert();
          } else this.submissionFailed = true;
          this.loading = false;
        });
    else
      this.assSrv
        .create(data)
        .pipe(take(1))
        .subscribe(res => {
          if (res) {
            this.updateAssignments();
            const assMdlEl = document.querySelector('#assignmentModalToggle');
            const assModal = bootstrap.Modal.getInstance(assMdlEl);
            assModal.hide();
            this.successAlert();
          } else this.submissionFailed = true;
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
      `<div class="alert alert-success" role="alert">Assignment issued successfully</div>`
    );
    this.renderer.appendChild(document.body, alert);
  }

  ngOnDestroy(): void {
    this.unsub$.next();
    this.unsub$.complete();
  }
}
