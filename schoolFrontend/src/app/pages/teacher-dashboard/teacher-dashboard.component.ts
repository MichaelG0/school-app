import { Component, OnInit } from '@angular/core';
import { forkJoin, take } from 'rxjs';
import { ITeacherMPK } from 'src/app/interfaces/iteacher-mpk';
import { IWeeklyScheduleItem } from 'src/app/interfaces/iweekly-schedule-item';
import { TeacherModulePerKlassService } from 'src/app/services/teacher-module-per-klass.service';
import { TeacherService } from 'src/app/services/teacher.service';
import { UserService } from 'src/app/services/user.service';
import { NgIf, NgFor } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CalendarComponent } from '../../components/calendar/calendar.component';

@Component({
  selector: 'app-teacher-dashboard',
  templateUrl: './teacher-dashboard.component.html',
  styleUrls: ['./teacher-dashboard.component.scss'],
  standalone: true,
  imports: [NgIf, NgFor, RouterLink, CalendarComponent],
})
export class TeacherDashboardComponent implements OnInit {
  teacherMPKList: ITeacherMPK[] = [];
  weeklySchedule: IWeeklyScheduleItem[] = [];

  constructor(
    private usrSrv: UserService,
    private tcrMPKSrv: TeacherModulePerKlassService,
    private tcrSrv: TeacherService
  ) {}

  ngOnInit(): void {
    this.usrSrv.loggedObs$.pipe(take(1)).subscribe(res => {
      if (!res) return;

      forkJoin([this.tcrMPKSrv.getByTeacherId(res.user.id), this.tcrSrv.getById(res.user.id)]).subscribe(
        ([mpks, teacher]) => {
          this.teacherMPKList = mpks;
          this.weeklySchedule = teacher.weeklySchedule;
        }
      );
    });
  }
}
