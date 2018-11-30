import { Injectable } from '@angular/core';
import { StepWorkflowService } from "../../workflow/services/step-workflow.service";
import { Http } from "@angular/http";
import { MessageService, ApiService } from "../../utils/index";
import { ElissiaChangeService } from "../../workflow/services/elissia-change.service";
import { AppForm, Step } from '../../_models';
import {Observable}  from 'rxjs/Observable';
import { RecreatePolicyRequest } from '../../subscription/models/recreate-policy';

@Injectable()
export class AdditionPremiumService extends StepWorkflowService<AppForm> {

     constructor($http: Http, 
                messageService: MessageService, 
                api: ApiService, 
                elissiaChangeService: ElissiaChangeService) {
        super($http, messageService, api, elissiaChangeService);
    }

    recreate(step:Step<AppForm>): Observable<Step<AppForm>> {
        const actionObservable = this.GET(this.api.getURL('recreateAdditionalPremium', step.workflowItemId), 'recreateAdditionalPremium').switchMap(res => Observable.of(step));

        return this.elissiaChangeService.pushStep(step, actionObservable);
    }
}