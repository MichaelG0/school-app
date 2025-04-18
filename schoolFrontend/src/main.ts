import { enableProdMode, importProvidersFrom } from '@angular/core';
import { environment } from './environments/environment';
import { HTTP_INTERCEPTORS, withInterceptorsFromDi, provideHttpClient } from '@angular/common/http';
import { AuthInterceptor } from './app/auth/auth.interceptor';
import { BrowserModule, bootstrapApplication } from '@angular/platform-browser';
import { routes } from './app/app-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { provideAnimations } from '@angular/platform-browser/animations';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(BrowserModule, ReactiveFormsModule),
    provideRouter(routes),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    provideHttpClient(withInterceptorsFromDi()),
    provideAnimations(),
  ],
}).catch(err => console.error(err));
