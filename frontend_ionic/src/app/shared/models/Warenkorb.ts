import { Artikelzustand } from './Artikelzustand';
import { Versand } from './Versand';

export interface Warenkorb {
    id: number;
    artikelSumme?: number;
    versandSumme?: number;
    gesamtSumme: number;
    warenkorbpostenlist: Warenkorbposten[];
}

export interface Warenkorbposten {
    postenNr?: number;
    menge: number;
    warenkorbartikel: Warenkorbartikel;
}

export interface Warenkorbartikel {
    id?: number;
    name: string;
    preis: number;
    warenkorbartikelzustand: Artikelzustand;
    versand: Versand;
    username: string;
    artikelIdReference: number;
}
