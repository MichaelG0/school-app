import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subscription, take } from 'rxjs';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { LoginModalService } from 'src/app/services/login-modal.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss'],
})
export class NavComponent implements OnInit, OnDestroy {
  loggedUser!: null | IJwtResponse;
  userSub!: Subscription;

  constructor(private usrSrv: UserService, private modalSrv: LoginModalService) {}

  ngOnInit(): void {
    this.userSub = this.usrSrv.loggedObs$.subscribe(res => (this.loggedUser = res));
  }

  logout() {
    this.usrSrv.logout();
  }

  setModal(title: string, navigateTo: string) {
    this.modalSrv.changeProps(title, navigateTo);
  }

  ngOnDestroy(): void {
    this.userSub.unsubscribe();
  }
  
}
