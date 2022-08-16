package de.hsos.swa.artikelverwaltung;

import de.hsos.swa.artikelverwaltung.boundary.rest.suchen.SuchenResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.keycloak.client.KeycloakTestClient;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Maik Proba
 * @version 1.0
 * @since 01-08-2022
 */

@QuarkusTest
@TestHTTPEndpoint(SuchenResource.class)
public class SuchenResourceTest {
    KeycloakTestClient keycloakClient = new KeycloakTestClient();

    protected String getAccessToken(String userName) {
        return keycloakClient.getAccessToken(userName);
    }

    @Test
    public void testGetAll() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/all")
                .then()
                .statusCode(200);
    }

    @Test
    public void testSuchen() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/suche?sw=T")
                .then()
                .statusCode(200);
    }
}
