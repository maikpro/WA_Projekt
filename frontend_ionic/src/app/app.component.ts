import { Component, isDevMode, OnInit } from '@angular/core';
import { WarenkorbService } from './shared/services/warenkorb.service';
import { environment } from '../environments/environment';
import { Platform } from '@ionic/angular';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent implements OnInit {

  constructor(
    public warenkorbService: WarenkorbService,
    public platform: Platform) {
    }

  public ngOnInit(): void {
    if (isDevMode()) {
      console.log(`Development! Running on: ${environment.domain}`);
    } else {
      console.log(`Production! Running on: ${environment.domain}`);
    }
    this.warenkorbService.setWarenkorbId();
    this.warenkorbService.updateWarenkorbArtikelAnzahl();

    this.platform.ready().then(() => {
      if (this.platform.is('android')) {
        console.log('running on Android device!');
      }
      if (this.platform.is('capacitor')) {
        console.log('running on capacitor!');
      }
      if (this.platform.is('ios')) {
          console.log('running on iOS device!');
      }
      if (this.platform.is('desktop')) {
          console.log('running in a browser on desktop!');
      }
      if (this.platform.is('hybrid')) {
          console.log('running hybrid!');
      }
    });
  }
}
