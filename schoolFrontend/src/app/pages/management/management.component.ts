import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, Observable, take, tap } from 'rxjs';
import { IPageable, Pageable } from 'src/app/interfaces/ipageable';
import { IUserResponse } from 'src/app/interfaces/iuser-response';
import { UserService } from 'src/app/services/user.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { NgIf, NgFor, AsyncPipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PaginatorComponent } from '../../components/paginator/paginator.component';
import { PureFunctionPipe } from '../../pipes/pure-function/pure-function.pipe';

@Component({
  selector: 'app-management',
  templateUrl: './management.component.html',
  styleUrls: ['./management.component.scss'],
  imports: [NgIf, NgFor, RouterLink, PaginatorComponent, AsyncPipe, PureFunctionPipe],
})
export class ManagementComponent implements OnInit {
  loggedObs$ = this.usrSrv.loggedObs$.pipe(takeUntilDestroyed());
  loggedUser: IJwtResponse | null = null;
  users$!: Observable<IPageable<IUserResponse>>;
  usersRolesMap: Map<number /*userId*/, string[] /*roles*/> = new Map();
  roles: { name: string; displayName: string }[] = [
    { name: 'ROLE_GUEST', displayName: 'Guest' },
    { name: 'ROLE_STUDENT', displayName: 'Student' },
    { name: 'ROLE_STAFF', displayName: 'Staff' },
    { name: 'ROLE_TEACHER', displayName: 'Teacher' },
    { name: 'ROLE_ADMIN', displayName: 'Admin' },
  ];
  page = 0;
  readonly pageSize = 12;
  private initialUsers = new Pageable<IUserResponse>();
  private readonly usersSubject = new BehaviorSubject<IPageable<IUserResponse>>(this.initialUsers);
  savedTableContent = new Pageable<IUserResponse>();

  constructor(private usrSrv: UserService) {}

  ngOnInit(): void {
    this.loggedObs$.subscribe(res => (this.loggedUser = res));
    this.users$ = this.usrSrv.getUsers(this.page, this.pageSize).pipe(
      tap(res => {
        this.initialUsers = JSON.parse(JSON.stringify(res));
        this.savedTableContent = res;
      })
    );
  }

  toggleRole(user: IUserResponse, roleName: string) {
    if (user.roles.includes(roleName)) {
      const roleIndex = user.roles.indexOf(roleName);
      user.roles.splice(roleIndex, 1);
    } else {
      user.roles.push(roleName);
    }
    this.usersRolesMap.set(user.id, user.roles);
  }

  undoChanges() {
    this.usersRolesMap.clear();
    this.users$ = this.usersSubject.asObservable();
    this.usersSubject.next(JSON.parse(JSON.stringify(this.initialUsers)));
  }

  save() {
    this.usrSrv
      .editUsersRoles(this.usersRolesMap)
      .pipe(take(1))
      .subscribe(res => {
        for (const editedUser of res) {
          const userToReplace = this.initialUsers.content.find(user => user.id == editedUser.id);
          if (userToReplace) {
            const indexToReplace = this.initialUsers.content.indexOf(userToReplace);
            this.initialUsers.content[indexToReplace] = editedUser;
          }
        }

        this.undoChanges();
      });
  }

  goToPage(value: number) {
    this.page = value;
    this.users$ = this.usrSrv.getUsers(this.page, this.pageSize).pipe(
      tap(res => {
        this.initialUsers = JSON.parse(JSON.stringify(res));
        for (const userId of this.usersRolesMap.keys()) {
          for (const user of res.content) {
            if (user.id == userId) user.roles = this.usersRolesMap.get(userId) as string[];
          }
        }
        this.savedTableContent = res;
      })
    );
  }

  disabledCondition = (user: IUserResponse, roleName: string) => {
    return (
      !this.loggedUser?.user?.roles?.includes('ROLE_ADMIN') &&
      (user.roles.includes('ROLE_ADMIN') || roleName == 'ROLE_ADMIN')
    );
  };
}
