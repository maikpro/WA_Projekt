/* eslint-disable no-var */
/* eslint-disable prefer-arrow/prefer-arrow-functions */
import * as Keycloak from 'node_modules/keycloak-ionic/keycloak.js';
import { KeycloakInitOptions } from 'node_modules/keycloak-ionic/keycloak.js';
import { environment } from 'src/environments/environment';

/*export function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: environment.keycloakApi,
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
}*/

export class MyKCInit {

  public static keycloak: Keycloak.KeycloakInstance;

  public static initializeKeycloak() {
    this.keycloak = Keycloak({
      realm: 'quarkus',
      clientId: 'quarkus-app',
      url: environment.keycloakApi,
    });

    const initOptions: KeycloakInitOptions = {
      adapter: 'capacitor',
      onLoad: 'check-sso',
      silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-check-sso.html'
    };

    return () => this.keycloak.init(initOptions);
  }
}
