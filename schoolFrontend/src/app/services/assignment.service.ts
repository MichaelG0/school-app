import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IAssignment } from '../interfaces/iassignment';
import { IPageable } from '../interfaces/ipageable';

@Injectable({
  providedIn: 'root',
})
export class AssignmentService {
  private readonly apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getById(id: number) {
    return this.http.get<IAssignment>(
      `${this.apiUrl}/assignments/${id}`
    );
  }

  getByKlassId(klassId: number, page: number = 0, size: number = 20) {
    return this.http.get<IPageable<IAssignment>>(
      `${this.apiUrl}/assignments/class/${klassId}?page=${page}&size=${size}`
    );
  }
  
}
