import { Injectable } from '@angular/core';
import { AnimationController, ModalController, ToastController } from '@ionic/angular';
import { ConfirmDialogComponent } from '../components/confirm-dialog/confirm-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private toastController: ToastController, private modalController: ModalController) { }

  public async showSuccessMessage(message: string) {
    const toast = await this.toastController.create({
      header: 'Erfolgreich',
      message,
      icon: 'checkmark-circle',
      color: 'success',
      duration: 3500,
    });
    await toast.present();
  }

  public async showErrorMessage(message: string) {
    const toast = await this.toastController.create({
      header: 'Fehler',
      message,
      icon: 'alert-circle',
      color: 'danger',
      duration: 3500,
    });
    await toast.present();
  }

  public async confirm(title: string, message: string, cancelButtonText: string, confirmButtonText?: string): Promise<boolean> {
    const modal = await this.modalController.create({
      component: ConfirmDialogComponent,
      componentProps: {
        title,
        message,
        cancelButtonText,
        confirmButtonText
      }
    });
    modal.present();

    const result = await modal.onDidDismiss();
    return result.data;
  }
}
