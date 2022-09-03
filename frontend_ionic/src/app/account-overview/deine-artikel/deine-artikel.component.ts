import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Artikel } from 'src/app/shared/models/Artikel';
import { ArtikelService } from 'src/app/shared/services/artikel.service';
import { NotificationService } from 'src/app/shared/services/notification.service';

@Component({
  selector: 'app-deine-artikel',
  templateUrl: './deine-artikel.component.html',
  styleUrls: ['./deine-artikel.component.scss'],
})
export class DeineArtikelComponent implements OnInit {
  public yourArtikel: Artikel[];

  constructor(private artikelService: ArtikelService, private router: Router, private notifyService: NotificationService) { }

  public ngOnInit() {
    this.getYourArtikel();
  }

  public doRefresh(event): void {
    this.getYourArtikel();
    event.target.complete();
  }

  public gotoBearbeiten(id: number): void {
    this.router.navigateByUrl(`/account-overview/deine-artikel/update/id/${id}`);
  }

  public async deleteArtikel(id: number) {
    // eslint-disable-next-line max-len
    const result = await this.notifyService.confirm(`Artikel(ID: ${id}) löschen`, `Wollen Sie den Artikel mit der ID '${id}' wirklich löschen?`, 'Nein', 'Ja');

    if(result){
      this.artikelService.deleteArtikel(id).subscribe(res => {
        console.log(res, `Artikel mit der id ${id} erfolgreich gelöscht!`);
        this.notifyService.showSuccessMessage(`Der Artikel mit der ID ${id} wurde erfolgreich gelöscht!`);
        this.getYourArtikel();
      });
    }
  }

  private getYourArtikel(): void {
    this.artikelService.getYourArtikel().subscribe((artikelArray: Artikel[]) => {
      console.log(artikelArray);
      this.yourArtikel = artikelArray;
    });
  }
}
