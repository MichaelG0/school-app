import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subject, Subscription, take, takeUntil } from 'rxjs';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { LoginModalService } from 'src/app/services/login-modal.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss'],
})
export class NavComponent implements OnInit, OnDestroy {
  unsub$ = new Subject<void>();
  loggedUser!: null | IJwtResponse;

  constructor(private usrSrv: UserService, private modalSrv: LoginModalService) {}

  ngOnInit(): void {
    this.usrSrv.loggedObs$.pipe(takeUntil(this.unsub$)).subscribe(res => (this.loggedUser = res));
  }

  logout() {
    this.usrSrv.logout();
  }

  setModal(title: string, navigateTo: string) {
    this.modalSrv.changeProps(title, navigateTo);
  }

  ngOnDestroy(): void {
    this.unsub$.next();
    this.unsub$.complete();
  }
  
}
