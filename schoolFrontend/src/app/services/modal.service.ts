import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { IAssignment } from '../interfaces/iassignment';
import { ICompletedAssignment } from '../interfaces/icompleted-assignment';

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

  private complAss = new BehaviorSubject<ICompletedAssignment | null>(null);
  complAss$ = this.complAss.asObservable();

  constructor() {}

  changeProps(title: string, link: string) {
    this.modalProps.next({ title, link });
  }

  setAssignmnetId(id: number | null) {
    this.assignmentId.next(id);
  }

  setAssignment(modalTitle: string, assignment: IAssignment | null = null) {
    this.assignment.next({ modalTitle, assignment });
  }

  setCompletedAssignment(complAss: ICompletedAssignment) {
    this.complAss.next(complAss);
  }
  
}
