import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, take } from 'rxjs';
import { ILoginRequest } from 'src/app/interfaces/ilogin-request';
import { ModalService } from 'src/app/services/modal.service';
import { UserService } from 'src/app/services/user.service';
import { NgIf, AsyncPipe } from '@angular/common';
declare var bootstrap: any;

@Component({
  selector: 'app-login-modal',
  templateUrl: './login-modal.component.html',
  styleUrls: ['./login-modal.component.scss'],
  imports: [ReactiveFormsModule, NgIf, AsyncPipe],
})
export class LoginModalComponent implements OnInit {
  modalProps$!: Observable<{ title: string; link: string }>;
  loginForm!: UntypedFormGroup;
  btnClicked: boolean = false;
  loginFailed: boolean = false;
  loading: boolean = false;

  constructor(
    private userSrv: UserService,
    private modalSrv: ModalService,
    private fb: UntypedFormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.modalProps$ = this.modalSrv.modalProps$;
    this.setForm();
  }

  setForm() {
    this.btnClicked = false;
    this.loginFailed = false;
    this.loginForm = this.fb.group({
      email: ['teacher@teacher.com', [Validators.required, Validators.email]],
      password: ['teacherteacher', [Validators.required, Validators.minLength(8)]],
    });
  }

  onSubmit(form: UntypedFormGroup) {
    if (!form.valid) return;

    this.loading = true;
    const authData: ILoginRequest = {
      email: form.value.email,
      password: form.value.password,
    };
    this.userSrv
      .login(authData)
      .pipe(take(1))
      .subscribe((res: any) => {
        if (res === true) this.loginFailed = res;
        else if (typeof res === 'object') {
          const lgnMdlEl = document.querySelector('#exampleModalToggle2');
          const loginModal = bootstrap.Modal.getInstance(lgnMdlEl);
          loginModal.hide();
          this.modalProps$.pipe(take(1)).subscribe(res => this.router.navigate([res.link]));
        }
        this.loading = false;
      });
  }
}
