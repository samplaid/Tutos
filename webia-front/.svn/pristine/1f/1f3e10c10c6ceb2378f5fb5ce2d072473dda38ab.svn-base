import { Http } from "@angular/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs/Observable";

import { HttpService, MessageService, ApiService, HandleErrorOptions } from "../utils";
import { Step, AppForm,StepName, Fund, CreateEditingRequest, EditingDocumentType, CreateEditingResponse } from '../_models';

@Injectable()
export class EditingService extends HttpService{

    constructor($http: Http, messageService: MessageService, protected api: ApiService) {
        super($http, messageService);
        this.domain = 'EditingService';  
    }
    
    prepareMandatManagementEditingRequest(fund: Fund, a: AppForm): CreateEditingRequest {
        let request = new CreateEditingRequest();
        request.fund = fund.fdsId;
        request.policy = a.policyId;
        request.product = a.productCd;
        request.documentType = EditingDocumentType.MANAGEMENT_MANDATE;
        return request;
    }

    preparePolicyScheduleEditingRequest(policyId: string, productCd: string): CreateEditingRequest {
        let request = new CreateEditingRequest();
        request.policy = policyId;
        request.product = productCd;
        request.documentType = EditingDocumentType.POLICY_SCHEDULE;
        return request;
    }

    generateDocumentation(a: AppForm): Observable<any> {
        return this.createEditingRequest(this.preparePolicyScheduleEditingRequest(a.policyId, a.productCd));
    }
    
   generateSouscriptionFollowUp(appForm: AppForm):Observable<CreateEditingResponse>{  
            return this.POST(this.api.getURL('souscriptionFollowUpRequest'), appForm ,'souscriptionFollowUpRequest', new HandleErrorOptions(true, true,'souscriptionFollowUpReq'));    
    }

    applyManagementMandateDocRequest(request: CreateEditingRequest): Observable<CreateEditingResponse>{  
        return this.POST(this.api.getURL('applyManagementMandateDocRequest'), request ,'applyManagementMandateDocRequest', new HandleErrorOptions(true, true));
    }

    createEditingRequest(createEditingRequest:CreateEditingRequest): Observable<any> {
        return this.POST(this.api.getURL('createEditingRequest'),createEditingRequest,'createEditingRequest');
    }

    generateMandatDeGestion(fund: Fund, a: AppForm): Observable<any> {
        return this.createEditingRequest(this.prepareMandatManagementEditingRequest(fund, a));
    }

    createAcceptanceReportEditingRequest(product: string, policyId: string, workflowItemId: number): Observable<CreateEditingResponse> {
        let request: CreateEditingRequest = new CreateEditingRequest();
        request.policy = policyId;
        request.product = product;
        request.workflowItemId = workflowItemId;
        return this.POST(this.api.getURL('acceptanceReportRequest'), request ,'acceptanceReportRequest', new HandleErrorOptions(true, true,'acceptanceReportReq'));
    }

    getFollowUpTitleByStepName(stepNames:String){
        if (stepNames === StepName.Account_Opening_Request || stepNames === StepName.Premium_Transfer_Request || stepNames === StepName.Awaiting_Account_Opening){
            return " Acceptation ";
        }else if (stepNames === StepName.Registration){
            return " proposal reception ";
        }
        else if (stepNames === StepName.Generate_documentation){
            return " Payment on contract ";
        }

        return " ";
    }

	generateSurrenderReport(policyId: string, eventDate: string, frenchtaxable:boolean):Observable<CreateEditingResponse> {
        let request = new CreateEditingRequest();
        request.policy = policyId;
        request.eventDate = eventDate;
        request.frenchTaxable = frenchtaxable;
        return this.POST(this.api.getURL('surrenderReportRequest'), request ,'surrenderReportRequest', new HandleErrorOptions(true, true,'surrenderReportReq'));    
    }
	createWorkflowDocument(workflowItemId:number, documentType:string): Observable<any> {
        return this.POST(this.api.getURL('workflowDocumentRequest', workflowItemId, documentType), {}, 'workflowDocumentRequest');
    }

   
}
