import { Component } from '@angular/core';
import { NavComponent } from './components/nav/nav.component';
import { LoginModalComponent } from './components/login-modal/login-modal.component';
import { RegisterModalComponent } from './components/register-modal/register-modal.component';
import { OffcanvasNavComponent } from './components/offcanvas-nav/offcanvas-nav.component';
import { RouterOutlet } from '@angular/router';
import { FooterComponent } from './components/footer/footer.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  imports: [
    NavComponent,
    LoginModalComponent,
    RegisterModalComponent,
    OffcanvasNavComponent,
    RouterOutlet,
    FooterComponent,
  ],
})
export class AppComponent {
  title = 'schoolapp';
}
