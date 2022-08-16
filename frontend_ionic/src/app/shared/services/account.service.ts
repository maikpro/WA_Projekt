import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Account } from '../models/Account';
import { KeycloakAccount } from '../models/KeycloakAccount';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private readonly apiUrl = "http://localhost:8080/api/accounts";

  constructor(private http: HttpClient) { }

  public getCurrentAccount(): Observable<KeycloakAccount> {
    return this.http.get<KeycloakAccount>(this.apiUrl + "/currentLogged");
  }

  public getCurrentAccountInfo(): Observable<Account> {
    return this.http.get<Account>(this.apiUrl + "/currentAccountInfo");
  }

  public getAccountByUsername(username: string): Observable<Account> {
    return this.http.get<Account>(this.apiUrl + `/username/${username}`);
  }

  public createAccount(account: Account): Observable<Account> {
    return this.http.post<Account>(this.apiUrl, account);
  }

  public updateAccount(account: Account): Observable<Account> {
    return this.http.put<Account>(this.apiUrl, account);
  }

  public deleteAccount(): Observable<any> {
    return this.http.delete<any>(this.apiUrl);
  }

  public deleteBeobachtungsartikel(id: number): Observable<Account> {
    return this.http.delete<Account>(this.apiUrl + `/beobachtungsliste/beobachtungsartikel/id/${id}`);
  }
}
