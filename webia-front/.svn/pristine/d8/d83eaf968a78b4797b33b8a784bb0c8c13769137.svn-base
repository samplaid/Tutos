import { UtilsModule, busyConfig } from './../utils/index';
import { NgModule, APP_INITIALIZER  } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule, Http } from '@angular/http';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { CountryService, UoptDetailService, WebiaService, OptionDetailService, OperationEntryService } from '../_services';

import { SearchAgentComponent} from './search-agent/search-agent.component';
import { AgentService } from './agent.service';
import { AgentComponent } from './form/agent.component';
import { AgentContactModal } from "./contact/modal/agent-contact-modal.component";
import { AgentContactAddSelectComponent } from "./contact/add-select/agent-contact-add-select.component";
import { AgentContactComponent } from './contact/agent-contact.component';

import { AgentLabelComponent } from "./components/label/agent-label.component";
import { BrokerPoliciesComponent } from "./components/broker-policies/broker-policies.component";
import { AgentFundsComponent } from "./components/agent-funds/agent-funds.component";
import { AgentBasicFormComponent } from "./components/basic-form/agent-basic-form.component";
import { AgentIdentityFormComponent } from "./components/identity-form/agent-identity-form.component";
import { AgentAddressFormComponent } from "./components/address/agent-address-form.component";
import { AgentBasicModalContent } from "./components/basic-form/modal/agent-basic-modal-content.component";
import { AgentBasicModalButtonComponent } from "./components/basic-form/modal/agent-basic-modal-btn.component";
import { AmStrategiesComponent } from "./components/am-strategies/am-strategies.component";
import { AgentHierachyTableComponent } from './components/agent-hierarchy/table/agent-hierarchy-table.component';
import { SearchAgentDisplayNameComponent } from './search-agent/search-agent-display-name.component';
import { AmStrategyListComponent } from './components/am-strategy-list/am-strategy-list.component';
import {NgBusyModule} from 'ng-busy';
import { PaginationModule } from "ngx-bootstrap";
import { OperationEntryModule } from "../operation-entry/operation-entry.module";

export const agentRoutes: Routes = [
   { path: '',  component: AgentComponent },
   { path: ':id',  component: AgentComponent }
   
];

@NgModule({
  imports: [RouterModule.forChild(agentRoutes)],
  exports: [RouterModule]
})
export class AgentRoutingModule {}

@NgModule({
  declarations: [ // Components / Directives/ Pipes
    SearchAgentComponent, 
    AgentComponent,
    AgentContactComponent, 
    AgentContactModal, 
    AgentContactAddSelectComponent,
    AgentLabelComponent,
    BrokerPoliciesComponent,
    AgentFundsComponent,
    BrokerPoliciesComponent,
    AgentBasicFormComponent,
    AgentIdentityFormComponent,
    AgentAddressFormComponent,
    AgentBasicModalContent,
    AgentBasicModalButtonComponent,
    AmStrategiesComponent,
    AgentHierachyTableComponent,
    SearchAgentDisplayNameComponent,
    AmStrategyListComponent
  ],
  entryComponents: [ AgentBasicModalContent ],
  imports: [
    AgentRoutingModule,
    UtilsModule,
    NgBusyModule.forRoot(busyConfig),
    PaginationModule.forRoot()
  ],   
  exports:[ 
    SearchAgentComponent, 
    BrokerPoliciesComponent,
    AgentFundsComponent,
    CommonModule, 
    AgentComponent, 
    AgentContactComponent,
    AgentContactModal, 
    AgentContactAddSelectComponent,
    AgentLabelComponent,
    AgentBasicFormComponent,
    AgentIdentityFormComponent,
    AgentAddressFormComponent,
    AgentBasicModalButtonComponent,
    AmStrategiesComponent,
    AgentHierachyTableComponent ,
    SearchAgentDisplayNameComponent,
    AmStrategyListComponent],
  providers: [
    Title,
    AgentService,
    UoptDetailService,
    OptionDetailService,
    CountryService,
    WebiaService,
    OperationEntryService
  ]
})
export class AgentModule {
  public static routes = agentRoutes;
}

export { SearchAgentComponent } from './search-agent/search-agent.component';
export { AgentService, SearchCriteria } from './agent.service';
