import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { HeroComponent } from 'src/app/components/hero/hero.component';


@NgModule({
    imports: [
        CommonModule,
        HomeRoutingModule,
        HomeComponent,
        HeroComponent
    ]
})
export class HomeModule { }
