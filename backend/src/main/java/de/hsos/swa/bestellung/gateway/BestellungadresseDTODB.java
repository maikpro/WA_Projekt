package de.hsos.swa.bestellung.gateway;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import de.hsos.swa.bestellung.entity.Bestellungadresse;

/**
 *
 * @author Maik Proba
 * @version 1.0
 * @since 29-07-2022
 */
@Entity
@Vetoed
@Table(name = "bestellungadresse")
public class BestellungadresseDTODB {
    @Id
    @GeneratedValue
    public Long id;
    public String strasse;
    public String hausnr;
    public String plz;
    public String ort;

    public BestellungadresseDTODB() {
    }

    public BestellungadresseDTODB(Long id, String strasse, String hausnr, String plz, String ort) {
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
        public static BestellungadresseDTODB toDTODB(Bestellungadresse bestellungadresse) {
            return new BestellungadresseDTODB(bestellungadresse.getId(), bestellungadresse.getStrasse(),
                    bestellungadresse.getHausnr(), bestellungadresse.getPlz(), bestellungadresse.getOrt());
        }

        public static Bestellungadresse toBestellungadresse(BestellungadresseDTODB bestellungadresseDTO) {
            return new Bestellungadresse(bestellungadresseDTO.id, bestellungadresseDTO.strasse,
                    bestellungadresseDTO.hausnr, bestellungadresseDTO.plz, bestellungadresseDTO.ort);
        }
    }
}
