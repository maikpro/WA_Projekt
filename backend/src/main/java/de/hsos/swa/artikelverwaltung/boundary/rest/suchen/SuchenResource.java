package de.hsos.swa.artikelverwaltung.boundary.rest.suchen;

import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

import de.hsos.swa.artikelverwaltung.boundary.rest.artikel.ArtikelDTO;
import de.hsos.swa.artikelverwaltung.control.SuchenService;
import de.hsos.swa.artikelverwaltung.entity.Artikel;

/**
 * Die Klasse SuchenResource
 *
 * @author Maik Proba
 * @version 1.0
 * @since 14-07-2022
 */

@Path("/artikel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class SuchenResource {
    private static final Logger LOG = Logger.getLogger(SuchenResource.class);

    @Inject
    SuchenService suchenService;

    @GET
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedGetAll", description = "Wie oft wurde getAll() ausgeführt.")
    @Timed(name = "getAllTimer", description = "Eine Messung wie lange es gedauert hat, um getAll auszuführen.")
    @Path("/all")
    @Operation(summary = "Gibt alle aktiven Artikel zurück", description = "Gibt alle aktiven Artikel zurück")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtikelDTO.class))),
    })
    public Response getAll() {
        Collection<Artikel> artikel = this.suchenService.getAll();
        Collection<ArtikelDTO> artikelDTOs = artikel.stream().map(a -> ArtikelDTO.Converter.toDTO(a)).toList();
        return Response.ok(artikelDTOs).build();
    }

    @GET
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedSuchen", description = "Wie oft wurde suchen() ausgeführt.")
    @Timed(name = "suchenTimer", description = "Eine Messung wie lange es gedauert hat, um suchen auszuführen.")
    @Path("/suche")
    @Operation(summary = "Sucht nach einem bestimmten Artikel, der dem Suchwort entspricht oder gleiche Buchstaben enthält", description = "Sucht nach einem bestimmten Artikel, der dem Suchwort entspricht oder gleiche Buchstaben enthält")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtikelDTO.class))),
    })
    public Response suchen(@QueryParam("sw") String suchwort) {
        if (suchwort == null || suchwort.length() == 0) {
            return Response.noContent().build();
        }
        LOG.debugf("Suche nach '%s' mit length(%d) beginnt...", suchwort, suchwort.length());
        Collection<Artikel> artikel = this.suchenService.getAllByLikeSearch(suchwort);
        Collection<ArtikelDTO> artikelDTOs = artikel.stream().map(a -> ArtikelDTO.Converter.toDTO(a)).toList();
        return Response.ok(artikelDTOs).build();
    }
}
