package de.hsos.swa.bestellung.gateway;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.hsos.swa.bestellung.entity.Bestellposten;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 23-07-2022
 */

@Entity
@Vetoed
@Table(name = "bestellposten")
public class BestellpostenDTODB {
    @Id
    @GeneratedValue
    public Long postenNr;
    public Integer menge;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bestellartikel_id")
    public BestellartikelDTODB bestellartikelDTODB;

    public BestellpostenDTODB() {
    }

    public BestellpostenDTODB(Long postenNr, Integer menge, BestellartikelDTODB bestellartikelDTODB) {
        this.postenNr = postenNr;
        this.menge = menge;
        this.bestellartikelDTODB = bestellartikelDTODB;
    }

    @Override
    public String toString() {
        return "{" +
                " postenNr='" + postenNr + "'" +
                ", menge='" + menge + "'" +
                ", bestellartikelDTODB='" + bestellartikelDTODB + "'" +
                "}";
    }

    public static class Converter {
        public static BestellpostenDTODB toBestellungpostenDTODB(Bestellposten bestellposten) {
            return new BestellpostenDTODB(bestellposten.getPostenNr(), bestellposten.getMenge(),
                    BestellartikelDTODB.Converter.toBestellartikelDTODB(bestellposten.getBestellartikel()));
        }

        public static Bestellposten toBestellposten(BestellpostenDTODB bestellungpostenDTODB) {
            return new Bestellposten(bestellungpostenDTODB.postenNr, bestellungpostenDTODB.menge,
                    BestellartikelDTODB.Converter.toBestellartikel(bestellungpostenDTODB.bestellartikelDTODB));
        }
    }
}
