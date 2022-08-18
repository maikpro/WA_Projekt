import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Bestellung } from '../shared/models/Bestellung';

@Injectable({
  providedIn: 'root'
})
export class BestellungService {
  private readonly apiUrl = 'http://localhost:8080/api/bestellung';

  constructor(private http: HttpClient) { }

  public createBestellung(bestellung: Bestellung): Observable<Bestellung> {
    return this.http.post<Bestellung>(this.apiUrl, bestellung);
  }

  public getBestellungById(id: number): Observable<Bestellung> {
    return this.http.get<Bestellung>(this.apiUrl + `/id/${id}`);
  }

  public getKaeufe(): Observable<any> {
    return this.http.get<any>(this.apiUrl + '/account/kaeufe');
  }
}
