import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Artikel } from 'src/app/shared/models/Artikel';
import { CookieService } from 'ngx-cookie-service';
import { Warenkorb } from '../models/Warenkorb';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class ArtikelService {
  private readonly apiUrl = environment.apiKey + '/api/artikel';

  constructor(private http: HttpClient, private cookieService: CookieService) { }

  public getArtikelById(id: number): Observable<Artikel> {
    return this.http.get<Artikel>(this.apiUrl + '/id/' + id);
  }

  public getYourArtikel(): Observable<Artikel[]> {
    return this.http.get<Artikel[]>(this.apiUrl + '/deine-artikel');
  }

  public getLatestArtikel(): Observable<Artikel[]> {
    return this.http.get<Artikel[]>(this.apiUrl);
  }

  public getVerkaufteArtikel(): Observable<Artikel[]> {
    return this.http.get<Artikel[]>(this.apiUrl + '/verkauft');
  }

  public createArtikel(artikel: Artikel): Observable<Artikel> {
    return this.http.post<Artikel>(this.apiUrl + '/anlegen', artikel);
  }

  public updateArtikel(artikel: Artikel): Observable<Artikel> {
    return this.http.put<Artikel>(this.apiUrl + '/id/' + artikel.id, artikel);
  }

  public deleteArtikel(id: number): any {
    return this.http.delete<any>(this.apiUrl + '/id/' + id);
  }

  public addArtikelToWarenkorb(artikel: Artikel): Observable<Warenkorb> {
    const warenkorbId = parseInt(this.cookieService.get('warenkorbId'), 10);
    return this.http.put<Warenkorb>(this.apiUrl + `/warenkorb/id/${warenkorbId}`, artikel);
  }

  public addArtikelToBeobachtungsliste(artikel: Artikel): Observable<Artikel> {
    return this.http.put<Artikel>(this.apiUrl + '/account/beobachtungsliste', artikel);
  }
}
