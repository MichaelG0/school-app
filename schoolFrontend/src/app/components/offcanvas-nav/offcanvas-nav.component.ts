import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subject, takeUntil } from 'rxjs';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { LoginModalService } from 'src/app/services/login-modal.service';
import { UserService } from 'src/app/services/user.service';
declare var bootstrap: any;

@Component({
  selector: 'app-offcanvas-nav',
  templateUrl: './offcanvas-nav.component.html',
  styleUrls: ['./offcanvas-nav.component.scss'],
})
export class OffcanvasNavComponent implements OnInit, OnDestroy {
  unsub$ = new Subject<void>();
  loggedUser!: null | IJwtResponse;
  roles!: null | string[];

  constructor(private usrSrv: UserService, private modalSrv: LoginModalService) {}

  ngOnInit(): void {
    this.usrSrv.loggedObs$.pipe(takeUntil(this.unsub$)).subscribe(res => {
      this.loggedUser = res;
      this.roles = res ? res.user.roles : null;
    });
  }

  closeOffcanvas() {
    const offcNavEl = document.querySelector('#offcanvasNav');
    const offcanvasNav = bootstrap.Offcanvas.getInstance(offcNavEl);
    offcanvasNav.hide();
  }

  onResizeDismiss() {
    const offcNavEl = document.querySelector('#offcanvasNav');
    const offcanvasNav = bootstrap.Offcanvas.getInstance(offcNavEl);
    if (offcanvasNav && window.innerWidth >= 992) offcanvasNav.hide();
  }

  setModal(title: string, navigateTo: string) {
    this.closeOffcanvas();
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
