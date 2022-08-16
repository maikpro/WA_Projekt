package de.hsos.swa.warenkorb.gateway;

import java.math.BigDecimal;
import java.util.List;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.hsos.swa.warenkorb.entity.Warenkorb;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 19-07-2022
 */

@Entity
@Vetoed
@Table(name = "warenkorb")
public class WarenkorbDTODB {
    @Id
    @GeneratedValue
    public Long id;
    public BigDecimal artikelSumme;
    public BigDecimal versandSumme;
    public BigDecimal gesamtSumme;

    @OneToMany(cascade = CascadeType.ALL)
    public List<WarenkorbpostenDTODB> warenkorbpostenDTODB;

    public WarenkorbDTODB() {
    }

    public WarenkorbDTODB(Long id, BigDecimal artikelSumme, BigDecimal versandSumme, BigDecimal gesamtSumme,
            List<WarenkorbpostenDTODB> warenkorbpostenDTODB) {
        this.id = id;
        this.artikelSumme = artikelSumme;
        this.versandSumme = versandSumme;
        this.gesamtSumme = gesamtSumme;
        this.warenkorbpostenDTODB = warenkorbpostenDTODB;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", gesamtSumme='" + gesamtSumme + "'" +
                ", warenkorbpostenDTODB='" + warenkorbpostenDTODB + "'" +
                "}";
    }

    public void gesamtSummeBerechnen() {
        this.artikelSumme = new BigDecimal(0.00);
        this.versandSumme = new BigDecimal(0.00);
        this.gesamtSumme = new BigDecimal(0.00);

        this.warenkorbpostenDTODB.stream()
                .forEach(p -> this.artikelSumme = this.artikelSumme.add(p.warenkorbartikelDTODB.preis));
        this.warenkorbpostenDTODB.stream().forEach(p -> this.versandSumme = this.versandSumme
                .add(p.warenkorbartikelDTODB.warenkorbartikelversandDTODB.kosten));
        this.gesamtSumme = this.gesamtSumme.add(this.artikelSumme).add(this.versandSumme);
    }

    public static class Converter {
        public static WarenkorbDTODB toWarenkorbDTODB(Warenkorb warenkorb) {
            return new WarenkorbDTODB(warenkorb.getId(), warenkorb.getArtikelSumme(), warenkorb.getVersandSumme(),
                    warenkorb.getGesamtSumme(),
                    warenkorb.getWarenkorbpostenList().stream().map(
                            warenkorbposten -> WarenkorbpostenDTODB.Converter.toWarenkorbpostenDTODB(warenkorbposten))
                            .toList());
        }

        public static Warenkorb toWarenkorb(WarenkorbDTODB warenkorbDTODB) {
            return new Warenkorb(warenkorbDTODB.id, warenkorbDTODB.artikelSumme, warenkorbDTODB.versandSumme,
                    warenkorbDTODB.gesamtSumme, warenkorbDTODB.warenkorbpostenDTODB
                            .stream()
                            .map(warenkorbpostenDTODB -> WarenkorbpostenDTODB.Converter
                                    .toWarenkorbposten(warenkorbpostenDTODB))
                            .toList());
        }
    }
}
