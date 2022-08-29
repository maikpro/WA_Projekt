import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../auth/auth-guard.guard';
import { BestellungCompleteComponent } from '../bestellung/bestellung-complete/bestellung-complete.component';
import { BestellungComponent } from '../bestellung/bestellung.component';
import { WarenkorbComponent } from '../warenkorb/warenkorb.component';
import { TabsPage } from './tabs.page';

const routes: Routes = [
  {
    path: '',
    component: TabsPage,
    children: [
      {
        path: 'home',
        loadChildren: () => import('../home/home.module').then(m => m.Tab1PageModule)
      },
      {
        path: 'search',
        loadChildren: () => import('../search/search.module').then(m => m.SearchPageModule)
      },
      {
        path: 'account-overview',
        loadChildren: () => import('../account-overview/account-overview.module').then(m => m.AccountOverviewPageModule)
      },
      {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full'
      },
      {
        path: 'warenkorb',
        component: WarenkorbComponent
      },
      {
        path: 'bestellung',
        children: [
          {
            path: '',
            component: BestellungComponent,
            canActivate: [AuthGuard],
          },
          {
            path: 'complete/id/:id',
            component: BestellungCompleteComponent,
            canActivate: [AuthGuard]
          }
        ],
      },
    ]
  },
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
})
export class TabsPageRoutingModule {}
