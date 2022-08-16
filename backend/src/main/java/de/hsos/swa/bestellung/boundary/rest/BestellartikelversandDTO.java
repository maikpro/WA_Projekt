package de.hsos.swa.bestellung.boundary.rest;

import java.math.BigDecimal;

import de.hsos.swa.bestellung.entity.Bestellartikelversand;
import de.hsos.swa.bestellung.entity.LaenderCode;
import de.hsos.swa.bestellung.entity.Lieferant;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 29-07-2022
 */

public class BestellartikelversandDTO {
    public Long id;
    public BigDecimal kosten;
    public Lieferant lieferant;
    public LaenderCode laenderCode;

    public BestellartikelversandDTO() {
    }

    public BestellartikelversandDTO(Long id, BigDecimal kosten, Lieferant lieferant, LaenderCode laenderCode) {
        this.id = id;
        this.kosten = kosten;
        this.lieferant = lieferant;
        this.laenderCode = laenderCode;
    }

    public static class Converter {
        public static BestellartikelversandDTO toBestellartikelversandDTO(Bestellartikelversand versand) {
            return new BestellartikelversandDTO(versand.getId(), versand.getKosten(), versand.getLieferant(),
                    versand.getLaenderCode());
        }

        public static Bestellartikelversand toBestellartikelversand(BestellartikelversandDTO bestellartikelversandDTO) {
            return new Bestellartikelversand(bestellartikelversandDTO.id, bestellartikelversandDTO.kosten,
                    bestellartikelversandDTO.lieferant,
                    bestellartikelversandDTO.laenderCode);
        }
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", kosten='" + kosten + "'" +
                ", lieferant='" + String.valueOf(lieferant) + "'" +
                ", laenderCode='" + String.valueOf(laenderCode) + "'" +
                "}";
    }
}
