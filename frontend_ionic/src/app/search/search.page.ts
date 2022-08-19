import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Artikel } from '../shared/models/Artikel';
import { ArtikelService } from '../shared/services/artikel.service';
import { SearchService } from '../shared/services/search.service';
import { WarenkorbService } from '../shared/services/warenkorb.service';

@Component({
  selector: 'app-search',
  templateUrl: 'search.page.html',
  styleUrls: ['search.page.scss']
})
export class SearchPage implements OnInit {
  public results: Artikel[] = [];
  public searchField: FormControl = new FormControl();
  public isSearching = false;

  public suchwort!: string;
  public artikelList: Artikel[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private searchService: SearchService,
    private artikelService: ArtikelService,
    public warenkorbService: WarenkorbService
  ) {}

  public ngOnInit(): void {
    this.search();

    this.route.params.subscribe(params => {
      this.suchwort = params.suchwort;
      this.searchField.patchValue(this.suchwort);
      this.isSearching=false;
      if(this.suchwort){
        console.log('aktuelles Suchwort: ', this.suchwort);
        this.showArtikellist();
      }
    });
  }

  public gotoSearchpage(): void {
    console.log('Gehe zur Suche anzeigen...');
    this.router.navigateByUrl(`/tabs/search/suchwort/${this.suchwort}`);
  }

  private showArtikellist(): void {
    this.searchService.getSearchResults(this.suchwort).subscribe((artikel: Artikel[]) => {
      this.artikelList = artikel;
    });
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
}
