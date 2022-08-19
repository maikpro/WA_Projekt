import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Bestellung } from 'src/app/shared/models/Bestellung';
import { WarenkorbService } from 'src/app/shared/services/warenkorb.service';
import { BestellungService } from '../bestellung.service';

@Component({
  selector: 'app-bestellung-complete',
  templateUrl: './bestellung-complete.component.html',
  styleUrls: ['./bestellung-complete.component.scss']
})
export class BestellungCompleteComponent implements OnInit {
  public id!: number;
  public bestellung!: Bestellung;

  constructor(private route: ActivatedRoute, private bestellService: BestellungService) { }

  public ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = +params.id;
      console.log('aktuelle Id: ', this.id);
      this.bestellService.getBestellungById(this.id).subscribe((bestellung: Bestellung) => {
        console.log(bestellung);
        this.bestellung=bestellung;
      });
    });
  }
}
