import { Injectable, OnInit } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { map, Observable, take } from 'rxjs';
import { IJwtResponse } from '../interfaces/ijwt-response';
import { IKlass } from '../interfaces/iklass';
import { KlassService } from '../services/klass.service';
import { UserService } from '../services/user.service';

@Injectable({
  providedIn: 'root',
})
export class IsStudentInThisKlassGuard  {
  jwt!: IJwtResponse | null;

  constructor(private userSrv: UserService, private klassSrv: KlassService, private router: Router) {
    this.userSrv.loggedObs$.pipe(take(1)).subscribe((res: IJwtResponse | null) => this.jwt = res);
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const klassId: number = parseInt(route.paramMap.get('klassId') || '');
    const navigate = this.router.createUrlTree(['/student_dashboard'])

    if (this.jwt && this.jwt.user.roles.includes('ROLE_STUDENT'))
    return this.klassSrv.getKlassByStudentId(this.jwt!.user.id).pipe(
      take(1),
      map((res: IKlass) => {
        return res && res.id == klassId ? true : navigate;
      })
    );
    return navigate
  }

}
