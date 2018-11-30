
import { NgModule, ModuleWithProviders } from "@angular/core";
import { HeaderComponent } from "./components/header/header.component";
import { SurveyComponent, QuestionComponent, SurveyService } from "../survey/index";
import { PolicyPicker } from "../utils/components/policy-picker.component";
import { OtherRelationsInputComponent } from "./components/other-relations-input/other-relations-input.component";
import { PolicyClausesInputComponent } from "./components/policy-clauses-input/policy-clauses-input.component";
import { DatePipe } from "@angular/common";
import { NgBusyModule } from "ng-busy";
import { PopoverModule } from "ngx-bootstrap";
import { UtilsModule, busyConfig } from "../utils/index";
import { ClientModule } from "../client/index";
import { WorkflowService, ClauseService, AppliParamService, ExchangeRateService, EditingService } from "../_services/index";
import { KeycloakService } from "../_guards/keycloak.service";
import { AnalysisService } from "../analysis/index";
import { QuestionService } from "../survey/question.service";
import { BeneficiaryInputcomponent } from "./components/beneficiary-input/beneficiary-input.component";
import { ElissiaChangeService } from './services/elissia-change.service';
import { HeaderService } from "./services/header.service";
import { PolicyHolderInputcomponent } from "./components/policy-holder-input/policy-holder-input.component";
import { PolicyTransferInputComponent } from "./components/policy-transfer-input/policy-transfer-input";
import { PolicyService } from '../policy/services/policy.service';
import { PolicyPremiumFeesComponent } from "./components/policy-premium-fees/policy-premium-fees.component";
import { PaymentComponent } from './components/payment/payment.component';
import { AnalysisInvestComponent, RegistrationInvestComponent, InvestmentService } from '../investment';
import { FundModule } from '../fund';
import { SimulationComponent } from './components/simulation/simulation.component';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { PolicyFeesComponent } from '../policy/components/policy-fees/policy-fees.component';
import { FundFormService } from '../_services/fund-form.service';
import { AgentModule } from '../agent';
import { PolicyComponentModule } from '../policy/components';
import { PolicyBrokerInputComponent } from './components/policy-broker-input/policy-broker-input';
import { PolicySendingRulesInputComponent } from './components/policy-sending-rules-input/policy-sending-rules-input';
import { PolicyFundFeesInputComponent } from './components/policy-fund-fees-input/policy-fund-fees-input';
import { SimulationService } from './services/simulation.service';
import { CheckDataService } from './services/check-data.service';
import { FeesComponent } from '@workflow/components/fees/fees.component';
import { FundTransactionComponent } from '@workflow/components/fund-transaction/fund-transaction.component';
import { SecuritiesTransferComponent } from '@workflow/components/securities-transfer/securities-transfer.component';
import { SecuritiesTransfersComponent } from '@workflow/components/securities-transfer/securities-transfers.component';
import { MultipleSimulationsComponent } from './components/multiple-simulations/multiple-simulations.component';import { CashTransfersComponent } from '@workflow/components/cash-transfer/cash-transfers.component';
import { CashTransferComponent } from '@workflow/components/cash-transfer/cash-transfer.component';
import { TabsModule } from 'ngx-bootstrap/tabs';
import {NgxMaskModule} from 'ngx-mask'
const components = [HeaderComponent,
                    SurveyComponent,
                    QuestionComponent,
                    PolicyPicker,
                    PolicyFeesComponent,
                    PolicyClausesInputComponent,
                    OtherRelationsInputComponent,
                    BeneficiaryInputcomponent,
                    PolicyHolderInputcomponent,
                    PolicyTransferInputComponent,
                    PolicyPremiumFeesComponent,
                    PaymentComponent,
                    AnalysisInvestComponent,
                    SimulationComponent,
                    MultipleSimulationsComponent,
                    RegistrationInvestComponent, 
                    PolicyBrokerInputComponent, 
                    PolicySendingRulesInputComponent, 
                    FeesComponent,
                    FundTransactionComponent,
                    SecuritiesTransferComponent,
                    SecuritiesTransfersComponent,
                    CashTransfersComponent,
                    CashTransferComponent,
                    PolicyFundFeesInputComponent];

@NgModule({
  declarations: [...components],
  imports: [
    UtilsModule,
    ClientModule,
    FundModule,
    AgentModule,
    NgBusyModule.forRoot(busyConfig),
    PopoverModule.forRoot(),
    TabsModule.forRoot(),
    PdfViewerModule,
    PolicyComponentModule,
    NgxMaskModule.forRoot()
  ],
  exports: [...components],
  providers: [InvestmentService]
})

export class WorkflowModule {

  static forRoot(): ModuleWithProviders {
    return {
      ngModule: WorkflowModule,
      providers: [
        WorkflowService,
        SurveyService,
        KeycloakService,
        AnalysisService,
        PolicyService,
        ClauseService,
        AppliParamService,
        QuestionService,
        DatePipe,
        ElissiaChangeService,
        HeaderService,
        ExchangeRateService,
        FundFormService,
        SimulationService,
        EditingService,
        CheckDataService
      ]
    };
  }
}

/* export others classes */
export  { CommentData } from './models/comment-data';
