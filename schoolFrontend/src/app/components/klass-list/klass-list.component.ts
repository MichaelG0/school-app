import { Component, Input, OnInit } from '@angular/core';
import { IKlass } from 'src/app/interfaces/iklass';

@Component({
  selector: 'app-klass-list',
  templateUrl: './klass-list.component.html',
  styleUrls: ['./klass-list.component.scss']
})
export class KlassListComponent implements OnInit {
  @Input() klass!: IKlass

  constructor() { }

  ngOnInit(): void {
  }

}
