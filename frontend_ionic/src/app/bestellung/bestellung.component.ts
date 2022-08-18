/* eslint-disable @typescript-eslint/no-shadow */
/* eslint-disable @typescript-eslint/member-ordering */
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Account } from '../shared/models/Account';
import { Warenkorb, Warenkorbposten } from '../shared/models/Warenkorb';
import { AccountService } from '../shared/services/account.service';
import { WarenkorbService } from '../shared/services/warenkorb.service';
import { Bestellung } from '../shared/models/Bestellung';
import { Bestellstatus } from '../shared/models/Bestellstatus';
import { BestellungService } from './bestellung.service';
import { Adresse } from '../shared/models/Adresse';
import { Router } from '@angular/router';

@Component({
  selector: 'app-bestellung',
  templateUrl: './bestellung.component.html',
  styleUrls: ['./bestellung.component.scss']
})
export class BestellungComponent implements OnInit {
  public warenkorb!: Warenkorb;
  public account!: Account;
  public bestellForm!: FormGroup;
  public submitted = false;

  constructor(
    private bestellService: BestellungService,
    private router: Router,
    private warenkorbService: WarenkorbService,
    private accountService: AccountService,
    private formBuilder: FormBuilder) { }

    private verkaeufer: Account[] = [];

  public ngOnInit(): void {
    this.warenkorbService.getWarenkorb().subscribe((warenkorb: Warenkorb) => {
      console.log(warenkorb);
      this.warenkorb = warenkorb;
      this.warenkorbService.updateWarenkorbArtikelAnzahl();
      this.createVerkaeufer();
    });

    this.accountService.getCurrentAccountInfo().subscribe((account: Account) => {
      console.log(account);
      this.account = account;
    });

    this.bestellForm = this.formBuilder.group({
      bezahlmethode: ['', Validators.required]
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.bestellForm.controls;
  }

  public bestellungAbschliessen(): void {
    console.log(this.submitted);
    this.submitted = true;
    if(this.bestellForm.valid){
      console.log(this.bestellForm.value);
      this.createBestellung();
      this.submitted = false;
    }
  }

  private createBestellung(): void {
    console.log(this.verkaeufer);

    const kaeufer = this.account;
    kaeufer.id=undefined;
    kaeufer.adresse.id=undefined;
    this.warenkorb.warenkorbpostenlist.forEach(posten => {
      posten.postenNr = undefined;
      posten.warenkorbartikel.id=undefined;
      posten.warenkorbartikel.versand.id = undefined;
    });

    const bestellung: Bestellung = {
      gesamtSumme: this.warenkorb.gesamtSumme,
      bezahlmethode: this.bestellForm.value.bezahlmethode,
      bestellstatus: Bestellstatus.BEZAHLT,
      bestellposten: this.warenkorb.warenkorbpostenlist,
      kaeufer,
      verkaeufer: this.verkaeufer
    };
    console.log(bestellung);
    this.bestellService.createBestellung(bestellung).subscribe((bestellung: Bestellung) => {
      console.log(bestellung);
      this.warenkorbService.clearAndsetWarenkorb();
      this.router.navigateByUrl(`/bestellung/complete/id/${bestellung.id}`);
    });
  }

  private createVerkaeufer(): void {
    this.warenkorb.warenkorbpostenlist.forEach((warenkorbposten: Warenkorbposten) => {
      const username: string = warenkorbposten.warenkorbartikel.username;
      this.accountService.getAccountByUsername(username).subscribe((account: Account) => {
        const newAdresse: Adresse = {
          strasse: account.adresse.strasse,
          hausnr: account.adresse.hausnr,
          plz: account.adresse.plz,
          ort: account.adresse.ort
        };

        const newAccount: Account = {
          vorname: account.vorname,
          nachname: account.nachname,
          email: account.email,
          username: account.username,
          adresse: newAdresse
        };
        this.verkaeufer.push(newAccount);
      });
    });
  }
}
