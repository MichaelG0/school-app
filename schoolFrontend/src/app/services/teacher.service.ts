import { Injectable } from '@angular/core';
import { ITeacher } from '../interfaces/iteacher';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class TeacherService {
  private readonly apiUrl = 'http://localhost:8080/teachers';

  constructor(private http: HttpClient) {}

  getById(teacherId: number) {
    return this.http.get<ITeacher>(`${this.apiUrl}/${teacherId}`);
  }
}
