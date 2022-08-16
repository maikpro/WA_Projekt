import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Artikel } from 'src/app/shared/models/Artikel';
import { CookieService } from 'ngx-cookie-service'
import { Warenkorb } from '../models/Warenkorb';
import { WarenkorbService } from './warenkorb.service';
import { Account } from '../models/Account';


@Injectable({
  providedIn: 'root'
})
export class ArtikelService {
  private readonly apiUrl = "http://localhost:8080/api/artikel";

  constructor(private http: HttpClient, private cookieService: CookieService) { }

  public getArtikelById(id: number): Observable<Artikel> {
    return this.http.get<Artikel>(this.apiUrl + "/id/" + id);
  }

  public getLatestArtikel(): Observable<Artikel[]> {
    return this.http.get<Artikel[]>(this.apiUrl);
  }

  public getVerkaufteArtikel(): Observable<Artikel[]> {
    return this.http.get<Artikel[]>(this.apiUrl + "/verkauft");
  }

  public createArtikel(artikel: Artikel): Observable<Artikel> {
    return this.http.post<Artikel>(this.apiUrl + "/anlegen", artikel);
  }

  public addArtikelToWarenkorb(artikel: Artikel): Observable<Warenkorb> {
    const warenkorbId = parseInt(this.cookieService.get("warenkorbId"));
    return this.http.put<Warenkorb>(this.apiUrl + `/warenkorb/id/${warenkorbId}`, artikel);
  }

  public addArtikelToBeobachtungsliste(artikel: Artikel): Observable<Artikel> {
    return this.http.put<Artikel>(this.apiUrl + "/account/beobachtungsliste", artikel);
  }
}
