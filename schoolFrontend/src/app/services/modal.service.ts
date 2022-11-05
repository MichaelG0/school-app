import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { IAssignment } from '../interfaces/iassignment';

@Injectable({
  providedIn: 'root',
})
export class ModalService {
  private modalProps = new BehaviorSubject<{ title: string; link: string }>({
    title: 'Log In',
    link: '',
  });
  modalProps$ = this.modalProps.asObservable();

  private assignmentId = new BehaviorSubject<number | null>(null);
  assingmentId$ = this.assignmentId.asObservable();

  private assignment = new BehaviorSubject<{ modalTitle: string; assignment: IAssignment | null }>({
    modalTitle: 'New Assignment',
    assignment: null,
  });
  assignment$ = this.assignment.asObservable();

  constructor() {}

  changeProps(title: string, link: string) {
    this.modalProps.next({ title, link });
  }

  setAssignmnetId(id: number | null) {
    this.assignmentId.next(id);
  }

  setAssignment(modalTitle: string, assignment: IAssignment | null) {
    this.assignment.next({ modalTitle, assignment });
  }
}
