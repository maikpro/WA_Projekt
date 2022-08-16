package de.hsos.swa.accountverwaltung;

import java.util.ArrayList;

import javax.inject.Inject;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import de.hsos.swa.accountverwaltung.boundary.rest.AccountDTO;
import de.hsos.swa.accountverwaltung.boundary.rest.AccountResource;
import de.hsos.swa.accountverwaltung.boundary.rest.AdresseDTO;
import de.hsos.swa.accountverwaltung.boundary.rest.BeobachtungsartikelDTO;
import de.hsos.swa.artikelverwaltung.boundary.rest.artikel.ArtikelDTO;
import de.hsos.swa.artikelverwaltung.control.ArtikelService;
import de.hsos.swa.artikelverwaltung.control.acl.IBeobachtungslisteService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.keycloak.client.KeycloakTestClient;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;

/**
 *
 * @author Maik Proba
 * @version 1.0
 * @since 01-08-2022
 */

@QuarkusTest
@TestHTTPEndpoint(AccountResource.class)
public class AccountResourceTest {
        KeycloakTestClient keycloakClient = new KeycloakTestClient();

        @Inject
        ArtikelService artikelService;

        @Inject
        IBeobachtungslisteService beobachtungslisteService;

        protected String getAccessToken(String userName) {
                return keycloakClient.getAccessToken(userName);
        }

        @Test
        public void testGetAllAccounts() {
                RestAssured.given().auth().oauth2(getAccessToken("admin"))
                                .contentType(ContentType.JSON)
                                .when()
                                .get()
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testGetKeycloakAccountByUsername() {
                RestAssured.given().auth().oauth2(getAccessToken("admin"))
                                .contentType(ContentType.JSON)
                                .when()
                                .get("/currentLogged")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testGetCurrentAccountByUsername() {
                RestAssured.given().auth().oauth2(getAccessToken("admin"))
                                .contentType(ContentType.JSON)
                                .when()
                                .get("/currentAccountInfo")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testGetAccountByUsername() {
                RestAssured.given().auth().oauth2(getAccessToken("admin"))
                                .contentType(ContentType.JSON)
                                .when()
                                .get("/username/admin")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testCreateAccountInfo() {
                AccountDTO accountDTO = new AccountDTO("admin", "admin", "maik@mail.com", "admin",
                                new AdresseDTO("admin", "13", "49078", "Osnabrück"),
                                new ArrayList<BeobachtungsartikelDTO>());
                RestAssured.given()
                                .auth().oauth2(getAccessToken("admin"))
                                .contentType(ContentType.JSON)
                                .body(accountDTO, ObjectMapperType.JSONB)
                                .when()
                                .post()
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testUpdateAccountInfo() {
                AccountDTO accountDTO = new AccountDTO("updateAdmin", "admin", "maik@mail.com", "admin",
                                new AdresseDTO("admin", "13", "49078", "Osnabrück"),
                                new ArrayList<BeobachtungsartikelDTO>());

                RestAssured.given()
                                .auth().oauth2(getAccessToken("admin"))
                                .contentType(ContentType.JSON)
                                .body(accountDTO, ObjectMapperType.JSONB)
                                .when()
                                .put()
                                .then()
                                .statusCode(200);
        }
        /*
         * @Test
         * public void testDeleteBeobachtungsartikel() {
         * ArtikelDTO artikelDTO =
         * ArtikelDTO.Converter.toDTO(this.artikelService.getById(11L).get());
         * this.beobachtungslisteService.artikelHinzufuegen(artikelDTO);
         * RestAssured.given()
         * .auth().oauth2(getAccessToken("admin"))
         * .contentType(ContentType.JSON)
         * .when()
         * .delete("/beobachtungsliste/beobachtungsartikel/id/{id}")
         * .then()
         * .statusCode(200);
         * }
         * 
         * 
         * @Test
         * public void testDeleteAccount() {
         * RestAssured.given()
         * .auth().oauth2(getAccessToken("admin"))
         * .contentType(ContentType.JSON)
         * .when()
         * .delete()
         * .then()
         * .statusCode(200);
         * }
         */
}
