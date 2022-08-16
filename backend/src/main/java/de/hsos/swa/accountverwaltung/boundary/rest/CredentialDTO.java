package de.hsos.swa.accountverwaltung.boundary.rest;

/**
 * Die Klasse CredentialDTO wird von der Keycloak genutzt um
 * Anmeldeinformationen
 * zu speichern.
 *
 * @author Maik Proba
 * @version 1.0
 * @since 25-07-2022
 */

public class CredentialDTO {
    public String type; // type => 'password'
    public String value; // DAS password
    public boolean temporary;

    public CredentialDTO() {
    }

    public CredentialDTO(String type, String value, boolean temporary) {
        this.type = type;
        this.value = value;
        this.temporary = temporary;
    }

    @Override
    public String toString() {
        return "{" +
                " type='" + type + "'" +
                ", value='" + value + "'" +
                ", temporary='" + temporary + "'" +
                "}";
    }

}
