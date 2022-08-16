package de.hsos.swa.artikelverwaltung.boundary.rest.artikel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonFormat;

import de.hsos.swa.artikelverwaltung.entity.Artikel;
import de.hsos.swa.artikelverwaltung.entity.Artikelstatus;
import de.hsos.swa.artikelverwaltung.entity.Artikelzustand;

/**
 * Die Klasse ArtikelDTO wird für den Transport der Daten aus der Artikel Entity
 * genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 13-07-2022
 */

public class ArtikelDTO {
    public Long id;
    public String name;
    public String beschreibung;
    public BigDecimal preis;
    public Artikelzustand artikelzustand;
    @JsonbProperty("versand")
    public VersandDTO versandDTO;

    /**
     * Beim Testen zur Vermeidung vom Fehler: Unable to deserialize property
     * 'erstellt' because of: Can't deserialize JSON array into: class
     * java.time.LocalDateTime
     * FORMAT : 2016-02-05T00:00:00
     * Quelle: https://www.youtube.com/watch?v=Afi10KVnq7o
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    // JavaScript Date to LocalDateTime => "2022-07-16T13:31:10.328Z"
    // @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public LocalDateTime erstellt;

    public LocalDateTime geaendert;
    public Integer aufrufe;
    public Integer beobachter;
    public LocalDateTime transaktionAm;
    public String username;
    public Artikelstatus artikelstatus;

    public ArtikelDTO() {
    }

    public ArtikelDTO(Long id, String name, String beschreibung, BigDecimal preis, Artikelzustand artikelzustand,
            VersandDTO versandDTO, LocalDateTime erstellt, LocalDateTime geaendert, Integer aufrufe, Integer beobachter,
            LocalDateTime transaktionAm, String username, Artikelstatus artikelstatus) {
        this.id = id;
        this.name = name;
        this.beschreibung = beschreibung;
        this.preis = preis;
        this.artikelzustand = artikelzustand;
        this.versandDTO = versandDTO;
        this.erstellt = erstellt;
        this.geaendert = geaendert;
        this.aufrufe = aufrufe;
        this.beobachter = beobachter;
        this.transaktionAm = transaktionAm;
        this.username = username;
        this.artikelstatus = artikelstatus;
    }

    // OHNE ID und LocalDateTime Zum Testen
    public ArtikelDTO(String name, String beschreibung, BigDecimal preis, Artikelzustand artikelzustand,
            VersandDTO versandDTO, Integer aufrufe, Integer beobachter, String username, Artikelstatus artikelstatus) {
        this.name = name;
        this.beschreibung = beschreibung;
        this.preis = preis;
        this.artikelzustand = artikelzustand;
        this.versandDTO = versandDTO;
        this.aufrufe = aufrufe;
        this.beobachter = beobachter;
        this.username = username;
        this.artikelstatus = artikelstatus;
    }

    /**
     * Der Converter konvertiert zwischen der Entity Artikel und dem Data Transfer
     * Object ArtikelDTO.
     */

    public static class Converter {
        public static ArtikelDTO toDTO(Artikel artikel) {
            return new ArtikelDTO(artikel.getId(), artikel.getName(), artikel.getBeschreibung(), artikel.getPreis(),
                    artikel.getArtikelzustand(),
                    VersandDTO.Converter.toDTO(artikel.getVersand()), artikel.getErstellt(), artikel.getGeaendert(),
                    artikel.getAufrufe(),
                    artikel.getBeobachter(), artikel.getTransaktionAm(), artikel.getUsername(),
                    artikel.getArtikelstatus());
        }

        public static Artikel toArtikel(ArtikelDTO artikelDTO) {
            return new Artikel(artikelDTO.id, artikelDTO.name, artikelDTO.beschreibung, artikelDTO.preis,
                    artikelDTO.artikelzustand,
                    VersandDTO.Converter.toVersand(artikelDTO.versandDTO), artikelDTO.erstellt, artikelDTO.geaendert,
                    artikelDTO.aufrufe, artikelDTO.beobachter, artikelDTO.transaktionAm, artikelDTO.username,
                    artikelDTO.artikelstatus);
        }
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", name='" + name + "'" +
                ", beschreibung='" + beschreibung + "'" +
                ", preis='" + preis + "'" +
                ", artikelzustand='" + String.valueOf(artikelzustand) + "'" +
                ", versandDTO='" + versandDTO + "'" +
                ", erstellt='" + erstellt + "'" +
                ", geaendert='" + geaendert + "'" +
                ", aufrufe='" + aufrufe + "'" +
                ", beobachter='" + beobachter + "'" +
                ", transaktionAm='" + transaktionAm + "'" +
                ", artikelStatus='" + artikelstatus + "'" +
                "}";
    }
}
