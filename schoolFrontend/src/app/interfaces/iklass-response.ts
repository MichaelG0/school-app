import { ICourseResponse } from './icourse-response';
import { ITeacherBasicResponse } from './iteacher-basic-response';
import { IUserBasicResponse } from './iuser-basic-response';
import { IWeeklyScheduleItem } from './iweekly-schedule-item';

export interface IKlassResponse {
  id: number;
  course: ICourseResponse;
  teachers: ITeacherBasicResponse[];
  students: IUserBasicResponse[];
  weeklySchedule: IWeeklyScheduleItem[];
}
