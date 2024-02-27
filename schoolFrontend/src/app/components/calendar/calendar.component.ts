import { animate, style, transition, trigger } from '@angular/animations';
import {
  AfterViewInit,
  Component,
  ElementRef,
  HostListener,
  Input,
  OnDestroy,
  OnInit,
  QueryList,
  Renderer2,
  ViewChild,
  ViewChildren,
} from '@angular/core';
import { Subject, Subscription, takeUntil } from 'rxjs';
import { ICourseDatesConverted } from 'src/app/interfaces/icourse-dates-converted';
import { IKlass } from 'src/app/interfaces/iklass';
import { IWeeklyScheduleItem } from 'src/app/interfaces/iweekly-schedule-item';
import { DatesConverterService } from 'src/app/services/dates-converter.service';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss'],
  animations: [
    trigger('slideIn', [
      transition(
        ':enter',
        [
          style({ transform: 'translateX({{ translateX }}%)' }),
          animate('300ms ease-in-out', style({ transform: 'translateX(0)' })),
        ],
        { params: { translateX: 0 } }
      ),
    ]),
  ],
})
export class CalendarComponent implements OnInit, AfterViewInit, OnDestroy {
  @Input() klass!: IKlass;
  @ViewChild('wrapp') readonly wrapp!: ElementRef<HTMLElement>;
  @ViewChild('roller') readonly roller!: ElementRef<HTMLElement>;
  @ViewChildren('sched') readonly scheDivs?: QueryList<ElementRef>;
  readonly unsub$ = new Subject<void>();
  scheduleSubscription?: Subscription;
  course!: ICourseDatesConverted;
  schedule: IWeeklyScheduleItem[] = [];
  days!: Date[];
  readonly daysLength = 15; // keep this an odd number
  readonly selectedDayIndex = this.daysLength / 2 - 0.5;
  readonly hours = [
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
  translateX = 0;
  showMonthDropdown = false;

  constructor(private renderer: Renderer2, private datesConv: DatesConverterService) {}

  ngOnInit(): void {
    this.course = this.datesConv.convertDates(this.klass.course);
    // console.log(this.klass);
    this.buildCalendar();
    this.makeSchedule();
  }

  ngAfterViewInit(): void {
    this.styleSchedule();
    this.scheduleSubscription = this.scheDivs!.changes.pipe(takeUntil(this.unsub$)).subscribe(() => {
      this.styleSchedule();
    });
  }

  private buildCalendar(date: Date = new Date()) {
    let days: Date[] = [];
    for (let i = 0; i < this.daysLength; i++) {
      days[i] = this.addDays(date, i - this.selectedDayIndex);
    }
    this.days = days;
  }

  private makeSchedule() {
    const weekDay = this.days[this.selectedDayIndex]
      .toLocaleDateString('en-us', { weekday: 'short' })
      .toUpperCase();
    this.schedule = this.klass.weeklySchedule.filter(x => x.weekDay == weekDay);
  }

  private styleSchedule() {
    if (this.scheDivs?.length) {
      let index = 0;
      for (let scheItem of this.schedule) {
        let startTime: number = parseInt(scheItem.startTime.substring(0, 2));
        let offset: number = startTime - 8;
        let height: number = parseInt(scheItem.endTime.substring(0, 2)) - startTime;
        let el = this.scheDivs!.get(index)?.nativeElement;
        this.renderer.setStyle(el, 'margin-top', 42.4 * offset + 'px');
        this.renderer.setStyle(el, 'height', 42.9 * height + 'px');
        this.renderer.setStyle(el, 'border', '4px solid ' + scheItem.module.renderColor);
        index++;
      }
    }
  }

  selectDayByClicking(dayIndex: number) {
    this.buildCalendar(this.days[dayIndex]);
    this.makeSchedule();
    this.translateX = 143 * (dayIndex - this.selectedDayIndex);
  }

  private selectDayByDragging() {
    const dayToPass = this.addDays(this.days[this.selectedDayIndex], this.dayIndex - this.selectedDayIndex);
    this.buildCalendar(dayToPass);
    this.makeSchedule();
    let tempTransX =
      (-this.offsetX +
        (this.daysLength - 1) * 21.4 -
        (this.daysLength * 42.8 - this.wrapp.nativeElement.offsetWidth) / 2) %
      42.8;
    tempTransX = ((tempTransX + 21.4) % 42.8) - 21.4;
    this.translateX = -tempTransX * 3;
  }

  doCloseDropdown(date: Date | void) {
    if (date) {
      const datesDifference = date.getTime() - this.getSelectedDay().getTime();

      if (datesDifference == 0) {
        this.translateX = 0;
      } else if (datesDifference > 0) {
        this.translateX = 143 * this.selectedDayIndex - this.wrapp.nativeElement.offsetWidth * 1.5;
      } else {
        this.translateX = -143 * this.selectedDayIndex + this.wrapp.nativeElement.offsetWidth * 1.5;
      }

      this.buildCalendar(date);
      this.makeSchedule();
    }

    this.showMonthDropdown = false;
  }

  private addDays(date: Date, days: number): Date {
    const result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
  }

  ngOnDestroy(): void {
    this.unsub$.next();
    this.unsub$.complete();
  }

  //======================== PIPED METHODS ========================

  getSelectedDay = (): Date => {
    return this.days[this.selectedDayIndex];
  };

  getDayName = (day: Date): string => {
    return day.toLocaleDateString('en-us', { weekday: 'short' });
  };

  getDayNumber = (day: Date): number => {
    return day.getDate();
  };

  lessonCondition = (): boolean => {
    return (
      this.days[this.selectedDayIndex] >= this.course.startDate &&
      this.days[this.selectedDayIndex] <= this.course.endDate
    );
  };

  isActiveDay = (dayIndex: number): boolean => {
    return dayIndex == this.selectedDayIndex;
  };

  //======================== DRAG ========================

  isDragging = false;
  dragged = false;
  initialX = 0;
  offsetX = 0;
  dayIndex = this.daysLength / 2 - 0.5;

  onDragStart(event: MouseEvent | TouchEvent) {
    this.isDragging = true;
    this.dragged = false;
    const clientX = event instanceof MouseEvent ? event.clientX : event.touches[0].clientX;
    this.initialX = clientX - this.roller.nativeElement.offsetLeft;
    this.translateX = 0;
  }

  @HostListener('document:mousemove', ['$event'])
  @HostListener('document:touchmove', ['$event'])
  onDragging(event: MouseEvent | TouchEvent) {
    if (this.isDragging) {
      this.dragged = true;

      if (this.dayIndex < this.selectedDayIndex || this.dayIndex > this.selectedDayIndex) {
        const dayToPass = this.addDays(
          this.days[this.selectedDayIndex],
          this.dayIndex - this.selectedDayIndex
        );
        this.buildCalendar(dayToPass);
        this.initialX -= 42.8 * (this.dayIndex - this.selectedDayIndex);
      }

      const clientX = event instanceof MouseEvent ? event.clientX : event.touches[0].clientX;
      this.offsetX = clientX - this.initialX;
      this.renderer.setStyle(this.roller.nativeElement, 'left', this.offsetX + 'px');
      this.dayIndex = Math.round(
        -this.offsetX / 42.8 +
          (this.daysLength - 1) / 2 -
          (this.daysLength - this.wrapp.nativeElement.offsetWidth / 42.8) / 2
      );
    }
  }

  @HostListener('document:mouseup')
  @HostListener('document:touchend')
  onDragEnd() {
    if (this.dragged) {
      this.renderer.setStyle(this.roller.nativeElement, 'left', 'auto');
      this.selectDayByDragging();
    }
    this.isDragging = false;
    this.dragged = false;
  }
}
