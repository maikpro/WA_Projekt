package de.hsos.swa.warenkorb.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 17-07-2022
 */

public class Warenkorb {
    private Long id;
    private BigDecimal artikelSumme;
    private BigDecimal versandSumme;
    private BigDecimal gesamtSumme;
    private List<Warenkorbposten> warenkorbpostenList;

    public Warenkorb(Long id, BigDecimal artikelSumme, BigDecimal versandSumme, BigDecimal gesamtSumme,
            List<Warenkorbposten> warenkorbpostenList) {
        this.id = id;
        this.artikelSumme = artikelSumme;
        this.versandSumme = versandSumme;
        this.gesamtSumme = gesamtSumme;
        this.warenkorbpostenList = warenkorbpostenList;
    }

    // OHNE ID zum Artikel hinzufügen, wird ein neuer Warenkorb angelegt...
    public Warenkorb(Builder builder) {
        this.artikelSumme = builder.artikelSumme;
        this.versandSumme = builder.versandSumme;
        this.gesamtSumme = builder.gesamtSumme;
        this.warenkorbpostenList = builder.warenkorbpostenList;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getGesamtSumme() {
        return this.gesamtSumme;
    }

    public void setGesamtSumme(BigDecimal gesamtSumme) {
        this.gesamtSumme = gesamtSumme;
    }

    public List<Warenkorbposten> getWarenkorbpostenList() {
        return this.warenkorbpostenList;
    }

    public BigDecimal getArtikelSumme() {
        return this.artikelSumme;
    }

    public BigDecimal getVersandSumme() {
        return this.versandSumme;
    }

    // TODO: menge hochzählen wenn Artikel bereits im Warenkorb
    public void artikelHinzufügen(Warenkorbartikel warenkorbartikel) {
        this.warenkorbpostenList = new ArrayList<>();
        this.warenkorbpostenList.add(new Warenkorbposten.Builder()
                .menge(1)
                .warenkorbartikel(warenkorbartikel)
                .build());
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", gesamtSumme='" + getGesamtSumme() + "'" +
                ", warenkorbpostenList='" + getWarenkorbpostenList() + "'" +
                "}";
    }

    public static class Builder {
        private BigDecimal artikelSumme;
        private BigDecimal versandSumme;
        private BigDecimal gesamtSumme;
        private List<Warenkorbposten> warenkorbpostenList;

        public Builder artikelSumme(BigDecimal artikelSumme) {
            this.artikelSumme = artikelSumme;
            return this;
        }

        public Builder versandSumme(BigDecimal versandSumme) {
            this.versandSumme = versandSumme;
            return this;
        }

        public Builder gesamtSumme(BigDecimal gesamtSumme) {
            this.gesamtSumme = gesamtSumme;
            return this;
        }

        public Builder warenkorbpostenList(List<Warenkorbposten> warenkorbpostenList) {
            this.warenkorbpostenList = warenkorbpostenList;
            return this;
        }

        public Warenkorb build() {
            return new Warenkorb(this);
        }
    }

}
