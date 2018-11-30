import { BrokerChangeAnalysisComponent } from './components/broker-change-analysis/broker-change-analysis.component';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { WorkflowModule } from '../workflow';
import { NgBusyModule } from 'ng-busy';
import { busyConfig, UtilsModule } from '../utils';
import { BrokerChangeComponent } from './components/broker-change/broker-change.component';
import { BrokerChangeService } from './services/broker-change-step.service';
import { BrokerChangeFormService } from './services/broker-change-form.service';
import { AgentService } from '../agent';
import { PolicyComponentModule } from '../policy/components';

export const routes = [
   { path: '',  component: BrokerChangeComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class BrokerChangeRoutingModule { }

@NgModule({
  declarations: [
    BrokerChangeComponent,
    BrokerChangeAnalysisComponent,
  ],
  imports: [
    BrokerChangeRoutingModule,
    WorkflowModule.forRoot(),
    NgBusyModule.forRoot(busyConfig),
    UtilsModule,
    PolicyComponentModule
  ],
  providers: [
    BrokerChangeService,
    BrokerChangeFormService, 
    AgentService
  ]
})
export class BrokerChangeModule {
  public static routes = routes;
}