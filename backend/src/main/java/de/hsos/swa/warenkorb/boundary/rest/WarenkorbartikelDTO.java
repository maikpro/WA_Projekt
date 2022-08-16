package de.hsos.swa.warenkorb.boundary.rest;

import java.math.BigDecimal;

import de.hsos.swa.warenkorb.entity.Warenkorbartikel;
import de.hsos.swa.warenkorb.entity.Warenkorbartikelzustand;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 19-07-2022
 */

public class WarenkorbartikelDTO {
    public Long id;
    public String name;
    public BigDecimal preis;
    public Warenkorbartikelzustand warenkorbartikelzustand;
    public WarenkorbartikelversandDTO versand;
    public String username;
    public Long artikelIdReference;

    public WarenkorbartikelDTO() {
    }

    public WarenkorbartikelDTO(Long id, String name, BigDecimal preis, Warenkorbartikelzustand warenkorbartikelzustand,
            WarenkorbartikelversandDTO versand, String username, Long artikelIdReference) {
        this.id = id;
        this.name = name;
        this.preis = preis;
        this.warenkorbartikelzustand = warenkorbartikelzustand;
        this.versand = versand;
        this.username = username;
        this.artikelIdReference = artikelIdReference;
    }

    public static class Converter {
        public static WarenkorbartikelDTO toWarenkorbartikelDTO(Warenkorbartikel warenkorbartikel) {
            return new WarenkorbartikelDTO(warenkorbartikel.getId(), warenkorbartikel.getName(),
                    warenkorbartikel.getPreis(), warenkorbartikel.getWarenkorbartikelzustand(),
                    WarenkorbartikelversandDTO.Converter.toDTO(warenkorbartikel.getVersand()),
                    warenkorbartikel.getUsername(), warenkorbartikel.getArtikelIdReference());
        }

        public static Warenkorbartikel toWarenkorbartikel(WarenkorbartikelDTO warenkorbartikelDTO) {
            return new Warenkorbartikel(warenkorbartikelDTO.id, warenkorbartikelDTO.name, warenkorbartikelDTO.preis,
                    warenkorbartikelDTO.warenkorbartikelzustand,
                    WarenkorbartikelversandDTO.Converter.toVersand(warenkorbartikelDTO.versand),
                    warenkorbartikelDTO.username, warenkorbartikelDTO.artikelIdReference);
        }
    }
}
