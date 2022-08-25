/* eslint-disable max-len */
import { ErrorHandler, Injectable, NgZone } from '@angular/core';
import { NotificationService } from '../services/notification.service';


@Injectable()
export class GlobalErrorHandler implements ErrorHandler {
    constructor(
        private notifyService: NotificationService,
        private zone: NgZone,
    ){}

    public handleError(error: any): void {
        if(error.name === 'HttpErrorResponse'){
            this.handleHttpError(error);
        } else {
            console.error(error);
        }
    }

    private handleHttpError(error: any): void {
        let errorMessage: string;
        if(error.status === 0){
            errorMessage = '0 Verbindungsfehler!';
        } else if(error.status === 400){
            errorMessage = '400 Bad Request - Diese Antwort bedeutet, dass der Server die Anfrage aufgrund einer ungültigen Syntax nicht verstehen konnte.';
        } else if(error.status === 401){
            errorMessage = '401 Unauthorized - Sie sind nicht eingeloggt und haben keinen Zugriff auf diese Funktion!';
        } else if(error.status === 403){
            errorMessage = '403 Forbidden - Sie haben keinen Zugriff auf diesen Bereich. Bitte loggen Sie sich ein';
        } else if(error.status === 404){
            errorMessage = '404 Not Found - Server kann angeforderte Ressource nicht finden.';
        } else if(error.status === 500){
            errorMessage = '500 Internal Server Error';
        } else {
            errorMessage = 'Unbekannter Fehler!';
        }
        this.showMessage(errorMessage);
    }

    private showMessage(message: string): void {
        this.zone.run(() => {
            this.notifyService.showErrorMessage(message);
            console.log(message);
        });
    }
}
