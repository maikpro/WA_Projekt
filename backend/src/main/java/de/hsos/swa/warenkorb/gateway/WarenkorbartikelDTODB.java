package de.hsos.swa.warenkorb.gateway;

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

import de.hsos.swa.warenkorb.entity.Warenkorbartikel;
import de.hsos.swa.warenkorb.entity.Warenkorbartikelzustand;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 19-07-2022
 */
@Entity
@Vetoed
@Table(name = "warenkorbartikel")
public class WarenkorbartikelDTODB {
    @Id
    @GeneratedValue
    public Long id;
    public String name;
    public BigDecimal preis;
    @Enumerated(EnumType.STRING)
    public Warenkorbartikelzustand warenkorbartikelzustand;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "warenkorbartikelversand_id")
    public WarenkorbartikelversandDTODB warenkorbartikelversandDTODB;

    public String username;
    public Long artikelIdReference;

    public WarenkorbartikelDTODB() {
    }

    public WarenkorbartikelDTODB(Long id, String name, BigDecimal preis,
            Warenkorbartikelzustand warenkorbartikelzustand, WarenkorbartikelversandDTODB warenkorbartikelversandDTODB,
            String username, Long artikelIdReference) {
        this.id = id;
        this.name = name;
        this.preis = preis;
        this.warenkorbartikelzustand = warenkorbartikelzustand;
        this.warenkorbartikelversandDTODB = warenkorbartikelversandDTODB;
        this.username = username;
        this.artikelIdReference = artikelIdReference;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", name='" + name + "'" +
                ", preis='" + preis + "'" +
                ", warenkorbartikelzustand='" + warenkorbartikelzustand + "'" +
                ", warenkorbartikelversandDTODB='" + warenkorbartikelversandDTODB + "'" +
                ", username='" + username + "'" +
                ", artikelIdReference='" + artikelIdReference + "'" +
                "}";
    }

    public static class Converter {
        public static WarenkorbartikelDTODB toDTODB(Warenkorbartikel warenkorbartikel) {
            return new WarenkorbartikelDTODB(warenkorbartikel.getId(), warenkorbartikel.getName(),
                    warenkorbartikel.getPreis(), warenkorbartikel.getWarenkorbartikelzustand(),
                    WarenkorbartikelversandDTODB.Converter.toDTO(warenkorbartikel.getVersand()),
                    warenkorbartikel.getUsername(), warenkorbartikel.getArtikelIdReference());
        }

        public static Warenkorbartikel toWarenkorbartikel(WarenkorbartikelDTODB warenkorbartikelDTODB) {
            return new Warenkorbartikel(warenkorbartikelDTODB.id, warenkorbartikelDTODB.name,
                    warenkorbartikelDTODB.preis,
                    warenkorbartikelDTODB.warenkorbartikelzustand,
                    WarenkorbartikelversandDTODB.Converter
                            .toVersand(warenkorbartikelDTODB.warenkorbartikelversandDTODB),
                    warenkorbartikelDTODB.username, warenkorbartikelDTODB.artikelIdReference);
        }
    }
}
