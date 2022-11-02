import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ITeacherMPK } from '../interfaces/iteacher-mpk';

@Injectable({
  providedIn: 'root'
})
export class TeacherModulePerKlassService {
  private readonly apiUrl = 'http://localhost:8080/teacher_mpk';

  constructor(private http: HttpClient) {}

  getByTeacherId(teacherId: number) {
    return this.http.get<ITeacherMPK[]>(`${this.apiUrl}/${teacherId}`);
  }

  getByTeacherAndKlassIds(teacherId: number, klassId: number) {
    return this.http.get<ITeacherMPK>(`${this.apiUrl}/${teacherId}/${klassId}`);
  }
  
}
