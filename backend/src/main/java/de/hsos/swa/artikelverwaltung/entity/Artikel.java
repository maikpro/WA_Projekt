package de.hsos.swa.artikelverwaltung.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Die Klasse Artikel wird von der Artikelverwaltung genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 13-07-2022
 */

public class Artikel {
    private Long id;
    private String name;
    private String beschreibung;
    private BigDecimal preis;
    private Artikelzustand artikelzustand;
    private Versand versand;
    private LocalDateTime erstellt;
    private LocalDateTime geaendert;
    private Integer aufrufe;
    private Integer beobachter;
    private LocalDateTime transaktionAm;
    private String username;
    private Artikelstatus artikelstatus;

    // Für DTO -> Entity
    public Artikel(Long id, String name, String beschreibung, BigDecimal preis, Artikelzustand artikelzustand,
            Versand versand, LocalDateTime erstellt, LocalDateTime geaendert, Integer aufrufe, Integer beobachter,
            LocalDateTime transaktionAm, String username, Artikelstatus artikelstatus) {
        this.id = id;
        this.name = name;
        this.beschreibung = beschreibung;
        this.preis = preis;
        this.artikelzustand = artikelzustand;
        this.versand = versand;
        this.erstellt = erstellt;
        this.geaendert = geaendert;
        this.aufrufe = aufrufe;
        this.beobachter = beobachter;
        this.transaktionAm = transaktionAm;
        this.username = username;
        this.artikelstatus = artikelstatus;
    }

    public Artikel(Builder builder) {
        this.name = builder.name;
        this.beschreibung = builder.beschreibung;
        this.preis = builder.preis;
        this.artikelzustand = builder.artikelzustand;
        this.versand = builder.versand;
        this.erstellt = builder.erstellt;
        this.geaendert = builder.geaendert;
        this.aufrufe = builder.aufrufe;
        this.beobachter = builder.beobachter;
        this.transaktionAm = builder.transaktionAm;
        this.username = builder.username;
        this.artikelstatus = builder.artikelstatus;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getBeschreibung() {
        return this.beschreibung;
    }

    public BigDecimal getPreis() {
        return this.preis;
    }

    public Artikelzustand getArtikelzustand() {
        return this.artikelzustand;
    }

    public Versand getVersand() {
        return this.versand;
    }

    public LocalDateTime getErstellt() {
        return this.erstellt;
    }

    public void setErstellt(LocalDateTime erstellt) {
        this.erstellt = erstellt;
    }

    public LocalDateTime getGeaendert() {
        return this.geaendert;
    }

    public Integer getAufrufe() {
        return this.aufrufe;
    }

    public void setAufrufe(Integer aufrufe) {
        this.aufrufe = aufrufe;
    }

    public Integer getBeobachter() {
        return this.beobachter;
    }

    public void setBeobachter(Integer beobachter) {
        this.beobachter = beobachter;
    }

    public void countBeobachterUp() {
        setBeobachter(++this.beobachter);
    }

    public void countBeobachterDown() {
        if (this.beobachter > 0) {
            setBeobachter(--this.beobachter);
        }
    }

    public LocalDateTime getTransaktionAm() {
        return this.transaktionAm;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Artikelstatus getArtikelstatus() {
        return this.artikelstatus;
    }

    public void setArtikelstatus(Artikelstatus artikelstatus) {
        this.artikelstatus = artikelstatus;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", beschreibung='" + getBeschreibung() + "'" +
                ", preis='" + getPreis() + "'" +
                ", artikelzustand='" + getArtikelzustand() + "'" +
                ", versand='" + getVersand() + "'" +
                ", erstellt='" + getErstellt() + "'" +
                ", geaendert='" + getGeaendert() + "'" +
                ", aufrufe='" + getAufrufe() + "'" +
                ", beobachter='" + getBeobachter() + "'" +
                ", transaktionAm='" + getTransaktionAm() + "'" +
                ", username='" + getUsername() + "'" +
                "}";
    }

    public static class Builder {
        private String name;
        private String beschreibung;
        private BigDecimal preis;
        private Artikelzustand artikelzustand;
        private Versand versand;
        private LocalDateTime erstellt;
        private LocalDateTime geaendert;
        private Integer aufrufe;
        private Integer beobachter;
        private LocalDateTime transaktionAm;
        private String username;
        private Artikelstatus artikelstatus;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder beschreibung(String beschreibung) {
            this.beschreibung = beschreibung;
            return this;
        }

        public Builder preis(BigDecimal preis) {
            this.preis = preis;
            return this;
        }

        public Builder artikelzustand(Artikelzustand artikelzustand) {
            this.artikelzustand = artikelzustand;
            return this;
        }

        public Builder versand(Versand versand) {
            this.versand = versand;
            return this;
        }

        public Builder erstellt(LocalDateTime erstellt) {
            this.erstellt = erstellt;
            return this;
        }

        public Builder geaendert(LocalDateTime geaendert) {
            this.geaendert = geaendert;
            return this;
        }

        public Builder aufrufe(Integer aufrufe) {
            this.aufrufe = aufrufe;
            return this;
        }

        public Builder beobachter(Integer beobachter) {
            this.beobachter = beobachter;
            return this;
        }

        public Builder transaktionAm(LocalDateTime transaktionAm) {
            this.transaktionAm = transaktionAm;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder artikelStatus(Artikelstatus artikelstatus) {
            this.artikelstatus = artikelstatus;
            return this;
        }

        public Artikel build() {
            return new Artikel(this);
        }
    }

}
