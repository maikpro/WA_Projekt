package de.hsos.swa.warenkorb.gateway;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.hsos.swa.warenkorb.entity.Warenkorbposten;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 20-07-2022
 */

@Entity
@Vetoed
@Table(name = "warenkorbposten")
public class WarenkorbpostenDTODB {
    @Id
    @GeneratedValue
    public Long postenNr;
    public Integer menge;

    @OneToOne
    @JoinColumn(name = "warenkorbartikel_id")
    public WarenkorbartikelDTODB warenkorbartikelDTODB;

    public WarenkorbpostenDTODB() {
    }

    public WarenkorbpostenDTODB(Long postenNr, Integer menge, WarenkorbartikelDTODB warenkorbartikelDTODB) {
        this.postenNr = postenNr;
        this.menge = menge;
        this.warenkorbartikelDTODB = warenkorbartikelDTODB;
    }

    // Zum Artikel hinzufügen
    public WarenkorbpostenDTODB(Integer menge, WarenkorbartikelDTODB warenkorbartikelDTODB) {
        this.menge = menge;
        this.warenkorbartikelDTODB = warenkorbartikelDTODB;
    }

    @Override
    public String toString() {
        return "{" +
                " postenNr='" + postenNr + "'" +
                ", menge='" + menge + "'" +
                ", warenkorbartikelDTODB='" + warenkorbartikelDTODB + "'" +
                "}";
    }

    public static class Converter {
        public static WarenkorbpostenDTODB toWarenkorbpostenDTODB(Warenkorbposten warenkorbposten) {
            return new WarenkorbpostenDTODB(warenkorbposten.getPostenNr(), warenkorbposten.getMenge(),
                    WarenkorbartikelDTODB.Converter.toDTODB(warenkorbposten.getWarenkorbartikel()));
        }

        public static Warenkorbposten toWarenkorbposten(WarenkorbpostenDTODB warenkorbpostenDTODB) {
            return new Warenkorbposten(warenkorbpostenDTODB.postenNr, warenkorbpostenDTODB.menge,
                    WarenkorbartikelDTODB.Converter.toWarenkorbartikel(warenkorbpostenDTODB.warenkorbartikelDTODB));
        }
    }

}
