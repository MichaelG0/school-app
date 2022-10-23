import { Component, OnInit } from '@angular/core';
import { Observable, take } from 'rxjs';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { IKlass } from 'src/app/interfaces/iklass';
import { KlassService } from 'src/app/services/klass.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-student-dashboard',
  templateUrl: './student-dashboard.component.html',
  styleUrls: ['./student-dashboard.component.scss'],
})
export class StudentDashboardComponent implements OnInit {
  loggedUser!: IJwtResponse | null
  klass$!: Observable<IKlass>

  constructor(private klsSrv: KlassService, private usrSrv: UserService) {}

  ngOnInit(): void {
    this.usrSrv.loggedObs$.pipe(take(1)).subscribe(res => this.loggedUser = res)
    this.klass$ = this.klsSrv.getKlassByStudentId(this.loggedUser!.user.id)
  }
  
}
