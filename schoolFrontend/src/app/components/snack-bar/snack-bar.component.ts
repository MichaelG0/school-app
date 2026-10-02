import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { SnackBarData } from 'src/app/interfaces/isnack-bar-data';

@Component({
  selector: 'app-snack-bar',
  imports: [],
  templateUrl: './snack-bar.component.html',
  styleUrl: './snack-bar.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SnackBarComponent {
  @Input() data = new SnackBarData();
}
