import { Http, Response }                           from '@angular/http';
import {Injectable}                                 from '@angular/core';
import {Observable}                                 from 'rxjs/Observable';
import { ApiService, MessageService, HttpService }  from '../utils';
import { Page, SearchPolicyCriteria } from './../_models';
import { ClientSearchCriteria } from "./client/client-search-criteria";
import { FundSearchCriteria } from "./fund/fund-search-criteria";
import { AgentSearchCriteria } from "./agent/agent-search-criteria";


@Injectable()
export class SearchService extends HttpService {    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {        
        super($http,  messageService);
        this.domain = 'SearchService';  
    }

    searchPolicies(searchCriteria:SearchPolicyCriteria): Observable<Page>{
        return this.POST(this.api.getURL('searchPolicies'),searchCriteria,'searchPolicies');
    }

    searchClient(searchCriteria: ClientSearchCriteria): Observable<Page> {
        return this.POST(this.api.getURL('searchClient'), searchCriteria, 'searchClient');
    }

    advanceSearch(criteria: FundSearchCriteria) : Observable<Page>{
        return this.POST(this.api.getURL('advanceSearch'), criteria, 'advanceSearch');
    }

    searchAgent(searchCriteria:AgentSearchCriteria): Observable<Page>{
        return this.POST(this.api.getURL('searchAgent'),searchCriteria,'searchAgent');
    }

    getAgent(id:string){
        return this.GET(this.api.getURL('getAgent', id.trim()),'getAgent').toPromise().catch(err => {});
    }

}
