package de.hsos.swa.artikelverwaltung.boundary.rest.artikel;

import java.util.Collection;
import java.util.Optional;

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
import org.jboss.logging.Logger;

import de.hsos.swa.artikelverwaltung.control.ArtikelService;
import de.hsos.swa.artikelverwaltung.control.acl.IBeobachtungslisteService;
import de.hsos.swa.artikelverwaltung.entity.Artikel;
import de.hsos.swa.warenkorb.boundary.rest.WarenkorbDTO;
import de.hsos.swa.warenkorb.control.WarenkorbZurSuche;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;

/**
 * Die Klasse ArtikelResource
 *
 * @author Maik Proba
 * @version 1.0
 * @since 13-07-2022
 */

@Path("/artikel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ArtikelResource {
    private static final Logger LOG = Logger.getLogger(ArtikelResource.class);

    @Inject
    ArtikelService artikelService;

    @Inject
    WarenkorbZurSuche warenkorbZurSuche;

    @Inject
    IBeobachtungslisteService beobachtungslisteService;

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @Path("/test")
    public Response testEndpoint() {
        Optional<Artikel> stuhl = this.artikelService.getById(12L);
        return Response.ok(stuhl).build();
    }

    @GET
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedGetArtikelById", description = "Wie oft wurde getArtikelById() ausgef??hrt.")
    @Timed(name = "getArtikelByIdTimer", description = "Eine Messung wie lange es gedauert hat, um getArtikelById auszuf??hren.")
    @Path("/id/{artikelid}")
    @Transactional // um Aufrufe hochzuz??hlen
    @Operation(summary = "Gibt einen Artikel mit der ID zur??ck", description = "Gibt einen Artikel mit der ID zur??ck")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtikelDTO.class))),
            @APIResponse(responseCode = "404", description = "Artikel nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response getArtikelById(@PathParam("artikelid") Long id) {
        Optional<Artikel> nullableArtikel = this.artikelService.getById(id);
        if (nullableArtikel.isEmpty()) {
            LOG.debugf("Artikel mit der Id %d nicht gefunden!", id);
            return Response.status(404).build();
        }

        ArtikelDTO artikelDTO = ArtikelDTO.Converter.toDTO(nullableArtikel.get());
        return Response.ok(artikelDTO).build();
    }

    @GET
    @Path("/deine-artikel")
    @Authenticated
    public Response getYourArtikel() {
        String username = this.securityIdentity.getPrincipal().getName();
        Collection<Artikel> yourArtikel = this.artikelService.getYourArtikel(username);
        Collection<ArtikelDTO> yourArtikelDTOs = yourArtikel.stream().map(a -> ArtikelDTO.Converter.toDTO(a)).toList();
        return Response.ok(yourArtikelDTOs).build();
    }

    @GET
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedGetLatestArtikel", description = "Wie oft wurde getLatestArtikel() ausgef??hrt.")
    @Timed(name = "getLatestArtikelTimer", description = "Eine Messung wie lange es gedauert hat, um getLatestArtikel auszuf??hren.")
    @Operation(summary = "Gibt die zuletzt eingestellten Artikel zur??ck", description = "Gibt die zuletzt eingestellten Artikel zur??ck")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtikelDTO.class))),
            @APIResponse(responseCode = "404", description = "Artikel nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response getLatestArtikel() {
        Collection<Artikel> artikel = this.artikelService.getLatestArtikel();
        Collection<ArtikelDTO> artikelDTOs = artikel.stream().map(a -> ArtikelDTO.Converter.toDTO(a)).toList();
        return Response.ok(artikelDTOs).build();
    }

    @GET
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedGetVerkaufteArtikel", description = "Wie oft wurde getVerkaufteArtikel() ausgef??hrt.")
    @Timed(name = "getVerkaufteArtikelTimer", description = "Eine Messung wie lange es gedauert hat, um getVerkaufteArtikel auszuf??hren.")
    @Authenticated
    @Path("/verkauft")
    @Operation(summary = "Gibt die verkauften Artikel des eingeloggten Accounts zur??ck", description = "Gibt die verkauften Artikel des eingeloggten Accounts zur??ck")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtikelDTO.class))),
            @APIResponse(responseCode = "404", description = "Artikel nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response getVerkaufteArtikel() {
        String username = this.securityIdentity.getPrincipal().getName();
        Collection<Artikel> verkaufteArtikel = this.artikelService.getVerkaufteArtikel(username);
        Collection<ArtikelDTO> artikelDTOs = verkaufteArtikel.stream().map(a -> ArtikelDTO.Converter.toDTO(a)).toList();
        return Response.ok(artikelDTOs).build();
    }

    @POST
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedCreateArtikel", description = "Wie oft wurde createArtikel() ausgef??hrt.")
    @Timed(name = "createArtikelTimer", description = "Eine Messung wie lange es gedauert hat, um createArtikel auszuf??hren.")
    @Authenticated
    @Path("/anlegen")
    @Transactional
    @Operation(summary = "Erstellt einen neuen Artikel und gibt diesen zur??ck", description = "Erstellt einen neuen Artikel und gibt diesen zur??ck")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtikelDTO.class))),
            @APIResponse(responseCode = "404", description = "Artikel nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response createArtikel(@Valid ArtikelDTO artikelDTO) {
        LOG.debugf("Artikel wird angelegt %s", artikelDTO.toString());
        if (artikelDTO.name == null || artikelDTO.preis == null) {
            LOG.debug("ArtikelDTO enth??lt entweder keinen Namen oder kein Preis wurde angegeben!");
            return Response.noContent().build();
        }

        Artikel artikel = ArtikelDTO.Converter.toArtikel(artikelDTO);
        Optional<Artikel> nullableArtikel = this.artikelService.createArtikel(artikel);

        if (nullableArtikel.isEmpty()) {
            LOG.debug("Beim Anlegen des Artikels ist ein Fehler aufgetreten!");
            return Response.status(404).build();
        }

        ArtikelDTO createdArtikel = ArtikelDTO.Converter.toDTO(nullableArtikel.get());
        return Response.ok(createdArtikel).build();
    }

    @PUT
    @Path("/id/{artikelid}")
    @Authenticated
    @Transactional
    public Response updateArtikel(@PathParam("artikelid") Long id, @Valid ArtikelDTO artikelDTO) {
        Optional<Artikel> nullableArtikel = this.artikelService.getById(id);
        if (nullableArtikel.isEmpty()) {
            LOG.debugf("Artikel mit der Id %d nicht gefunden!", id);
            return Response.status(404).build();
        }

        Optional<Artikel> updatedNullableArtikel = this.artikelService
                .updateArtikel(ArtikelDTO.Converter.toArtikel(artikelDTO));
        if (updatedNullableArtikel.isEmpty()) {
            LOG.debugf("Artikel mit der Id %d konnte nicht aktualisiert werden!", id);
            return Response.status(404).build();
        }

        ArtikelDTO updatedArtikelDTO = ArtikelDTO.Converter.toDTO(updatedNullableArtikel.get());
        return Response.ok(updatedArtikelDTO).build();
    }

    @DELETE
    @Path("/id/{artikelid}")
    @Authenticated
    @Transactional
    public Response deleteArtikel(@PathParam("artikelid") Long id) {
        Optional<Artikel> nullableArtikel = this.artikelService.getById(id);
        if (nullableArtikel.isEmpty()) {
            LOG.debugf("Artikel mit der Id %d nicht gefunden!", id);
            return Response.status(404).build();
        }

        boolean isDeleted = this.artikelService.deleteArtikel(nullableArtikel.get());

        if (!isDeleted) {
            LOG.debugf("Artikel mit der id %d konnte nicht gel??scht werden!", nullableArtikel.get());
            return Response.status(404).build();
        }

        return Response.ok().build();
    }

    @PUT
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedArtikelInDenWarenkorbLegen", description = "Wie oft wurde artikelInDenWarenkorbLegen() ausgef??hrt.")
    @Timed(name = "artikelInDenWarenkorbLegenTimer", description = "Eine Messung wie lange es gedauert hat, um artikelInDenWarenkorbLegen auszuf??hren.")
    @Path("/warenkorb/id/{id}")
    @Transactional
    @Operation(summary = "Legt einen vorhandenen Artikel in den Warenkorb mit der ID rein", description = "Legt einen vorhandenen Artikel in den Warenkorb mit der ID rein")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WarenkorbDTO.class))),
            @APIResponse(responseCode = "404", description = "Warenkorb nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response artikelInDenWarenkorbLegen(@PathParam("id") Long warenkorbId, @Valid ArtikelDTO artikelDTO) {
        LOG.debugf("Der Artikel wird in den Warenkorb %d gelegt: %s", warenkorbId, artikelDTO);
        Optional<WarenkorbDTO> nullableWarenkorbDTO = this.warenkorbZurSuche.artikelHinzufuegen(warenkorbId,
                artikelDTO);
        if (nullableWarenkorbDTO.isEmpty()) {
            LOG.debug("WarenkorbDTO ist leer!");
            return Response.status(404).build();
        }
        return Response.ok(nullableWarenkorbDTO.get()).build();
    }

    @PUT
    @Retry(maxRetries = 2)
    @Timeout(500)
    @Counted(name = "performedArtikelInBeobachtungslisteHinzufuegen", description = "Wie oft wurde artikelInBeobachtungslisteHinzufuegen() ausgef??hrt.")
    @Timed(name = "artikelInBeobachtungslisteHinzufuegenTimer", description = "Eine Messung wie lange es gedauert hat, um artikelInBeobachtungslisteHinzufuegen auszuf??hren.")
    @Authenticated // man muss eingeloggt sein, da Beobachtungsliste auf den geloggten Account
                   // gesetzt wird.
    @Path("/account/beobachtungsliste")
    @Transactional
    @Operation(summary = "Legt einen vorhandenen Artikel auf die Beobachtungsliste des eingeloggten Accounts", description = "Legt einen vorhandenen Artikel auf die Beobachtungsliste des eingeloggten Accounts")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Erfolgreich", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtikelDTO.class))),
            @APIResponse(responseCode = "404", description = "Artikel nicht gefunden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class)))
    })
    public Response artikelInBeobachtungslisteHinzufuegen(@Valid ArtikelDTO artikelDTO) {
        LOG.debugf("Beobachkteranzahl vorher: %d", artikelDTO.beobachter);
        Optional<ArtikelDTO> nullableArtikelDTO = this.beobachtungslisteService.artikelHinzufuegen(artikelDTO);
        if (nullableArtikelDTO.isEmpty()) {
            LOG.debug("ArtikelDTO ist leer!");
            return Response.status(404).build();
        }
        LOG.debugf("Beobachkteranzahl nachher: %d", nullableArtikelDTO.get().beobachter);

        // update Artikel
        Optional<Artikel> nullableUpdatedArtikel = this.artikelService
                .updateArtikel(ArtikelDTO.Converter.toArtikel(nullableArtikelDTO.get()));
        if (nullableUpdatedArtikel.isEmpty()) {
            LOG.debug("Fehler beim updaten vom Artikel");
            return Response.status(404).build();
        }

        return Response.ok(ArtikelDTO.Converter.toDTO(nullableUpdatedArtikel.get())).build();
    }

}
