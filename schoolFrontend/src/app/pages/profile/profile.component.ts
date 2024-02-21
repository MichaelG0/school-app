import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { IUserResponse } from 'src/app/interfaces/iuser-response';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  loggedObs$!: Observable<IJwtResponse | null>;
  user$!: Observable<IUserResponse>;
  props: { name: keyof IUserResponse; displayName: string }[] = [
    { name: 'gender', displayName: 'Gender:' },
    { name: 'bio', displayName: 'Bio:' },
    { name: 'email', displayName: 'Email:' },
    { name: 'phone', displayName: 'Phone:' },
    { name: 'address', displayName: 'Address:' },
  ];

  constructor(private usrSrv: UserService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const idStr: string = this.route.snapshot.paramMap.get('id')!;
    const id: number = parseInt(idStr);
    this.user$ = this.usrSrv.getUser(id);

    this.loggedObs$ = this.usrSrv.loggedObs$;
  }
}
