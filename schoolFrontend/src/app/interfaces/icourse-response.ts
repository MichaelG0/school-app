import { ICourseInfo } from "./icourse-info";

export interface ICourseResponse {
  id: number;
  startDate: string;
  endDate: string;
  info: ICourseInfo;
}
