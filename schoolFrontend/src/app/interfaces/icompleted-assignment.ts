import { IUserBasicResponse } from './iuser-basic-response';

export interface ICompletedAssignment {
  id: number;
  submittedDate: string;
  student: IUserBasicResponse;
  file: string;
  grade: number;
}
