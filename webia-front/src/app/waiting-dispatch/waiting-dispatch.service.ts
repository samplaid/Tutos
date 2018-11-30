import { Http } from "@angular/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs/Observable";

import { HttpService, MessageService, ApiService } from "../utils";
import { BasicFormDataService } from "../_services";
import { Step, AppForm } from "../_models";
import { AgentService } from '../agent';


@Injectable()
export class WaitingDispatchService extends BasicFormDataService<Step<AppForm>>{

    constructor(public agentService:AgentService, $http: Http, messageService: MessageService, protected api: ApiService) {
        super($http, messageService, api);
        this.domain = 'WaitingDispatchService';  
    }
    
    save(step: Step<AppForm>, complete?: boolean): Observable<Step<AppForm>> {
        return super.save(step, complete);
    }
}