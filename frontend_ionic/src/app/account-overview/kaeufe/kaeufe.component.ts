import { Component, OnInit } from '@angular/core';
import { BestellungService } from '../../bestellung/bestellung.service';
import { Warenkorbartikel, Warenkorbposten } from '../../shared/models/Warenkorb';

@Component({
  selector: 'app-kaeufe',
  templateUrl: './kaeufe.component.html',
  styleUrls: ['./kaeufe.component.scss']
})
export class KaeufeComponent implements OnInit {
  public kaufliste: Warenkorbartikel[] = [];

  constructor(private bestellService: BestellungService) { }

  public ngOnInit(): void {
    this.getKaeufe();
  }

  public doRefresh(event): void {
    this.kaufliste = [];
    this.getKaeufe();
    event.target.complete();
  }

  private getKaeufe(): void {
    this.bestellService.getKaeufe().subscribe(bestellungen => {
      console.log(bestellungen);
      bestellungen.forEach((bestellung: { bestellposten: any[] }) => bestellung.bestellposten.forEach(bp => {
        console.log(bp.bestellartikel);
        this.kaufliste.push(bp.bestellartikel);
      }));
    });
  }
}
