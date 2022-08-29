import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Artikel } from 'src/app/shared/models/Artikel';
import { ArtikelService } from 'src/app/shared/services/artikel.service';

@Component({
  selector: 'app-deine-artikel',
  templateUrl: './deine-artikel.component.html',
  styleUrls: ['./deine-artikel.component.scss'],
})
export class DeineArtikelComponent implements OnInit {
  public yourArtikel: Artikel[];

  constructor(private artikelService: ArtikelService, private router: Router) { }

  public ngOnInit() {
    this.artikelService.getYourArtikel().subscribe((artikelArray: Artikel[]) => {
      console.log(artikelArray);
      this.yourArtikel = artikelArray;
    });
  }

  public gotoBearbeiten(id: number): void {
    this.router.navigateByUrl(`/account-overview/deine-artikel/update/id/${id}`);
  }

}
