import {HttpClient} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Artikel } from '../models/Artikel';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private readonly apiUrl = environment.apiKey + '/api/artikel/suche?sw=';

  constructor(private http: HttpClient) { }

  public getSearchResults(searchInput: string): Observable<Artikel[]> {
    return this.http.get<Artikel[]>(this.apiUrl + searchInput);
  }
}
