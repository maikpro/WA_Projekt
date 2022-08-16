import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AccountComponent } from 'src/app/account/account.component';
import { NotificationService } from '../services/notification.service';

@Injectable({
  providedIn: 'root'
})
export class AccountValidateGuard implements CanDeactivate<AccountComponent> {
  
  constructor(private notifyService: NotificationService) {}
  
  canDeactivate(
    component: AccountComponent,
    currentRoute: ActivatedRouteSnapshot,
    currentState: RouterStateSnapshot,
    nextState?: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(component.submitted) {
      this.notifyService.warning('Account Adresse angeben', 'Sie müssen zuerst ihre Adresse hinterlegen, damit sie weiter navigieren können!');
      return false;
    }
    return true;
  }
  
}
