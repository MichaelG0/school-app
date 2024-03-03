import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { map, Observable, take } from 'rxjs';
import { IJwtResponse } from '../interfaces/ijwt-response';
import { UserService } from '../services/user.service';

@Injectable({
  providedIn: 'root',
})
export class IsOwnerOrStaffGuard  {
  constructor(private userSrv: UserService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.userSrv.loggedObs$.pipe(
      take(1),
      map((res: IJwtResponse | null) => {
        const routeIdStr: string | null = route.paramMap.get('id');
        if (res && routeIdStr) {
          if (parseInt(routeIdStr) == res.user.id) return true;
        }
        if (res) {
          const roles = res.user.roles
          if (roles.includes('ROLE_ADMIN') || roles.includes('ROLE_TEACHER') || roles.includes('ROLE_STAFF')) return true;
        }
        return this.router.createUrlTree(['/']);
      })
    );
  }
  
}
