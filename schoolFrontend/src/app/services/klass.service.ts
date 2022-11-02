import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IKlass } from '../interfaces/iklass';

@Injectable({
  providedIn: 'root',
})
export class KlassService {
  private readonly apiUrl = 'http://localhost:8080/classes';

  constructor(private http: HttpClient) {}

  getKlassByStudentId(studentId: number) {
    return this.http.get<IKlass>(`${this.apiUrl}/student/${studentId}`);
  }

  getKlassesByTeacherId(teacherId: number) {
    return this.http.get<IKlass[]>(`${this.apiUrl}/teacher/${teacherId}`);
  }

  getById(id: number) {
    return this.http.get<IKlass>(`${this.apiUrl}/${id}`);
  }

}
