import { Injectable } from '@angular/core';
import { StepWorkflowService } from "../../workflow/services/step-workflow.service";
import { Http } from "@angular/http";
import { MessageService, ApiService } from "../../utils";
import { ElissiaChangeService } from "../../workflow/services/elissia-change.service";
import { BrokerChangeForm } from '../models/broker-change-form';
import { BrokerChange } from '../models/broker-change';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class BrokerChangeService extends StepWorkflowService<BrokerChangeForm> {

     constructor($http: Http, 
                messageService: MessageService, 
                api: ApiService, 
                elissiaChangeService: ElissiaChangeService) {
        super($http, messageService, api, elissiaChangeService);
    }

    getBrokerChangeBefore(workflowItemId:number): Observable<BrokerChange>{
        return this.functionWithStore('BrokerChangeBefore'+workflowItemId,this.api.getURL('getBrokerChangeBefore', workflowItemId), 'getBrokerChangeBefore');
    }
}