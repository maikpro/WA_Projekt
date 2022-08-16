package de.hsos.swa.warenkorb.boundary.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import de.hsos.swa.warenkorb.entity.Warenkorb;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 19-07-2022
 */

public class WarenkorbDTO {
    public Long id;
    public BigDecimal artikelSumme;
    public BigDecimal versandSumme;
    public BigDecimal gesamtSumme;
    @JsonbProperty("warenkorbpostenlist")
    public List<WarenkorbpostenDTO> warenkorbpostenDTO;

    public WarenkorbDTO() {
    }

    public WarenkorbDTO(Long id, BigDecimal artikelSumme, BigDecimal versandSumme, BigDecimal gesamtSumme,
            List<WarenkorbpostenDTO> warenkorbpostenDTO) {
        this.id = id;
        this.artikelSumme = artikelSumme;
        this.versandSumme = versandSumme;
        this.gesamtSumme = gesamtSumme;
        this.warenkorbpostenDTO = warenkorbpostenDTO;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", artikelSumme='" + artikelSumme + "'" +
                ", versandSumme='" + versandSumme + "'" +
                ", gesamtSumme='" + gesamtSumme + "'" +
                ", warenkorbpostenDTO='" + warenkorbpostenDTO + "'" +
                "}";
    }

    public static class Converter {
        public static WarenkorbDTO toWarenkorbDTO(Warenkorb warenkorb) {
            return new WarenkorbDTO(warenkorb.getId(), warenkorb.getArtikelSumme(), warenkorb.getVersandSumme(),
                    warenkorb.getGesamtSumme(),
                    warenkorb.getWarenkorbpostenList().stream().map(
                            warenkorbposten -> WarenkorbpostenDTO.Converter.toWarenkorbpostenDTO(warenkorbposten))
                            .toList());
        }

        public static Warenkorb toWarenkorb(WarenkorbDTO warenkorbDTO) {
            return new Warenkorb(warenkorbDTO.id, warenkorbDTO.artikelSumme, warenkorbDTO.versandSumme,
                    warenkorbDTO.gesamtSumme, warenkorbDTO.warenkorbpostenDTO.stream()
                            .map(warenkorbpostenDTO -> WarenkorbpostenDTO.Converter
                                    .toWarenkorbposten(warenkorbpostenDTO))
                            .toList());
        }
    }
}
