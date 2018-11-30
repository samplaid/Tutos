import { Injectable } from '@angular/core';
import { OperationEntrySearchCriteria } from "../_models/index";
import { HttpService, MessageService, ApiService, Store } from "../utils/index";
import { Http } from "@angular/http";
import { Observable } from 'rxjs/Observable';
import { PolicyService } from '../policy/services/policy.service';

@Injectable()
export class OperationEntryService extends HttpService {

    openStatus = ['NEW', 'DRAFT', 'CREATED', 'IN_FORCE'];

    constructor($http: Http, messageService: MessageService, private api: ApiService, private store: Store) {        
        super( $http,  messageService);
        this.domain = 'OperationEntryService';
    }

    //TODO : Thoses functions should be rewrite in a signle way (not one in webia-app, the other in webia-service with a list of opened status,... )


    searchOperationEntry(searchCriteria: OperationEntrySearchCriteria) {
        searchCriteria.status = this.openStatus;
        return this.GET(this.api.getURL('searchOperationEntry', searchCriteria.fundId, searchCriteria.clientId, searchCriteria.pageNum, searchCriteria.pageSize, null, searchCriteria.status),'searchOperationEntry');
    }

    getOperationForPartner(agtId:string, category:string) {
        return this.GET(this.api.getURL('getOperationForPartner', agtId, category),'getOperationForPartner');
    }
    
    getOperationForPolicy(policyId:string) {
        return this.GET(this.api.getURL('getOperationForPolicy', policyId),'getOperationForPolicy');
    }

}