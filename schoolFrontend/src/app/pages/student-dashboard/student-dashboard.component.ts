import { Component, HostListener, OnInit } from '@angular/core';
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
  page = 0;
  pageSize: number;
  upcoming: boolean = true;

  constructor(
    private klsSrv: KlassService,
    private usrSrv: UserService,
    private rgsSrv: RegisterService,
    private assSrv: AssignmentService,
    private complAssSrv: CompletedAssignmentService
  ) {
    const tempPageSize = Math.floor((window.innerHeight - 170) / 65) + 1;
    this.pageSize = tempPageSize > 0 ? tempPageSize : 1;
  }

  ngOnInit(): void {
    this.usrSrv.loggedObs$.pipe(take(1)).subscribe(res => (this.loggedUser = res));
    this.klsSrv
      .getKlassByStudentId(this.loggedUser!.user.id)
      .pipe(take(1))
      .subscribe(res => {
        this.klass = res;
        this.attendance$ = this.rgsSrv.getAttendance(this.loggedUser!.user.id, this.klass.id);
        this.goToPage();
      });
    this.completedAssignments$ = this.complAssSrv
      .getBasicByStudentId(this.loggedUser!.user.id)
      .pipe(tap(res => (this.complAssIds = res.completedAssignments.map(x => x.assignmentId))));
  }

  @HostListener('window:resize', ['$event'])
  onWindowResize() {
    let tempPageSize = Math.floor((window.innerHeight - 170) / 65) + 1;
    tempPageSize = tempPageSize > 0 ? tempPageSize : 1;
    if (this.pageSize > tempPageSize) {
      this.pageSize = tempPageSize;
      this.goToPage();
    } else if (this.pageSize < tempPageSize) {
      this.pageSize = tempPageSize;
      this.goToPage(0);
    }
  }

  switchUpcoming() {
    this.upcoming = !this.upcoming;
    this.goToPage(0);
  }

  goToPage(value = this.page) {
    this.page = value;
    this.assignments$ = this.upcoming
      ? this.assSrv.getUpcomingByKlassId(this.klass.id, this.page, this.pageSize)
      : this.assSrv.getPastByKlassId(this.klass.id, this.page, this.pageSize);
  }
}
