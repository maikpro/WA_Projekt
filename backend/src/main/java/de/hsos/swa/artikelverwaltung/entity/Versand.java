package de.hsos.swa.artikelverwaltung.entity;

import java.math.BigDecimal;

public class Versand {
    private Long id;
    private BigDecimal kosten;
    private Lieferant lieferant;
    private LaenderCode laenderCode;

    public Versand(BigDecimal kosten, Lieferant lieferant, LaenderCode laenderCode) {
        this.kosten = kosten;
        this.lieferant = lieferant;
        this.laenderCode = laenderCode;
    }

    public Versand(Builder builder) {
        this.kosten = builder.kosten;
        this.lieferant = builder.lieferant;
        this.laenderCode = builder.laenderCode;
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

    public static class Builder {
        private BigDecimal kosten;
        private Lieferant lieferant;
        private LaenderCode laenderCode;

        public Builder kosten(BigDecimal kosten) {
            this.kosten = kosten;
            return this;
        }

        public Builder lieferant(Lieferant lieferant) {
            this.lieferant = lieferant;
            return this;
        }

        public Builder laenderCode(LaenderCode laenderCode) {
            this.laenderCode = laenderCode;
            return this;
        }

        public Versand build() {
            return new Versand(this);
        }
    }

}
