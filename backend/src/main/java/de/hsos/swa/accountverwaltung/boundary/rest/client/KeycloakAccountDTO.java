package de.hsos.swa.accountverwaltung.boundary.rest.client;

import java.util.Collection;

import javax.json.bind.annotation.JsonbProperty;

import de.hsos.swa.accountverwaltung.boundary.rest.AccountDTO;
import de.hsos.swa.accountverwaltung.boundary.rest.CredentialDTO;

/**
 * Die Klasse KeycloakAccountDTO wird von der Accountverwaltung genutzt.
 * Für das Anlegen eines Account bei Keycloak werden spezielle Attribute
 * benötigt, die nicht in der myBay Datenbank abgespeichert werden müssen,
 * sondern bei der Keycloak Datenbank.
 * 
 *
 * @author Maik Proba
 * @version 1.0
 * @since 25-07-2022
 */

public class KeycloakAccountDTO {
    public String id; // bei keycloak ist id ein String: => "7e06fda2-51d9-4dba-a5b4-e49735fa728d"
    public String firstName;
    public String lastName;
    public String email;
    public String username;

    // Speziell NUR für keycloak
    public boolean enabled;
    @JsonbProperty("credentials")
    public Collection<CredentialDTO> credentialDTOs;

    public KeycloakAccountDTO() {
    }

    public KeycloakAccountDTO(String firstName, String lastName, String email, String username,
            Collection<CredentialDTO> credentialDTOs) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.enabled = true; // erstellte Accounts sollen immer aktiviert sein!
        this.credentialDTOs = credentialDTOs;
    }

    @Override
    public String toString() {
        return "{" +
                " firstName='" + firstName + "'" +
                ", lastName='" + lastName + "'" +
                ", email='" + email + "'" +
                ", username='" + username + "'" +
                ", enabled='" + enabled + "'" +
                ", credentials='" + credentialDTOs + "'" +
                "}";
    }

    public static class Converter {
        public static KeycloakAccountDTO toDTO(AccountDTO accountDTO) {
            return new KeycloakAccountDTO(accountDTO.vorname, accountDTO.nachname, accountDTO.email,
                    accountDTO.username, accountDTO.credentialDTOs);
        }
    }

}
