/* eslint-disable no-trailing-spaces */
/* eslint-disable max-len */
/* eslint-disable @typescript-eslint/no-inferrable-types */
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Artikel } from '../shared/models/Artikel';
import { ArtikelService } from '../shared/services/artikel.service';
import { WarenkorbService } from '../shared/services/warenkorb.service';
import { KeycloakService } from 'keycloak-angular';
import { AccountService } from '../shared/services/account.service';
import { Account } from '../shared/models/Account';
import { Warenkorb } from '../shared/models/Warenkorb';
import { NotificationService } from '../shared/services/notification.service';
import { KeycloakAccount } from '../shared/models/KeycloakAccount';



@Component({
  selector: 'app-artikel',
  templateUrl: './artikel.component.html',
  styleUrls: ['./artikel.component.scss']
})
export class ArtikelComponent implements OnInit {
  public artikel!: Artikel;
  public id!: number;
  public error?: HttpErrorResponse;
  public isLogged: boolean = false;
  public isArtikelOnBeobachtungsliste: boolean = false;
  public isArtikelInWarenkorb: boolean = false;
  public canBuy: boolean = true;

  constructor(
    private route: ActivatedRoute, 
    private artikelService: ArtikelService, 
    public warenkorbService: WarenkorbService, 
    private keycloakService: KeycloakService, 
    private accountService: AccountService, 
    private notifyService: NotificationService
  ) {}

  public ngOnInit(): void {
    this.keycloakService.isLoggedIn().then(res => {
      this.isLogged = res;
      if(this.isLogged){
        // Checkt, ob artikel bereits auf der beobachtungsliste
        this.checkIsArtikelOnBeobachtungsliste();
      }
    });

    this.route.params.subscribe(params => {
      this.id = +params.id;
      console.log('aktuelle Id: ', this.id);

      this.artikelService.getArtikelById(this.id).subscribe(res => {
        console.log(res);
        this.artikel = res;

        if(this.isLogged){
          // Checkt, ob der Artikel vom User ist
          this.accountService.getCurrentAccount().subscribe((keycloakAccount: KeycloakAccount) => {
            if(this.artikel.username === keycloakAccount.username){
              this.canBuy = false;
            }
          });
        }

      },
      error => {
        console.log(error);
        this.error=error;
      });
    });

    this.checkIfArtikelInWarenkorb();
  }

  public artikelInWarenkorb(): void {
    this.artikelService.addArtikelToWarenkorb(this.artikel).subscribe(warenkorb => {
      console.log(warenkorb);
      this.warenkorbService.updateWarenkorbArtikelAnzahl();
      this.notifyService.showSuccessMessage(`Du hast den Artikel '${this.artikel.name}' in den Warenkorb gelegt`);
      this.checkIfArtikelInWarenkorb();
    });
  }

  public artikelInBeobachtungsliste(): void {
    this.artikelService.addArtikelToBeobachtungsliste(this.artikel).subscribe(artikel => {
      console.log(artikel);
      this.artikel = artikel;
      this.notifyService.showSuccessMessage(`Du hast den Artikel '${this.artikel.name}' auf die Beobachtungsliste gesetzt`);
      this.checkIsArtikelOnBeobachtungsliste();
    });
  }

  private checkIsArtikelOnBeobachtungsliste(){
    this.accountService.getCurrentAccountInfo().subscribe(res => {
      const account: Account = res;
      if(account.beobachtungsliste !== undefined){
        this.isArtikelOnBeobachtungsliste = account.beobachtungsliste.some( beobachtungsartikel => beobachtungsartikel.artikelIdReference === this.id);
        console.log(this.isArtikelOnBeobachtungsliste);
      }
    });
  }

  private checkIfArtikelInWarenkorb(): void {
    this.warenkorbService.getWarenkorb().subscribe((warenkorb: Warenkorb) => {
      console.log(warenkorb);
      this.isArtikelInWarenkorb = warenkorb.warenkorbpostenlist.some( wp => wp.warenkorbartikel.artikelIdReference === this.artikel.id );
    });
  }
}
