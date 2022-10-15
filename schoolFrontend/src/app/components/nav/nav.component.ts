import { Component, OnInit } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { LoginModalService } from 'src/app/services/login-modal.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss'],
})
export class NavComponent implements OnInit {
  loggedObs$!: Observable<null | IJwtResponse>;

  constructor(private usrSrv: UserService, private modalSrv: LoginModalService) {}

  ngOnInit(): void {
    this.loggedObs$ = this.usrSrv.loggedObs$;
  }

  logout() {
    this.usrSrv.logout();
  }

  dafaultModal() {
    this.modalSrv.changeProps('Log In', '');
  }

  applyModal() {
    this.modalSrv.changeProps('Log In To Apply', '/apply');
  }
  
}
