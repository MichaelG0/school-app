import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { UserService } from 'src/app/services/user.service';
declare var bootstrap: any;

@Component({
  selector: 'app-offcanvas-nav',
  templateUrl: './offcanvas-nav.component.html',
  styleUrls: ['./offcanvas-nav.component.scss']
})
export class OffcanvasNavComponent implements OnInit {
  loggedObs$!: Observable<null | IJwtResponse>;

  constructor(private usrSrv: UserService) {}

  ngOnInit(): void {
    this.loggedObs$ = this.usrSrv.loggedObs$
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

}
