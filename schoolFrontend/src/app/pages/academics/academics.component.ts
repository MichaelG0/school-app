import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-academics',
  templateUrl: './academics.component.html',
  styleUrls: ['./academics.component.scss'],
  standalone: true,
  imports: [RouterLink],
})
export class AcademicsComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
