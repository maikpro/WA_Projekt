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

  constructor(
    private route: ActivatedRoute, 
    private artikelService: ArtikelService, 
    public warenkorbService: WarenkorbService, 
    private keycloakService: KeycloakService, 
    private accountService: AccountService, 
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
      },
      error => {
        console.log(error);
        this.error=error;
      });
    });
  }

  public artikelInWarenkorb(): void {
    this.artikelService.addArtikelToWarenkorb(this.artikel).subscribe(warenkorb => {
      console.log(warenkorb);
      this.warenkorbService.updateWarenkorbArtikelAnzahl();
      //this.notifyService.showSuccessMessage(`Du hast den Artikel '${this.artikel.name}' in den Warenkorb gelegt`);
    });
  }

  public artikelInBeobachtungsliste(): void {
    this.artikelService.addArtikelToBeobachtungsliste(this.artikel).subscribe(artikel => {
      console.log(artikel);
      this.artikel = artikel;
      //this.notifyService.showSuccessMessage(`Du hast den Artikel '${this.artikel.name}' auf die Beobachtungsliste gesetzt`);
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
}
