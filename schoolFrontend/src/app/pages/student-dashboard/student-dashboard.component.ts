import { Component, OnInit } from '@angular/core';
import { Observable, take, tap } from 'rxjs';
import { IComplAssignBasicResponseWithAverageGrade } from 'src/app/interfaces/icompl-assign-basic-response-with-average-grade';
import { IAssignment } from 'src/app/interfaces/iassignment';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { IKlass } from 'src/app/interfaces/iklass';
import { IPageable } from 'src/app/interfaces/ipageable';
import { AssignmentService } from 'src/app/services/assignment.service';
import { CompletedAssignmentService } from 'src/app/services/completed-assignment.service';
import { KlassService } from 'src/app/services/klass.service';
import { RegisterService } from 'src/app/services/register.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-student-dashboard',
  templateUrl: './student-dashboard.component.html',
  styleUrls: ['./student-dashboard.component.scss'],
})
export class StudentDashboardComponent implements OnInit {
  loggedUser!: IJwtResponse | null;
  klass!: IKlass;
  attendance$!: Observable<number>;
  completedAssignments$!: Observable<IComplAssignBasicResponseWithAverageGrade>;
  complAssIds!: number[];
  assignments$!: Observable<IPageable<IAssignment>>;
  page: number = 0;

  constructor(
    private klsSrv: KlassService,
    private usrSrv: UserService,
    private rgsSrv: RegisterService,
    private assSrv: AssignmentService,
    private complAssSrv: CompletedAssignmentService
  ) {}

  ngOnInit(): void {
    this.usrSrv.loggedObs$.pipe(take(1)).subscribe(res => (this.loggedUser = res));
    this.klsSrv
      .getKlassByStudentId(this.loggedUser!.user.id)
      .pipe(take(1))
      .subscribe(res => {
        this.klass = res;
        this.attendance$ = this.rgsSrv.getAttendance(this.loggedUser!.user.id, this.klass.id);
        this.assignments$ = this.assSrv.getByKlassId(this.klass.id, this.page, 5);
      });
    this.completedAssignments$ = this.complAssSrv
      .getBasicByStudentId(this.loggedUser!.user.id)
      .pipe(tap(res => (this.complAssIds = res.completedAssignments.map(x => x.assignmentId))));
  }

  paginate(value: number) {
    this.page += value;
    this.assignments$ = this.assSrv.getByKlassId(this.klass.id, this.page, 5);
  }
  
}
