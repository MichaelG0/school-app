import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IPageable } from 'src/app/interfaces/ipageable';

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrl: './paginator.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PaginatorComponent implements OnInit {
  @Input() pageable!: IPageable<any>;
  @Input() selectedPage = 0;
  @Input() firstLast = false
  @Output() switchPage = new EventEmitter<number>();

  constructor() {}

  ngOnInit(): void {}
}
