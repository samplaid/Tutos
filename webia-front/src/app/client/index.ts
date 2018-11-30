import { NgModule, APP_INITIALIZER  } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule, Http } from '@angular/http';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { CountryService, UoptDetailService, WebiaService, OptionDetailService} from '../_services';
import { AgentModule } from '../agent';
import { UtilsModule, busyConfig } from '../utils';

/* exposed outside */
import  { SearchClientComponent } from './search-client/search-client.component'; export  { SearchClientComponent } from './search-client/search-client.component';
import { ModalClientContent } from './modal/modal-client-content.component'; export { ModalClientContent } from './modal/modal-client-content.component';
import { ModalClientSearch } from './modal/modal-client-search.component'; export { ModalClientSearch } from './modal/modal-client-search.component';
import { ModalClientDeathNotify } from "./modal/modal-client-death-notify.component";export { ModalClientDeathNotify } from "./modal/modal-client-death-notify.component";
let entryComponents = [SearchClientComponent, ModalClientContent, ModalClientSearch, ModalClientDeathNotify];

/* use internal */
import  { SearchClientMultipleComponent } from './search-client-multiple/search-client-multiple.component';
import  { PersonPhysicalComponent } from './form/person-physical/person-physical.component';
import  { PersonMoralComponent } from './form/person-moral/person-moral.component';
import  { ClientService } from './client.service';
import  { ClientContactComponent } from './components/contact/client-contact.component';
import  { TinComponent } from './components/tin/tin.component';
import  { ClientPoliciesComponent } from './components/client-policies/client-policies.component';
import  { ClientComplianceComponent } from './components/client-compliance/client-compliance.component';
import { ClientInsiderTradingComponent } from './components/client-insider-trading/client-insider-trading.component';
import { OperationEntryModule } from "../operation-entry/operation-entry.module";
import { NgBusyModule } from "ng-busy";
import { PaginationModule } from "ngx-bootstrap";
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { AccountsComponent } from './form/accounts/client-accounts.component';

export const routes = [
   { path: 'physical',  component: PersonPhysicalComponent },
   { path: 'physical/:id',  component: PersonPhysicalComponent },
   { path: 'moral',  component: PersonMoralComponent },
   { path: 'moral/:id',  component: PersonMoralComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ClientRoutingModule { }

@NgModule({
  declarations: [ // Components / Directives/ Pipes
    SearchClientMultipleComponent, 
    PersonPhysicalComponent, 
    PersonMoralComponent,
    AccountsComponent,    
    ClientContactComponent,
    TinComponent,
    ClientPoliciesComponent,
    ClientComplianceComponent,
    ClientInsiderTradingComponent,
    ...entryComponents
  ],
  entryComponents: [...entryComponents],
  imports: [
    ClientRoutingModule,
    NgBusyModule.forRoot(busyConfig),
    PaginationModule.forRoot(),
    UtilsModule,
    AgentModule, 
    OperationEntryModule,
    NgxDatatableModule
  ],
  exports:[SearchClientComponent, CommonModule, RouterModule], 
  providers: [
    Title,
    ClientService,
    CountryService,
    UoptDetailService,
    WebiaService,
    OptionDetailService
  ]
})
export class ClientModule {
  public static routes = routes;
}

/* export others classes */
export  { ClientSearchCriteria } from './client.service';
export  { ClientContactModel } from './components/contact/client-contact.model';
export  { TinModel } from './components/tin/tin.model'; 
export  { AbstractPersonComponent } from './form/abstract-person.component';