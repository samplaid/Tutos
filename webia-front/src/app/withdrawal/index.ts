import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { WorkflowModule } from '../workflow';
import { NgBusyModule } from 'ng-busy';
import { busyConfig, UtilsModule } from '../utils';
import { WithdrawalComponent } from './components/withdrawal/withdrawal.component';
import { WithdrawalAnalysisComponent } from './components/withdrawal-analysis/withdrawal-analysis.component';
import { WithdrawalService } from './services/withdrawal-step.service';
import { WithdrawalFormService } from './services/withdrawal-form.service';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TransferService } from './services/transfer.service';
import { PolicyComponentModule } from '../policy/components';
import { WithdrawalSimulationService } from './services/withdrawal-simulation.service';

export const routes = [
   { path: '',  component: WithdrawalComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class WithdrawalRoutingModule { }

@NgModule({
  declarations: [
    WithdrawalComponent,
    WithdrawalAnalysisComponent
  ],
  imports: [
    WithdrawalRoutingModule,
    PolicyComponentModule,
    WorkflowModule.forRoot(),
    NgBusyModule.forRoot(busyConfig),
    BsDropdownModule.forRoot(),
    UtilsModule
  ],
  providers: [
    WithdrawalService,
    WithdrawalFormService, 
    WithdrawalSimulationService,
    TransferService
  ]
})
export class WithdrawalModule {
  public static routes = routes;
}