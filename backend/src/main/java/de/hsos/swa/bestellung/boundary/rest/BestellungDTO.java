package de.hsos.swa.bestellung.boundary.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import de.hsos.swa.bestellung.entity.Bestellaccount;
import de.hsos.swa.bestellung.entity.Bestellstatus;
import de.hsos.swa.bestellung.entity.Bestellung;
import de.hsos.swa.bestellung.entity.Bezahlmethode;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 23-07-2022
 */

public class BestellungDTO {
        public Long id;
        public BigDecimal gesamtSumme;
        public Bezahlmethode bezahlmethode;
        public Bestellstatus bestellstatus;
        @JsonbProperty("bestellposten")
        public List<BestellpostenDTO> bestellpostenDTO = new ArrayList<>();
        public BestellaccountDTO kaeufer;
        public List<BestellaccountDTO> verkaeufer = new ArrayList<>();

        public BestellungDTO() {
        }

        public BestellungDTO(BigDecimal gesamtSumme, Bezahlmethode bezahlmethode, Bestellstatus bestellstatus,
                        List<BestellpostenDTO> bestellpostenDTO, BestellaccountDTO kaeufer,
                        List<BestellaccountDTO> verkaeufer) {
                this.gesamtSumme = gesamtSumme;
                this.bezahlmethode = bezahlmethode;
                this.bestellstatus = bestellstatus;
                this.bestellpostenDTO = bestellpostenDTO;
                this.kaeufer = kaeufer;
                this.verkaeufer = verkaeufer;
        }

        public BestellungDTO(Long id, BigDecimal gesamtSumme, Bezahlmethode bezahlmethode, Bestellstatus bestellstatus,
                        List<BestellpostenDTO> bestellpostenDTO, BestellaccountDTO kaeufer,
                        List<BestellaccountDTO> verkaeufer) {
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
                public static BestellungDTO toBestellungDTO(Bestellung bestellung) {
                        List<BestellaccountDTO> verkaueferDTOs = bestellung.getVerkaeufer().stream()
                                        .map(v -> BestellaccountDTO.Converter.toDTO(v)).toList();
                        return new BestellungDTO(bestellung.getId(), bestellung.getGesamtSumme(),
                                        bestellung.getBezahlmethode(),
                                        bestellung.getBestellstatus(), bestellung.getBestellposten().stream().map(
                                                        bestellposten -> BestellpostenDTO.Converter
                                                                        .toBestellungpostenDTO(bestellposten))
                                                        .toList(),
                                        BestellaccountDTO.Converter.toDTO(bestellung.getKaeufer()),
                                        verkaueferDTOs);
                }

                public static Bestellung toBestellung(BestellungDTO bestellungDTO) {
                        List<Bestellaccount> verkauefer = bestellungDTO.verkaeufer.stream()
                                        .map(vdto -> BestellaccountDTO.Converter.toBestellaccount(vdto)).toList();
                        return new Bestellung(bestellungDTO.id, bestellungDTO.gesamtSumme, bestellungDTO.bezahlmethode,
                                        bestellungDTO.bestellstatus, bestellungDTO.bestellpostenDTO.stream().map(
                                                        bestellungpostenDTO -> BestellpostenDTO.Converter
                                                                        .toBestellposten(bestellungpostenDTO))
                                                        .toList(),
                                        BestellaccountDTO.Converter.toBestellaccount(bestellungDTO.kaeufer),
                                        verkauefer);
                }
        }
}
