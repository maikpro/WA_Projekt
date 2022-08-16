package de.hsos.swa.bestellung.boundary.rest;

import javax.json.bind.annotation.JsonbProperty;

import de.hsos.swa.bestellung.entity.Bestellposten;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 23-07-2022
 */

public class BestellpostenDTO {
    public Long postenNr;
    public Integer menge;
    @JsonbProperty("warenkorbartikel")
    public BestellartikelDTO bestellartikelDTO;

    public BestellpostenDTO() {
    }

    public BestellpostenDTO(Long postenNr, Integer menge, BestellartikelDTO bestellartikelDTO) {
        this.postenNr = postenNr;
        this.menge = menge;
        this.bestellartikelDTO = bestellartikelDTO;
    }

    @Override
    public String toString() {
        return "{" +
                " postenNr='" + postenNr + "'" +
                ", menge='" + menge + "'" +
                ", bestellartikelDTO='" + bestellartikelDTO + "'" +
                "}";
    }

    public static class Converter {
        public static BestellpostenDTO toBestellungpostenDTO(Bestellposten bestellposten) {
            return new BestellpostenDTO(bestellposten.getPostenNr(), bestellposten.getMenge(),
                    BestellartikelDTO.Converter.toBestellartikelDTO(bestellposten.getBestellartikel()));
        }

        public static Bestellposten toBestellposten(BestellpostenDTO bestellungpostenDTO) {
            return new Bestellposten(bestellungpostenDTO.postenNr, bestellungpostenDTO.menge,
                    BestellartikelDTO.Converter.toBestellartikel(bestellungpostenDTO.bestellartikelDTO));
        }
    }
}
