package de.hsos.swa.accountverwaltung.entity;

import java.util.List;

/**
 * Die Klasse Account wird von der Accountverwaltung genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 25-07-2022
 */

public class Account {
    private Long id;
    private String vorname;
    private String nachname;
    private String email;
    private String username;
    private Adresse adresse;
    private List<Beobachtungsartikel> beobachtungsliste;

    public Account(Long id, String vorname, String nachname, String email, String username, Adresse adresse,
            List<Beobachtungsartikel> beobachtungsliste) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.username = username;
        this.adresse = adresse;
        this.beobachtungsliste = beobachtungsliste;
    }

    public Long getId() {
        return this.id;
    }

    public String getVorname() {
        return this.vorname;
    }

    public String getNachname() {
        return this.nachname;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public Adresse getAdresse() {
        return this.adresse;
    }

    public List<Beobachtungsartikel> getBeobachtungsliste() {
        return this.beobachtungsliste;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", vorname='" + getVorname() + "'" +
                ", nachname='" + getNachname() + "'" +
                ", email='" + getEmail() + "'" +
                ", username='" + getUsername() + "'" +
                ", adresse='" + getAdresse() + "'" +
                "}";
    }

}
