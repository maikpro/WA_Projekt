import { Component, TemplateRef } from '@angular/core';
import { faCancel, faCheck, faFaceSmileBeam } from '@fortawesome/free-solid-svg-icons';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.css']
})
export class ToastComponent {

  faCheck = faCheck;
  faCancel = faCancel;

  constructor(public notifyService: NotificationService) { }

  isTemplate(toast: any) { return toast.textOrTpl instanceof TemplateRef; }

}
