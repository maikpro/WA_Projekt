package de.hsos.swa.bestellung.entity;

import java.math.BigDecimal;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 29-07-2022
 */

public class Bestellartikelversand {
    private Long id;
    private BigDecimal kosten;
    private Lieferant lieferant;
    private LaenderCode laenderCode;

    public Bestellartikelversand(Long id, BigDecimal kosten, Lieferant lieferant, LaenderCode laenderCode) {
        this.id = id;
        this.kosten = kosten;
        this.lieferant = lieferant;
        this.laenderCode = laenderCode;
    }

    public Long getId() {
        return this.id;
    }

    public BigDecimal getKosten() {
        return this.kosten;
    }

    public Lieferant getLieferant() {
        return this.lieferant;
    }

    public LaenderCode getLaenderCode() {
        return this.laenderCode;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", kosten='" + getKosten() + "'" +
                ", lieferant='" + getLieferant() + "'" +
                ", laenderCode='" + getLaenderCode() + "'" +
                "}";
    }
}
