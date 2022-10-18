import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IPageable } from 'src/app/interfaces/ipageable';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-management',
  templateUrl: './management.component.html',
  styleUrls: ['./management.component.scss'],
})
export class ManagementComponent implements OnInit {
  users$!: Observable<IPageable>;
  roles: string[] = ['Guest', 'Student', 'Staff', 'Teacher', 'Admin'];
  page: number = 0

  constructor(private usrSrv: UserService) {}

  ngOnInit(): void {
    this.users$ = this.usrSrv.getUsers(this.page, 3);
  }

  changeRole(id: number, roleName: string, element: HTMLInputElement) {
    if (element.checked) this.usrSrv.addRole(id, roleName);
    else this.usrSrv.removeRole(id, roleName);
  }

  paginate(value: number) {
    this.page += value
    this.users$ = this.usrSrv.getUsers(this.page, 3);
  }

}
