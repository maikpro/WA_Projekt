package de.hsos.swa.artikelverwaltung.boundary.rest.artikel;

import java.math.BigDecimal;

import de.hsos.swa.artikelverwaltung.entity.LaenderCode;
import de.hsos.swa.artikelverwaltung.entity.Lieferant;
import de.hsos.swa.artikelverwaltung.entity.Versand;

/**
 * Die Klasse VersandDTO wird für den Transport der Daten aus der Versand Entity
 * genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 13-07-2022
 */

public class VersandDTO {
    public Long id;
    public BigDecimal kosten;
    public Lieferant lieferant;
    public LaenderCode laenderCode;

    public VersandDTO() {
    }

    public VersandDTO(Long id, BigDecimal kosten, Lieferant lieferant, LaenderCode laenderCode) {
        this.id = id;
        this.kosten = kosten;
        this.lieferant = lieferant;
        this.laenderCode = laenderCode;
    }

    // Ohne id zum testen
    public VersandDTO(BigDecimal kosten, Lieferant lieferant, LaenderCode laenderCode) {
        this.kosten = kosten;
        this.lieferant = lieferant;
        this.laenderCode = laenderCode;
    }

    public static class Converter {
        public static VersandDTO toDTO(Versand versand) {
            return new VersandDTO(versand.getId(), versand.getKosten(), versand.getLieferant(),
                    versand.getLaenderCode());
        }

        public static Versand toVersand(VersandDTO versandDTO) {
            return new Versand(versandDTO.kosten, versandDTO.lieferant, versandDTO.laenderCode);
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
