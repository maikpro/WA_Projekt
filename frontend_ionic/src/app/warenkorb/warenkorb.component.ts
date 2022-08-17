import { Component, OnInit } from '@angular/core';
import { Warenkorb } from '../shared/models/Warenkorb';
import { WarenkorbService } from '../shared/services/warenkorb.service';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-warenkorb',
  templateUrl: './warenkorb.component.html',
  styleUrls: ['./warenkorb.component.scss']
})
export class WarenkorbComponent implements OnInit {
  public warenkorb!: Warenkorb;

  constructor(private router: Router, private warenkorbService: WarenkorbService, private cookieService: CookieService) { }

  public ngOnInit(): void {
    //this.warenkorbService.setWarenkorbId();
    //this.warenkorbService.updateWarenkorbArtikelAnzahl();

    this.warenkorbService.getWarenkorb().subscribe(warenkorb => {
      console.log(warenkorb);
      this.warenkorb = warenkorb;
    },error => {
      if(error.status === 404){
        this.cookieService.delete('warenkorbId');
      }
    });
  }

  public deleteArtikel(id: number): void {
    console.log('artikelId zum löschen: ', id);
    this.warenkorbService.deleteArtikelFromWarenkorb(id).subscribe(warenkorb => {
      console.log(warenkorb);
      this.warenkorb = warenkorb;
      this.warenkorbService.updateWarenkorbArtikelAnzahl();
      //this.notifyService.showSuccessMessage("Der Artikel wurde entfernt!");
    });
  }

  public gotoBestellung(): void {
    this.router.navigateByUrl('/bestellung');
  }
}
