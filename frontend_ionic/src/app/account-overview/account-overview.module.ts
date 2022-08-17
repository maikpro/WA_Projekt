import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AccountOverviewPage } from './account-overview.page';
import { ExploreContainerComponentModule } from '../explore-container/explore-container.module';

import { AccountOverviewPageRoutingModule } from './account-overview-routing.module';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ExploreContainerComponentModule,
    RouterModule.forChild([{ path: '', component: AccountOverviewPage }]),
    AccountOverviewPageRoutingModule,
  ],
  declarations: [AccountOverviewPage]
})
export class AccountOverviewPageModule {}
