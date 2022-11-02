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
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-klass',
  templateUrl: './klass.component.html',
  styleUrls: ['./klass.component.scss'],
})
export class KlassComponent implements OnInit {
  loggedUser!: IJwtResponse | null
  klass!: IKlass;
  assignments$!: Observable<IPageable<IAssignment>>;
  complAssignments$!: Observable<IPageable<ICompletedAssignment>>;
  page: number = 0;
  page2: number = 0;

  constructor(
    private usrSrv: UserService,
    private klsSrv: KlassService,
    private assSrv: AssignmentService,
    private complAssSrv: CompletedAssignmentService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.usrSrv.loggedObs$.pipe(take(1)).subscribe(res => this.loggedUser = res)
    const id = parseInt(this.route.snapshot.paramMap.get('klassId')!);
    this.klsSrv
      .getById(id)
      .pipe(take(1))
      .subscribe(res => {
        this.klass = res;
        this.assignments$ = this.assSrv.getUpcomingByKlassAndTeacherIds(this.klass.id, this.loggedUser!.user.id, this.page, 9);
        this.complAssignments$ = this.complAssSrv.getByKlassAndTeacherIds(this.klass.id, this.loggedUser!.user.id, this.page2, 9)
      });
  }

  paginate(value: number) {
    this.page += value;
    this.assignments$ = this.assSrv.getUpcomingByKlassAndTeacherIds(this.klass.id, this.loggedUser!.user.id, this.page, 9);
  }

  paginate2(value: number) {
    this.page2 += value;
    this.complAssignments$ = this.complAssSrv.getByKlassAndTeacherIds(this.klass.id, this.loggedUser!.user.id, this.page2, 9);
  }

  update() {
    this.page = 0
    this.assignments$ = this.assSrv.getUpcomingByKlassAndTeacherIds(this.klass.id, this.loggedUser!.user.id, this.page, 9);
  }

}
