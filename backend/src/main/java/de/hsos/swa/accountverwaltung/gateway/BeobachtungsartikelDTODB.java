package de.hsos.swa.accountverwaltung.gateway;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import de.hsos.swa.accountverwaltung.entity.Artikelzustand;
import de.hsos.swa.accountverwaltung.entity.Beobachtungsartikel;

/**
 * Die Klasse BeobachtungsartikelDTODB wird von der Accountverwaltung genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 28-07-2022
 */

@Entity
@Table(name = "beobachtungsartikel")
public class BeobachtungsartikelDTODB {
    @Id
    @GeneratedValue
    public Long id;
    public String name;
    public BigDecimal preis;
    public Artikelzustand artikelzustand;
    public Long artikelIdReference;

    public BeobachtungsartikelDTODB() {
    }

    public BeobachtungsartikelDTODB(Long id, String name, BigDecimal preis, Artikelzustand artikelzustand,
            Long artikelIdReference) {
        this.id = id;
        this.name = name;
        this.preis = preis;
        this.artikelzustand = artikelzustand;
        this.artikelIdReference = artikelIdReference;
    }

    public static class Converter {
        public static BeobachtungsartikelDTODB toDTODB(Beobachtungsartikel beobachtungsartikel) {
            return new BeobachtungsartikelDTODB(beobachtungsartikel.getId(), beobachtungsartikel.getName(),
                    beobachtungsartikel.getPreis(), beobachtungsartikel.getArtikelzustand(),
                    beobachtungsartikel.getArtikelIdReference());
        }

        public static Beobachtungsartikel toBeobachtungsartikel(BeobachtungsartikelDTODB beobachtungsartikelDTODB) {
            return new Beobachtungsartikel(beobachtungsartikelDTODB.id, beobachtungsartikelDTODB.name,
                    beobachtungsartikelDTODB.preis, beobachtungsartikelDTODB.artikelzustand,
                    beobachtungsartikelDTODB.artikelIdReference);
        }
    }
}
