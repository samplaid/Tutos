/* tslint:disable: max-line-length */
import { Routes } from '@angular/router';

import { NotFound404Component } from './not-found404.component';

export const routes: Routes = [
  { path: '',  redirectTo : 'search/policy', pathMatch: 'full' },
  { path: 'state',  redirectTo : 'search/policy', pathMatch: 'full' },
  { path: 'step', loadChildren: './subscription/index#SubscriptionModule' },
  { path: 'workflow/additionalPremium', loadChildren: './additional-premium/index#AdditionalPremiumModule' },  
  { path: 'workflow/changeBenef', loadChildren: './beneficiary-change/index#BeneficiaryChangeModule' },
  { path: 'workflow/changeBroker', loadChildren: './broker-change/index#BrokerChangeModule' },
  { path: 'workflow/withdrawal', loadChildren: './withdrawal/index#WithdrawalModule' },  
  { path: 'workflow/surrender', loadChildren: './surrender/index#SurrenderModule' },  
  { path: 'fund',  loadChildren: './fund/index#FundModule'  },
  { path: 'client',  loadChildren: './client/index#ClientModule' },
  { path: 'search', loadChildren: './search/index#SearchModule' },
  { path: 'agent', loadChildren: './agent/index#AgentModule' },
  { path: 'policy', loadChildren: './policy/index#PolicyModule' },
  { path: '**', component: NotFound404Component }
];
