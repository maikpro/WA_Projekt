package de.hsos.swa.bestellung.boundary.rest;

import javax.json.bind.annotation.JsonbProperty;

import de.hsos.swa.bestellung.entity.Bestellaccount;

/**
 *
 * @author Maik Proba
 * @version 1.0
 * @since 29-07-2022
 */
public class BestellaccountDTO {
    public Long id;
    public String vorname;
    public String nachname;
    public String email;
    public String username;
    @JsonbProperty("adresse")
    public BestellungadresseDTO bestellungadresseDTO;

    public BestellaccountDTO() {
    }

    public BestellaccountDTO(Long id, String vorname, String nachname, String email, String username,
            BestellungadresseDTO bestellungadresseDTO) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.username = username;
        this.bestellungadresseDTO = bestellungadresseDTO;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", vorname='" + vorname + "'" +
                ", nachname='" + nachname + "'" +
                ", email='" + email + "'" +
                ", username='" + username + "'" +
                ", bestellungadresse='" + bestellungadresseDTO + "'" +
                "}";
    }

    public static class Converter {
        public static BestellaccountDTO toDTO(Bestellaccount bestellaccount) {
            return new BestellaccountDTO(bestellaccount.getId(), bestellaccount.getVorname(),
                    bestellaccount.getNachname(), bestellaccount.getEmail(), bestellaccount.getUsername(),
                    BestellungadresseDTO.Converter.toDTO(bestellaccount.getBestellungadresse()));
        }

        public static Bestellaccount toBestellaccount(BestellaccountDTO bestellaccountDTO) {
            return new Bestellaccount(bestellaccountDTO.id, bestellaccountDTO.vorname, bestellaccountDTO.nachname,
                    bestellaccountDTO.email, bestellaccountDTO.username,
                    BestellungadresseDTO.Converter.toBestellungadresse(bestellaccountDTO.bestellungadresseDTO));
        }
    }
}
