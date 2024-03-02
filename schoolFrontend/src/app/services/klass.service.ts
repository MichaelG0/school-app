import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IKlassResponse } from '../interfaces/iklass-response';
import { map } from 'rxjs';
import { DatesConverterService } from './dates-converter.service';

@Injectable({
  providedIn: 'root',
})
export class KlassService {
  private readonly apiUrl = 'http://localhost:8080/classes';

  constructor(private http: HttpClient, private datesConv: DatesConverterService) {}

  getKlassByStudentId(studentId: number) {
    return this.http
      .get<IKlassResponse>(`${this.apiUrl}/student/${studentId}`)
      .pipe(map(klass => this.datesConv.convertKlass(klass)));
  }

  getKlassesByTeacherId(teacherId: number) {
    return this.http
      .get<IKlassResponse[]>(`${this.apiUrl}/teacher/${teacherId}`)
      .pipe(map(klasses => this.datesConv.convertKlasses(klasses)));
  }

  getById(id: number) {
    return this.http
      .get<IKlassResponse>(`${this.apiUrl}/${id}`)
      .pipe(map(klass => this.datesConv.convertKlass(klass)));
  }
}
