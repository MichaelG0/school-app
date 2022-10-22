import {
  AfterViewInit,
  Component,
  ElementRef,
  OnDestroy,
  OnInit,
  QueryList,
  Renderer2,
  ViewChildren,
} from '@angular/core';
import { animate, style, transition, trigger } from '@angular/animations';
import { Subject, takeUntil } from 'rxjs';

const rollTra = transition(':enter', [
  //
  style({ opacity: 0 }),
  animate('1s ease', style({ opacity: 1 })),
]);
const roll = trigger('roll', [rollTra]);

@Component({
  selector: 'app-student-dashboard',
  templateUrl: './student-dashboard.component.html',
  styleUrls: ['./student-dashboard.component.scss'],
  animations: [roll],
})
export class StudentDashboardComponent implements OnInit, AfterViewInit, OnDestroy {
  private unsubscribe$ = new Subject<void>();
  date: Date = new Date();
  days!: Date[];
  hours = [
    '8 am',
    '9 am',
    '10 am',
    '11 am',
    '12 am',
    '1 pm',
    '2 pm',
    '3 pm',
    '4 pm',
    '5 pm',
    '6 pm',
    '7 pm',
  ];
  @ViewChildren('day') divs!: QueryList<ElementRef>;

  constructor(private renderer: Renderer2) {}

  ngOnInit(): void {
    this.buildWeek();
  }

  ngAfterViewInit(): void {
    this.renderer.addClass(this.divs.get(3)!.nativeElement, 'active-day');
    this.divs.changes.pipe(takeUntil(this.unsubscribe$)).subscribe(res => {
      this.renderer.addClass(res.get(3)!.nativeElement, 'active-day');
    });
  }

  buildWeek(date: Date = this.date) {
    let week: Date[] = [];
    for (let i = 0; i < 7; i++) {
      week[i] = this.addDays(date, i - 3);
    }
    this.days = week;
  }

  selectDay(event: Event, date: Date) {
    this.buildWeek(date);
    this.divs.forEach(div => this.renderer.removeClass(div.nativeElement, 'active-day'));
    this.renderer.addClass(event.target, 'active-day');
  }

  addDays(date: Date, days: number) {
    let result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
