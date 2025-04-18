import { animate, style, transition, trigger } from '@angular/animations';
import {
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  QueryList,
  Renderer2,
  ViewChild,
  ViewChildren,
} from '@angular/core';
import { Subject, Subscription, takeUntil } from 'rxjs';
import { IWeeklyScheduleItem } from 'src/app/interfaces/iweekly-schedule-item';
import { NgIf, NgFor, NgClass } from '@angular/common';
import { MonthYearDropdownComponent } from '../month-year-dropdown/month-year-dropdown.component';
import { PureFunctionPipe } from '../../pipes/pure-function/pure-function.pipe';

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
  imports: [NgIf, MonthYearDropdownComponent, NgFor, NgClass, PureFunctionPipe],
})
export class CalendarComponent implements OnInit, AfterViewInit, OnDestroy {
  @Input() weeklySchedule: IWeeklyScheduleItem[] = [];
  @Input() showKlass = true;
  @ViewChild('wrapp') readonly wrapp!: ElementRef<HTMLElement>;
  @ViewChild('roller') readonly roller!: ElementRef<HTMLElement>;
  @ViewChildren('sched') readonly scheDivs: QueryList<ElementRef<HTMLElement>> = new QueryList();
  readonly unsub$ = new Subject<void>();
  scheduleSubscription?: Subscription;
  daySchedule: IWeeklyScheduleItem[] = [];
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

  constructor(private renderer: Renderer2) {}

  ngOnInit(): void {
    this.buildCalendar();
    this.makeSchedule();
  }

  ngAfterViewInit(): void {
    this.styleSchedule();
    this.scheduleSubscription = this.scheDivs.changes.pipe(takeUntil(this.unsub$)).subscribe(() => {
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
    this.daySchedule = this.weeklySchedule.filter(x => x.weekDay == weekDay);
  }

  private styleSchedule() {
    if (this.scheDivs.length) {
      for (let i = 0; i < this.daySchedule.length; i++) {
        const startTime = parseInt(this.daySchedule[i].startTime.substring(0, 2));
        const offset = startTime - 8;
        const height = parseInt(this.daySchedule[i].endTime.substring(0, 2)) - startTime;
        const el = this.scheDivs.get(i)?.nativeElement;
        if (el) {
          this.renderer.setStyle(el, 'margin-top', 42.4 * offset + 'px');
          this.renderer.setStyle(el, 'height', 42.9 * height + 'px');
          if (this.showKlass)
            this.renderer.setStyle(el, 'border', '4px solid ' + this.daySchedule[i].klass.renderColor);
          else this.renderer.setStyle(el, 'border', '4px solid ' + this.daySchedule[i].module.renderColor);
          const gap = el.offsetHeight < 80 ? 0 : 1;
          this.renderer.setStyle(el, 'gap', gap + 'rem');
        }
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

  lessonCondition = (days: Date[] /* pipe trigger */, scheduleItem: IWeeklyScheduleItem): boolean => {
    return (
      this.days[this.selectedDayIndex] < scheduleItem.klass.course.startDate ||
      this.days[this.selectedDayIndex] > scheduleItem.klass.course.endDate
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
  private unlistenMouseMove!: () => void;
  private unlistenTouchMove!: () => void;
  private unlistenMouseUp!: () => void;
  private unlistenTouchEnd!: () => void;

  onDragStart(event: MouseEvent | TouchEvent) {
    this.isDragging = true;
    this.dragged = false;
    const clientX = event instanceof MouseEvent ? event.clientX : event.touches[0].clientX;
    this.initialX = clientX - this.roller.nativeElement.offsetLeft;
    this.translateX = 0;
    // LISTENERS
    this.unlistenMouseMove = this.renderer.listen('document', 'mousemove', event => this.onDragging(event));
    this.unlistenTouchMove = this.renderer.listen('document', 'touchmove', event => this.onDragging(event));
    this.unlistenMouseUp = this.renderer.listen('document', 'mouseup', () => this.onDragEnd());
    this.unlistenTouchEnd = this.renderer.listen('document', 'touchend', () => this.onDragEnd());
  }

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

  onDragEnd() {
    if (this.dragged) {
      this.renderer.setStyle(this.roller.nativeElement, 'left', 'auto');
      this.selectDayByDragging();
    }
    this.isDragging = false;
    this.dragged = false;
    // LISTENERS
    this.unlistenMouseMove();
    this.unlistenTouchMove();
    this.unlistenMouseUp();
    this.unlistenTouchEnd();
  }
}
