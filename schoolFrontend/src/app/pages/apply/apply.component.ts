import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { take } from 'rxjs';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { IStudentRequest } from 'src/app/interfaces/istudent-request';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-apply',
  templateUrl: './apply.component.html',
  styleUrls: ['./apply.component.scss'],
})
export class ApplyComponent implements OnInit {
  user!: IJwtResponse | null;
  applyForm!: FormGroup;
  loading: boolean = false;
  submissionFailed: boolean = false;

  constructor(private userSrv: UserService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.userSrv.loggedObs$.pipe(take(1)).subscribe((res) => (this.user = res));
    this.setForm();
  }

  setForm() {
    this.applyForm = this.fb.group({
      email: [this.user?.email, [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$')]],
      firstname: [this.user?.name, [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
      surname: [this.user?.surname, [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
      gender: [null, Validators.required],
      address: [null, Validators.required],
    });
  }

  onSubmit(form: FormGroup) {
    if (!form.valid) return;

    this.loading = true;
    const data: IStudentRequest = {
      email: form.value.email,
      name: form.value.firstname,
      surname: form.value.surname,
      gender: form.value.gender,
      address: form.value.address,
    };

    this.userSrv.apply(data).pipe(take(1)).subscribe((res: any) => {
      if (res === true) this.submissionFailed = res;
      this.loading = false;
    });
  }
  
}
