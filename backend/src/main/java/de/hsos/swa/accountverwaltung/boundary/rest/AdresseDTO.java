package de.hsos.swa.accountverwaltung.boundary.rest;

import de.hsos.swa.accountverwaltung.entity.Adresse;

/**
 * Die Klasse AdresseDTO wird von der Accountverwaltung genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 25-07-2022
 */

public class AdresseDTO {
    public Long id;
    public String strasse;
    public String hausnr;
    public String plz;
    public String ort;

    public AdresseDTO() {
    }

    // OHNE ID zum Testen
    public AdresseDTO(String strasse, String hausnr, String plz, String ort) {
        this.strasse = strasse;
        this.hausnr = hausnr;
        this.plz = plz;
        this.ort = ort;
    }

    public AdresseDTO(Long id, String strasse, String hausnr, String plz, String ort) {
        this.id = id;
        this.strasse = strasse;
        this.hausnr = hausnr;
        this.plz = plz;
        this.ort = ort;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", strasse='" + strasse + "'" +
                ", hausnr='" + hausnr + "'" +
                ", plz='" + plz + "'" +
                ", ort='" + ort + "'" +
                "}";
    }

    public static class Converter {
        public static AdresseDTO toDTO(Adresse adresse) {
            return new AdresseDTO(adresse.getId(), adresse.getStrasse(), adresse.getHausnr(), adresse.getPlz(),
                    adresse.getOrt());
        }

        public static Adresse toAdresse(AdresseDTO adresseDTO) {
            return new Adresse(adresseDTO.id, adresseDTO.strasse, adresseDTO.hausnr, adresseDTO.plz, adresseDTO.ort);
        }
    }
}
