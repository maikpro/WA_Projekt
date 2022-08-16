import { Account } from "./Account";
import { Bestellstatus } from "./Bestellstatus";
import { Bezahlmethode } from "./Bezahlmethode";
import { Warenkorbposten } from "./Warenkorb";

export interface Bestellung {
    id?: number;
    gesamtSumme: number;
    bezahlmethode: Bezahlmethode;
    bestellstatus: Bestellstatus;
    bestellposten: Warenkorbposten[];
    kaeufer: Account;
    verkaeufer: Account[];
}