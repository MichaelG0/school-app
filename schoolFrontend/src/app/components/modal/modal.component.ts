import { trigger, state, style, transition, animate } from '@angular/animations';
import { ChangeDetectionStrategy, Component, ComponentRef, ElementRef, Type, ViewChild, ViewContainerRef } from '@angular/core';
import { Subject } from 'rxjs';
import { DomService } from 'src/app/services/dom.service';

@Component({
  selector: 'app-modal',
  imports: [],
  templateUrl: './modal.component.html',
  styleUrl: './modal.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: [
    trigger('overlayFade', [
      state('void', style({ 'background-color': 'rgba(0, 0, 0, 0)' })),
      state('*', style({ 'background-color': 'rgba(0, 0, 0, 0.5)' })),
      transition(':enter', [animate('{{ overlayTransitionDuration }}ms ease-in-out')], {
        params: { delay: 300, overlayTransitionDuration: 300 },
      }),
      transition(':leave', [animate('{{ delay }}ms', style({ opacity: 1 })), animate('{{ overlayTransitionDuration }}ms ease-in-out')], {
        params: { delay: 300, overlayTransitionDuration: 300 },
      }),
    ]),
    trigger('modalFade', [
      state('void', style({ opacity: 0, top: 'calc(50% - 3rem)' })),
      state('*', style({ opacity: 1, top: '50%' })),
      transition(':enter', [animate('{{ delay }}ms', style({ opacity: 0 })), animate('{{ modalTransitionDuration }}ms ease-out')], {
        params: { delay: 300, modalTransitionDuration: 300 },
      }),
      transition(':leave', [animate('{{ modalTransitionDuration }}ms ease-out')], {
        params: { delay: 300, modalTransitionDuration: 300 },
      }),
    ]),
  ],
})
export class ModalComponent {
  @ViewChild('overlay') readonly overlay!: ElementRef<HTMLElement>;
  @ViewChild('modal') readonly modal!: ElementRef<HTMLElement>;
  @ViewChild('modalContent', { read: ViewContainerRef }) private readonly modalContent!: ViewContainerRef;
  private loadedComponent!: Type<any>;
  private loadedComponentRef!: ComponentRef<any>;
  readonly viewInitialized = new Subject<void>(); // Empty subject to emit when the view is initialized
  modalOpen = true;
  overlayTransitionDuration = 300;
  overlayExitDelay = 300;
  modalTransitionDuration = 300;

  constructor(private domService: DomService) {
    this.overlayTransitionDuration = domService.getOverlayTransitionDuration();
    this.overlayExitDelay = domService.getOverlayExitDelay();
    this.modalTransitionDuration = domService.getModalTransitionDuration();
  }

  ngAfterViewInit() {
    this.viewInitialized.next();
    this.viewInitialized.complete();
  }

  loadComponent(component: Type<any>) {
    this.loadedComponent = component;
    this.modalContent.clear();
    this.loadedComponentRef = this.modalContent.createComponent(component);
  }

  getLoadedComponent(): ComponentRef<any> {
    return this.loadedComponentRef;
  }

  animateClose() {
    this.modalOpen = false;
  }

  closeModal() {
    this.domService.closeModal(this.loadedComponent);
  }
}
