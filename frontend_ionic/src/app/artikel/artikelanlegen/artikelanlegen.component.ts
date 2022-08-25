/* eslint-disable @typescript-eslint/member-ordering */
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
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


  constructor(private artikelService: ArtikelService, private formBuilder: FormBuilder, private notifyService: NotificationService) { }

  public ngOnInit(): void {
    this.artikelZustaende = Object.values(Artikelzustand); // Alle möglichen Zustände

    this.artikelForm = this.formBuilder.group({
      name: ['', Validators.required],
      beschreibung: ['', Validators.required],
      artikelzustand: ['', Validators.required],
      preis: ['', [Validators.pattern(this.numRegex), Validators.required]],
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.artikelForm.controls;
  }

  public createArtikel(): void {
    this.submitted = true;

    console.log(this.f.preis);

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
}
