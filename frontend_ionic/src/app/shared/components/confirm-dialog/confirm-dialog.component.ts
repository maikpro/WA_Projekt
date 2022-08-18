import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.scss'],
})
export class ConfirmDialogComponent {
  @Input() title!: string;
  @Input() message!: string;
  @Input() cancelButtonText!: string;
  @Input() confirmButtonText!: string;

  constructor(private modalController: ModalController) { }

  public cancel() {
    return this.modalController.dismiss(false, 'cancel');
  }

  public confirm() {
    return this.modalController.dismiss(true, 'confirm');
  }
}
