import { Component, OnInit } from '@angular/core';
import { Observable, take } from 'rxjs';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { ITeacherMPK } from 'src/app/interfaces/iteacher-mpk';
import { TeacherModulePerKlassService } from 'src/app/services/teacher-module-per-klass.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-teacher-dashboard',
  templateUrl: './teacher-dashboard.component.html',
  styleUrls: ['./teacher-dashboard.component.scss'],
})
export class TeacherDashboardComponent implements OnInit {
  loggedUser!: IJwtResponse | null;
  teacherMPKList$!: Observable<ITeacherMPK[]>;

  constructor(private usrSrv: UserService, private tcrMPKSrv: TeacherModulePerKlassService) {}

  ngOnInit(): void {
    this.usrSrv.loggedObs$.pipe(take(1)).subscribe(res => {
      this.loggedUser = res;
      this.teacherMPKList$ = this.tcrMPKSrv.getByTeacherId(res!.user.id);
    });
  }
}
