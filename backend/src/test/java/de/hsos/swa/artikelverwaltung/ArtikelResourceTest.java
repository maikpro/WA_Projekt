package de.hsos.swa.artikelverwaltung;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.keycloak.client.KeycloakTestClient;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;

import java.math.BigDecimal;

import org.jboss.logging.Logger;
// Quelle: https://quarkus.io/guides/security-openid-connect#integration-testing-keycloak-devservices

import de.hsos.swa.artikelverwaltung.boundary.rest.artikel.ArtikelDTO;
import de.hsos.swa.artikelverwaltung.boundary.rest.artikel.ArtikelResource;
import de.hsos.swa.artikelverwaltung.boundary.rest.artikel.VersandDTO;
import de.hsos.swa.artikelverwaltung.control.ArtikelService;
import de.hsos.swa.artikelverwaltung.entity.Artikelstatus;
import de.hsos.swa.artikelverwaltung.entity.Artikelzustand;
import de.hsos.swa.artikelverwaltung.entity.LaenderCode;
import de.hsos.swa.artikelverwaltung.entity.Lieferant;
import de.hsos.swa.warenkorb.control.WarenkorbService;

/**
 *
 * @author Maik Proba
 * @version 1.0
 * @since 31-07-2022
 */

@QuarkusTest
@TestHTTPEndpoint(ArtikelResource.class)
public class ArtikelResourceTest {
    private static Logger LOG = Logger.getLogger(ArtikelResourceTest.class);
    KeycloakTestClient keycloakClient = new KeycloakTestClient();

    @Inject
    ArtikelService artikelService;

    @Inject
    WarenkorbService warenkorbService;

    protected String getAccessToken(String userName) {
        return keycloakClient.getAccessToken(userName);
    }

    @Test
    public void testGetArtikelById() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/id/9")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetLatestArtikel() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetVerkaufteArtikel() {
        RestAssured.given().auth().oauth2(getAccessToken("admin"))
                .contentType(ContentType.JSON)
                .when()
                .get("/verkauft")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateArtikel() {
        VersandDTO versand = new VersandDTO(new BigDecimal(4.99), Lieferant.DHL, LaenderCode.DEU);
        ArtikelDTO artikelDTO = new ArtikelDTO("test", "bla", new BigDecimal(9.99), Artikelzustand.GEBRAUCHT,
                versand, 99, 10, "admin", Artikelstatus.ACTIVE);

        RestAssured.given()
                .auth().oauth2(getAccessToken("admin"))
                .contentType(ContentType.JSON)
                .body(artikelDTO, ObjectMapperType.JSONB)
                .when()
                .post("/anlegen")
                .then()
                .statusCode(200);
    }

    @Test
    public void testArtikelInDenWarenkorbLegen() {
        Long warenkorbId = this.warenkorbService.warenkorbAnlegen().get().getId();
        ArtikelDTO artikelDTO = ArtikelDTO.Converter.toDTO(this.artikelService.getById(10L).get());
        LOG.debug(">> " + artikelDTO);
        RestAssured.given()
                .auth().oauth2(getAccessToken("admin"))
                .contentType(ContentType.JSON)
                .body(artikelDTO, ObjectMapperType.JSONB)
                .when()
                .put("/warenkorb/id/" + warenkorbId)
                .then()
                .statusCode(200);
    }

    @Test
    public void testArtikelInBeobachtungslisteHinzufuegen() {
        ArtikelDTO artikelDTO = ArtikelDTO.Converter.toDTO(this.artikelService.getById(10L).get());
        LOG.debug(">> " + artikelDTO);
        RestAssured.given()
                .auth().oauth2(getAccessToken("admin"))
                .contentType(ContentType.JSON)
                .body(artikelDTO, ObjectMapperType.JSONB)
                .when()
                .put("/account/beobachtungsliste")
                .then()
                .statusCode(200);
    }

}
