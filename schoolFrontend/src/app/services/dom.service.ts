import { ApplicationRef, ComponentRef, EmbeddedViewRef, Injectable, Type, ViewContainerRef } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { ModalComponent } from '../components/modal/modal.component';

@Injectable({
  providedIn: 'root',
})
export class DomService {
  private viewContainerRef!: ViewContainerRef;
  private readonly modalComponentRefs: Map<Type<any>, { ref: ComponentRef<ModalComponent>; subject: Subject<any> }> = new Map();
  private readonly overlayTransitionDuration = 150;
  private readonly overlayExitDelay = 100;
  private readonly modalTransitionDuration = 250;

  constructor(private appRef: ApplicationRef) {}

  setRootViewContainerRef(vcr: ViewContainerRef) {
    this.viewContainerRef = vcr;
  }

  getOverlayTransitionDuration(): number {
    return this.overlayTransitionDuration;
  }

  getOverlayExitDelay(): number {
    return this.overlayExitDelay;
  }

  getModalTransitionDuration(): number {
    return this.modalTransitionDuration;
  }

  getAppRootChildren(): HTMLElement[] {
    const appRootView = this.appRef.components[0];
    const hostElem = appRootView.location.nativeElement as HTMLElement;
    return Array.from(hostElem.children) as HTMLElement[];
  }

  openModal<T1, T2>(component: Type<T1>, ...inputs: { name: string; value: any }[]): Observable<T2> {
    // 1. Create the modal component reference
    const modalComponentRef = this.viewContainerRef.createComponent(ModalComponent);

    const subject = new Subject<T2>();
    this.modalComponentRefs.set(component, { ref: modalComponentRef, subject });

    const modalComponent = modalComponentRef.instance;

    // 2. Wait for the AfterViewInit lifecycle hook, then load the desired component into the modal
    modalComponent.viewInitialized.subscribe(() => {
      modalComponent.loadComponent(component);

      // 3. Set component inputs
      for (const input of inputs) {
        modalComponent.getLoadedComponent().setInput(input.name, input.value);
      }

      // 4. Prevent interaction with elements behind the modal
      const modalHTMLElement = (modalComponentRef.hostView as EmbeddedViewRef<any>).rootNodes[0] as HTMLElement;

      this.getAppRootChildren().forEach(el => {
        if (el !== modalHTMLElement) {
          (el as HTMLElement).inert = true;
        }
      });
    });

    // 5. Return an observable that emits when the modal is closed
    return subject.asObservable();
  }

  closeModal<T1, T2>(component: Type<T1>, data?: T2) {
    const modalData = this.modalComponentRefs.get(component);

    if (modalData) {
      const { ref: componentRef, subject } = modalData;
      const modalComponent = componentRef.instance;
      modalComponent.animateClose();

      setTimeout(() => {
        this.modalComponentRefs.delete(component);
        this.appRef.detachView(componentRef.hostView);
        componentRef.destroy();
        subject.next(data);
        subject.complete();

        // Restore interaction with all elements
        this.getAppRootChildren().forEach(el => {
          (el as HTMLElement).inert = false;
        });
      }, this.overlayExitDelay + this.overlayTransitionDuration);
    }
  }

  openSnackbar<T>(component: Type<T>, ...inputs: { name: string; value: any }[]) {
    // 1. Create a component reference from the component
    const componentRef = this.viewContainerRef.createComponent(component);

    // 2. Set component inputs
    for (const input of inputs) {
      componentRef.setInput(input.name, input.value);
    }

    // 3. Wait some time and remove it from the component tree and from the DOM
    setTimeout(() => {
      this.appRef.detachView(componentRef.hostView);
      componentRef.destroy();
    }, 3000); // Bootstrap's alert duration
  }
}
