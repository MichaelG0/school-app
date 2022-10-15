import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { take } from 'rxjs';
import { ISignUpRequest } from 'src/app/interfaces/isign-up-request';
import { UserService } from 'src/app/services/user.service';
declare var bootstrap: any;

@Component({
  selector: 'app-register-modal',
  templateUrl: './register-modal.component.html',
  styleUrls: ['./register-modal.component.scss'],
})
export class RegisterModalComponent implements OnInit {
  signUpForm!: FormGroup;
  gotEmail: boolean = false;
  btnClicked: boolean = false;
  btnClicked2: boolean = false;

  constructor(private userSrv: UserService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.setForm();
  }

  setForm() {
    this.gotEmail = false;
    this.btnClicked = false;
    this.btnClicked2 = false;
    this.signUpForm = this.fb.group({
      email: ['', [Validators.required,Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$')]],
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
    else if (this.signUpForm.get(prop)!.valid && btnClk === true)
      return 'success';
    return '';
  }
}
