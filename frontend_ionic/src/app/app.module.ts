import { HttpClientModule } from '@angular/common/http';
import { APP_INITIALIZER, ErrorHandler, NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { CookieService } from 'ngx-cookie-service';
import { AccountComponent } from './account-overview/account/account.component';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ArtikelComponent } from './artikel/artikel.component';
import { ArtikelanlegenComponent } from './artikel/artikelanlegen/artikelanlegen.component';
import { BeobachtungslisteComponent } from './account-overview/beobachtungsliste/beobachtungsliste.component';
import { BestellungCompleteComponent } from './bestellung/bestellung-complete/bestellung-complete.component';
import { BestellungComponent } from './bestellung/bestellung.component';
import { KaeufeComponent } from './account-overview/kaeufe/kaeufe.component';
import { ConfirmDialogComponent } from './shared/components/confirm-dialog/confirm-dialog.component';
import { GlobalErrorHandler } from './shared/misc/global-error-handler';
import { VerkaeufeComponent } from './account-overview/verkaeufe/verkaeufe.component';
import { WarenkorbComponent } from './warenkorb/warenkorb.component';
import { DeineArtikelComponent } from './account-overview/deine-artikel/deine-artikel.component';

@NgModule({
  declarations: [
    AppComponent,
    ArtikelComponent,
    WarenkorbComponent,
    AccountComponent,
    ConfirmDialogComponent,
    BestellungComponent,
    BestellungCompleteComponent,
    BeobachtungslisteComponent,
    ArtikelanlegenComponent,
    KaeufeComponent,
    VerkaeufeComponent,
    DeineArtikelComponent
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(),
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [
    {
      provide: RouteReuseStrategy,
      useClass: IonicRouteStrategy
    },
    CookieService,
    /*{
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
    */
    {
      provide: ErrorHandler,
      useClass: GlobalErrorHandler,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
