import { Injectable } from '@angular/core';
import { StepWorkflowService } from "../../workflow/services/step-workflow.service";
import { Http } from "@angular/http";
import { MessageService, ApiService } from "../../utils";
import { ElissiaChangeService } from "../../workflow/services/elissia-change.service";
import { AppForm, Step } from '../../_models';
import { Observable } from 'rxjs';

@Injectable()
export class SubscriptionService extends StepWorkflowService<AppForm> {

     constructor($http: Http, 
                messageService: MessageService, 
                api: ApiService, 
                elissiaChangeService: ElissiaChangeService) {
        super($http, messageService, api, elissiaChangeService);
    }

    update(step: Step<AppForm>): Observable<Step<AppForm>> {
        throw new Error("Unauthorized method 'update'");
    }


}