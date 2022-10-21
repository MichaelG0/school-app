import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subject, takeUntil } from 'rxjs';
import { ICourse } from 'src/app/interfaces/icourse';
import { CourseService } from 'src/app/services/course.service';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.scss'],
})
export class CourseListComponent implements OnInit, OnDestroy {
  private unsubscribe$ = new Subject<void>();
  title!: string;
  courses$!: Observable<ICourse[]>;

  constructor(private crsSrv: CourseService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.route.paramMap.pipe(takeUntil(this.unsubscribe$)).subscribe(res => {
      this.title = res.get('type') || '';
      if (!(res.get('type') == 'undergraduate' || res.get('type') == 'graduate'))
        this.router.navigate(['/']);
      this.courses$ = this.crsSrv.getCoursesByType(this.title.toUpperCase());
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}