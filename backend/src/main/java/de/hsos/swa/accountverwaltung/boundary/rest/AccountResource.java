package de.hsos.swa.accountverwaltung.boundary.rest;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import de.hsos.swa.accountverwaltung.boundary.rest.client.KeycloakAccountDTO;
import de.hsos.swa.accountverwaltung.boundary.rest.client.KeycloakGatewayMP;
import de.hsos.swa.accountverwaltung.control.AccountService;
import de.hsos.swa.accountverwaltung.control.acl.IArtikelBeobachterAktualisierung;
import de.hsos.swa.accountverwaltung.entity.Account;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;

/**
 * Die Klasse AccountResource wird von der Accountverwaltung genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 25-07-2022
 */

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AccountResource {
    private static final Logger LOG = Logger.getLogger(AccountResource.class);

    @Inject
    @RestClient
    KeycloakGatewayMP keycloakGatewayMP;

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    AccountService accountService;

    @Inject
    IArtikelBeobachterAktualisierung artikelAktualisierung;

    @GET
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedGetAllAccounts", description = "Wie oft wurde getAllAccounts() ausgef??hrt.")
    @Timed(name = "getAllAccountsTimer", description = "Eine Messung wie lange es gedauert hat, um getAllAccounts auszuf??hren.")
    @RolesAllowed("admin")
    @Operation(summary = "Gibt alle Keycloakaccounts zur??ck", description = "Gibt alle Keycloakaccounts zur??ck")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = KeycloakAccountDTO.class))),
            @APIResponse(responseCode = "404", description = "Keycloak Account nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response getAllAccounts() {
        LOG.debug("List all Keycloak Accounts...");
        return Response.ok(this.keycloakGatewayMP.getAllAccounts()).build();
    }

    // Account Data from Keycloak OHNE Adresse!
    @GET
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedGetKeycloakAccountByUsername", description = "Wie oft wurde getKeycloakAccountByUsername() ausgef??hrt.")
    @Timed(name = "getKeycloakAccountByUsernameTimer", description = "Eine Messung wie lange es gedauert hat, um getKeycloakAccountByUsername auszuf??hren.")
    @Authenticated
    @Path("/currentLogged")
    @Operation(summary = "Gibt den Keycloakaccount des eingeloggten Accounts zur??ck", description = "Gibt den Keycloakaccount des eingeloggten Accounts zur??ck")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = KeycloakAccountDTO.class))),
            @APIResponse(responseCode = "404", description = "Keycloak Account nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response getKeycloakAccountByUsername() {
        String currentUsername = this.securityIdentity.getPrincipal().getName();
        LOG.debugf("Account mit usernamen '%s'", currentUsername);

        Optional<KeycloakAccountDTO> nullableKeycloakAccountDTO = this.keycloakGatewayMP
                .getAccountByUsername(currentUsername).stream().findFirst();
        if (nullableKeycloakAccountDTO.isEmpty()) {
            LOG.debugf("Account mit dem usernamen '%s' bei keycloak nicht gefunden!", currentUsername);
            return Response.status(404).build();
        }

        return Response.ok(nullableKeycloakAccountDTO.get()).build();
    }

    // Account Data from myBay Database!
    @GET
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedGetCurrentAccountByUsername", description = "Wie oft wurde getCurrentAccountByUsername() ausgef??hrt.")
    @Timed(name = "getCurrentAccountByUsernameTimer", description = "Eine Messung wie lange es gedauert hat, um getCurrentAccountByUsername auszuf??hren.")
    @Authenticated
    @Path("/currentAccountInfo")
    @Operation(summary = "Gibt den myBay-Account des eingeloggten Accounts zur??ck", description = "Gibt den myBay-Account des eingeloggten Accounts zur??ck")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
            @APIResponse(responseCode = "404", description = "myBay Account nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response getCurrentAccountByUsername() {
        String currentUsername = this.securityIdentity.getPrincipal().getName();
        LOG.debugf("Account mit usernamen '%s'", currentUsername);
        Optional<Account> nullableAccount = this.accountService.getAccountByUsername(currentUsername);
        if (nullableAccount.isEmpty()) {
            LOG.debugf("Account mit dem Usernamen '%s' in der myBay Datenbank nicht gefunden!", currentUsername);
            return Response.status(404).build();
        }

        AccountDTO accountDTO = AccountDTO.Converter.toDTO(nullableAccount.get());
        return Response.ok(accountDTO).build();
    }

    // Hole bestimmten Account durch usernamen
    @GET
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedGetAccountByUsername", description = "Wie oft wurde getAccountByUsername() ausgef??hrt.")
    @Timed(name = "getAccountByUsernameTimer", description = "Eine Messung wie lange es gedauert hat, um getAccountByUsername auszuf??hren.")
    @Authenticated
    @Path("/username/{username}")
    @Operation(summary = "Gibt den myBay-Account mit dem Usernamen zur??ck", description = "Gibt den myBay-Account mit dem Usernamen zur??ck")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
            @APIResponse(responseCode = "404", description = "myBay Account nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response getAccountByUsername(@PathParam("username") String username) {
        Optional<Account> nullableAccount = this.accountService.getAccountByUsername(username);
        if (nullableAccount.isEmpty()) {
            LOG.debugf("Account mit dem Usernamen '%s' in der myBay Datenbank nicht gefunden!", username);
            return Response.status(404).build();
        }

        AccountDTO accountDTO = AccountDTO.Converter.toDTO(nullableAccount.get());
        return Response.ok(accountDTO).build();
    }

    @POST
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedCreateAccountInfo", description = "Wie oft wurde createAccountInfo() ausgef??hrt.")
    @Timed(name = "createAccountInfoTimer", description = "Eine Messung wie lange es gedauert hat, um createAccountInfo auszuf??hren.")
    @Authenticated
    @Transactional
    @Operation(summary = "Erstellt einen myBay-Account und gibt diesen zur??ck", description = "Erstellt einen myBay-Account und gibt diesen zur??ck")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
            @APIResponse(responseCode = "404", description = "myBay Account nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response createAccountInfo(@Valid AccountDTO accountDTO) {
        LOG.debug("AccountInfo wird in myBay Datenbank angelegt...");
        Account account = AccountDTO.Converter.toAccount(accountDTO);
        Optional<Account> nullableAccount = this.accountService.createAccount(account);
        if (nullableAccount.isEmpty()) {
            LOG.debug("Der Account konnte nicht erstellt werden!");
            return Response.status(404).build();
        }
        LOG.debugf("Account wurde in der myBay DB angelegt: %s", nullableAccount.get());
        AccountDTO createdAccountDTO = AccountDTO.Converter.toDTO(nullableAccount.get());
        return Response.ok(createdAccountDTO).build();
    }

    @PUT
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedUpdateAccountInfo", description = "Wie oft wurde updateAccountInfo() ausgef??hrt.")
    @Timed(name = "updateAccountInfoTimer", description = "Eine Messung wie lange es gedauert hat, um updateAccountInfo auszuf??hren.")
    @Authenticated
    @Transactional
    @Operation(summary = "Aktualisiert einen myBay-Account und gibt diesen zur??ck", description = "Aktualisiert einen myBay-Account und gibt diesen zur??ck")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
            @APIResponse(responseCode = "404", description = "myBay Account nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response updateAccountInfo(@Valid AccountDTO accountDTO) {
        LOG.debug("AccountInfo wird in myBay Datenbank aktualisiert...");
        Account account = AccountDTO.Converter.toAccount(accountDTO);
        Optional<Account> nullableAccount = this.accountService.updateAccount(account);
        if (nullableAccount.isEmpty()) {
            LOG.debug("Der Account konnte nicht aktualisiert werden!");
            return Response.status(404).build();
        }

        AccountDTO updatedAccountDTO = AccountDTO.Converter.toDTO(nullableAccount.get());
        LOG.debugf("Account wurde in der myBay DB aktualisiert: %s", updatedAccountDTO);
        return Response.ok(updatedAccountDTO).build();
    }

    @DELETE
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedDeleteAccount", description = "Wie oft wurde deleteAccount() ausgef??hrt.")
    @Timed(name = "deleteAccountTimer", description = "Eine Messung wie lange es gedauert hat, um deleteAccount auszuf??hren.")
    @Authenticated
    @Transactional
    @Operation(summary = "L??scht einen Account bei Keycloak und myBay", description = "L??scht einen Account bei Keycloak und myBay")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
            @APIResponse(responseCode = "404", description = "Account nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response deleteAccount() {
        String currentUsername = this.securityIdentity.getPrincipal().getName();
        LOG.debugf("Account mit dem Usernamen '%s' wird gel??scht...", currentUsername);
        // Zuerst Keycloak Acc l??schen
        Optional<KeycloakAccountDTO> nullableKeycloakAccountDTO = this.keycloakGatewayMP
                .getAccountByUsername(currentUsername).stream().findFirst();
        if (nullableKeycloakAccountDTO.isEmpty()) {
            LOG.debugf("Account mit dem usernamen '%s' bei keycloak nicht gefunden!", currentUsername);
            return Response.status(404).build();
        }

        LOG.debug("L??sche Account von Keycloak...");
        this.keycloakGatewayMP.deleteAccount(nullableKeycloakAccountDTO.get().id);

        // Dann von myBay l??schen, wenn vorhanden!
        LOG.debug("L??sche Account von myBay Datenbank...");
        boolean isDeleted = this.accountService.deleteAccount(currentUsername);

        if (!isDeleted) {
            LOG.debugf("Account mit dem usernamen '%s' in myBay DB nicht gefunden!", currentUsername);
            return Response.status(204).build();
        }

        return Response.ok().build();
    }

    @DELETE
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedDeleteBeobachtungsartikel", description = "Wie oft wurde deleteBeobachtungsartikel() ausgef??hrt.")
    @Timed(name = "deleteBeobachtungsartikelTimer", description = "Eine Messung wie lange es gedauert hat, um deleteBeobachtungsartikel auszuf??hren.")
    @Authenticated
    @Path("/beobachtungsliste/beobachtungsartikel/id/{id}")
    @Transactional
    @Operation(summary = "L??scht einen Beobachtungsartikel vom myBay-Account", description = "L??scht einen Beobachtungsartikel vom myBay-Account")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
            @APIResponse(responseCode = "404", description = "Account nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response deleteBeobachtungsartikel(@PathParam("id") Long id) {
        String currentUsername = this.securityIdentity.getPrincipal().getName();

        // BeobachterAnzahl vom Artikel aktualisieren
        // Problem ben??tigen ArtikelID vom Artikelverwaltungscontext!
        // L??sung => Nutze Service um referenceId zu bekommen
        Optional<Long> nullableReferenceArtikelId = this.accountService.getReferenceArtikelIdFromBeobachtungsartikel(id,
                currentUsername);
        if (nullableReferenceArtikelId.isEmpty()) {
            LOG.debug("Keine ReferenceId gefunden!");
            return Response.status(404).build();
        }
        LOG.debugf("ReferenceId gefunden: %d", nullableReferenceArtikelId.get());

        this.artikelAktualisierung.beobachterAnzahlReduzieren(nullableReferenceArtikelId.get());

        Optional<Account> nullableAccount = this.accountService.deleteBeobachtungsartikel(id, currentUsername);
        if (nullableAccount.isEmpty()) {
            LOG.debugf("Der Beobachtungsartikel mit der id %d vom Account konnte nicht gel??scht werden!", id);
            return Response.status(404).build();
        }

        AccountDTO accountWithoutBeobachtungsartikel = AccountDTO.Converter.toDTO(nullableAccount.get());
        return Response.ok(accountWithoutBeobachtungsartikel).build();
    }
}
