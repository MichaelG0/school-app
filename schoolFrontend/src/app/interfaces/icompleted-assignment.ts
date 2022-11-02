import { IUserBasicResponse } from './iuser-basic-response';

export interface ICompletedAssignment {
  id: number;
  title: String;
  submittedDate: string;
  student: IUserBasicResponse;
  link: string;
  grade: number;
}
