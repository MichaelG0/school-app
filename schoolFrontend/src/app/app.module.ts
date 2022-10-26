import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginModalComponent } from './components/login-modal/login-modal.component';
import { NavComponent } from './components/nav/nav.component';
import { OffcanvasNavComponent } from './components/offcanvas-nav/offcanvas-nav.component';
import { RegisterModalComponent } from './components/register-modal/register-modal.component';
import { AuthInterceptor } from './auth/auth.interceptor';
import { FooterComponent } from './components/footer/footer.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CircleProgressComponent } from './components/circle-progress/circle-progress.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginModalComponent,
    NavComponent,
    OffcanvasNavComponent,
    RegisterModalComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
