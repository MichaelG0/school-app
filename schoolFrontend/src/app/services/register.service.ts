import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RegisterService {
  private readonly apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getAttendance(studentId: number, klassId: number) {
    return this.http.get<number>(`${this.apiUrl}/class_register/${studentId}/${klassId}`);
  }

}
