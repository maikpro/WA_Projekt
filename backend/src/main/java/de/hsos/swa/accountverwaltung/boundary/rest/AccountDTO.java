package de.hsos.swa.accountverwaltung.boundary.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import de.hsos.swa.accountverwaltung.entity.Account;
import de.hsos.swa.accountverwaltung.entity.Beobachtungsartikel;

/**
 * Die Klasse AccountDTO wird von der Accountverwaltung genutzt.
 * 
 *
 * @author Maik Proba
 * @version 1.0
 * @since 25-07-2022
 */

public class AccountDTO {
    public Long id;
    public String vorname;
    public String nachname;
    public String email;
    public String username;

    @JsonbProperty("adresse")
    public AdresseDTO adresseDTO;

    public Collection<CredentialDTO> credentialDTOs; // speziell für das Passwort für keycloak

    public List<BeobachtungsartikelDTO> beobachtungsliste;

    public AccountDTO() {
    }

    // Ohne ID, Credentials für Tests
    public AccountDTO(String vorname, String nachname, String email, String username, AdresseDTO adresseDTO,
            List<BeobachtungsartikelDTO> beobachtungsliste) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.username = username;
        this.adresseDTO = adresseDTO;
        this.beobachtungsliste = beobachtungsliste;
    }

    // Ohne Credentials
    public AccountDTO(Long id, String vorname, String nachname, String email, String username, AdresseDTO adresseDTO,
            List<BeobachtungsartikelDTO> beobachtungsliste) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.username = username;
        this.adresseDTO = adresseDTO;
        this.beobachtungsliste = beobachtungsliste;
    }

    // Mit credentials zum Testen
    public AccountDTO(Long id, String vorname, String nachname, String email, String username, AdresseDTO adresseDTO,
            Collection<CredentialDTO> credentialDTOs) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.username = username;
        this.adresseDTO = adresseDTO;
        this.credentialDTOs = credentialDTOs;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", vorname='" + vorname + "'" +
                ", nachname='" + nachname + "'" +
                ", email='" + email + "'" +
                ", username='" + username + "'" +
                ", adresseDTO='" + adresseDTO + "'" +
                ", credentials='" + credentialDTOs + "'" +
                "}";
    }

    public static class Converter {
        public static AccountDTO toDTO(Account account) {
            List<BeobachtungsartikelDTO> beobachtungslisteDTO = account.getBeobachtungsliste().stream()
                    .map(b -> BeobachtungsartikelDTO.Converter.toDTO(b)).toList();

            return new AccountDTO(account.getId(), account.getVorname(), account.getNachname(), account.getEmail(),
                    account.getUsername(), AdresseDTO.Converter.toDTO(account.getAdresse()), beobachtungslisteDTO);
        }

        public static Account toAccount(AccountDTO accountDTO) {
            List<Beobachtungsartikel> beobachtungsliste = accountDTO.beobachtungsliste.stream()
                    .map(bdto -> BeobachtungsartikelDTO.Converter.toBeobachtungsartikel(bdto)).toList();

            return new Account(accountDTO.id, accountDTO.vorname, accountDTO.nachname, accountDTO.email,
                    accountDTO.username, AdresseDTO.Converter.toAdresse(accountDTO.adresseDTO), beobachtungsliste);
        }
    }

}
