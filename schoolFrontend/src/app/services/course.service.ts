import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';
import { DatesConverterService } from './dates-converter.service';
import { ICourseResponse } from '../interfaces/icourse-response';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  private readonly apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient, private datesConv: DatesConverterService) {}

  getCourses() {
    return this.http
      .get<ICourseResponse[]>(`${this.apiUrl}/courses`)
      .pipe(map(courses => this.datesConv.convertCourses(courses)));
  }

  getUpcoming() {
    return this.http
      .get<ICourseResponse[]>(`${this.apiUrl}/courses/upcoming`)
      .pipe(map(courses => this.datesConv.convertCourses(courses)));
  }

  getCourseById(id: number) {
    return this.http
      .get<ICourseResponse>(`${this.apiUrl}/courses/${id}`)
      .pipe(map(course => this.datesConv.convertCourse(course)));
  }
}
