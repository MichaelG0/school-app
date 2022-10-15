import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginModalService {
  private modalProps = new BehaviorSubject<{ title: string; link: string }>({
    title: 'Log In',
    link: '',
  });
  modalProps$ = this.modalProps.asObservable();

  constructor() {}

  changeProps(title: string, link: string) {
    this.modalProps.next({ title, link });
  }
}
