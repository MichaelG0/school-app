import { Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-month-year-dropdown',
  templateUrl: './month-year-dropdown.component.html',
  styleUrls: ['./month-year-dropdown.component.scss'],
})
export class MonthYearDropdownComponent implements OnInit {
  @Input() selectedDay!: Date;
  @Output() closeDropdown = new EventEmitter<Date | void>();
  selectedYear!: number;
  months!: Date[];
  isOpenClick = true;

  constructor(private elementRef: ElementRef) {}

  ngOnInit(): void {
    this.selectedYear = this.selectedDay.getFullYear();
    this.months = Array.from({ length: 12 }, (_, i) => new Date(this.selectedYear, i, 1));
  }

  selectMonth(month: Date) {
    const date = new Date(this.selectedYear, month.getMonth(), this.selectedDay.getDate());
    this.closeDropdown.emit(date);
  }

  prevYear() {
    this.selectedYear--;
    this.months = Array.from({ length: 12 }, (_, i) => new Date(this.selectedYear, i, 1));
  }

  nextYear() {
    this.selectedYear++;
    this.months = Array.from({ length: 12 }, (_, i) => new Date(this.selectedYear, i, 1));
  }

  @HostListener('document:click', ['$event'])
  closeDropdownOnClickOutside(event: MouseEvent) {
    if (this.isOpenClick) {
      this.isOpenClick = false;
      return;
    }
    const hostElement: HTMLElement = this.elementRef.nativeElement;
    const targetElement = event.target as HTMLElement;
    if (!hostElement.contains(targetElement)) {
      this.closeDropdown.emit();
    }
  }

  //======================== PIPED METHODS ========================

  isSelectedMonth = (month: Date): boolean => {
    return (
      month.getMonth() == this.selectedDay.getMonth() &&
      month.getFullYear() == this.selectedDay.getFullYear()
    );
  };

  getMonthName = (month: Date): string => {
    return month.toLocaleDateString('en-us', { month: 'long' });
  };
}
