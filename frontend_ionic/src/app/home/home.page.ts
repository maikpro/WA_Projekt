/* eslint-disable @typescript-eslint/no-unused-expressions */
/* eslint-disable @typescript-eslint/member-ordering */
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { App } from '@capacitor/app';
import { Platform } from '@ionic/angular';
import Keycloak from 'keycloak-capacitor';
import { environment } from 'src/environments/environment';
import { Artikel } from '../shared/models/Artikel';
import { ArtikelService } from '../shared/services/artikel.service';
import { NotificationService } from '../shared/services/notification.service';
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

  public keycloak: Keycloak.KeycloakInstance;

  constructor(private router: Router,
    private searchService: SearchService,
    private artikelService: ArtikelService,
    public warenkorbService: WarenkorbService,
    private notifyService: NotificationService,
    public platform: Platform
  ) {}

  public ngOnInit(): void {
    this.search();
    this.latestArtikel();

    this.keycloak = new Keycloak({
      clientId: 'quarkus-app',
      realm: 'quarkus',
      url: environment.keycloakApi,
    });

    if (this.platform.is('hybrid')) {
      this.keycloak.init({
        adapter: 'capacitor-native',
        responseMode: 'query',
        redirectUri: window.location.origin
      });
    } else { // for web
        this.keycloak.init({
          adapter: 'default',
          redirectUri: environment.frontend
        });
    }

    async () => {
      const { url } = await App.getLaunchUrl();

      console.log('App opened with URL: ' + url.toString());
    };
  }

  public tester(): void {
    App.addListener('appUrlOpen', data => {
      console.log('App opened with URL:', data.url);
    });
  }

  public login(): void {
    this.keycloak.login();
  }

  public doRefresh(event): void {
    this.latestArtikel();
    event.target.complete();
  }

  public gotoSearchpage(): void {
    console.log('Gehe zur Suche anzeigen...');
    this.router.navigateByUrl(`/search/suchwort/${this.suchwort}`);
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
    }, error => {
      this.notifyService.showErrorMessage('Beim Abrufen der Artikelliste ist ein Fehler aufgetreten');
    });
  }

}
