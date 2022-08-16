package de.hsos.swa.bestellung.gateway;

import java.math.BigDecimal;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.hsos.swa.bestellung.entity.Bestellartikel;
import de.hsos.swa.bestellung.entity.Bestellartikelzustand;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 23-07-2022
 */

@Entity
@Vetoed
@Table(name = "bestellartikel")
public class BestellartikelDTODB {
    @Id
    @GeneratedValue
    public Long id;
    public String name;
    public BigDecimal preis;

    @Enumerated(EnumType.STRING)
    public Bestellartikelzustand bestellartikelzustand;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bestellartikelversand_id")
    public BestellartikelversandDTODB bestellartikelversandDTODB;

    public String username;
    public Long artikelIdReference;

    public BestellartikelDTODB() {
    }

    public BestellartikelDTODB(Long id, String name, BigDecimal preis, Bestellartikelzustand bestellartikelzustand,
            BestellartikelversandDTODB bestellartikelversandDTODB, String username, Long artikelIdReference) {
        this.id = id;
        this.name = name;
        this.preis = preis;
        this.bestellartikelzustand = bestellartikelzustand;
        this.bestellartikelversandDTODB = bestellartikelversandDTODB;
        this.username = username;
        this.artikelIdReference = artikelIdReference;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", name='" + name + "'" +
                ", preis='" + preis + "'" +
                ", bestellartikelzustand='" + bestellartikelzustand + "'" +
                ", bestellartikelversandDTODB='" + bestellartikelversandDTODB + "'" +
                ", username='" + username + "'" +
                ", artikelIdReference='" + artikelIdReference + "'" +
                "}";
    }

    public static class Converter {
        public static BestellartikelDTODB toBestellartikelDTODB(Bestellartikel bestellartikel) {
            return new BestellartikelDTODB(bestellartikel.getId(), bestellartikel.getName(), bestellartikel.getPreis(),
                    bestellartikel.getBestellartikelzustand(),
                    BestellartikelversandDTODB.Converter.toBestellartikelversandDTODB(bestellartikel.getVersand()),
                    bestellartikel.getUsername(), bestellartikel.getArtikelIdReference());
        }

        public static Bestellartikel toBestellartikel(BestellartikelDTODB bestellartikelDTO) {
            return new Bestellartikel(bestellartikelDTO.id, bestellartikelDTO.name, bestellartikelDTO.preis,
                    bestellartikelDTO.bestellartikelzustand,
                    BestellartikelversandDTODB.Converter
                            .toBestellartikelversand(bestellartikelDTO.bestellartikelversandDTODB),
                    bestellartikelDTO.username, bestellartikelDTO.artikelIdReference);
        }
    }
}
