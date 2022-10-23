import { ICourseInfo } from './icourse-info';

export interface ICourse {
  id: number;
  startDate: string;
  endDate: string;
  info: ICourseInfo;
}
