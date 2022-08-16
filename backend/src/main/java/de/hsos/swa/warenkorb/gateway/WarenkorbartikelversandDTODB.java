package de.hsos.swa.warenkorb.gateway;

import java.math.BigDecimal;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import de.hsos.swa.warenkorb.entity.LaenderCode;
import de.hsos.swa.warenkorb.entity.Lieferant;
import de.hsos.swa.warenkorb.entity.Warenkorbartikelversand;

/**
 * Die Klasse WarenkorbartikelversandDTODB wird für den Transport der Daten aus
 * der Versand Entity
 * genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 27-07-2022
 */
@Entity
@Vetoed
@Table(name = "warenkorbartikelversand")
public class WarenkorbartikelversandDTODB {
    @Id
    @GeneratedValue
    public Long id;
    public BigDecimal kosten;

    @Enumerated(EnumType.STRING)
    public Lieferant lieferant;

    @Enumerated(EnumType.STRING)
    public LaenderCode laenderCode;

    public WarenkorbartikelversandDTODB() {
    }

    public WarenkorbartikelversandDTODB(Long id, BigDecimal kosten, Lieferant lieferant, LaenderCode laenderCode) {
        this.id = id;
        this.kosten = kosten;
        this.lieferant = lieferant;
        this.laenderCode = laenderCode;
    }

    public static class Converter {
        public static WarenkorbartikelversandDTODB toDTO(Warenkorbartikelversand versand) {
            return new WarenkorbartikelversandDTODB(versand.getId(), versand.getKosten(), versand.getLieferant(),
                    versand.getLaenderCode());
        }

        public static Warenkorbartikelversand toVersand(WarenkorbartikelversandDTODB versandDTO) {
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
