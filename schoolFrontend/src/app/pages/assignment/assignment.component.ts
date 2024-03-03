import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { map, Observable, take } from 'rxjs';
import { IAssignment } from 'src/app/interfaces/iassignment';
import { ICompletedAssignment } from 'src/app/interfaces/icompleted-assignment';
import { ICompletedAssignmentDto } from 'src/app/interfaces/icompleted-assignment-dto';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { AssignmentService } from 'src/app/services/assignment.service';
import { CompletedAssignmentService } from 'src/app/services/completed-assignment.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-assignment',
  templateUrl: './assignment.component.html',
  styleUrls: ['./assignment.component.scss'],
})
export class AssignmentComponent implements OnInit {
  assignment$!: Observable<IAssignment>;
  assignmentId!: number;
  complAssignment!: ICompletedAssignment | null;
  loggedUser!: null | IJwtResponse;
  linkForm!: UntypedFormGroup;
  confirmation: boolean = false;
  loading: boolean = false;

  constructor(
    private usrSrv: UserService,
    private assSrv: AssignmentService,
    private complAssSrv: CompletedAssignmentService,
    private route: ActivatedRoute,
    private fb: UntypedFormBuilder
  ) {}

  ngOnInit(): void {
    const idStr: string = this.route.snapshot.paramMap.get('assignmentId')!;
    this.assignmentId = parseInt(idStr);
    this.assignment$ = this.assSrv.getById(this.assignmentId);
    this.usrSrv.loggedObs$.pipe(take(1)).subscribe(res => (this.loggedUser = res));
    this.complAssSrv.getByStudentAndAssignmentIds(this.loggedUser!.user.id, this.assignmentId).pipe(take(1)).subscribe(res => {
      this.complAssignment = res;
      this.linkForm.patchValue({
        link: this.complAssignment?.link
      });
    });
    this.setForm();
  }

  setForm() {
    this.linkForm = this.fb.group({
      link: ['', [Validators.required, Validators.pattern('')]],
    });
  }

  onSubmit(form: UntypedFormGroup) {
    if (!form.valid && !this.loggedUser) return;

    this.loading = true;
    const data: ICompletedAssignmentDto = {
      studentId: this.loggedUser!.user.id,
      link: form.value.link,
      assignmentId: this.assignmentId,
    };

    this.complAssSrv.create(data).pipe(take(1)).subscribe((res: any) => {
      if (typeof res === 'object') this.confirmation = true;
      this.loading = false;
    });
  }
  
}
