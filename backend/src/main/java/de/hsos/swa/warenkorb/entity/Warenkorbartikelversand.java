package de.hsos.swa.warenkorb.entity;

import java.math.BigDecimal;

/**
 *
 * @author Maik Proba
 * @version 1.0
 * @since 27-07-2022
 */
public class Warenkorbartikelversand {
    private Long id;
    private BigDecimal kosten;
    private Lieferant lieferant;
    private LaenderCode laenderCode;

    public Warenkorbartikelversand(Long id, BigDecimal kosten, Lieferant lieferant, LaenderCode laenderCode) {
        this.id = id;
        this.kosten = kosten;
        this.lieferant = lieferant;
        this.laenderCode = laenderCode;
    }

    public Warenkorbartikelversand(BigDecimal kosten, Lieferant lieferant, LaenderCode laenderCode) {
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
