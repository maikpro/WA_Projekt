package de.hsos.swa.bestellung.gateway;

import java.math.BigDecimal;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import de.hsos.swa.bestellung.entity.Bestellartikelversand;
import de.hsos.swa.bestellung.entity.LaenderCode;
import de.hsos.swa.bestellung.entity.Lieferant;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 29-07-2022
 */

@Entity
@Vetoed
@Table(name = "bestellartikelversand")
public class BestellartikelversandDTODB {
    @Id
    @GeneratedValue
    public Long id;
    public BigDecimal kosten;

    @Enumerated(EnumType.STRING)
    public Lieferant lieferant;

    @Enumerated(EnumType.STRING)
    public LaenderCode laenderCode;

    public BestellartikelversandDTODB() {
    }

    public BestellartikelversandDTODB(Long id, BigDecimal kosten, Lieferant lieferant, LaenderCode laenderCode) {
        this.id = id;
        this.kosten = kosten;
        this.lieferant = lieferant;
        this.laenderCode = laenderCode;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", kosten='" + kosten + "'" +
                ", lieferant='" + lieferant + "'" +
                ", laenderCode='" + laenderCode + "'" +
                "}";
    }

    public static class Converter {
        public static BestellartikelversandDTODB toBestellartikelversandDTODB(
                Bestellartikelversand bestellartikelversand) {
            return new BestellartikelversandDTODB(bestellartikelversand.getId(), bestellartikelversand.getKosten(),
                    bestellartikelversand.getLieferant(), bestellartikelversand.getLaenderCode());
        }

        public static Bestellartikelversand toBestellartikelversand(
                BestellartikelversandDTODB bestellartikelversandDTODB) {
            return new Bestellartikelversand(bestellartikelversandDTODB.id, bestellartikelversandDTODB.kosten,
                    bestellartikelversandDTODB.lieferant,
                    bestellartikelversandDTODB.laenderCode);
        }
    }

}
