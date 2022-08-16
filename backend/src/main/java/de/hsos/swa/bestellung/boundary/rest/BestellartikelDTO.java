package de.hsos.swa.bestellung.boundary.rest;

import java.math.BigDecimal;

import de.hsos.swa.bestellung.entity.Bestellartikel;
import de.hsos.swa.bestellung.entity.Bestellartikelzustand;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 23-07-2022
 */

public class BestellartikelDTO {
    public Long id;
    public String name;
    public BigDecimal preis;
    public Bestellartikelzustand bestellartikelzustand;
    public BestellartikelversandDTO versand;
    public String username;
    public Long artikelIdReference;

    public BestellartikelDTO() {
    }

    public BestellartikelDTO(Long id, String name, BigDecimal preis, Bestellartikelzustand bestellartikelzustand,
            BestellartikelversandDTO versand, String username, Long artikelIdReference) {
        this.id = id;
        this.name = name;
        this.preis = preis;
        this.bestellartikelzustand = bestellartikelzustand;
        this.versand = versand;
        this.username = username;
        this.artikelIdReference = artikelIdReference;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", name='" + name + "'" +
                ", preis='" + preis + "'" +
                ", bestellartikelzustand='" + bestellartikelzustand + "'" +
                ", versand='" + versand + "'" +
                ", username='" + username + "'" +
                ", artikelIdReference='" + artikelIdReference + "'" +
                "}";
    }

    public static class Converter {
        public static BestellartikelDTO toBestellartikelDTO(Bestellartikel bestellartikel) {
            return new BestellartikelDTO(bestellartikel.getId(), bestellartikel.getName(), bestellartikel.getPreis(),
                    bestellartikel.getBestellartikelzustand(),
                    BestellartikelversandDTO.Converter.toBestellartikelversandDTO(bestellartikel.getVersand()),
                    bestellartikel.getUsername(), bestellartikel.getArtikelIdReference());
        }

        public static Bestellartikel toBestellartikel(BestellartikelDTO bestellartikelDTO) {
            return new Bestellartikel(bestellartikelDTO.id, bestellartikelDTO.name, bestellartikelDTO.preis,
                    bestellartikelDTO.bestellartikelzustand,
                    BestellartikelversandDTO.Converter.toBestellartikelversand(bestellartikelDTO.versand),
                    bestellartikelDTO.username, bestellartikelDTO.artikelIdReference);
        }
    }
}
