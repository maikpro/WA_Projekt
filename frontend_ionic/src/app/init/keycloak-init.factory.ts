/* eslint-disable no-var */
/* eslint-disable prefer-arrow/prefer-arrow-functions */
import { isDevMode } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import * as Keycloak from 'node_modules/keycloak-ionic/keycloak.js';
import { KeycloakInitOptions } from 'node_modules/keycloak-ionic/keycloak.js';
import { environment } from 'src/environments/environment';

export class MyKCInit {
  public static keycloakCap: Keycloak.KeycloakInstance;

  public static initializeKeycloak(keycloak: KeycloakService) {
    
    // Variante 1 fürs Web (DEV MODE):
    if(isDevMode){
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
    }

    else {
      // Variante 2 für capacitor und mobile:
      this.keycloakCap = Keycloak({
        realm: 'quarkus',
        clientId: 'quarkus-app',
        url: environment.keycloakApi,
      });
  
      const initOptions: KeycloakInitOptions = {
        adapter: 'capacitor',
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-check-sso.html'
      };
  
      return () => this.keycloakCap.init(initOptions);
    }
  }
}
