import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Artikel } from '../shared/models/Artikel';
import { ArtikelService } from '../shared/services/artikel.service';
import { SearchService } from '../shared/services/search.service';
import { WarenkorbService } from '../shared/services/warenkorb.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss']
})
export class HomePage implements OnInit {
  public results: Artikel[] = [];
  public searchField: FormControl = new FormControl();
  public isSearching = false;

  public artikelList: Artikel[] = [];
  private suchwort: string;

  constructor(private router: Router,
    private searchService: SearchService,
    private artikelService: ArtikelService,
    public warenkorbService: WarenkorbService
  ) {}

  public ngOnInit(): void {
    this.search();
    this.latestArtikel();
  }

  public gotoSearchpage(): void {
    console.log('Gehe zur Suche anzeigen...');
    this.router.navigateByUrl(`/tabs/search/suchwort/${this.suchwort}`);
  }

  /**
   * Initialisiert die Suchleiste. Bei Eingabe in der Suchleiste wird nach 500ms debounce eine Anfrage an den Server verschickt.
   */
  private search(): void {
    this.searchField.valueChanges.subscribe(searchInput => {
      if(this.searchField.value.length > 0){
        this.isSearching = true;
        this.suchwort = searchInput;
        this.searchService.getSearchResults(searchInput).subscribe(response => {
          this.results = response;
          console.log(this.results);
        });
      } else {
        this.isSearching = false;
      }
    });
  }

  /**
   * Holt die zuletzt hinzugefügten Artikel vom Server
   */
  private latestArtikel(): void {
    this.artikelService.getLatestArtikel().subscribe((artikel: Artikel[]) => {
      console.log(artikel);
      this.artikelList = artikel;
    });
  }

}
