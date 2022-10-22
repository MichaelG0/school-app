import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICourseInfo } from 'src/app/interfaces/icourse-info';
import { CourseService } from 'src/app/services/course.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss']
})
export class CourseComponent implements OnInit {
  course$!: Observable<ICourseInfo>

  constructor(private crsSrv: CourseService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    const idStr: string = this.route.snapshot.paramMap.get('id')!;
    const id: number = parseInt(idStr)
    this.course$ = this.crsSrv.getCourseById(id);
  }

}
