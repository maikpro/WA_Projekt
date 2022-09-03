import { Component, isDevMode, NgZone, OnInit } from '@angular/core';
import { WarenkorbService } from './shared/services/warenkorb.service';
import { environment } from '../environments/environment';
import { Router } from '@angular/router';
import { App, URLOpenListenerEvent } from '@capacitor/app';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent implements OnInit {
  constructor(
    private router: Router,
    private zone: NgZone,
    public warenkorbService: WarenkorbService) {
      //this.initializeApp();
    }

  public ngOnInit(): void {
    if (isDevMode()) {
      console.log('Development!');
    } else {
      console.log('Production!');
    }
    this.warenkorbService.setWarenkorbId();
    this.warenkorbService.updateWarenkorbArtikelAnzahl();
  }

  private initializeApp() {
    App.addListener('appUrlOpen', (event: URLOpenListenerEvent) => {
        this.zone.run(() => {
            const domain = environment.domain;
            const slug = event.url.split(domain).pop();
            if (slug) {
                this.router.navigateByUrl(slug);
            }
            // If no match, do nothing - let regular routing
            // logic take over
        });
    });
  }
}
