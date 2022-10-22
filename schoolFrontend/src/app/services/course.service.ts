import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ICourseInfo } from '../interfaces/icourse-info';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  private readonly apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getCourses() {
    return this.http.get<ICourseInfo[]>(`${this.apiUrl}/courses`);
  }

  getCoursesByType(type: string) {
    return this.http.get<ICourseInfo[]>(`${this.apiUrl}/courses/type/${type}`);
  }

  getCourseById(id: number) {
    return this.http.get<ICourseInfo>(`${this.apiUrl}/courses/${id}`);
  }
}
