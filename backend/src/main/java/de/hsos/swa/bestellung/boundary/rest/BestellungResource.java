package de.hsos.swa.bestellung.boundary.rest;

import java.util.Collection;
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
import org.jboss.logging.Logger;

import de.hsos.swa.bestellung.control.BestellaccountService;
import de.hsos.swa.bestellung.control.BestellungService;
import de.hsos.swa.bestellung.control.acl.IArtikelVerkauft;
import de.hsos.swa.bestellung.control.EmailService;
import de.hsos.swa.bestellung.entity.Bestellaccount;
import de.hsos.swa.bestellung.entity.Bestellung;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 23-07-2022
 */

@RequestScoped
@Path("/bestellung")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BestellungResource {
    private static final Logger LOG = Logger.getLogger(BestellungResource.class);

    @Inject
    BestellungService bestellungService;

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    IArtikelVerkauft artikelVerkauft;

    @Inject
    BestellaccountService bestellaccountService;

    @Inject
    EmailService emailService;

    @GET
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedBestellungAnzeigen", description = "Wie oft wurde bestellungAnzeigen() ausgeführt.")
    @Timed(name = "bestellungAnzeigenTimer", description = "Eine Messung wie lange es gedauert hat, um bestellungAnzeigen auszuführen.")
    @Path("/id/{bestellungId}")
    @Authenticated
    @Operation(summary = "Gibt die Bestellung mit der ID zurück", description = "Gibt die Bestellung mit der ID zurück")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BestellungDTO.class))),
            @APIResponse(responseCode = "404", description = "Bestellung nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response bestellungAnzeigen(@PathParam("bestellungId") Long id) {
        Optional<Bestellung> nullableBestellung = this.bestellungService.bestellungAnzeigen(id);
        if (nullableBestellung.isEmpty()) {
            LOG.debugf("Bestellung mit der ID: %d nicht gefunden!", id);
            return Response.status(404).build();
        }
        return Response.ok(BestellungDTO.Converter.toBestellungDTO(nullableBestellung.get())).build();
    }

    @GET
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedBestellungByUsername", description = "Wie oft wurde bestellungByUsername() ausgeführt.")
    @Timed(name = "bestellungByUsernameTimer", description = "Eine Messung wie lange es gedauert hat, um bestellungByUsername auszuführen.")
    @Authenticated
    @Path("/account/kaeufe")
    @Operation(summary = "Gibt die Bestellungen des Usernamens zurück", description = "Gibt die Bestellungen des Usernamens zurück")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BestellungDTO.class))),
            @APIResponse(responseCode = "404", description = "Bestellung nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response bestellungByUsername() {
        String currentUsername = this.securityIdentity.getPrincipal().getName();
        Collection<Bestellaccount> bestellaccounts = this.bestellaccountService
                .getBestellaccountsByUsername(currentUsername);

        Collection<Bestellung> bestellungen = this.bestellungService.kaeufeAnzeigen(bestellaccounts);
        LOG.debug(bestellungen);
        return Response.ok(bestellungen).build();
    }

    @POST
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedBestellungAnlegen", description = "Wie oft wurde bestellungAnlegen() ausgeführt.")
    @Timed(name = "bestellungAnlegenTimer", description = "Eine Messung wie lange es gedauert hat, um bestellungAnlegen auszuführen.")
    @Authenticated
    @Transactional
    @Operation(summary = "Erstellt eine neue Bestellung", description = "Erstellt eine neue Bestellung")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BestellungDTO.class))),
            @APIResponse(responseCode = "404", description = "Bestellung nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response bestellungAnlegen(@Valid BestellungDTO bestellungDTO) {
        LOG.debugf("Bestellung wird angelegt: %s", bestellungDTO);
        if (bestellungDTO == null) {
            LOG.debug("BestellungDTO ist null!");
            return Response.noContent().build();
        }
        Bestellung bestellung = BestellungDTO.Converter.toBestellung(bestellungDTO);
        Optional<Bestellung> nullableBestellung = this.bestellungService.bestellungAnlegen(bestellung);
        if (nullableBestellung.isEmpty()) {
            LOG.debug("Fehler beim Anlegen der Bestellung!");
            return Response.noContent().build();
        }

        nullableBestellung.get().getBestellposten().forEach(
                bp -> this.artikelVerkauft.artikelstatusUpdate(bp.getBestellartikel().getArtikelIdReference()));
        this.emailService.mailsVersenden(nullableBestellung.get());
        return Response.ok(nullableBestellung.get()).build();
    }

    @DELETE
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedBestellungLoeschen", description = "Wie oft wurde bestellungLoeschen() ausgeführt.")
    @Timed(name = "bestellungLoeschenTimer", description = "Eine Messung wie lange es gedauert hat, um bestellungLoeschen auszuführen.")
    @Path("/id/{bestellungId}")
    @Transactional
    @RolesAllowed("admin")
    @Operation(summary = "Löscht eine Bestellung mit der gewissen ID", description = "Löscht eine Bestellung mit der gewissen ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BestellungDTO.class))),
            @APIResponse(responseCode = "404", description = "Bestellung nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response bestellungLoeschen(@PathParam("bestellungId") Long id) {
        boolean erfolgreich = this.bestellungService.bestellungLoeschen(id);
        if (!erfolgreich) {
            LOG.debugf("Fehler beim Löschen der Bestellung mit der ID: %d!", id);
            return Response.status(404).build();
        }
        return Response.ok().build();
    }

}
