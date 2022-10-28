import { ICourse } from './icourse';
import { IUserBasicResponse } from './iuser-basic-response';
import { ITeacherBasicResponse } from './iteacher-basic-response';
import { IWeeklyScheduleItem } from './iweekly-schedule-item';

export interface IKlass {
  id: number;
  course: ICourse;
  teachers: ITeacherBasicResponse[];
  students: IUserBasicResponse[];
  weeklySchedule: IWeeklyScheduleItem[];
}
