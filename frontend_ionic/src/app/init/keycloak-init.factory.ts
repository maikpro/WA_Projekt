import { KeycloakService } from "keycloak-angular";

export function initializeKeycloak(keycloak: KeycloakService) {
    return () =>
      keycloak.init({
        config: {
          url: 'http://localhost:9090',
          realm: 'quarkus',
          clientId: 'quarkus-app',
        },
        bearerPrefix: 'Bearer',
        initOptions: {
            onLoad: 'check-sso',
            silentCheckSsoRedirectUri:
                window.location.origin + '/assets/silent-check-sso.html'
        },
      });
}