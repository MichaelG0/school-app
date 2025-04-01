import { inject } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';
import { switchMap, take } from 'rxjs';
import { UserService } from '../services/user.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const userSrv = inject(UserService);

  return userSrv.loggedObs$.pipe(
    take(1),
    switchMap(data => {
      if (!data) return next(req);

      const newRequest = req.clone({ headers: req.headers.set('Authorization', `Bearer ${data.token}`) });
      return next(newRequest);
    })
  );
};
