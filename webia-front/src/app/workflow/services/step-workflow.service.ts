import { StepLight } from '../../_models/step/step-light';
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { HttpService, MessageService, ApiService, HandleErrorOptions } from '../../utils';
import { Observable } from 'rxjs/Observable';
import { Step } from '../../_models';
import { ElissiaChangeService } from './elissia-change.service';

export abstract class StepWorkflowService<T> extends HttpService {

    constructor($http: Http, 
                messageService: MessageService, 
                protected api: ApiService, 
                protected elissiaChangeService: ElissiaChangeService) {
        super($http, messageService);
        this.domain = 'StepWorfklowService';
    }

    getStepByWorkflow(workflowItemId: number): Observable<Step<T>> {
        return this.GET(this.api.getURL('loadWorkflowStep', workflowItemId), 'loadWorkflowStep');
    }

    update(step: Step<T>): Observable<Step<T>> {
        return this.POST(this.api.getURL('updateStep'), step, 'updateStep', new HandleErrorOptions(true, true));
    }

    abort(step: Step<T>): Observable<Step<T>> {
        const abortStepObservable = this.POST(this.api.getURL('abortStep'), step, 'abortStep');
        return this.elissiaChangeService.pushStep(step, abortStepObservable);
    }

    next(step: Step<T>, allSteps: StepLight[]): Observable<Step<T>> {
        const nextObs = targetStep => this.POST(this.api.getURL('completeStep'), targetStep, 'completeStep', new HandleErrorOptions(true, true));
        const refreshObs =  this.getStepByWorkflow(step.workflowItemId);
        
        return this.elissiaChangeService.pushAutomatically(step, nextObs, allSteps, refreshObs);
    }

    previous(step: Step<T>): Observable<Step<T>> {
        const rejectObservable = this.POST(this.api.getURL('previousStep'),step, 'previousStep', new HandleErrorOptions(true, true));
        return this.elissiaChangeService.pushStep(step, rejectObservable);
    }

    getAllStepsByWorkflowType(workflowTypeId: number) : Observable<StepLight[]> {
        return this.GET(this.api.getURL('loadStepsByWorkflowItemTypeId', workflowTypeId), 'loadStepsByWorkflowItemTypeId');
    }

    canGenerateAcceptanceDocument(workflowTypeId: number) : Observable<any> {
        return this.GET(this.api.getURL('canGenerateAcceptanceDocument', workflowTypeId), 'canGenerateAcceptanceDocument');
    }
}