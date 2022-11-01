import { IUserBasicResponse } from './iuser-basic-response';

export interface ICompletedAssignment {
  id: number;
  submittedDate: string;
  student: IUserBasicResponse;
  link: string;
  grade: number;
}
