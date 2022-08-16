package de.hsos.swa.bestellung.gateway;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.hsos.swa.bestellung.entity.Bestellaccount;
import de.hsos.swa.bestellung.entity.Bestellstatus;
import de.hsos.swa.bestellung.entity.Bestellung;
import de.hsos.swa.bestellung.entity.Bezahlmethode;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 23-07-2022
 */
@Entity
@Vetoed
@Table(name = "bestellung")
public class BestellungDTODB {
        @Id
        @GeneratedValue
        public Long id;
        public BigDecimal gesamtSumme;
        public Bezahlmethode bezahlmethode;
        public Bestellstatus bestellstatus;

        @OneToMany(cascade = CascadeType.ALL)
        public List<BestellpostenDTODB> bestellpostenDTO = new ArrayList<>();

        @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
        @JoinColumn(name = "kaeufer_id")
        public BestellaccountDTODB kaeufer;

        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
        @JoinColumn(name = "verkaeufer_id")
        public List<BestellaccountDTODB> verkaeufer;

        public BestellungDTODB() {
        }

        public BestellungDTODB(Long id, BigDecimal gesamtSumme, Bezahlmethode bezahlmethode,
                        Bestellstatus bestellstatus,
                        List<BestellpostenDTODB> bestellpostenDTO, BestellaccountDTODB kaeufer,
                        List<BestellaccountDTODB> verkaeufer) {
                this.id = id;
                this.gesamtSumme = gesamtSumme;
                this.bezahlmethode = bezahlmethode;
                this.bestellstatus = bestellstatus;
                this.bestellpostenDTO = bestellpostenDTO;
                this.kaeufer = kaeufer;
                this.verkaeufer = verkaeufer;
        }

        @Override
        public String toString() {
                return "{" +
                                " id='" + id + "'" +
                                ", gesamtSumme='" + gesamtSumme + "'" +
                                ", bezahlmethode='" + bezahlmethode + "'" +
                                ", bestellstatus='" + bestellstatus + "'" +
                                ", bestellpostenDTO='" + bestellpostenDTO + "'" +
                                ", kaeufer='" + kaeufer + "'" +
                                ", verkaeufer='" + verkaeufer + "'" +
                                "}";
        }

        public static class Converter {
                public static BestellungDTODB toDTODB(Bestellung bestellung) {
                        List<BestellaccountDTODB> verkauferDTODBs = bestellung.getVerkaeufer().stream()
                                        .map(vdtodb -> BestellaccountDTODB.Converter.toDTO(vdtodb)).toList();
                        return new BestellungDTODB(bestellung.getId(), bestellung.getGesamtSumme(),
                                        bestellung.getBezahlmethode(),
                                        bestellung.getBestellstatus(), bestellung.getBestellposten().stream().map(
                                                        bestellposten -> BestellpostenDTODB.Converter
                                                                        .toBestellungpostenDTODB(bestellposten))
                                                        .toList(),
                                        BestellaccountDTODB.Converter.toDTO(bestellung.getKaeufer()),
                                        verkauferDTODBs);
                }

                public static Bestellung toBestellung(BestellungDTODB bestellungDTO) {
                        List<Bestellaccount> verkaufer = bestellungDTO.verkaeufer.stream()
                                        .map(v -> BestellaccountDTODB.Converter.toBestellaccount(v)).toList();
                        return new Bestellung(bestellungDTO.id, bestellungDTO.gesamtSumme, bestellungDTO.bezahlmethode,
                                        bestellungDTO.bestellstatus, bestellungDTO.bestellpostenDTO.stream().map(
                                                        bestellungpostenDTO -> BestellpostenDTODB.Converter
                                                                        .toBestellposten(bestellungpostenDTO))
                                                        .toList(),
                                        BestellaccountDTODB.Converter.toBestellaccount(bestellungDTO.kaeufer),
                                        verkaufer);
                }
        }
}
