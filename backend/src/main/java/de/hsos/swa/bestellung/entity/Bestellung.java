package de.hsos.swa.bestellung.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 23-07-2022
 */

public class Bestellung {
    private Long id;
    private BigDecimal gesamtSumme;
    private Bezahlmethode bezahlmethode;
    private Bestellstatus bestellstatus;
    private List<Bestellposten> bestellposten = new ArrayList<>();
    private Bestellaccount kaeufer;
    private List<Bestellaccount> verkaeufer;

    public Bestellung() {
    }

    public Bestellung(BigDecimal gesamtSumme, Bestellstatus bestellstatus,
            List<Bestellposten> bestellposten) {
        this.gesamtSumme = gesamtSumme;
        this.bestellstatus = bestellstatus;
        this.bestellposten = bestellposten;
    }

    public Bestellung(Long id, BigDecimal gesamtSumme, Bezahlmethode bezahlmethode, Bestellstatus bestellstatus,
            List<Bestellposten> bestellposten, Bestellaccount kaeufer, List<Bestellaccount> verkaeufer) {
        this.id = id;
        this.gesamtSumme = gesamtSumme;
        this.bezahlmethode = bezahlmethode;
        this.bestellstatus = bestellstatus;
        this.bestellposten = bestellposten;
        this.kaeufer = kaeufer;
        this.verkaeufer = verkaeufer;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getGesamtSumme() {
        return this.gesamtSumme;
    }

    public Bezahlmethode getBezahlmethode() {
        return this.bezahlmethode;
    }

    public Bestellstatus getBestellstatus() {
        return this.bestellstatus;
    }

    public void setBestellstatus(Bestellstatus bestellstatus) {
        this.bestellstatus = bestellstatus;
    }

    public List<Bestellposten> getBestellposten() {
        return this.bestellposten;
    }

    public Bestellaccount getKaeufer() {
        return this.kaeufer;
    }

    public List<Bestellaccount> getVerkaeufer() {
        return this.verkaeufer;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", gesamtSumme='" + getGesamtSumme() + "'" +
                ", bezahlmethode='" + getBezahlmethode() + "'" +
                ", bestellstatus='" + getBestellstatus() + "'" +
                ", bestellposten='" + getBestellposten() + "'" +
                "}";
    }

}
