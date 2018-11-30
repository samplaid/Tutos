import { NgModule, APP_INITIALIZER  } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule, Http } from '@angular/http';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { UtilsModule, busyConfig} from './../utils';
import { ClientModule } from '../client';
import { AgentModule } from '../agent';
/*******
 * webia
 */
import { AuthGuard, RequiredRole  } from '../_guards';
import { FundModule } from '../fund';
import { CurrencyService, UoptDetailService, OptionDetailService, ProductService, CountryService, WorkflowService, ClauseService, AppliParamService, DeathCoverageService, WebiaService, EditingService, ExchangeRateService } from '../_services';

import { KeycloakHttp, KEYCLOAK_HTTP_PROVIDER } from '../_guards/keycloak.http';
import { KeycloakService  } from '../_guards/keycloak.service';
import { AnalysisComponent, AnalysisService } from '../analysis';
import { RegistrationComponent, RegistrationService } from '../registration';
import { WaitingDispatchComponent, WaitingDispatchService } from '../waiting-dispatch';
import { GenerateMandatDeGestionComponent, GenerateMandatDeGestionService } from '../generate-mandat-de-gestion';
import { SurveyComponent, QuestionComponent, SurveyService, QuestionService } from '../survey';
import { InvestmentService } from '../investment';
import { NgBusyModule } from "ng-busy";
import { ModalModule, PaginationModule, PopoverModule } from "ngx-bootstrap";
import { WorkflowModule } from "../workflow/index";
import { SubscriptionComponent } from "./components/subscription.component";
import { StepService } from "./services/step.service";
import { SubscriptionService } from './services/subscription-step.service';

export const stepRoutes:Routes = [
    { path: '',  component: SubscriptionComponent },
    { path: ':stepId',  component: SubscriptionComponent },
];


@NgModule({
  imports: [RouterModule.forChild(stepRoutes)],
  exports: [RouterModule],
})
export class SubscriptionRoutingModule { }

@NgModule({
  declarations: [ // Components / Directives/ Pipes
    SubscriptionComponent,
    AnalysisComponent, 
    RegistrationComponent, 
    WaitingDispatchComponent,
    GenerateMandatDeGestionComponent,    
  ],
  imports: [
    SubscriptionRoutingModule,
    UtilsModule,
    WorkflowModule.forRoot(),
    NgBusyModule.forRoot(busyConfig),
    ModalModule.forRoot(),
    PaginationModule.forRoot(),
    PopoverModule.forRoot(),
    ClientModule,
    FundModule,
    AgentModule 
  ],
  providers: [
    Title,
    WebiaService,
    StepService,
    CurrencyService,
    ProductService,
    CountryService,
    UoptDetailService,
    OptionDetailService,
    WorkflowService,
    InvestmentService,
    DeathCoverageService,
    RegistrationService,
    WaitingDispatchService,  
    GenerateMandatDeGestionService,
    EditingService,
    ExchangeRateService,
    SubscriptionService,
    DatePipe
  ],
  bootstrap: [SubscriptionComponent]
})
export class SubscriptionModule {
  public static routes = stepRoutes;
}