import { Injectable } from '@angular/core';
import { BeneficiaryChangeForm } from "../models/beneficiary-form";
import { StepWorkflowService } from "../../workflow/services/step-workflow.service";
import { Http } from "@angular/http";
import { MessageService, ApiService } from "../../utils/index";
import { ElissiaChangeService } from "../../workflow/services/elissia-change.service";

@Injectable()
export class BeneficiaryChangeStepWorkflowService extends StepWorkflowService<BeneficiaryChangeForm> {

     constructor($http: Http, 
                messageService: MessageService, 
                api: ApiService, 
                elissiaChangeService: ElissiaChangeService) {
        super($http, messageService, api, elissiaChangeService);
    }
}