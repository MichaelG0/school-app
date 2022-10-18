import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GraduateComponent } from './graduate.component';

const routes: Routes = [{ path: '', component: GraduateComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GraduateRoutingModule { }
