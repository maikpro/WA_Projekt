package de.hsos.swa.accountverwaltung.entity;

import java.math.BigDecimal;

/**
 * Die Klasse Beobachtungsartikel wird für den Transport der Daten aus der
 * Account
 * Entity zur Datenbank genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 28-07-2022
 */

public class Beobachtungsartikel {
    private Long id;
    private String name;
    private BigDecimal preis;
    private Artikelzustand artikelzustand;
    private Long artikelIdReference;

    public Beobachtungsartikel(Long id, String name, BigDecimal preis, Artikelzustand artikelzustand,
            Long artikelIdReference) {
        this.id = id;
        this.name = name;
        this.preis = preis;
        this.artikelzustand = artikelzustand;
        this.artikelIdReference = artikelIdReference;
    }

    // OHNE ID zum konvertieren von ArtikelDTO -> Beobachtungsliste
    public Beobachtungsartikel(String name, BigDecimal preis, Artikelzustand artikelzustand,
            Long artikelIdReference) {
        this.name = name;
        this.preis = preis;
        this.artikelzustand = artikelzustand;
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

    public Artikelzustand getArtikelzustand() {
        return this.artikelzustand;
    }

    public Long getArtikelIdReference() {
        return this.artikelIdReference;
    }

}
