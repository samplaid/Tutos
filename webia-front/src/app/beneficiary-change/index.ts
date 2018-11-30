import { NgModule  } from '@angular/core';
import { BeneficiaryChangeComponent } from "./components/beneficiary-change/beneficiary-change.component";
import { BeneficiaryChangeAnalysisComponent } from "./components/beneficiary-change-analysis/beneficiary-change-analysis.component";
import { WorkflowModule } from "../workflow/index";
import { RouterModule } from "@angular/router";
import { NgBusyModule } from "ng-busy";
import { busyConfig, UtilsModule } from "../utils/index";
import { BeneficiaryChangeFormService } from './services/beneficiary-change-form.service';
import { BeneficiaryChangeStepWorkflowService } from "./services/beneficiary-change-step-workflow.service";
import { PolicyModule } from '../policy';export const routes = [
   { path: '',  component: BeneficiaryChangeComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class BeneficiaryChangeRoutingModule { }

@NgModule({
  declarations: [
    BeneficiaryChangeComponent,
    BeneficiaryChangeAnalysisComponent
  ],
  imports: [
    BeneficiaryChangeRoutingModule,
    WorkflowModule.forRoot(),
    NgBusyModule.forRoot(busyConfig),
    UtilsModule,
    PolicyModule
  ],
  providers: [
    BeneficiaryChangeStepWorkflowService,
    BeneficiaryChangeFormService
  ]
})
export class BeneficiaryChangeModule {
  public static routes = routes;
}