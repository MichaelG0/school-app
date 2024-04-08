import { Component, HostListener, OnInit } from '@angular/core';
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
  issuedAssPageNum = 0;
  submittedAssPageNum = 0;
  issuedAssPageSize: number;
  submittedAssPageSize: number;
  upcoming = true;

  constructor(
    private usrSrv: UserService,
    private klsSrv: KlassService,
    private assSrv: AssignmentService,
    private complAssSrv: CompletedAssignmentService,
    private mdlSrv: ModalService,
    private route: ActivatedRoute
  ) {
    const tempIssuedAssPageSize = Math.floor((window.innerHeight - 170) / 65);
    this.issuedAssPageSize = tempIssuedAssPageSize > 0 ? tempIssuedAssPageSize : 1;
    this.submittedAssPageSize = this.issuedAssPageSize + 1;
  }

  ngOnInit(): void {
    this.usrSrv.loggedObs$.pipe(take(1)).subscribe(res => (this.loggedUser = res));
    const id = parseInt(this.route.snapshot.paramMap.get('klassId')!);
    this.klsSrv
      .getById(id)
      .pipe(take(1))
      .subscribe(res => {
        this.klass = res;
        this.paginateIssued();
        this.paginateSubmitted();
      });
  }

  @HostListener('window:resize', ['$event'])
  onWindowResize() {
    let tempIssuedAssPageSize = Math.floor((window.innerHeight - 170) / 65);
    tempIssuedAssPageSize = tempIssuedAssPageSize > 0 ? tempIssuedAssPageSize : 1;
    if (this.issuedAssPageSize > tempIssuedAssPageSize) {
      this.issuedAssPageSize = tempIssuedAssPageSize;
      this.submittedAssPageSize = this.issuedAssPageSize + 1;
      this.paginateIssued();
      this.paginateSubmitted();
    } else if (this.issuedAssPageSize < tempIssuedAssPageSize) {
      this.issuedAssPageSize = tempIssuedAssPageSize;
      this.submittedAssPageSize = this.issuedAssPageSize + 1;
      this.paginateIssued(0);
      this.paginateSubmitted(0);
    }
  }

  switchUpcoming() {
    this.upcoming = !this.upcoming;
    this.paginateIssued(0);
  }

  paginateIssued(value = this.issuedAssPageNum) {
    this.issuedAssPageNum = value;
    this.assignments$ = this.upcoming
      ? this.assSrv.getUpcomingByKlassAndTeacherIds(
          this.klass.id,
          this.loggedUser!.user.id,
          this.issuedAssPageNum,
          this.issuedAssPageSize
        )
      : this.assSrv.getPastByKlassAndTeacherIds(
          this.klass.id,
          this.loggedUser!.user.id,
          this.issuedAssPageNum,
          this.issuedAssPageSize
        );
  }

  paginateSubmitted(value = this.submittedAssPageNum) {
    this.submittedAssPageNum = value;
    this.complAssignments$ = this.complAssSrv.getByKlassAndTeacherIds(
      this.klass.id,
      this.loggedUser!.user.id,
      this.submittedAssPageNum,
      this.submittedAssPageSize
    );
  }

  updateAll() {
    this.issuedAssPageNum = 0;
    this.submittedAssPageNum = 0;
    this.assignments$ = this.assSrv.getUpcomingByKlassAndTeacherIds(
      this.klass.id,
      this.loggedUser!.user.id,
      this.issuedAssPageNum,
      this.issuedAssPageSize
    );
    this.complAssignments$ = this.complAssSrv.getByKlassAndTeacherIds(
      this.klass.id,
      this.loggedUser!.user.id,
      this.submittedAssPageNum,
      this.submittedAssPageSize
    );
  }

  setAssignmentToDeleteId(id: number) {
    this.mdlSrv.setAssignmnetId(id);
  }

  setAssignmentToUpdate(modalTitle: string, assignment?: IAssignment) {
    this.mdlSrv.setAssignment(modalTitle, assignment);
  }

  setCompletedAssignment(complAss: ICompletedAssignment) {
    this.mdlSrv.setCompletedAssignment(complAss);
  }
}
