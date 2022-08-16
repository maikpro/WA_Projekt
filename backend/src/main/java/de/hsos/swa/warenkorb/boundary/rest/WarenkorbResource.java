package de.hsos.swa.warenkorb.boundary.rest;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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

import de.hsos.swa.warenkorb.control.WarenkorbService;
import de.hsos.swa.warenkorb.entity.Warenkorb;
import io.quarkus.security.identity.SecurityIdentity;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 19-07-2022
 */

@RequestScoped
@Path("/warenkorb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WarenkorbResource {
    private static final Logger LOG = Logger.getLogger(WarenkorbResource.class);

    @Inject
    WarenkorbService warenkorbService;

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedWarenkorbAnzeigen", description = "Wie oft wurde warenkorbAnzeigen() ausgeführt.")
    @Timed(name = "warenkorbAnzeigenTimer", description = "Eine Messung wie lange es gedauert hat, um warenkorbAnzeigen auszuführen.")
    @Path("/id/{warenkorbId}")
    @Operation(summary = "Gibt den Warenkorb mit der gewissen ID zurück", description = "Gibt den Warenkorb mit der gewissen ID zurück")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WarenkorbDTO.class))),
            @APIResponse(responseCode = "404", description = "Bestellung nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response warenkorbAnzeigen(@PathParam("warenkorbId") Long id) {
        Optional<Warenkorb> nullableWarenkorb = this.warenkorbService.warenkorbAnzeigen(id);
        if (nullableWarenkorb.isEmpty()) {
            LOG.debugf("Warenkorb mit der ID: %d nicht gefunden!", id);
            return Response.status(404).build();
        }
        return Response.ok(WarenkorbDTO.Converter.toWarenkorbDTO(nullableWarenkorb.get())).build();
    }

    @POST
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedWarenkorbAnlegen", description = "Wie oft wurde warenkorbAnlegen() ausgeführt.")
    @Timed(name = "warenkorbAnlegenTimer", description = "Eine Messung wie lange es gedauert hat, um warenkorbAnlegen auszuführen.")
    @Transactional
    @Operation(summary = "Erstellt einen neuen Warenkorb und gibt diesen zurück", description = "Erstellt einen neuen Warenkorb und gibt diesen zurück")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WarenkorbDTO.class))),
            @APIResponse(responseCode = "404", description = "Bestellung nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response warenkorbAnlegen() {
        Optional<Warenkorb> nullableWarenkorb = this.warenkorbService.warenkorbAnlegen();
        if (nullableWarenkorb.isEmpty()) {
            LOG.debug("Fehler beim Anlegen des Warenkorbs!");
            return Response.noContent().build();
        }
        WarenkorbDTO warenkorbDTO = WarenkorbDTO.Converter.toWarenkorbDTO(nullableWarenkorb.get());
        return Response.ok(warenkorbDTO).build();
    }

    @DELETE
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedWarenkorbArtikelEntfernen", description = "Wie oft wurde warenkorbArtikelEntfernen() ausgeführt.")
    @Timed(name = "warenkorbArtikelEntfernenTimer", description = "Eine Messung wie lange es gedauert hat, um warenkorbArtikelEntfernen auszuführen.")
    @Path("/id/{warenkorbId}/artikelId/{artikelId}")
    @Operation(summary = "Entfernt einen Warenkorbartikel (mit artikelID) aus dem Warenkorb (warenkorbId)", description = "Entfernt einen Warenkorbartikel (mit artikelID) aus dem Warenkorb (warenkorbId)")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WarenkorbDTO.class))),
            @APIResponse(responseCode = "404", description = "Bestellung nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response warenkorbArtikelEntfernen(@PathParam("warenkorbId") Long warenkorbId,
            @PathParam("artikelId") Long artikelId) {
        Optional<Warenkorb> nullableWarenkorb = this.warenkorbService.warenkorbArtikelEntfernen(warenkorbId, artikelId);
        if (nullableWarenkorb.isEmpty()) {
            LOG.debugf("Fehler beim Entfernen des Artikels mit der ID: %d!", warenkorbId);
            return Response.status(404).build();
        }
        WarenkorbDTO warenkorbDTO = WarenkorbDTO.Converter.toWarenkorbDTO(nullableWarenkorb.get());
        return Response.ok(warenkorbDTO).build();
    }

}
