import { Artikelzustand } from "./Artikelzustand";

export interface Beobachtungsartikel {
    id: number;
    name: string;
    preis: number;
    artikelzustand: Artikelzustand;
    artikelIdReference: number;
}