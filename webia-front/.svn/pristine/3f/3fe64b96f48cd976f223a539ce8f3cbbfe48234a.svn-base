import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { WorkflowModule } from '../workflow';
import { NgBusyModule } from 'ng-busy';
import { busyConfig, UtilsModule } from '../utils';
import { AdditionPremiumService } from './services/additional-premium-step-workflow.service';
import { AdditionalPremiumComponent } from './components/additional-premium/additional-premium.component';
import { AdditionalPremiumAnalysisComponent } from "./components/additional-premium-analysis/additional-premium-analysis.component";
import { AdditionalPremiumFormService } from "./services/additional-premium-form.service";
import { FundModule } from '../fund';
import { PolicyComponentModule } from '../policy/components';


export const routes = [
   { path: '',  component: AdditionalPremiumComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdditionalPremiumRoutingModule { }

@NgModule({
  declarations: [
    AdditionalPremiumComponent,
    AdditionalPremiumAnalysisComponent
  ],
  imports: [
    AdditionalPremiumRoutingModule,
    FundModule,    
    WorkflowModule.forRoot(),
    NgBusyModule.forRoot(busyConfig),
    UtilsModule,
    PolicyComponentModule
  ],
  providers: [
    AdditionPremiumService,
    AdditionalPremiumFormService
  ]
})
export class AdditionalPremiumModule {
  public static routes = routes;
}