import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ICourse } from 'src/app/interfaces/icourse';
import { CourseService } from 'src/app/services/course.service';

@Component({
  selector: 'app-graduate',
  templateUrl: './graduate.component.html',
  styleUrls: ['./graduate.component.scss']
})
export class GraduateComponent implements OnInit {
  courses$!: Observable<ICourse[]>

  constructor(private crsSrv: CourseService) { }

  ngOnInit(): void {
    this.courses$ = this.crsSrv.getCoursesByType('GRADUATE')
  }

}
