import { NgModule, APP_BOOTSTRAP_LISTENER, ModuleWithProviders } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule, Http } from '@angular/http';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, Routes } from '@angular/router';

import { ClientModule } from './../client/index';
import { AgentModule } from './../agent/index';
import { UtilsModule, busyConfig } from './../utils/index';

import { CurrencyService, UoptDetailService } from './../_services';

import { FundComponent } from './fund.component';
import { PartialFidComponent } from './_partial/fid/partialFid.component';
import { PartialFasComponent } from './_partial/fas/partialFas.component';
import { PricingComponent } from "./components/pricing/pricing.component";
import { FundValuationComponent } from "./components/fund-valuation/fund-valuation.component";
import { FundPricesComponent } from "./components/fund-prices/fund-prices.component";
import { FundFooterComponent } from "./components/fund-footer/fund-footer.component";
import { SearchFundComponent } from './search-fund/search-fund.component';
import { ModalFundContent } from './modal/modal-fund-content.component';
import { ExternalFundComponent } from './external-fund/external-fund.component';
import { FicComponent } from "./fic/fic.component";



import { FundService } from './fund.service';
import { AgentService } from "../agent/agent.service";
import { CircularComponent } from './components/circular/circular.component';
import { OperationEntryModule } from "../operation-entry/index";
import { NgBusyModule } from "ng-busy";
import { ModalModule, PaginationModule } from "ngx-bootstrap";

export const fundRoutes = [
   { path: 'FID',  component: FundComponent, data:{subType:'FID'}},
   { path: 'FID/:id',  component: FundComponent, data:{subType:'FID'} },
   { path: 'FAS',  component: FundComponent, data:{subType:'FAS'}},
   { path: 'FAS/:id',  component: FundComponent, data:{subType:'FAS'} },
   { path: 'FE',  component: ExternalFundComponent, data:{subType:'FE'}},
   { path: 'FE/:id',  component: ExternalFundComponent, data:{subType:'FE'} },
   { path: 'FIC',  component: FicComponent, data:{subType:'FIC'}},
   { path: 'FIC/:id',  component: FicComponent, data:{subType:'FIC'} },
];



@NgModule({
  imports: [RouterModule.forChild(fundRoutes)],
  exports: [RouterModule]
})
export class FundRoutingModule {}


@NgModule({
  declarations: [ // Components / Directives/ Pipes
    FundComponent,
    PartialFidComponent,
    PartialFasComponent,
    SearchFundComponent,
    ModalFundContent,
    PricingComponent,
    FundValuationComponent,
    FundPricesComponent,
    FundFooterComponent,
    ExternalFundComponent,
    FicComponent,
    CircularComponent
  ],
  entryComponents: [SearchFundComponent, ModalFundContent],
  imports: [    
    NgBusyModule.forRoot(busyConfig),
    ModalModule.forRoot(),
    PaginationModule.forRoot(),
    UtilsModule,
    FundRoutingModule,
    AgentModule,
    ClientModule,
    OperationEntryModule      
  ],
  exports:[ FundComponent, CircularComponent, PartialFidComponent, PartialFasComponent, PricingComponent,FundValuationComponent,FundPricesComponent,FundFooterComponent, SearchFundComponent, ModalFundContent, CommonModule, RouterModule], 
  providers: [
    Title,
    FundService,
    CurrencyService,
    UoptDetailService,
    AgentService
  ],
  bootstrap: [FundComponent]
})
export class FundModule {
  public static routes = fundRoutes;
}

export { FundType } from './_partial/fund-type';
export { FundSearchCriteria } from './fund.service';
