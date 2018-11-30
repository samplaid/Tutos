import { Http, Response, Headers } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import * as _ from "lodash";

import { ApiService, MessageService, HttpService, ConfigService } from '../../utils';
import { SurveyService } from '../../survey';
import { Step, StepName, CheckStep, CheckData, AppForm, Policy, WorkFlowStatus, agentMailSendingRuleCodes } from '../../_models';
import { InvestmentService } from "../../investment";
import { BasicFormDataService, WorkflowService } from "../../_services";
import { RegistrationService } from "../../registration";
import { AnalysisService } from "../../analysis";
import { WaitingDispatchService } from '../../waiting-dispatch/waiting-dispatch.service';
import { GenerateMandatDeGestionService } from '../../generate-mandat-de-gestion/generate-mandat-de-gestion.service';
import { AgentService } from "../../agent/agent.service";
import { Subject } from 'rxjs';
import { ElissiaChangeService } from '../../workflow/services/elissia-change.service';
import { RecreatePolicyRequest } from '../models/recreate-policy';
import { PolicyService } from '../../policy/services/policy.service';
import { StepLight } from '../../_models/step/step-light';


@Injectable()
export class StepService extends HttpService {

    constructor($http: Http, messageService: MessageService,
                private api: ApiService,
                private workflowService: WorkflowService,
                private surveyService: SurveyService,
                private investmentService: InvestmentService,
                private registrationService: RegistrationService,
                private analysisService: AnalysisService,
                private waitingDispatchService: WaitingDispatchService,
                private generateMandatDeGestionService: GenerateMandatDeGestionService,
                private policyService: PolicyService,
                private agentService:AgentService,
                private config: ConfigService,
                private elissiaChangeService: ElissiaChangeService                
                ) {
        super($http, messageService);
        this.domain = 'StepService';
    }

    lookUpService(stepId): BasicFormDataService<Step<AppForm>> {
        switch (stepId) {
            case StepName.New_Business:
            case StepName.Registration: 
                    return this.registrationService;
            case StepName.Waiting_Dispatch: 
                    return this.waitingDispatchService; 
            case StepName.Generate_Mandat_de_Gestion: 
                    return this.generateMandatDeGestionService; 
            default:
                    return this.analysisService; 
        }
    }

    loadStep(...params): Observable<Step<AppForm>> {
        return this.GET(this.api.getURL('loadStep', ...params), 'loadStep').map((data:any)=>this.initStep(data));
    }

    getRejectData(workflowItemId: number){
        if (workflowItemId)
            return this.GET(  this.api.getURL('getRejectData', workflowItemId), 'getRejectData').map((data:any)=>this.initStep(data)).toPromise();
    }

    getCommentsHistory(workflowItemId:number, workflowItemTypeId:number){
        return this.GET(this.api.getURL('getCommentsHistory',workflowItemId, workflowItemTypeId), 'getCommentsHistory').toPromise(); 
    }

    initStep(step): Step<AppForm>{   
        if (!step.formData)  
            step.formData = new AppForm();
        return step;
    }

    save(step: Step<AppForm>, complete, allSteps: StepLight[]) {
        //survey traitment
        let postaData:Step<AppForm> = _.cloneDeep(step);
        postaData.checkSteps = this.surveyService.prune(postaData.checkSteps);
        this.cleanFormData(postaData.formData);
        //formData traitment

        // appel du web service de webia application
        //let saveObservable = this.lookUpService(postaData.stepWorkflow).save(postaData, complete);

        const saveObservable = anyStep => this.lookUpService(anyStep.stepWorkflow).save(anyStep, complete);

        if (complete){
            return this.elissiaChangeService.pushAutomatically(postaData, saveObservable, allSteps, this.loadStep(step.workflowItemId, null, null));
        } else{
            return this.lookUpService(step.stepWorkflow).save(step, complete);
        }           
    }

    cleanFormData(formData:AppForm){
        if(formData) {
            this.removeDirtyAgents(formData);
            this.treatPartners(formData);
            this.setMailAgentToNullIfNotInList(formData, agentMailSendingRuleCodes);
        }
    }

    setMailAgentToNullIfNotInList(appForm: AppForm, mailsToSendRulesCode: any[]): void {
        if(appForm && mailsToSendRulesCode) {
            let canSendMailToAgent = mailsToSendRulesCode.some(sendingRuleCode => sendingRuleCode === appForm.sendingRules)
            
            if(!canSendMailToAgent){
                appForm.mailToAgent = null;
            }
        }
    }

    removeDirtyAgents(formData:AppForm){
        if(formData.brokerContact && !formData.brokerContact.partnerId)
            formData.brokerContact = null;
        if(formData.subBroker && !formData.subBroker.partnerId)
            formData.subBroker  = null;
    }

    treatPartners(formData: AppForm ) {
        if(formData) {
            if(formData.broker) {
                if(this.agentService.isWealins(formData.broker.partnerId) ){
                    // Rule Business intro: Show if broker ==  wealins
                    // Then clean contact person                    
                    formData.brokerContact = null;                
                } else {
                    // Rule contact person: Show if broker !=  wealins
                    // Then clean Business intro
                    formData.businessIntroducer = null
                }  
            }
        }        
    }

    previousStep(step:Step<AppForm>){
        
        if(step.formData){
            this.cleanFormData(step.formData);
            let prevousStepObservable = this.lookUpService(step.stepWorkflow).previousStep(step.formData);
        }

        let rejectObservable = this.POST(step, 'previousStep').map((data:any)=>this.initStep(data));
        return this.elissiaChangeService.pushStep(step, rejectObservable);
    }

    abortStep(step:Step<AppForm>){
        let postaData:Step<AppForm> = _.cloneDeep(step);
        postaData.checkSteps = this.surveyService.prune(postaData.checkSteps);
        if(postaData.formData){
            this.cleanFormData(postaData.formData);
        }
        return this.messageService.confirm("Please confirm your action").flatMap(confirm => {
            if (confirm) {
                const abortObservable = this.POST(postaData, 'abortStep').map((data: any) => this.initStep(data));
                return this.elissiaChangeService.pushStep(postaData, abortObservable);
            } else {
                return Observable.empty();
            }
        }, reject => { });
    }

    POST(step:Step<AppForm>, name:string) : Observable<Step<AppForm>>{
        return new Observable(observer => { 
            super.POST(this.api.getURL(name), step, name)
                .toPromise().then(step=>{observer.next(step);observer.complete();}, 
                                        e=>{observer.error();observer.complete();});
        });
    }

    refreshAppForm(currentStep:Step<AppForm>, reloadedStep:Step<AppForm>){
        this.lookUpService(reloadedStep.stepWorkflow).refreshAppForm(currentStep,reloadedStep);
    }

    recreatePolicy(step:Step<AppForm>): Observable<Step<AppForm>> {
        const request: RecreatePolicyRequest =  {formId: step.formData.formId, workflowItemId: step.workflowItemId};
        const actionObservable = super.POST(this.api.getURL('recreatePolicy'), request, 'recreatePolicy').switchMap(res => Observable.of(step));

        return this.elissiaChangeService.pushStep(step, actionObservable);
    }
}