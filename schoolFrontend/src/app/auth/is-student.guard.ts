import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { map, Observable, take } from 'rxjs';
import { IJwtResponse } from '../interfaces/ijwt-response';
import { UserService } from '../services/user.service';

@Injectable({
  providedIn: 'root',
})
export class IsStudentGuard  {
  constructor(private userSrv: UserService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.userSrv.loggedObs$.pipe(
      take(1),
      map((res: IJwtResponse | null) => {
        if (res && res.user.roles.includes('ROLE_STUDENT')) return true;
        return this.router.createUrlTree(['/']);
      })
    );
  }
  
}
