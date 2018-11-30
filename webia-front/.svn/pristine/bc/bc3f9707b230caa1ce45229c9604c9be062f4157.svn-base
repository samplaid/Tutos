import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CommonModule } from "@angular/common";
import { UtilsModule, busyConfig } from './../utils/index';
import { WebiaService, DeathCoverageService, OperationEntryService } from './../_services';

import { AgentModule } from "../agent/index";
import {NgBusyModule} from 'ng-busy';
import { PopoverModule } from "ngx-bootstrap";
import { PaginationModule } from "ngx-bootstrap";
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { TransactionSearchComponent } from './components/transaction-search/transaction-search.component';
import { TransactionDetailsComponent } from './components/transaction-details/transaction-details.component';
import { PolicyComponent } from './components/policy/policy.component';
import { PolicyService } from './services/policy.service';
import { FilterService } from './services/filter.service';
import { TransactionHistoryDetailsComponent } from './components/transaction-details/transactions-history-details.component';
import { PolicyClausesComponent } from './components/policy-clauses/policy-clauses.component';
import { PolicyComponentModule } from './components';
import { PolicyPartnersComponent } from './components/policy-partners/policy-partners.component';
import { PolicyCommunicationComponent } from './components/policy-communication/policy-communication.component';
import { PolicyOperationComponent } from './components/policy-operation/policy-operation.component';

const routes: Routes = [
    { path: '', component: PolicyComponent },
];
export const routedComponents = [PolicyComponent];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PolicyRoutingModule { }

@NgModule({
    imports: [
        PolicyRoutingModule,
        UtilsModule,
        AgentModule,
        NgBusyModule.forRoot(busyConfig),
        PopoverModule.forRoot(),
        PaginationModule.forRoot(),
        PolicyComponentModule,
        NgxDatatableModule
    ],
    exports: [
        FormsModule, 
        CommonModule,
        PolicyPartnersComponent,
        PolicyCommunicationComponent,
        PolicyClausesComponent,
        PolicyComponentModule
    ],
    declarations: [PolicyComponent,
        TransactionSearchComponent, 
        TransactionDetailsComponent,        
        PolicyClausesComponent,
        PolicyOperationComponent
    ],
    providers: [
        PolicyService,
        WebiaService,
        DeathCoverageService,
        FilterService,
        OperationEntryService
    ]
})
export class PolicyModule { }