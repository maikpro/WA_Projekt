import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArtikelanlegenComponent } from '../artikel/artikelanlegen/artikelanlegen.component';
import { AuthGuard } from '../auth/auth-guard.guard';
import { BeobachtungslisteComponent } from './beobachtungsliste/beobachtungsliste.component';
import { KaeufeComponent } from './kaeufe/kaeufe.component';
import { AccountValidateGuard } from '../shared/misc/account-validate.guard';
import { VerkaeufeComponent } from './verkaeufe/verkaeufe.component';
import { AccountOverviewPage } from './account-overview.page';
import { AccountComponent } from './account/account.component';
import { DeineArtikelComponent } from './deine-artikel/deine-artikel.component';

const routes: Routes = [
  {
    path: '',
    component: AccountOverviewPage,
  },
  {
    path: 'account',
    component: AccountComponent,
    canActivate: [AuthGuard],
    canDeactivate: [AccountValidateGuard]
  },
  {
    path: 'beobachtungsliste',
    component: BeobachtungslisteComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'kaeufe',
    component: KaeufeComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'verkaeufe',
    component: VerkaeufeComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'artikel/anlegen',
    component: ArtikelanlegenComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'deine-artikel',
    component: DeineArtikelComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'deine-artikel/update/id/:id',
    component: ArtikelanlegenComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccountOverviewPageRoutingModule {}
