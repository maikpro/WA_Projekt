import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../auth/auth-guard.guard';
import { AccountValidateGuard } from '../shared/misc/account-validate.guard';
import { AccountOverviewPage } from './account-overview.page';
import { AccountComponent } from './account/account.component';

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
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccountOverviewPageRoutingModule {}
