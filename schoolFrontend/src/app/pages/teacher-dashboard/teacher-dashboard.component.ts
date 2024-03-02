import { Component, OnInit } from '@angular/core';
import { forkJoin, take } from 'rxjs';
import {
  ISuperWeeklyScheduleItem,
  SuperWeeklyScheduleItem,
} from 'src/app/interfaces/isuper-weekly-schedule-item';
import { ITeacherMPK } from 'src/app/interfaces/iteacher-mpk';
import { KlassService } from 'src/app/services/klass.service';
import { TeacherModulePerKlassService } from 'src/app/services/teacher-module-per-klass.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-teacher-dashboard',
  templateUrl: './teacher-dashboard.component.html',
  styleUrls: ['./teacher-dashboard.component.scss'],
})
export class TeacherDashboardComponent implements OnInit {
  teacherMPKList: ITeacherMPK[] = [];
  weeklySchedule: ISuperWeeklyScheduleItem[] = [];

  constructor(
    private usrSrv: UserService,
    private tcrMPKSrv: TeacherModulePerKlassService,
    private klsSrv: KlassService
  ) {}

  ngOnInit(): void {
    this.usrSrv.loggedObs$.pipe(take(1)).subscribe(res => {
      if (!res) return;

      forkJoin([
        this.tcrMPKSrv.getByTeacherId(res.user.id),
        this.klsSrv.getKlassesByTeacherId(res.user.id),
      ]).subscribe(([mpks, klasses]) => {
        this.teacherMPKList = mpks;

        for (const klass of klasses) {
          for (const tcrMpk of this.teacherMPKList) {
            for (const module of tcrMpk.modules) {
              for (const scheItem of klass.weeklySchedule) {
                if (this.weeklySchedule.some(item => item.id == scheItem.id)) {
                  continue;
                }

                if (scheItem.module.name == module) {
                  const superScheItem = new SuperWeeklyScheduleItem();
                  Object.assign(superScheItem, scheItem);
                  superScheItem.klassId = klass.id;
                  superScheItem.course = klass.course;
                  this.weeklySchedule.push(superScheItem);
                }
              }
            }
          }
        }
      });
    });
  }
}
