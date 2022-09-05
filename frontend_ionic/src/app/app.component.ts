import { Component, isDevMode, NgZone, OnInit } from '@angular/core';
import { WarenkorbService } from './shared/services/warenkorb.service';
import { environment } from '../environments/environment';
import { Router } from '@angular/router';

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
    }

  public ngOnInit(): void {
    if (isDevMode()) {
      console.log(`Development! Running on: ${environment.domain}`);
    } else {
      console.log(`Production! Running on: ${environment.domain}`);
    }
    this.warenkorbService.setWarenkorbId();
    this.warenkorbService.updateWarenkorbArtikelAnzahl();
  }
}
