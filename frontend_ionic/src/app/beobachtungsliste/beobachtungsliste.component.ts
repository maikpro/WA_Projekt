import { Component, OnInit } from '@angular/core';
import { Account } from '../shared/models/Account';
import { Beobachtungsartikel } from '../shared/models/Beobachtungsartikel';
import { AccountService } from '../shared/services/account.service';
import { ArtikelService } from '../shared/services/artikel.service';

@Component({
  selector: 'app-beobachtungsliste',
  templateUrl: './beobachtungsliste.component.html',
  styleUrls: ['./beobachtungsliste.component.scss']
})
export class BeobachtungslisteComponent implements OnInit {
  public beobachtungsliste!: Beobachtungsartikel[];

  constructor(private accountService: AccountService) { }

  public ngOnInit(): void {
    this.setBeobachtungsliste();
  }

  public setBeobachtungsliste(): void {
    this.accountService.getCurrentAccountInfo().subscribe(res => {
      console.log(res);
      const account: Account = res;
      if(account.beobachtungsliste !== undefined){
        this.beobachtungsliste = account.beobachtungsliste;
      }
    });
  }

  public deleteBeobachtungsartikel(beobachtungsartikel: Beobachtungsartikel): void {
    this.accountService.deleteBeobachtungsartikel(beobachtungsartikel.id).subscribe(res => {
      const account: Account = res;
      console.log(account);
      if(account.beobachtungsliste !== undefined){
        this.beobachtungsliste = account.beobachtungsliste;
      }
    });
  }

}
