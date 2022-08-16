package de.hsos.swa.bestellung.gateway;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.hsos.swa.bestellung.entity.Bestellaccount;

/**
 *
 * @author Maik Proba
 * @version 1.0
 * @since 29-07-2022
 */
@Entity
@Vetoed
@Table(name = "bestellungaccount")
public class BestellaccountDTODB {
    @Id
    @GeneratedValue
    public Long id;
    public String vorname;
    public String nachname;
    public String email;
    public String username;

    @OneToOne(cascade = CascadeType.ALL)
    public BestellungadresseDTODB bestellungadresse;

    public BestellaccountDTODB() {
    }

    public BestellaccountDTODB(Long id, String vorname, String nachname, String email, String username,
            BestellungadresseDTODB bestellungadresse) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.username = username;
        this.bestellungadresse = bestellungadresse;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", vorname='" + vorname + "'" +
                ", nachname='" + nachname + "'" +
                ", email='" + email + "'" +
                ", username='" + username + "'" +
                ", bestellungadresse='" + bestellungadresse + "'" +
                "}";
    }

    public static class Converter {
        public static BestellaccountDTODB toDTO(Bestellaccount bestellaccount) {
            return new BestellaccountDTODB(bestellaccount.getId(), bestellaccount.getVorname(),
                    bestellaccount.getNachname(), bestellaccount.getEmail(), bestellaccount.getUsername(),
                    BestellungadresseDTODB.Converter.toDTODB(bestellaccount.getBestellungadresse()));
        }

        public static Bestellaccount toBestellaccount(BestellaccountDTODB bestellaccountDTO) {
            return new Bestellaccount(bestellaccountDTO.id, bestellaccountDTO.vorname, bestellaccountDTO.nachname,
                    bestellaccountDTO.email, bestellaccountDTO.username,
                    BestellungadresseDTODB.Converter.toBestellungadresse(bestellaccountDTO.bestellungadresse));
        }
    }
}
