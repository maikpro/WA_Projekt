package de.hsos.swa.bestellung.entity;

import java.math.BigDecimal;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 23-07-2022
 */

public class Bestellartikel {
    private Long id;
    private String name;
    private BigDecimal preis;
    private Bestellartikelzustand bestellartikelzustand;
    private Bestellartikelversand versand;
    private String username;
    private Long artikelIdReference;

    // Für DTO -> Entity
    public Bestellartikel(Long id, String name, BigDecimal preis, Bestellartikelzustand bestellartikelzustand,
            Bestellartikelversand versand, String username, Long artikelIdReference) {
        this.id = id;
        this.name = name;
        this.preis = preis;
        this.bestellartikelzustand = bestellartikelzustand;
        this.versand = versand;
        this.username = username;
        this.artikelIdReference = artikelIdReference;
    }

    public Bestellartikel(String name, BigDecimal preis, Bestellartikelzustand bestellartikelzustand,
            Bestellartikelversand versand, String username, Long artikelIdReference) {
        this.name = name;
        this.preis = preis;
        this.bestellartikelzustand = bestellartikelzustand;
        this.versand = versand;
        this.username = username;
        this.artikelIdReference = artikelIdReference;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPreis() {
        return this.preis;
    }

    public void setPreis(BigDecimal preis) {
        this.preis = preis;
    }

    public Bestellartikelzustand getBestellartikelzustand() {
        return this.bestellartikelzustand;
    }

    public void setBestekkartikelzustand(Bestellartikelzustand bestellartikelzustand) {
        this.bestellartikelzustand = bestellartikelzustand;
    }

    public Bestellartikelversand getVersand() {
        return this.versand;
    }

    public String getUsername() {
        return this.username;
    }

    public Long getArtikelIdReference() {
        return this.artikelIdReference;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", preis='" + getPreis() + "'" +
                ", bestellartikelzustand='" + getBestellartikelzustand() + "'" +
                ", versand='" + getVersand() + "'" +
                ", username='" + getUsername() + "'" +
                ", artikelIdReference='" + getArtikelIdReference() + "'" +
                "}";
    }

}
