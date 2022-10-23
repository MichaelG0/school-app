import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IKlass } from '../interfaces/iklass';

@Injectable({
  providedIn: 'root',
})
export class KlassService {
  private readonly apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getKlassByStudentId(id: number) {
    return this.http.get<IKlass>(`${this.apiUrl}/classes/student/${id}`);
  }
}
