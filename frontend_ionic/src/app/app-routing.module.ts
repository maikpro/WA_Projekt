import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth/auth-guard.guard';
import { BestellungCompleteComponent } from './bestellung/bestellung-complete/bestellung-complete.component';
import { BestellungComponent } from './bestellung/bestellung.component';
import { WarenkorbComponent } from './warenkorb/warenkorb.component';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./tabs/tabs.module').then(m => m.TabsPageModule)
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
  { path: '**', redirectTo: 'home' }
];
@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
