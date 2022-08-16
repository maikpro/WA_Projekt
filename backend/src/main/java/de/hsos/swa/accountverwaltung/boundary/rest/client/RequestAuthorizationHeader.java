package de.hsos.swa.accountverwaltung.boundary.rest.client;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.jboss.logging.Logger;

import io.quarkus.oidc.client.OidcClient;
import io.quarkus.oidc.client.Tokens;

/**
 * Der RequestAuthorizationHeader wird für die Authorisierung des
 * KeycloakGatewayMP Clients gegenüber der Keycloak API verwendet.
 * 
 * Informationen zum Vorgehen:
 * Für die Authorisierung muss ein Auth-Header mitgeschickt werden, dabei wird
 * ein Interface 'ClientHeadersFactory' in RequestAuthorizationHeader
 * implementiert und bei jedem Aufruf des Restclients dann mitgeschickt:
 * Quellen: https://quarkus.io/guides/rest-client#custom-headers-support
 * https://quarkus.io/guides/security-openid-connect-client-reference
 * 
 * @author Maik Proba
 * @version 1.0
 * @since 25-07-2022
 */

public class RequestAuthorizationHeader implements ClientHeadersFactory {
    private final static Logger LOG = Logger.getLogger(RequestAuthorizationHeader.class);

    /**
     * https://stackoverflow.com/questions/106591/what-is-the-volatile-keyword-useful-for
     * If there is a write operation going on a volatile variable, and suddenly a
     * read operation is requested, it is guaranteed that the write operation will
     * be finished prior to the read operation.
     */
    volatile Tokens currentTokens;

    @Inject
    OidcClient oidcClient;

    @PostConstruct
    public void init() {
        currentTokens = this.oidcClient.getTokens().await().indefinitely();
    }

    /**
     * Für die Authorisierung wird ein Auth-Header benötigt:
     * Auth Header ist key value pair mit folgender Struktur:
     * "Authorization", "Bearer <JWT Token>"
     * Damit kann sich der Client beim Keycloak-Server authorisieren.
     */

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
            MultivaluedMap<String, String> clientOutgoingHeaders) {

        Tokens tokens = currentTokens;
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();

        if (tokens.isAccessTokenExpired()) {
            // Add @Blocking method annotation if this code is used with Reactive RestClient
            tokens = this.oidcClient.refreshTokens(tokens.getRefreshToken()).await().indefinitely();
            currentTokens = tokens;
        }

        result.add("Authorization", "Bearer " + tokens.getAccessToken());
        LOG.debugf("Auth-Header: %s", result);
        return result;
    }

}
