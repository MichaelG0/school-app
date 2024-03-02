import { ICourseInfo } from './icourse-info';

export interface ICourse {
  id: number;
  startDate: Date;
  endDate: Date;
  info: ICourseInfo;
}
