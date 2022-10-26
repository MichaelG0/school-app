import { AfterViewInit, Component, ElementRef, Input, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Observable, take } from 'rxjs';

@Component({
  selector: 'app-circle-progress',
  templateUrl: './circle-progress.component.html',
  styleUrls: ['./circle-progress.component.scss'],
})
export class CircleProgressComponent implements OnInit, AfterViewInit {
  @Input() attendance$!: Observable<number>;
  attendance!: number;
  @ViewChild('percentage') percentage!: ElementRef;

  constructor(private renderer: Renderer2) {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.attendance$.pipe(take(1)).subscribe((res: number) => {
      this.attendance = res;
      let offset: string = 440 - (res * 440 / 100) + ''
      this.renderer.setAttribute(this.percentage.nativeElement, 'stroke-dashoffset', offset);
    });
  }
}
