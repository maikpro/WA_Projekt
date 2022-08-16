package de.hsos.swa.warenkorb.entity;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 17-07-2022
 */

public class Warenkorbposten {
    private Long postenNr;
    private Integer menge;
    private Warenkorbartikel warenkorbartikel;

    public Warenkorbposten(Long postenNr, Integer menge, Warenkorbartikel warenkorbartikel) {
        this.postenNr = postenNr;
        this.menge = menge;
        this.warenkorbartikel = warenkorbartikel;
    }

    public Warenkorbposten(Builder builder) {
        this.menge = builder.menge;
        this.warenkorbartikel = builder.warenkorbartikel;
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

    public Warenkorbartikel getWarenkorbartikel() {
        return this.warenkorbartikel;
    }

    public void setWarenkorbartikel(Warenkorbartikel warenkorbartikel) {
        this.warenkorbartikel = warenkorbartikel;
    }

    @Override
    public String toString() {
        return "{" +
                " postenNr='" + getPostenNr() + "'" +
                ", menge='" + getMenge() + "'" +
                ", warenkorbartikel='" + getWarenkorbartikel() + "'" +
                "}";
    }

    public static class Builder {
        private Integer menge;
        private Warenkorbartikel warenkorbartikel;

        public Builder menge(Integer menge) {
            this.menge = menge;
            return this;
        }

        public Builder warenkorbartikel(Warenkorbartikel warenkorbartikel) {
            this.warenkorbartikel = warenkorbartikel;
            return this;
        }

        public Warenkorbposten build() {
            return new Warenkorbposten(this);
        }
    }

}
