import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, switchMap, take } from 'rxjs';
import { UserService } from '../services/user.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private userSrv: UserService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return this.userSrv.loggedObs$.pipe(
      take(1),
      switchMap(data => {
        if (!data) return next.handle(request);

        const newRequest = request.clone({
          headers: request.headers.set('Authorization', `Bearer ${data.token}`),
        });

        return next.handle(newRequest);
      })
    );
  }
}
