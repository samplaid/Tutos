import { Injectable } from '@angular/core';
import { StepWorkflowService } from "../../workflow/services/step-workflow.service";
import { Http } from "@angular/http";
import { MessageService, ApiService } from "../../utils";
import { ElissiaChangeService } from "../../workflow/services/elissia-change.service";
import { TransactionForm } from '../models/withdrawal-form';


@Injectable()
export class WithdrawalService extends StepWorkflowService<TransactionForm> {

     constructor($http: Http, 
                messageService: MessageService, 
                api: ApiService, 
                elissiaChangeService: ElissiaChangeService) {
        super($http, messageService, api, elissiaChangeService);
    }
}