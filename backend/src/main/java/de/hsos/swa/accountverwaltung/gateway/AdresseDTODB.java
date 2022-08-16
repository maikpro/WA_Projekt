package de.hsos.swa.accountverwaltung.gateway;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import de.hsos.swa.accountverwaltung.entity.Adresse;

/**
 * Die Klasse AdresseDTODB wird für den Transport der Daten aus der Adresse
 * Entity zur Datenbank genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 19-07-2022
 */

@Entity
@Vetoed
@Table(name = "adresse")
public class AdresseDTODB {
    @Id
    @GeneratedValue
    public Long id;
    public String strasse;
    public String hausnr;
    public String plz;
    public String ort;

    public AdresseDTODB() {
    }

    public AdresseDTODB(Long id, String strasse, String hausnr, String plz, String ort) {
        this.id = id;
        this.strasse = strasse;
        this.hausnr = hausnr;
        this.plz = plz;
        this.ort = ort;
    }

    /**
     * Beim Account-Update können die Felder Straße, Hausnr, PLZ, Ort geändert
     * werden.
     */
    public void aktualisieren(Adresse geaenderteAdresse) {
        this.strasse = geaenderteAdresse.getStrasse();
        this.hausnr = geaenderteAdresse.getHausnr();
        this.plz = geaenderteAdresse.getPlz();
        this.ort = geaenderteAdresse.getOrt();
    }

    public static class Converter {
        public static AdresseDTODB toDTODB(Adresse adresse) {
            return new AdresseDTODB(adresse.getId(), adresse.getStrasse(), adresse.getHausnr(), adresse.getPlz(),
                    adresse.getOrt());
        }

        public static Adresse toAdresse(AdresseDTODB adresseDTODB) {
            return new Adresse(adresseDTODB.id, adresseDTODB.strasse, adresseDTODB.hausnr, adresseDTODB.plz,
                    adresseDTODB.ort);
        }
    }

}
