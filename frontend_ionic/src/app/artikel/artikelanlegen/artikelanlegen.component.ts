/* eslint-disable @typescript-eslint/member-ordering */
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Artikel } from 'src/app/shared/models/Artikel';
import { Artikelzustand } from 'src/app/shared/models/Artikelzustand';
import { LaenderCode } from 'src/app/shared/models/LaenderCode';
import { Lieferant } from 'src/app/shared/models/Lieferant';
import { Versand } from 'src/app/shared/models/Versand';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ArtikelService } from '../../shared/services/artikel.service';


@Component({
  selector: 'app-artikelanlegen',
  templateUrl: './artikelanlegen.component.html',
  styleUrls: ['./artikelanlegen.component.scss']
})
export class ArtikelanlegenComponent implements OnInit {
  public artikelForm!: FormGroup;
  public artikelZustaende!: Artikelzustand[];
  public submitted = false;

  private numRegex = /^\d{0,6}(\.\d{0,2})?$/;

  // Zum bearbeiten
  public artikel!: Artikel;


  constructor(
    private route: ActivatedRoute,
    private artikelService: ArtikelService,
    private formBuilder: FormBuilder,
    private notifyService: NotificationService
  ) { }

  public ngOnInit(): void {
    this.artikelZustaende = Object.values(Artikelzustand); // Alle möglichen Zustände

    // Neuen Artikel erstellen
    this.artikelForm = this.formBuilder.group({
      name: ['', Validators.required],
      beschreibung: ['', Validators.required],
      artikelzustand: ['', Validators.required],
      preis: ['', [Validators.pattern(this.numRegex), Validators.required]],
    });

    this.route.params.subscribe(params => {
      //this.id = +params.id;

      if(+params.id){
        // Zum Artikel bearbeiten
        this.artikelService.getArtikelById(+params.id).subscribe((artikel: Artikel) => {
          console.log(artikel);
          this.artikel = artikel;
          this.artikelForm = this.formBuilder.group({
            name: [this.artikel.name, Validators.required],
            beschreibung: [this.artikel.beschreibung, Validators.required],
            artikelzustand: [this.artikel.artikelzustand, Validators.required],
            preis: [this.artikel.preis, [Validators.pattern(this.numRegex), Validators.required]],
          });
        });
      }
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.artikelForm.controls;
  }

  public createArtikel(): void {
    this.submitted = true;

    console.log(this.f.preis);

    // Aktuell nur statische Zuweisung vom Versand im Frontend
    const testVersand: Versand = {
      kosten: 4.99,
      lieferant: Lieferant.DHL,
      laenderCode: LaenderCode.DEU
    };

    /*const testArtikel: Artikel = {
      name: "testArtikel",
      beschreibung: "irgendeine beschreibung",
      preis: 99.98,
      artikelzustand: Artikelzustand.GEBRAUCHT,
      versand: testVersand
    }

    console.log("CREATE: ", testArtikel);*/

    if(this.artikelForm.valid){
      const neuerArtikel: Artikel = this.artikelForm.value;
      neuerArtikel.versand = testVersand;

      console.log(neuerArtikel);
      this.artikelService.createArtikel(neuerArtikel).subscribe(res => {
        console.log(res);
        this.notifyService.showSuccessMessage(`Du hast den Artikel '${neuerArtikel.name}' angelegt!`);
      });
      this.artikelForm.reset();
      this.submitted = false;
    }
  }

  public updateArtikel(): void {
    if(this.artikelForm.valid){
      //Update fields
      this.artikel.name = this.artikelForm.value.name;
      this.artikel.beschreibung = this.artikelForm.value.beschreibung;
      this.artikel.artikelzustand = this.artikelForm.value.artikelzustand;
      this.artikel.preis = this.artikelForm.value.preis;

      const updateArtikel: Artikel = this.artikel;
      console.log(updateArtikel);
      this.artikelService.updateArtikel(updateArtikel).subscribe(res => {
        console.log(res);
        this.notifyService.showSuccessMessage(`Du hast den Artikel '${updateArtikel.name}' aktualisiert!`);
      });
      //this.artikelForm.reset();
      this.submitted = false;
    }
  }
}
