package de.hsos.swa.bestellung.boundary.rest;

import de.hsos.swa.bestellung.entity.Bestellungadresse;

/**
 *
 * @author Maik Proba
 * @version 1.0
 * @since 29-07-2022
 */
public class BestellungadresseDTO {
    public Long id;
    public String strasse;
    public String hausnr;
    public String plz;
    public String ort;

    public BestellungadresseDTO() {
    }

    public BestellungadresseDTO(Long id, String strasse, String hausnr, String plz, String ort) {
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
        public static BestellungadresseDTO toDTO(Bestellungadresse bestellungadresse) {
            return new BestellungadresseDTO(bestellungadresse.getId(), bestellungadresse.getStrasse(),
                    bestellungadresse.getHausnr(), bestellungadresse.getPlz(), bestellungadresse.getOrt());
        }

        public static Bestellungadresse toBestellungadresse(BestellungadresseDTO bestellungadresseDTO) {
            return new Bestellungadresse(bestellungadresseDTO.id, bestellungadresseDTO.strasse,
                    bestellungadresseDTO.hausnr, bestellungadresseDTO.plz, bestellungadresseDTO.ort);
        }
    }
}
