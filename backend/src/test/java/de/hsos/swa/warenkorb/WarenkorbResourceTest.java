package de.hsos.swa.warenkorb;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.keycloak.client.KeycloakTestClient;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.Test;

import de.hsos.swa.artikelverwaltung.boundary.rest.artikel.ArtikelDTO;
import de.hsos.swa.artikelverwaltung.control.ArtikelService;
import de.hsos.swa.warenkorb.boundary.rest.WarenkorbDTO;
import de.hsos.swa.warenkorb.boundary.rest.WarenkorbResource;
import de.hsos.swa.warenkorb.control.WarenkorbService;
import de.hsos.swa.warenkorb.control.WarenkorbZurSuche;

import javax.inject.Inject;

/**
 *
 * @author Maik Proba
 * @version 1.0
 * @since 31-07-2022
 */

@QuarkusTest
@TestHTTPEndpoint(WarenkorbResource.class)
public class WarenkorbResourceTest {
    KeycloakTestClient keycloakClient = new KeycloakTestClient();

    @Inject
    WarenkorbService warenkorbService;

    @Inject
    ArtikelService artikelService;

    @Inject
    WarenkorbZurSuche warenkorbZurSuche;

    protected String getAccessToken(String userName) {
        return keycloakClient.getAccessToken(userName);
    }

    @Test
    public void testWarenkorbAnzeigen() {
        Long warenkorbId = this.warenkorbService.warenkorbAnlegen().get().getId();
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/id/" + warenkorbId)
                .then()
                .statusCode(200);
    }

    @Test
    public void testWarenkorbAnlegen() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(200);
    }

    @Test
    public void testWarenkorbArtikelEntfernen() {
        Long warenkorbId = this.warenkorbService.warenkorbAnlegen().get().getId();
        ArtikelDTO artikelDTO = ArtikelDTO.Converter.toDTO(this.artikelService.getById(10L).get());

        WarenkorbDTO warenkorbDTO = this.warenkorbZurSuche.artikelHinzufuegen(warenkorbId, artikelDTO).get();

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/id/" + warenkorbId + "/artikelId/"
                        + warenkorbDTO.warenkorbpostenDTO.get(0).warenkorbartikelDTO.id)
                .then()
                .statusCode(200);
    }
}
