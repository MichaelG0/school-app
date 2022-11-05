import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, Subject, takeUntil } from 'rxjs';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { ModalService } from 'src/app/services/modal.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss'],
})
export class NavComponent implements OnInit, OnDestroy {
  unsub$ = new Subject<void>();
  loggedUser!: null | IJwtResponse;
  roles!: null | string[];
  rtr!: Router;

  constructor(private usrSrv: UserService, private modalSrv: ModalService, private router: Router) {}

  ngOnInit(): void {
    this.usrSrv.loggedObs$.pipe(takeUntil(this.unsub$)).subscribe(res => {
      this.loggedUser = res;
      this.roles = res ? res.user.roles : null;
    });
    this.rtr = this.router;
  }

  logout() {
    this.usrSrv.logout();
  }

  setModal(title: string, navigateTo: string) {
    this.modalSrv.changeProps(title, navigateTo);
  }

  dashboardCondition(): string {
    if (this.roles && this.roles.includes('ROLE_TEACHER'))
      return '/teacher_dashboard';
    return '/student_dashboard';
  }

  ngOnDestroy(): void {
    this.unsub$.next();
    this.unsub$.complete();
  }
}
