package de.hsos.swa.warenkorb.entity;

import java.math.BigDecimal;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 17-07-2022
 */

public class Warenkorbartikel {
    private Long id;
    private String name;
    private BigDecimal preis;
    private Warenkorbartikelzustand warenkorbartikelzustand;
    private Warenkorbartikelversand versand;
    private String username;
    private Long artikelIdReference;

    // Für DTO -> Entity
    public Warenkorbartikel(Long id, String name, BigDecimal preis, Warenkorbartikelzustand warenkorbartikelzustand,
            Warenkorbartikelversand versand, String username, Long artikelIdReference) {
        this.id = id;
        this.name = name;
        this.preis = preis;
        this.warenkorbartikelzustand = warenkorbartikelzustand;
        this.versand = versand;
        this.username = username;
        this.artikelIdReference = artikelIdReference;
    }

    // Zum Mappen von ArtikelDTO auf Warenkorb
    public Warenkorbartikel(String name, BigDecimal preis, Warenkorbartikelzustand warenkorbartikelzustand,
            Warenkorbartikelversand versand, String username, Long artikelIdReference) {
        this.name = name;
        this.preis = preis;
        this.warenkorbartikelzustand = warenkorbartikelzustand;
        this.versand = versand;
        this.username = username;
        this.artikelIdReference = artikelIdReference;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPreis() {
        return this.preis;
    }

    public Warenkorbartikelzustand getWarenkorbartikelzustand() {
        return this.warenkorbartikelzustand;
    }

    public Warenkorbartikelversand getVersand() {
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
                ", warenkorbartikelzustand='" + getWarenkorbartikelzustand() + "'" +
                ", versand='" + getVersand() + "'" +
                ", username='" + getUsername() + "'" +
                ", artikelIdReference='" + getArtikelIdReference() + "'" +
                "}";
    }

}
