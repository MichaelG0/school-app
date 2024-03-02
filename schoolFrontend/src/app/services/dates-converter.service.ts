import { Injectable } from '@angular/core';
import { ICourseResponse } from '../interfaces/icourse-response';
import { ICourse } from '../interfaces/icourse';
import { IKlassResponse } from '../interfaces/iklass-response';
import { IKlass, Klass } from '../interfaces/iklass';

@Injectable({
  providedIn: 'root',
})
export class DatesConverterService {
  constructor() {}

  convertCourse(course: ICourseResponse): ICourse {
    const startDateParts: number[] = course.startDate.split('-').map(x => parseInt(x));
    const endDateParts: number[] = course.endDate.split('-').map(x => parseInt(x));
    const startDate: Date = new Date(startDateParts[0], startDateParts[1] - 1, startDateParts[2]);
    const endDate: Date = new Date(endDateParts[0], endDateParts[1] - 1, endDateParts[2]);
    return { id: course.id, startDate: startDate, endDate: endDate, info: course.info };
  }

  convertCourses(courses: ICourseResponse[]): ICourse[] {
    const convertedCourses: ICourse[] = [];

    for (const course of courses) {
      const startDateParts: number[] = course.startDate.split('-').map(x => parseInt(x));
      const endDateParts: number[] = course.endDate.split('-').map(x => parseInt(x));
      const startDate: Date = new Date(startDateParts[0], startDateParts[1] - 1, startDateParts[2]);
      const endDate: Date = new Date(endDateParts[0], endDateParts[1] - 1, endDateParts[2]);
      convertedCourses.push({ id: course.id, startDate: startDate, endDate: endDate, info: course.info });
    }

    return convertedCourses;
  }

  convertKlass(klass: IKlassResponse): IKlass {
    const startDateParts: number[] = klass.course.startDate.split('-').map(x => parseInt(x));
    const endDateParts: number[] = klass.course.endDate.split('-').map(x => parseInt(x));
    const startDate: Date = new Date(startDateParts[0], startDateParts[1] - 1, startDateParts[2]);
    const endDate: Date = new Date(endDateParts[0], endDateParts[1] - 1, endDateParts[2]);

    const convertedKlass = new Klass();
    Object.assign(convertedKlass, klass);
    convertedKlass.course = {
      id: klass.course.id,
      startDate: startDate,
      endDate: endDate,
      info: klass.course.info,
    };
    return convertedKlass;
  }

  convertKlasses(klasses: IKlassResponse[]): IKlass[] {
    const convertedKlasses: IKlass[] = [];

    for (const klass of klasses) {
      const startDateParts: number[] = klass.course.startDate.split('-').map(x => parseInt(x));
      const endDateParts: number[] = klass.course.endDate.split('-').map(x => parseInt(x));
      const startDate: Date = new Date(startDateParts[0], startDateParts[1] - 1, startDateParts[2]);
      const endDate: Date = new Date(endDateParts[0], endDateParts[1] - 1, endDateParts[2]);

      const convertedKlass = new Klass();
      Object.assign(convertedKlass, klass);
      convertedKlass.course = {
        id: klass.course.id,
        startDate: startDate,
        endDate: endDate,
        info: klass.course.info,
      };
      convertedKlasses.push(convertedKlass);
    }

    return convertedKlasses;
  }
}
