package de.hsos.swa.warenkorb.boundary.rest;

import java.math.BigDecimal;

import de.hsos.swa.warenkorb.entity.LaenderCode;
import de.hsos.swa.warenkorb.entity.Lieferant;
import de.hsos.swa.warenkorb.entity.Warenkorbartikelversand;

/**
 * Die Klasse WarenkorbartikelversandDTO wird für den Transport der Daten aus
 * der Versand Entity
 * genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 27-07-2022
 */

public class WarenkorbartikelversandDTO {
    public Long id;
    public BigDecimal kosten;
    public Lieferant lieferant;
    public LaenderCode laenderCode;

    public WarenkorbartikelversandDTO() {
    }

    public WarenkorbartikelversandDTO(Long id, BigDecimal kosten, Lieferant lieferant, LaenderCode laenderCode) {
        this.id = id;
        this.kosten = kosten;
        this.lieferant = lieferant;
        this.laenderCode = laenderCode;
    }

    public static class Converter {
        public static WarenkorbartikelversandDTO toDTO(Warenkorbartikelversand versand) {
            return new WarenkorbartikelversandDTO(versand.getId(), versand.getKosten(), versand.getLieferant(),
                    versand.getLaenderCode());
        }

        public static Warenkorbartikelversand toVersand(WarenkorbartikelversandDTO versandDTO) {
            return new Warenkorbartikelversand(versandDTO.id, versandDTO.kosten, versandDTO.lieferant,
                    versandDTO.laenderCode);
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
