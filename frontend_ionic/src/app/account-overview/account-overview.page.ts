import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';
import { environment } from 'src/environments/environment';
import { KeycloakAccount } from '../shared/models/KeycloakAccount';
import { AccountService } from '../shared/services/account.service';

@Component({
  selector: 'app-account-overview',
  templateUrl: 'account-overview.page.html',
  styleUrls: ['account-overview.page.scss']
})
export class AccountOverviewPage implements OnInit {
  public isLogged = false;
  public username = '';

  constructor(private router: Router, private keycloakService: KeycloakService, private accountService: AccountService) {}

  public ngOnInit(): void {
    this.keycloakService.isLoggedIn().then(res => {
      this.isLogged = res;
      if(this.isLogged){
        this.accountService.getCurrentAccount().subscribe((account: KeycloakAccount) => this.username = account.username);
      }
    });
  }

  public login(): void {
    this.router.navigateByUrl('/account-overview/account');
  }

  public logout(): void {
    this.keycloakService.logout(environment.frontend + '/account-overview');
  }
}
