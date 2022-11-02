import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, take } from 'rxjs';
import { ISignUpRequest } from 'src/app/interfaces/isign-up-request';
import { LoginModalService } from 'src/app/services/login-modal.service';
import { UserService } from 'src/app/services/user.service';
declare var bootstrap: any;

@Component({
  selector: 'app-register-modal',
  templateUrl: './register-modal.component.html',
  styleUrls: ['./register-modal.component.scss'],
})
export class RegisterModalComponent implements OnInit {
  modalProps$!: Observable<{ title: string; link: string }>;
  signUpForm!: FormGroup;
  gotEmail: boolean = false;
  btnClicked: boolean = false;
  btnClicked2: boolean = false;

  constructor(private userSrv: UserService, private modalSrv: LoginModalService, private fb: FormBuilder, private router: Router) {}

  ngOnInit(): void {
    this.modalProps$ = this.modalSrv.modalProps$;
    this.setForm();
  }

  setForm() {
    this.gotEmail = false;
    this.btnClicked = false;
    this.btnClicked2 = false;
    this.signUpForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      name: ['', [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
      surname: ['', [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
      password: ['', [Validators.required, Validators.minLength(8)]],
    });
  }

  onSubmit(form: FormGroup) {
    if (!form.valid) return;

    const user: ISignUpRequest = {
      email: form.value.email,
      name: form.value.name,
      surname: form.value.surname,
      password: form.value.password,
    };
    this.userSrv.signUp(user).pipe(take(1)).subscribe((res: any) => {
      if (typeof res === 'object') {
        const sgnMdlEl = document.querySelector('#exampleModalToggle');
        const signUpModal = bootstrap.Modal.getInstance(sgnMdlEl);
        signUpModal.hide();
        this.modalProps$.pipe(take(1)).subscribe(res => this.router.navigate([res.link]));
      }
    });
  }

  resetModal() {
    document.querySelector('#carouselExampleIndicators .carousel-item.active')?.classList.remove('active');
    document.querySelector('#carouselExampleIndicators .carousel-item')?.classList.add('active');
    this.setForm();
  }

  warning(prop: string, btnClk: boolean) {
    return this.signUpForm.get(prop)!.invalid && btnClk === true;
  }

  success(prop: string, btnClk: boolean) {
    return this.signUpForm.get(prop)!.valid && btnClk === true;
  }

  textWarning(prop: string, btnClk: boolean) {
    if (this.signUpForm.get(prop)!.invalid && btnClk === true) return 'warning';
    else if (this.signUpForm.get(prop)!.valid && btnClk === true) return 'success';
    return '';
  }
  
}
