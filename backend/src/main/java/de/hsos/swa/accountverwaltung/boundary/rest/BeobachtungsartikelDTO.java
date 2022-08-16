package de.hsos.swa.accountverwaltung.boundary.rest;

import java.math.BigDecimal;

import de.hsos.swa.accountverwaltung.entity.Artikelzustand;
import de.hsos.swa.accountverwaltung.entity.Beobachtungsartikel;

/**
 * Die Klasse BeobachtungsartikelDTO wird von der Accountverwaltung genutzt.
 * 
 *
 * @author Maik Proba
 * @version 1.0
 * @since 28-07-2022
 */

public class BeobachtungsartikelDTO {
    public Long id;
    public String name;
    public BigDecimal preis;
    public Artikelzustand artikelzustand;
    public Long artikelIdReference;

    public BeobachtungsartikelDTO() {
    }

    public BeobachtungsartikelDTO(Long id, String name, BigDecimal preis, Artikelzustand artikelzustand,
            Long artikelIdReference) {
        this.id = id;
        this.name = name;
        this.preis = preis;
        this.artikelzustand = artikelzustand;
        this.artikelIdReference = artikelIdReference;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", name='" + name + "'" +
                ", preis='" + preis + "'" +
                ", artikelzustand='" + artikelzustand + "'" +
                ", artikelIdReference='" + artikelIdReference + "'" +
                "}";
    }

    public static class Converter {
        public static BeobachtungsartikelDTO toDTO(Beobachtungsartikel beobachtungsartikel) {
            return new BeobachtungsartikelDTO(beobachtungsartikel.getId(), beobachtungsartikel.getName(),
                    beobachtungsartikel.getPreis(), beobachtungsartikel.getArtikelzustand(),
                    beobachtungsartikel.getArtikelIdReference());
        }

        public static Beobachtungsartikel toBeobachtungsartikel(BeobachtungsartikelDTO beobachtungsartikelDTO) {
            return new Beobachtungsartikel(beobachtungsartikelDTO.id, beobachtungsartikelDTO.name,
                    beobachtungsartikelDTO.preis, beobachtungsartikelDTO.artikelzustand,
                    beobachtungsartikelDTO.artikelIdReference);
        }
    }

}
