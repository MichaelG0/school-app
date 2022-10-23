import { ICourseInfo } from "./icourse-info";

export interface ICourseDatesConverted {
  id: number;
  startDate: Date;
  endDate: Date;
  info: ICourseInfo;
}
