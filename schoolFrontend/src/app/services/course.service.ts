import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ICourse } from '../interfaces/icourse';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private readonly apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getCourses() {
    return this.http.get<ICourse[]>(`${this.apiUrl}/courses`);
  }

  getUpcoming() {
    return this.http.get<ICourse[]>(`${this.apiUrl}/courses/upcoming`);
  }

  getCourseById(id: number) {
    return this.http.get<ICourse>(`${this.apiUrl}/courses/${id}`);
  }
}
