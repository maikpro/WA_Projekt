package de.hsos.swa.bestellung.entity;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 23-07-2022
 */

public class Bestellposten {
    private Long postenNr;
    private Integer menge;
    private Bestellartikel bestellartikel;

    public Bestellposten(Long postenNr, Integer menge, Bestellartikel bestellartikel) {
        this.postenNr = postenNr;
        this.menge = menge;
        this.bestellartikel = bestellartikel;
    }

    public Long getPostenNr() {
        return this.postenNr;
    }

    public void setPostenNr(Long postenNr) {
        this.postenNr = postenNr;
    }

    public Integer getMenge() {
        return this.menge;
    }

    public void setMenge(Integer menge) {
        this.menge = menge;
    }

    public Bestellartikel getBestellartikel() {
        return this.bestellartikel;
    }

    public void setBestellartikel(Bestellartikel bestellartikel) {
        this.bestellartikel = bestellartikel;
    }

    @Override
    public String toString() {
        return "{" +
                " postenNr='" + getPostenNr() + "'" +
                ", menge='" + getMenge() + "'" +
                ", bestellartikel='" + getBestellartikel() + "'" +
                "}";
    }

}
