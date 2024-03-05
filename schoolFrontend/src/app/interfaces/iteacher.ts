import { IModule } from './imodule';
import { IWeeklyScheduleItem } from './iweekly-schedule-item';

export interface ITeacher {
  id: 112;
  name: string;
  surname: string;
  gender: string;
  address: string;
  avatar: string;
  phone: string;
  bio: string;
  email: string;
  modules: IModule[];
  weeklySchedule: IWeeklyScheduleItem[];
}
