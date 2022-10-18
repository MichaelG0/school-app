import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ICourse } from 'src/app/interfaces/icourse';
import { CourseService } from 'src/app/services/course.service';

@Component({
  selector: 'app-undergraduate',
  templateUrl: './undergraduate.component.html',
  styleUrls: ['./undergraduate.component.scss']
})
export class UndergraduateComponent implements OnInit {
  courses$!: Observable<ICourse[]>

  constructor(private crsSrv: CourseService) { }

  ngOnInit(): void {
    this.courses$ = this.crsSrv.getCoursesByType('UNDERGRADUATE')
  }

}
