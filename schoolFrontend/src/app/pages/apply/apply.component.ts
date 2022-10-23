import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable, take } from 'rxjs';
import { ICourse } from 'src/app/interfaces/icourse';
import { IJwtResponse } from 'src/app/interfaces/ijwt-response';
import { IStudentRequest } from 'src/app/interfaces/istudent-request';
import { CourseService } from 'src/app/services/course.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-apply',
  templateUrl: './apply.component.html',
  styleUrls: ['./apply.component.scss'],
})
export class ApplyComponent implements OnInit {
  jwtResp!: IJwtResponse | null;
  upcCourses$!: Observable<ICourse[]>
  applyForm!: FormGroup;
  loading: boolean = false;
  btnClicked: boolean = false;
  submissionFailed: boolean = false;
  confirmation: boolean = false
  
  constructor(private userSrv: UserService, private crsSrv: CourseService, private fb: FormBuilder) {}
  
  ngOnInit(): void {
    this.userSrv.loggedObs$.pipe(take(1)).subscribe((res) => (this.jwtResp = res));
    this.upcCourses$ = this.crsSrv.getUpcoming()
    this.setForm();
  }
  
  setForm() {
    this.btnClicked = false;
    this.applyForm = this.fb.group({
      email: [this.jwtResp?.user.email, [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$')]],
      firstname: [this.jwtResp?.user.name, [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
      surname: [this.jwtResp?.user.surname, [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
      gender: [null, Validators.required],
      address: [null, Validators.required],
      course: [null, Validators.required]
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
      courseId: form.value.course
    };

    this.userSrv.apply(data).pipe(take(1)).subscribe((res: any) => {
      if (res === true) this.submissionFailed = res;
      else this.confirmation = true;
      this.loading = false;
    });
  }

  warning(prop: string, btnClk: boolean) {
    return this.applyForm.get(prop)!.invalid && btnClk === true;
  }

  textWarning(prop: string, btnClk: boolean) {
    if (this.applyForm.get(prop)!.invalid && btnClk === true) return 'warning';
    else if (this.applyForm.get(prop)!.valid && btnClk === true) return 'success';
    return '';
  }
  
}
