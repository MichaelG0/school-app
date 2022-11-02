import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { KlassListComponent } from 'src/app/components/klass-list/klass-list.component';

@NgModule({
  declarations: [
    KlassListComponent
  ],
  exports: [
    KlassListComponent
  ],
  imports: [
    CommonModule
  ],
})
export class KlassListModule {}
