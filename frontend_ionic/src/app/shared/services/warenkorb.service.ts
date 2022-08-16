/* eslint-disable @typescript-eslint/member-ordering */
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Warenkorb } from '../models/Warenkorb';
import { CookieService } from 'ngx-cookie-service';


@Injectable({
  providedIn: 'root'
})
export class WarenkorbService {
  private readonly apiUrl = 'http://localhost:8080/api/warenkorb';

  public warenkorbArtikelAnzahl = 0;

  constructor(private http: HttpClient, private cookieService: CookieService) { }

  public getWarenkorb(): Observable<Warenkorb> {
    const warenkorbId = parseInt(this.cookieService.get('warenkorbId'), 10);
    return this.http.get<Warenkorb>(this.apiUrl + `/id/${warenkorbId}`);
  }

  public updateWarenkorbArtikelAnzahl(): void {
    this.getWarenkorb().subscribe(warenkorb => {
      console.log(warenkorb);
      this.warenkorbArtikelAnzahl = warenkorb.warenkorbpostenlist.length;
    },
    error => {
      if(error.status === 404){
        this.cookieService.delete('warenkorbId');
      }
    });
  }

  public setWarenkorbId(): void {
    if(this.cookieService.get('warenkorbId').length === 0){
      this.http.post<Warenkorb>(this.apiUrl, null).subscribe(res => {
        const warenkorb: Warenkorb = res;
        console.log(warenkorb);

        // NUR ZUM TESTEN!
        // 60 Sekunden wird die WarenkorbId gespeichert
        //const expireDate = new Date();
        //expireDate.setSeconds(expireDate.getSeconds() + 60)

        // Warenkorb wird für 24 Stunden gespeichert!
        const expireDate = new Date();
        expireDate.setHours(expireDate.getHours() + 24);

        this.cookieService.set('warenkorbId', warenkorb.id.toString(), expireDate);
      });
    }
  }

  public deleteArtikelFromWarenkorb(id: number): Observable<Warenkorb> {
    const warenkorbId = parseInt(this.cookieService.get('warenkorbId'), 10);
    return this.http.delete<Warenkorb>(this.apiUrl + `/id/${warenkorbId}/artikelId/${id}`);
  }

  public clearAndsetWarenkorb(): void {
    this.http.post<Warenkorb>(this.apiUrl, null).subscribe(res => {
      const warenkorb: Warenkorb = res;
      console.log(warenkorb);
      const expireDate = new Date();
      expireDate.setHours(expireDate.getHours() + 24);

      this.cookieService.set('warenkorbId', warenkorb.id.toString(), expireDate);
      this.updateWarenkorbArtikelAnzahl();
    });
  }
}
