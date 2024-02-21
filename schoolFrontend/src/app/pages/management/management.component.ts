import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IPageable } from 'src/app/interfaces/ipageable';
import { IUserResponse } from 'src/app/interfaces/iuser-response';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-management',
  templateUrl: './management.component.html',
  styleUrls: ['./management.component.scss'],
})
export class ManagementComponent implements OnInit {
  loggedObs$ = this.usrSrv.loggedObs$;
  users$!: Observable<IPageable<IUserResponse>>;
  roles: { name: string; displayName: string }[] = [
    { name: 'ROLE_GUEST', displayName: 'Guest' },
    { name: 'ROLE_STUDENT', displayName: 'Student' },
    { name: 'ROLE_STAFF', displayName: 'Staff' },
    { name: 'ROLE_TEACHER', displayName: 'Teacher' },
    { name: 'ROLE_ADMIN', displayName: 'Admin' },
  ];
  page = 0;

  constructor(private usrSrv: UserService) {}

  ngOnInit(): void {
    this.users$ = this.usrSrv.getUsers(this.page, 12);
  }

  toggleRole(user: IUserResponse, roleName: string) {
    if (user.roles.includes(roleName)) this.usrSrv.removeRole(user.id, roleName).subscribe();
    else this.usrSrv.addRole(user.id, roleName).subscribe();
  }

  paginate(value: number) {
    this.page += value;
    this.users$ = this.usrSrv.getUsers(this.page, 12);
  }

  goToPage(value: number) {
    this.page = value;
    this.users$ = this.usrSrv.getUsers(this.page, 12);
  }
}
