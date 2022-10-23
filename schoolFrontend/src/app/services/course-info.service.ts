import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ICourseInfo } from '../interfaces/icourse-info';

@Injectable({
  providedIn: 'root',
})
export class CourseInfoService {
  private readonly apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getCourses() {
    return this.http.get<ICourseInfo[]>(`${this.apiUrl}/courseinfo`);
  }

  getCoursesByType(type: string) {
    return this.http.get<ICourseInfo[]>(`${this.apiUrl}/courseinfo/type/${type}`);
  }

  getCourseById(id: number) {
    return this.http.get<ICourseInfo>(`${this.apiUrl}/courseinfo/${id}`);
  }
  
}
