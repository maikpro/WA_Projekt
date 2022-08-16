package de.hsos.swa.warenkorb.boundary.rest;

import javax.json.bind.annotation.JsonbProperty;

import de.hsos.swa.warenkorb.entity.Warenkorbposten;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 20-07-2022
 */

public class WarenkorbpostenDTO {
    public Long postenNr;
    public Integer menge;
    @JsonbProperty("warenkorbartikel")
    public WarenkorbartikelDTO warenkorbartikelDTO;

    public WarenkorbpostenDTO() {
    }

    public WarenkorbpostenDTO(Long postenNr, Integer menge, WarenkorbartikelDTO warenkorbartikelDTO) {
        this.postenNr = postenNr;
        this.menge = menge;
        this.warenkorbartikelDTO = warenkorbartikelDTO;
    }

    @Override
    public String toString() {
        return "{" +
                " postenNr='" + postenNr + "'" +
                ", menge='" + menge + "'" +
                ", warenkorbartikelDTO='" + warenkorbartikelDTO + "'" +
                "}";
    }

    public static class Converter {
        public static WarenkorbpostenDTO toWarenkorbpostenDTO(Warenkorbposten warenkorbposten) {
            return new WarenkorbpostenDTO(warenkorbposten.getPostenNr(), warenkorbposten.getMenge(),
                    WarenkorbartikelDTO.Converter.toWarenkorbartikelDTO(warenkorbposten.getWarenkorbartikel()));
        }

        public static Warenkorbposten toWarenkorbposten(WarenkorbpostenDTO warenkorbpostenDTO) {
            return new Warenkorbposten(warenkorbpostenDTO.postenNr, warenkorbpostenDTO.menge,
                    WarenkorbartikelDTO.Converter.toWarenkorbartikel(warenkorbpostenDTO.warenkorbartikelDTO));
        }
    }

}
