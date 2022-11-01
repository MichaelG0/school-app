import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IComplAssignBasicResponseWithAverageGrade } from '../interfaces/icompl-assign-basic-response-with-average-grade';
import { ICompletedAssignment } from '../interfaces/icompleted-assignment';
import { ICompletedAssignmentDto } from '../interfaces/icompleted-assignment-dto';

@Injectable({
  providedIn: 'root',
})
export class CompletedAssignmentService {
  private readonly apiUrl = 'http://localhost:8080/completed_assignments';

  constructor(private http: HttpClient) {}

  create(assignment: ICompletedAssignmentDto) {
    return this.http.post<ICompletedAssignment>(this.apiUrl, assignment);
  }

  getBasicByStudentId(studentId: number) {
    return this.http.get<IComplAssignBasicResponseWithAverageGrade>(`${this.apiUrl}/student/${studentId}`);
  }

  getByStudentAndAssignmentIds(studentId: number, assignmentId: number) {
    return this.http.get<ICompletedAssignment>(`${this.apiUrl}/${studentId}/${assignmentId}`)
  }
}
