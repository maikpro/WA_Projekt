import { Adresse } from "./Adresse";
import { Beobachtungsartikel } from "./Beobachtungsartikel";

export interface Account {
    id?: number;
    vorname: string;
    nachname: string;
    email: string;
    username: string;
    adresse: Adresse;
    beobachtungsliste?: Beobachtungsartikel[];
}