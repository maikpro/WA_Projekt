import { Component, OnInit } from '@angular/core';
import { Artikel } from '../../shared/models/Artikel';
import { ArtikelService } from '../../shared/services/artikel.service';

@Component({
  selector: 'app-verkaeufe',
  templateUrl: './verkaeufe.component.html',
  styleUrls: ['./verkaeufe.component.scss']
})
export class VerkaeufeComponent implements OnInit {
  public verkaufteArtikel: Artikel[] = [];

  constructor(private artikelService: ArtikelService) { }

  public ngOnInit(): void {
    this.getVerkaeufe();
  }

  public doRefresh(event): void {
    this.verkaufteArtikel = [];
    this.getVerkaeufe();
    event.target.complete();
  }

  private getVerkaeufe(): void {
    this.artikelService.getVerkaufteArtikel().subscribe((verkaufteArtikel: Artikel[]) => {
      console.log(verkaufteArtikel);
      this.verkaufteArtikel=verkaufteArtikel;
    });
  }
}
