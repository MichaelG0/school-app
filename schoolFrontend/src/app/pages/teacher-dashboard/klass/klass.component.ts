import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, take } from 'rxjs';
import { IAssignment } from 'src/app/interfaces/iassignment';
import { ICompletedAssignment } from 'src/app/interfaces/icompleted-assignment';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { IKlass } from 'src/app/interfaces/iklass';
import { IPageable } from 'src/app/interfaces/ipageable';
import { AssignmentService } from 'src/app/services/assignment.service';
import { CompletedAssignmentService } from 'src/app/services/completed-assignment.service';
import { KlassService } from 'src/app/services/klass.service';
import { ModalService } from 'src/app/services/modal.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-klass',
  templateUrl: './klass.component.html',
  styleUrls: ['./klass.component.scss'],
})
export class KlassComponent implements OnInit {
  loggedUser!: IJwtResponse | null;
  klass!: IKlass;
  assignments$!: Observable<IPageable<IAssignment>>;
  complAssignments$!: Observable<IPageable<ICompletedAssignment>>;
  page: number = 0;
  page2: number = 0;
  upcoming: boolean = true;

  constructor(
    private usrSrv: UserService,
    private klsSrv: KlassService,
    private assSrv: AssignmentService,
    private complAssSrv: CompletedAssignmentService,
    private mdlSrv: ModalService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.usrSrv.loggedObs$.pipe(take(1)).subscribe(res => (this.loggedUser = res));
    const id = parseInt(this.route.snapshot.paramMap.get('klassId')!);
    this.klsSrv
      .getById(id)
      .pipe(take(1))
      .subscribe(res => {
        this.klass = res;
        this.paginate();
        this.paginate2();
      });
  }

  switchPage() {
    this.upcoming = !this.upcoming;
    let value = -this.page;
    this.paginate(value);
  }

  paginate(value: number = 0) {
    this.page += value;
    this.assignments$ = this.upcoming
      ? this.assSrv.getUpcomingByKlassAndTeacherIds(this.klass.id, this.loggedUser!.user.id, this.page, 9)
      : this.assSrv.getPastByKlassAndTeacherIds(this.klass.id, this.loggedUser!.user.id, this.page, 9);
  }

  paginate2(value: number = 0) {
    this.page2 += value;
    this.complAssignments$ = this.complAssSrv.getByKlassAndTeacherIds(this.klass.id, this.loggedUser!.user.id, this.page2, 9);
  }

  updateAll() {
    this.page = 0;
    this.page2 = 0;
    this.assignments$ = this.assSrv.getUpcomingByKlassAndTeacherIds(this.klass.id, this.loggedUser!.user.id, this.page, 9);
    this.complAssignments$ = this.complAssSrv.getByKlassAndTeacherIds(this.klass.id, this.loggedUser!.user.id, this.page2, 9);
  }

  setAssignmentToDeleteId(id: number) {
    this.mdlSrv.setAssignmnetId(id);
  }

  setAssignmentToUpdate(modalTitle: string, assignment: IAssignment | null = null) {
    this.mdlSrv.setAssignment(modalTitle, assignment);
  }

  setCompletedAssignment(complAss: ICompletedAssignment) {
    this.mdlSrv.setCompletedAssignment(complAss);
  }
}
