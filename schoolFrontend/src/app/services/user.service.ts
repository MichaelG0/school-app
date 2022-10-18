import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, from, of } from 'rxjs';
import { catchError, concatMap, tap } from 'rxjs/operators';
import { IJwtResponse } from '../interfaces/ijwt-response';
import { ILoginRequest } from '../interfaces/ilogin-request';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ISignUpRequest } from '../interfaces/isign-up-request';
import { IStudentRequest } from '../interfaces/istudent-request';
import { IUserResponse } from '../interfaces/iuser-response';
import { IPageable } from '../interfaces/ipageable';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly apiUrl = 'http://localhost:8080';

  jwtHelper = new JwtHelperService();
  autoLogoutTimer: any

  private loggedUser = new BehaviorSubject<null | IJwtResponse>(null);
  loggedObs$ = this.loggedUser.asObservable();

  constructor(private http: HttpClient, private router: Router) {
    this.restore();
  }

  restore() {
    let userJson = localStorage.getItem('user');
    if (!userJson)
      return;
    const user: IJwtResponse = JSON.parse(userJson);
    if (this.jwtHelper.isTokenExpired(user.token)) {
      this.logout();
      return;
    }
    this.loggedUser.next(user);
    this.autoLogout(user.token)
  }

  signUp(user: ISignUpRequest) {
    return this.http.post<IJwtResponse>(`${this.apiUrl}/users`, user).pipe(
      tap((res) => {
        localStorage.setItem('user', JSON.stringify(res));
        this.loggedUser.next(res);
      })
    );
  }

  login(authData: ILoginRequest) {
    return this.http.post<IJwtResponse>(`${this.apiUrl}/login`, authData).pipe(
      tap((res) => {
        localStorage.setItem('user', JSON.stringify(res));
        this.autoLogout(res.token)
        this.loggedUser.next(res);
      }),
      catchError(() => of(true))
    );
  }

  logout(navData?: boolean) {
    localStorage.removeItem('user');
    this.loggedUser.next(null);
    this.router.navigate([''], {state: {data: navData}})
  }

  // editUser(id: number, userData: ISignUpRequest) {
  //   return this.http.patch<ISignUpRequest>(`${this.apiUrl}/users/${id}`, userData).pipe(
  //     tap((res: any) => {
  //       const user: IJwtResponse = {
  //         token: JSON.parse(localStorage.getItem('user')!).token,
  //         user: res
  //       }
  //       localStorage.setItem('user', JSON.stringify(user))
  //       this.loggedUser.next(user)
  //     }),
  //     catchError(() => of(false))
  //   );
  // }

  apply(student: IStudentRequest) {
    return this.http.post<IUserResponse>(`${this.apiUrl}/users/apply`, student).pipe(
      tap((res) => {
        const user: IJwtResponse = {
          token: JSON.parse(localStorage.getItem('user')!).token,
          type: 'Bearer',
          user: res
        }
        localStorage.setItem('user', JSON.stringify(user))
        this.loggedUser.next(user);
      }),
      catchError(() => of(true))
    );
  }

  enrol(token: string) {
    return this.http.get<IUserResponse>(`${this.apiUrl}/users/enrol?token=${token}`).pipe(
      catchError(() => of(false))
    )
  }

  getUser(id: number) {
    return this.http.get<IUserResponse>(`${this.apiUrl}/users/${id}`)
  }

  // getSpecificUsers(ids: number[]) {
  //   return from(ids).pipe(
  //     concatMap((id) => this.http.get(`${this.apiUrl}/users/${id}`))
  //   );
  // }

  getUsers(page: number = 0, size: number = 20) {
    return this.http.get<IPageable>(`${this.apiUrl}/users?page=${page}&size=${size}`)
  }

  deleteUser(id: number) {
    return this.http.delete<void>(`${this.apiUrl}/users/${id}`)
  }

  addRole(id: number, roleName: string) {
    return this.http.put<void>(`${this.apiUrl}/users/${id}/add_role`, roleName)
  }

  removeRole(id: number, roleName: string) {
    return this.http.put<void>(`${this.apiUrl}/users/${id}/remove_role`, roleName)
  }

  autoLogout(at: string) {
    const exDate = this.jwtHelper.getTokenExpirationDate(at) as Date;
    const exMs = exDate.getTime() - new Date().getTime()

    this.autoLogoutTimer = setTimeout(() => {
      this.logout();
    }, exMs)
  }

}
