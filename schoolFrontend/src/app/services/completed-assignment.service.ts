import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IComplAssignBasicResponseWithAverageGrade } from '../interfaces/icompl-assign-basic-response-with-average-grade';
import { ICompletedAssignment } from '../interfaces/icompleted-assignment';
import { ICompletedAssignmentDto } from '../interfaces/icompleted-assignment-dto';
import { IPageable } from '../interfaces/ipageable';

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
    return this.http.get<ICompletedAssignment>(`${this.apiUrl}/${studentId}/${assignmentId}`);
  }

  getByKlassAndTeacherIds(klassId: number, teacherId: number, page: number = 0, size: number = 20) {
    return this.http.get<IPageable<ICompletedAssignment>>(
      `${this.apiUrl}/class/${klassId}/${teacherId}?page=${page}&size=${size}`
    );
  }

  assignGrade(id: number, grade: number) {
    return this.http.put<ICompletedAssignment>(`${this.apiUrl}/${id}/assess`, grade);
  }
  
}
