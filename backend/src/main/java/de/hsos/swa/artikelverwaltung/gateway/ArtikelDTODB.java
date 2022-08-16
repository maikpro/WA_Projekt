package de.hsos.swa.artikelverwaltung.gateway;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.hsos.swa.artikelverwaltung.entity.Artikel;
import de.hsos.swa.artikelverwaltung.entity.Artikelstatus;
import de.hsos.swa.artikelverwaltung.entity.Artikelzustand;

/**
 * Die Klasse ArtikelDTODB wird für den Transport der Daten aus der Artikel
 * Entity zur Datenbank sgenutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 13-07-2022
 */

@Entity
@Vetoed
@Table(name = "artikel")
public class ArtikelDTODB {
    @Id
    @GeneratedValue
    public Long id;
    public String name;

    @Column(length = 1024)
    public String beschreibung;
    public BigDecimal preis;

    @Enumerated(EnumType.STRING)
    public Artikelzustand artikelzustand;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "versand_id")
    public VersandDTODB versandDTODB;

    public LocalDateTime erstellt;
    public LocalDateTime geaendert;
    public Integer aufrufe;
    public Integer beobachter;
    public LocalDateTime transaktionAm;
    public String username;

    @Enumerated(EnumType.STRING)
    public Artikelstatus artikelstatus;

    public ArtikelDTODB() {
    }

    public ArtikelDTODB(Long id, String name, String beschreibung, BigDecimal preis, Artikelzustand artikelzustand,
            VersandDTODB versandDTODB, LocalDateTime erstellt, LocalDateTime geaendert, Integer aufrufe,
            Integer beobachter, LocalDateTime transaktionAm, String username, Artikelstatus artikelstatus) {
        this.id = id;
        this.name = name;
        this.beschreibung = beschreibung;
        this.preis = preis;
        this.artikelzustand = artikelzustand;
        this.versandDTODB = versandDTODB;
        this.erstellt = erstellt;
        this.geaendert = geaendert;
        this.aufrufe = aufrufe;
        this.beobachter = beobachter;
        this.transaktionAm = transaktionAm;
        this.username = username;
        this.artikelstatus = artikelstatus;
    }

    /**
     * Wird zum Updaten des Artikels verwendet:
     * - beobachter update!
     * 
     * @param artikel
     */
    public void update(Artikel artikel) {
        this.name = artikel.getName();
        this.beschreibung = artikel.getBeschreibung();
        this.preis = artikel.getPreis();
        this.artikelzustand = artikel.getArtikelzustand();
        this.versandDTODB = VersandDTODB.Converter.toDTODB(artikel.getVersand());
        this.beobachter = artikel.getBeobachter();
        this.artikelstatus = artikel.getArtikelstatus();
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", name='" + name + "'" +
                ", beschreibung='" + beschreibung + "'" +
                ", preis='" + preis + "'" +
                ", artikelzustand='" + artikelzustand + "'" +
                ", versandDTODB='" + versandDTODB + "'" +
                ", erstellt='" + erstellt + "'" +
                ", geaendert='" + geaendert + "'" +
                ", aufrufe='" + aufrufe + "'" +
                ", beobachter='" + beobachter + "'" +
                ", transaktionAm='" + transaktionAm + "'" +
                ", username='" + username + "'" +
                "}";
    }

    /**
     * Der Converter konvertiert zwischen der Entity Artikel und dem Data Transfer
     * Object ArtikelDTO.
     */

    public static class Converter {
        public static ArtikelDTODB toDTODB(Artikel artikel) {
            return new ArtikelDTODB(artikel.getId(), artikel.getName(), artikel.getBeschreibung(), artikel.getPreis(),
                    artikel.getArtikelzustand(),
                    VersandDTODB.Converter.toDTODB(artikel.getVersand()), artikel.getErstellt(), artikel.getGeaendert(),
                    artikel.getAufrufe(),
                    artikel.getBeobachter(), artikel.getTransaktionAm(), artikel.getUsername(),
                    artikel.getArtikelstatus());
        }

        public static Artikel toArtikel(ArtikelDTODB artikelDTODB) {
            return new Artikel(artikelDTODB.id, artikelDTODB.name, artikelDTODB.beschreibung, artikelDTODB.preis,
                    artikelDTODB.artikelzustand,
                    VersandDTODB.Converter.toVersand(artikelDTODB.versandDTODB), artikelDTODB.erstellt,
                    artikelDTODB.geaendert,
                    artikelDTODB.aufrufe, artikelDTODB.beobachter, artikelDTODB.transaktionAm, artikelDTODB.username,
                    artikelDTODB.artikelstatus);
        }
    }
}
