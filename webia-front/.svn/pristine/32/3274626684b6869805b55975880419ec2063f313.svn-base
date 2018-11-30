import { Http } from "@angular/http";
import { Injectable } from '@angular/core';

import { MessageService, ApiService } from "../utils";
import { BasicFormDataService, EditingService } from "../_services";
import { Step, AppForm, Fund, CreateEditingRequest } from '../_models';
import { AgentService } from '../agent';
import { Observable } from 'rxjs';


@Injectable()
export class GenerateMandatDeGestionService extends BasicFormDataService<Step<AppForm>>{

    constructor(public agentService:AgentService, $http: Http, messageService: MessageService, protected api: ApiService, protected editingService: EditingService) {
        super($http, messageService, api);
        this.domain = 'GenerateMandatDeGestionService';  
    }
    
    generateMandatDeGestion(fund: Fund, a: AppForm) {
        return this.editingService.generateMandatDeGestion(fund, a);
    }

    applyManagementMandateDocRequest(request: CreateEditingRequest): Observable<any> {        
        return this.editingService.applyManagementMandateDocRequest(request);
    }
}
