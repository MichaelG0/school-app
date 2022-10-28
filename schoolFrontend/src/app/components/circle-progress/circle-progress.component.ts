import { AfterViewInit, Component, ElementRef, Input, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Observable, take } from 'rxjs';

@Component({
  selector: 'app-circle-progress',
  templateUrl: './circle-progress.component.html',
  styleUrls: ['./circle-progress.component.scss'],
})
export class CircleProgressComponent implements OnInit, AfterViewInit {
  @Input() percentage$!: Observable<number>;
  percentage!: number;
  @ViewChild('circle') circle!: ElementRef;

  constructor(private renderer: Renderer2) {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.percentage$.pipe(take(1)).subscribe((res: number) => {
      this.percentage = res;
      let offset: string = 440 - (res * 440 / 100) + ''
      this.renderer.setAttribute(this.circle.nativeElement, 'stroke-dashoffset', offset);
    });
  }
  
}
