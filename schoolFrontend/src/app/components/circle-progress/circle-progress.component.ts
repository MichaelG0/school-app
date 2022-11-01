import { AfterViewInit, Component, ElementRef, Input, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Observable, take } from 'rxjs';
import { IComplAssignBasicResponseWithAverageGrade } from 'src/app/interfaces/icompl-assign-basic-response-with-average-grade';

@Component({
  selector: 'app-circle-progress',
  templateUrl: './circle-progress.component.html',
  styleUrls: ['./circle-progress.component.scss'],
})
export class CircleProgressComponent implements OnInit, AfterViewInit {
  @Input() percentage$!: Observable<number | IComplAssignBasicResponseWithAverageGrade>;
  percentage!: number;
  @ViewChild('circle') circle!: ElementRef;

  constructor(private renderer: Renderer2) {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.percentage$.pipe(take(1)).subscribe((res: number | IComplAssignBasicResponseWithAverageGrade) => {
      this.percentage = typeof res == 'number' ? res : res.averageGrade;
      let offset: string = 377 - (this.percentage * 377 / 100) + ''
      this.renderer.setAttribute(this.circle.nativeElement, 'stroke-dashoffset', offset);
    });
  }
  
}
