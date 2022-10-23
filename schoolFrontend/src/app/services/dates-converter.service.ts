import { Injectable } from '@angular/core';
import { ICourseDatesConverted } from '../interfaces/icourse-dates-converted';
import { ICourse } from '../interfaces/icourse';

@Injectable({
  providedIn: 'root'
})
export class DatesConverterService {

  constructor() { }

  convertDates(course: ICourse): ICourseDatesConverted {
    const startDateParts: number[] = course.startDate.split('-').map(x => parseInt(x));
    const endDateParts: number[] = course.endDate.split('-').map(x => parseInt(x));    
    const startDate: Date = new Date(startDateParts[0], startDateParts[1] - 1, startDateParts[2]);
    const endDate: Date = new Date(endDateParts[0], endDateParts[1] - 1, endDateParts[2]);
    return { id: course.id, startDate: startDate, endDate: endDate, info: course.info };
  }
  
}
