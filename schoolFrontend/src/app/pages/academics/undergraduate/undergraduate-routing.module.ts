import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UndergraduateComponent } from './undergraduate.component';

const routes: Routes = [{ path: '', component: UndergraduateComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UndergraduateRoutingModule { }
