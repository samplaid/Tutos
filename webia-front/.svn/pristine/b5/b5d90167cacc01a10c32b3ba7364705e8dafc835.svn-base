import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { WorkflowModule } from '../workflow';
import { NgBusyModule } from 'ng-busy';
import { busyConfig, UtilsModule } from '../utils';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { SurrenderComponent } from './components/surrender/surrender.component';
import { SurrenderAnalysisComponent } from './components/surrender-analysis/surrender-analysis.component';
import { PolicyComponentModule } from '@policy/components';
import { SurrenderService } from './services/surrender-step.service';
import { SurrenderFormService } from './services/surrender-form.service';
import { TransferService } from '../withdrawal/services/transfer.service';
import { WithdrawalSimulationService } from '../withdrawal/services/withdrawal-simulation.service';
import { LissiaTransactionComponent } from './components/lissia-transaction/lissia-transaction.component';

export const routes = [
   { path: '',  component: SurrenderComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SurrenderRoutingModule { }

@NgModule({
  declarations: [
    SurrenderComponent,
    SurrenderAnalysisComponent,
    LissiaTransactionComponent
  ],
  imports: [
    SurrenderRoutingModule,
    WorkflowModule.forRoot(),
    NgBusyModule.forRoot(busyConfig),
    BsDropdownModule.forRoot(),
    UtilsModule,
    PolicyComponentModule
  ],
  providers: [
    SurrenderService,
    SurrenderFormService,
    WithdrawalSimulationService,
    TransferService
  ]
})
export class SurrenderModule {
  public static routes = routes;
}