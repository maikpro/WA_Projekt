package de.hsos.swa.accountverwaltung.boundary.rest.client;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * Das Interface KeycloakGatewayMP wird als Rest-Client für die Kommunikation
 * zwischen der Keycloak-REST-API und dem Onlineshop myBay verwendet, um
 * Accounts mit Keycloak zu verwalten.
 * 
 * Passwörter werden nur bei Keycloak persistiert. Andere Daten des Nutzers, wie
 * die Adresse und E-Mail werden in der myBay Datenbank gespeichert für den
 * Versand und die Email Benachrichtigung.
 * 
 * Informationen zum Vorgehen:
 * Für die Authorisierung muss ein Auth-Header mitgeschickt werden, dabei wird
 * ein Interface 'ClientHeadersFactory' in RequestAuthorizationHeader
 * implementiert und bei jedem Aufruf des Restclients dann mitgeschickt:
 * Quelle: https://quarkus.io/guides/rest-client#custom-headers-support
 *
 * @author Maik Proba
 * @version 1.0
 * @since 25-07-2022
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "keycloak-rest-api")
@RegisterClientHeaders(RequestAuthorizationHeader.class) // Siehe Informationen zum Vorgehen
public interface KeycloakGatewayMP {
    @GET
    @Path("/users")
    public Collection<KeycloakAccountDTO> getAllAccounts();

    @POST
    @Path("/users")
    public Response createAccount(KeycloakAccountDTO keycloakAccountDTO);

    @GET
    @Path("/users")
    public Collection<KeycloakAccountDTO> getAccountByUsername(@QueryParam("username") String username);

    @DELETE
    @Path("/users/{id}")
    public Response deleteAccount(@PathParam("id") String id);
}
