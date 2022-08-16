package de.hsos.swa.bestellung;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.keycloak.client.KeycloakTestClient;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.Test;

import de.hsos.swa.artikelverwaltung.boundary.rest.artikel.VersandDTO;
import de.hsos.swa.bestellung.boundary.rest.BestellaccountDTO;
import de.hsos.swa.bestellung.boundary.rest.BestellartikelDTO;
import de.hsos.swa.bestellung.boundary.rest.BestellartikelversandDTO;
import de.hsos.swa.bestellung.boundary.rest.BestellpostenDTO;
import de.hsos.swa.bestellung.boundary.rest.BestellungDTO;
import de.hsos.swa.bestellung.boundary.rest.BestellungResource;
import de.hsos.swa.bestellung.boundary.rest.BestellungadresseDTO;
import de.hsos.swa.bestellung.control.BestellungService;
import de.hsos.swa.bestellung.entity.Bestellartikelzustand;
import de.hsos.swa.bestellung.entity.Bestellstatus;
import de.hsos.swa.bestellung.entity.Bestellung;
import de.hsos.swa.bestellung.entity.Bezahlmethode;
import de.hsos.swa.bestellung.entity.LaenderCode;
import de.hsos.swa.bestellung.entity.Lieferant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 *
 * @author Maik Proba
 * @version 1.0
 * @since 01-08-2022
 */

@QuarkusTest
@TestHTTPEndpoint(BestellungResource.class)
public class BestellungResourceTest {
        KeycloakTestClient keycloakClient = new KeycloakTestClient();

        @Inject
        BestellungService bestellungService;

        protected String getAccessToken(String userName) {
                return keycloakClient.getAccessToken(userName);
        }

        @Test
        public void testBestellungAnzeigen() {
                BestellartikelversandDTO bv = new BestellartikelversandDTO(null, new BigDecimal(9.99), Lieferant.DHL,
                                LaenderCode.DEU);
                BestellartikelDTO ba = new BestellartikelDTO(null, "name", new BigDecimal(99.99),
                                Bestellartikelzustand.GEBRAUCHT, bv, "username", 55L);
                BestellpostenDTO bp = new BestellpostenDTO(null, 1, ba);
                BestellungadresseDTO bsta = new BestellungadresseDTO(null, "strasse", "hausnr", "plz", "ort");
                BestellaccountDTO kaeufer = new BestellaccountDTO(null, "vorname", "nachname", "email", "username",
                                bsta);
                BestellaccountDTO verkaeufer = new BestellaccountDTO(null, "vorname", "nachname", "email", "username",
                                bsta);

                List<BestellpostenDTO> bplist = new ArrayList<BestellpostenDTO>();
                bplist.add(bp);
                List<BestellaccountDTO> vlist = new ArrayList<BestellaccountDTO>();
                vlist.add(verkaeufer);
                BestellungDTO bestellungDTO = new BestellungDTO(new BigDecimal(9.99), Bezahlmethode.PAYPAL,
                                Bestellstatus.BEZAHLT, bplist, kaeufer, vlist);
                Bestellung bestellung = BestellungDTO.Converter.toBestellung(bestellungDTO);

                Long bestellId = this.bestellungService.bestellungAnlegen(bestellung).get().getId();

                RestAssured.given().auth().oauth2(getAccessToken("admin"))
                                .contentType(ContentType.JSON)
                                .when()
                                .get("/id/" + bestellId)
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testBestellungByUsername() {
                RestAssured.given().auth().oauth2(getAccessToken("admin"))
                                .contentType(ContentType.JSON)
                                .when()
                                .get("/account/kaeufe")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testBestellungAnlegen() {
                BestellartikelversandDTO bv = new BestellartikelversandDTO(null, new BigDecimal(9.99), Lieferant.DHL,
                                LaenderCode.DEU);
                BestellartikelDTO ba = new BestellartikelDTO(null, "name", new BigDecimal(99.99),
                                Bestellartikelzustand.GEBRAUCHT, bv, "username", 55L);
                BestellpostenDTO bp = new BestellpostenDTO(null, 1, ba);
                BestellungadresseDTO bsta = new BestellungadresseDTO(null, "strasse", "hausnr", "plz", "ort");
                BestellaccountDTO kaeufer = new BestellaccountDTO(null, "vorname", "nachname", "email", "username",
                                bsta);
                BestellaccountDTO verkaeufer = new BestellaccountDTO(null, "vorname", "nachname", "email", "username",
                                bsta);

                List<BestellpostenDTO> bplist = new ArrayList<BestellpostenDTO>();
                bplist.add(bp);
                List<BestellaccountDTO> vlist = new ArrayList<BestellaccountDTO>();
                vlist.add(verkaeufer);
                BestellungDTO bestellungDTO = new BestellungDTO(new BigDecimal(9.99), Bezahlmethode.PAYPAL,
                                Bestellstatus.BEZAHLT, bplist, kaeufer, vlist);

                RestAssured.given()
                                .auth().oauth2(getAccessToken("admin"))
                                .contentType(ContentType.JSON)
                                .body(bestellungDTO, ObjectMapperType.JSONB)
                                .when()
                                .post()
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testBestellungLoeschen() {
                BestellartikelversandDTO bv = new BestellartikelversandDTO(null, new BigDecimal(9.99), Lieferant.DHL,
                                LaenderCode.DEU);
                BestellartikelDTO ba = new BestellartikelDTO(null, "name", new BigDecimal(99.99),
                                Bestellartikelzustand.GEBRAUCHT, bv, "username", 55L);
                BestellpostenDTO bp = new BestellpostenDTO(null, 1, ba);
                BestellungadresseDTO bsta = new BestellungadresseDTO(null, "strasse", "hausnr", "plz", "ort");
                BestellaccountDTO kaeufer = new BestellaccountDTO(null, "vorname", "nachname", "email", "username",
                                bsta);
                BestellaccountDTO verkaeufer = new BestellaccountDTO(null, "vorname", "nachname", "email", "username",
                                bsta);

                List<BestellpostenDTO> bplist = new ArrayList<BestellpostenDTO>();
                bplist.add(bp);
                List<BestellaccountDTO> vlist = new ArrayList<BestellaccountDTO>();
                vlist.add(verkaeufer);
                BestellungDTO bestellungDTO = new BestellungDTO(new BigDecimal(9.99), Bezahlmethode.PAYPAL,
                                Bestellstatus.BEZAHLT, bplist, kaeufer, vlist);
                Bestellung bestellung = BestellungDTO.Converter.toBestellung(bestellungDTO);

                Long bestellId = this.bestellungService.bestellungAnlegen(bestellung).get().getId();

                RestAssured.given().auth().oauth2(getAccessToken("admin"))
                                .contentType(ContentType.JSON)
                                .when()
                                .delete("/id/" + bestellId)
                                .then()
                                .statusCode(200);
        }

}
