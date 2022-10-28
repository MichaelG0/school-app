import { transition, style, animate, trigger } from '@angular/animations';
import {
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  QueryList,
  Renderer2,
  ViewChildren,
} from '@angular/core';
import { Subject, Subscription, takeUntil } from 'rxjs';
import { ICourseDatesConverted } from 'src/app/interfaces/icourse-dates-converted';
import { IKlass } from 'src/app/interfaces/iklass';
import { IWeeklyScheduleItem } from 'src/app/interfaces/iweekly-schedule-item';
import { DatesConverterService } from 'src/app/services/dates-converter.service';

const rollTra = transition(':enter', [
  //
  style({ opacity: 0 }),
  animate('1s ease', style({ opacity: 1 })),
]);
const roll = trigger('roll', [rollTra]);

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss'],
  animations: [roll],
})
export class CalendarComponent implements OnInit, AfterViewInit, OnDestroy {
  unsubscribe$ = new Subject<void>();
  scheduleSubscription?: Subscription;
  @Input() klass!: IKlass;
  course!: ICourseDatesConverted;
  schedule: IWeeklyScheduleItem[] = [];
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
  @ViewChildren('day') dayDivs!: QueryList<ElementRef>;
  @ViewChildren('sched') scheDivs?: QueryList<ElementRef>;

  constructor(private renderer: Renderer2, private datesConv: DatesConverterService) {}

  ngOnInit(): void {
    this.course = this.datesConv.convertDates(this.klass.course);
    // console.log(this.klass);
    this.buildWeek();
    this.makeSchedule();
  }

  ngAfterViewInit(): void {
    this.renderer.addClass(this.dayDivs.get(3)!.nativeElement, 'active-day');
    this.dayDivs.changes.pipe(takeUntil(this.unsubscribe$)).subscribe(res => {
      this.renderer.addClass(res.get(3)!.nativeElement, 'active-day');
    });
    this.styleSchedule();
    this.styleScheduleSubs();
  }

  buildWeek(date: Date = new Date()) {
    let week: Date[] = [];
    for (let i = 0; i < 7; i++) {
      week[i] = this.addDays(date, i - 3);
    }
    this.days = week;
  }

  makeSchedule() {
    const weekDay = this.days[3].toLocaleDateString('en-us', { weekday: 'short' }).toUpperCase();
    const schedule = this.klass.weeklySchedule.filter(x => x.weekDay == weekDay);
    this.schedule = schedule;
  }

  styleSchedule() {
    if (this.scheDivs && this.scheDivs.length > 0) {
      let index = 0;
      for (let scheItem of this.schedule) {
        let startTime: number = parseInt(scheItem.startTime.substring(0, 2));
        let offset: number = startTime - 8;
        let height: number = parseInt(scheItem.endTime.substring(0, 2)) - startTime;
        let el = this.scheDivs!.get(index)?.nativeElement
        this.renderer.setStyle(el, 'margin-top', 42.4 * offset + 'px');
        this.renderer.setStyle(el, 'height', 42.9 * height + 'px');
        this.renderer.setStyle(el, 'border', '4px solid ' + scheItem.module.renderColor)
        index++;
      }
    }
  }

  styleScheduleSubs() {
    if (!this.scheduleSubscription) {
      this.scheduleSubscription = this.scheDivs!.changes.pipe(takeUntil(this.unsubscribe$)).subscribe(() => {
        this.styleSchedule();
      });
    }
  }

  selectDay(event: Event, date: Date) {
    this.buildWeek(date);
    this.makeSchedule();
    this.styleScheduleSubs();
    this.dayDivs.forEach(div => this.renderer.removeClass(div.nativeElement, 'active-day'));
    this.renderer.addClass(event.target, 'active-day');
  }

  addDays(date: Date, days: number) {
    let result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
  }

  lessonCondition() {
    return (
      this.days[3] >= this.course.startDate &&
      this.days[3] <= this.course.endDate
    );
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
