package de.hsos.swa.accountverwaltung.entity;

/**
 * Die Klasse Adresse wird von der Accountverwaltung genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 25-07-2022
 */

public class Adresse {
    private Long id;
    private String strasse;
    private String hausnr;
    private String plz;
    private String ort;

    public Adresse(Long id, String strasse, String hausnr, String plz, String ort) {
        this.id = id;
        this.strasse = strasse;
        this.hausnr = hausnr;
        this.plz = plz;
        this.ort = ort;
    }

    public Long getId() {
        return this.id;
    }

    public String getStrasse() {
        return this.strasse;
    }

    public String getHausnr() {
        return this.hausnr;
    }

    public String getPlz() {
        return this.plz;
    }

    public String getOrt() {
        return this.ort;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", strasse='" + getStrasse() + "'" +
                ", hausnr='" + getHausnr() + "'" +
                ", plz='" + getPlz() + "'" +
                ", ort='" + getOrt() + "'" +
                "}";
    }
}
