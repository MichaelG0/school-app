import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { KlassComponent } from './klass.component';

const routes: Routes = [{ path: '', component: KlassComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class KlassRoutingModule { }
