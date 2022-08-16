package de.hsos.swa.bestellung.entity;

public class Bestellaccount {
    private Long id;
    private String vorname;
    private String nachname;
    private String email;
    private String username;
    private Bestellungadresse bestellungadresse;

    public Bestellaccount(Long id, String vorname, String nachname, String email, String username,
            Bestellungadresse bestellungadresse) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.username = username;
        this.bestellungadresse = bestellungadresse;
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

    public Bestellungadresse getBestellungadresse() {
        return this.bestellungadresse;
    }
}
