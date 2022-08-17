import { HttpClientModule } from '@angular/common/http';
import { APP_INITIALIZER, ErrorHandler, NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { CookieService } from 'ngx-cookie-service';
import { AccountComponent } from './account-overview/account/account.component';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ArtikelComponent } from './artikel/artikel.component';
import { initializeKeycloak } from './init/keycloak-init.factory';
import { GlobalErrorHandler } from './shared/misc/global-error-handler';
import { WarenkorbComponent } from './warenkorb/warenkorb.component';

@NgModule({
  declarations: [
    AppComponent,
    ArtikelComponent,
    WarenkorbComponent,
    AccountComponent
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(),
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    KeycloakAngularModule
  ],
  providers: [
    {
      provide: RouteReuseStrategy,
      useClass: IonicRouteStrategy
    },
    CookieService,
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
    {
      provide: ErrorHandler,
      useClass: GlobalErrorHandler,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
