import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UtilsModule,busyConfig, WDatePipe } from './../utils';

import {SearchMenuComponent} from './search-menu/search-menu.component';
import {SearchComponent} from './search.component';
import {SearchService} from './search.service';
import { PolicySearchComponent } from './policy/policy-search.component';
import { ClientSearchComponent } from "./client/client-search.component";
import { FundSearchComponent } from "./fund/fund-search.component";
import { AgentSearchComponent } from './agent/agent-search.component';
import { AgentModule } from "../agent";
import { NgBusyModule } from "ng-busy";
import { PaginationModule } from "ngx-bootstrap";
import { CommissionTableComponent } from './commission/components/commission-table/commission-table.component';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { SortableDirective } from './commission/sorting/sortable.directive';
import { PaymentsComptaComponent } from './payments/components/payments-compta.component';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AuthGuard, RequiredRole } from '../_guards';
import { Roles } from '../_models';

export const routes: Routes = [
  { path: '', component: SearchComponent },
  { path: 'policy', component: PolicySearchComponent },
  { path: 'client', component: ClientSearchComponent },
  { path: 'fund', component: FundSearchComponent },
  { path: 'agent', component: AgentSearchComponent },
  { path: 'commissionview', component: CommissionTableComponent },
  { path: 'payments', component: PaymentsComptaComponent, canActivate: [AuthGuard], 
    data: {
      roles : [Roles.COMPTA], 
      required: RequiredRole.ALL
    } 
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SearchRoutingModule { }

@NgModule({
  imports: [
    SearchRoutingModule,    
    NgBusyModule.forRoot(busyConfig),
    PaginationModule.forRoot(),
    UtilsModule,
    AgentModule,
    NgxDatatableModule,
    TooltipModule.forRoot(),
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [ // Components / Directives/ Pipes
    SearchMenuComponent,
    SearchComponent,
    PolicySearchComponent,
    ClientSearchComponent,
    FundSearchComponent,
    AgentSearchComponent,
    CommissionTableComponent,
    SortableDirective,
    PaymentsComptaComponent    
  ],  
  providers: [
    SearchService,
    WDatePipe
  ]
})
export class SearchModule {
  public static routes = routes;
}