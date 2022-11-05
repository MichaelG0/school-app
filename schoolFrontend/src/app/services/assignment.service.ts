import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, of } from 'rxjs';
import { IAssignment } from '../interfaces/iassignment';
import { IAssignmentDto } from '../interfaces/iassignment-dto';
import { IPageable } from '../interfaces/ipageable';

@Injectable({
  providedIn: 'root',
})
export class AssignmentService {
  private readonly apiUrl = 'http://localhost:8080/assignments';

  constructor(private http: HttpClient) {}

  create(assignment: IAssignmentDto) {
    return this.http.post<IAssignment>(this.apiUrl, assignment).pipe(catchError(() => of(null)));
  }

  getById(id: number) {
    return this.http.get<IAssignment>(`${this.apiUrl}/${id}`);
  }

  getUpcomingByKlassId(klassId: number, page: number = 0, size: number = 20) {
    return this.http.get<IPageable<IAssignment>>(
      `${this.apiUrl}/class/${klassId}?page=${page}&size=${size}`
    );
  }

  getUpcomingByKlassAndTeacherIds(klassId: number, teacherId: number, page: number = 0, size: number = 20) {
    return this.http.get<IPageable<IAssignment>>(
      `${this.apiUrl}/class/${klassId}/${teacherId}?page=${page}&size=${size}`
    );
  }

  update(id: number, assignment: IAssignmentDto) {
    return this.http.put(this.apiUrl + '/' + id, assignment)
  }

  delete(id: number) {
    return this.http.delete<void>(this.apiUrl + '/' + id).pipe(catchError(() => of(false)));
  }

}
