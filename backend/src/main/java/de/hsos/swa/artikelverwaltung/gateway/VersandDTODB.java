package de.hsos.swa.artikelverwaltung.gateway;

import java.math.BigDecimal;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import de.hsos.swa.artikelverwaltung.entity.LaenderCode;
import de.hsos.swa.artikelverwaltung.entity.Lieferant;
import de.hsos.swa.artikelverwaltung.entity.Versand;

/**
 * Die Klasse VersandDTODB wird für den Transport der Daten aus der Versand
 * Entity zur Datenbank genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 13-07-2022
 */
@Entity
@Vetoed
@Table(name = "versand")
public class VersandDTODB {
    @Id
    @GeneratedValue
    public Long id;
    public BigDecimal kosten;

    @Enumerated(EnumType.STRING)
    public Lieferant lieferant;

    @Enumerated(EnumType.STRING)
    public LaenderCode laenderCode;

    public VersandDTODB() {
    }

    public VersandDTODB(Long id, BigDecimal kosten, Lieferant lieferant, LaenderCode laenderCode) {
        this.id = id;
        this.kosten = kosten;
        this.lieferant = lieferant;
        this.laenderCode = laenderCode;
    }

    public static class Converter {
        public static VersandDTODB toDTODB(Versand versand) {
            return new VersandDTODB(versand.getId(), versand.getKosten(), versand.getLieferant(),
                    versand.getLaenderCode());
        }

        public static Versand toVersand(VersandDTODB versandDTODB) {
            return new Versand(versandDTODB.kosten, versandDTODB.lieferant, versandDTODB.laenderCode);
        }
    }
}
