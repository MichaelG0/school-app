import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-switch-upcoming',
  templateUrl: './switch-upcoming.component.html',
  styleUrl: './switch-upcoming.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true,
})
export class SwitchUpcomingComponent implements OnInit {
  @Input() upcoming = true;
  @Output() switchUpcoming = new EventEmitter<void>();

  constructor() {}

  ngOnInit(): void {}

  doSwitchUpcoming(upcoming: boolean) {
    if (this.upcoming == upcoming) return;
    this.switchUpcoming.emit();
  }
}
