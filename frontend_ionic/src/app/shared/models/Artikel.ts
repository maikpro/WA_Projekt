import { Artikelstatus } from "./Artikelstatus";
import { Artikelzustand } from "./Artikelzustand";
import { Versand } from "./Versand";

export interface Artikel {
    id?: number;
    name: string;
    beschreibung: string;
    preis: number;
    artikelzustand: Artikelzustand;
    versand: Versand;
    erstellt?: Date;
    geaendert?: Date;
    aufrufe?: Number;
    beobachter?: Number;
    transaktionAm?: Date;
    username?: string;
    artikelstatus?: Artikelstatus;
}