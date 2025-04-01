import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLinkActive, RouterLink } from '@angular/router';
import { take } from 'rxjs';
import { IUserResponse } from 'src/app/interfaces/iuser-response';
import { UserService } from 'src/app/services/user.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-enrolment-confirmation',
  templateUrl: './enrolment-confirmation.component.html',
  styleUrls: ['./enrolment-confirmation.component.scss'],
  standalone: true,
  imports: [NgIf, RouterLinkActive, RouterLink],
})
export class EnrolmentConfirmationComponent implements OnInit {
  enrolledStudent!: IUserResponse;
  outcome!: string;

  constructor(private route: ActivatedRoute, private usrSrv: UserService) {}

  ngOnInit(): void {
    this.outcome = 'loading';
    const token = this.route.snapshot.paramMap.get('token')!;
    this.usrSrv
      .enrol(token)
      .pipe(take(1))
      .subscribe((res: any) => {
        if (typeof res === 'object') {
          this.enrolledStudent = res;
          this.outcome = 'success';
        } else {
          this.outcome = 'fail';
        }
      });
  }
}
