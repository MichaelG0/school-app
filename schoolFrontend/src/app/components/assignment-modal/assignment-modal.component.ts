import { ChangeDetectionStrategy, Component, Input, OnInit, signal } from '@angular/core';
import { Validators, UntypedFormGroup, UntypedFormBuilder, ReactiveFormsModule } from '@angular/forms';
import { map, Observable, take } from 'rxjs';
import { IAssignment } from 'src/app/interfaces/iassignment';
import { IAssignmentDto } from 'src/app/interfaces/iassignment-dto';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { IKlass } from 'src/app/interfaces/iklass';
import { ITeacherMPK } from 'src/app/interfaces/iteacher-mpk';
import { AssignmentService } from 'src/app/services/assignment.service';
import { TeacherModulePerKlassService } from 'src/app/services/teacher-module-per-klass.service';
import { AsyncPipe } from '@angular/common';
import { DomService } from 'src/app/services/dom.service';
import { SnackBarComponent } from '../snack-bar/snack-bar.component';

@Component({
  selector: 'app-assignment-modal',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './assignment-modal.component.html',
  styleUrls: ['./assignment-modal.component.scss'],
  imports: [ReactiveFormsModule, AsyncPipe],
})
export class AssignmentModalComponent implements OnInit {
  @Input() modalTitle = '';
  @Input() assToUpdate: IAssignment | undefined;
  @Input({ required: true }) klass!: IKlass;
  @Input({ required: true }) loggedUser!: IJwtResponse;
  taughtModules$!: Observable<string[]>;
  assignmentForm!: UntypedFormGroup;
  submissionFailed = signal(false);
  loading = signal(false);

  constructor(
    private assSrv: AssignmentService,
    private tcrMPKSrv: TeacherModulePerKlassService,
    private fb: UntypedFormBuilder,
    private domSrv: DomService
  ) {
    this.assignmentForm = this.fb.group({
      title: ['', [Validators.required, Validators.nullValidator]],
      caption: ['', [Validators.required, Validators.nullValidator]],
      module: ['', [Validators.required, Validators.nullValidator]],
      due: ['', [Validators.required, Validators.nullValidator]],
    });
  }

  ngOnInit(): void {
    this.taughtModules$ = this.tcrMPKSrv
      .getByTeacherAndKlassIds(this.loggedUser!.user.id, this.klass.id)
      .pipe(map((res: ITeacherMPK) => res.modules));

    if (this.assToUpdate) {
      this.assignmentForm.patchValue({
        title: this.assToUpdate.title,
        caption: this.assToUpdate.caption,
        module: this.assToUpdate.module,
        due: this.assToUpdate.dueDate,
      });
    }
  }

  onSubmit(form: UntypedFormGroup) {
    if (form.invalid) return;

    this.loading.set(true);
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
            this.showSuccess();
            this.domSrv.closeModal(AssignmentModalComponent, res);
          } else {
            this.submissionFailed.set(true);
          }

          this.loading.set(false);
        });
    else
      this.assSrv
        .create(data)
        .pipe(take(1))
        .subscribe(res => {
          if (res) {
            this.showSuccess();
            this.domSrv.closeModal(AssignmentModalComponent, res);
          } else {
            this.submissionFailed.set(true);
          }

          this.loading.set(false);
        });
  }

  private showSuccess() {
    this.domSrv.openSnackbar(SnackBarComponent, {
      name: 'data',
      value: {
        type: 'alert-success',
        message: 'Assignment issued successfully',
        icon: 'bi bi-check-circle-fill',
      },
    });
  }
}
