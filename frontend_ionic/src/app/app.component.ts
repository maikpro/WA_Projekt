import { Component, OnInit } from '@angular/core';
import { WarenkorbService } from './shared/services/warenkorb.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent implements OnInit {
  constructor(public warenkorbService: WarenkorbService) {}

  public ngOnInit(): void {
    this.warenkorbService.setWarenkorbId();
    this.warenkorbService.updateWarenkorbArtikelAnzahl();
  }
}
