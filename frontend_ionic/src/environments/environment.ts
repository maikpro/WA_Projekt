// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

// Wlan Netzwerk: http://192.168.178.20
const domain = 'http://192.168.178.20';
export const environment = {
  production: false,
  apiKey: domain + ':8080',
  keycloakApi: domain + ':9090',
  frontend: domain + ':8100',
  domain
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
