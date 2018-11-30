import { Injectable } from '@angular/core';
import { Http, URLSearchParams } from "@angular/http";
import { HttpService, MessageService, ApiService, AbstractSearchCriteria, HandleErrorOptions } from "../utils/index";
import { Observable } from "rxjs/Rx";
import { Page } from "../_models/page";
import { FullClient, ClientUrlSearchParams, ClientClaimsDetail } from "../_models";

@Injectable()
export class ClientService extends HttpService {
    constructor($http: Http, protected messageService: MessageService, private api: ApiService) {
        super($http, messageService);
        this.domain = 'SearchClientService';
    }

    /**
     * @deprecated The search function will be moved to search.service.ts
     * @param searchCriteria 
     */
    searchClient(searchCriteria: ClientSearchCriteria): Observable<Page> {
        return this.POST(this.api.getURL('searchClient'), searchCriteria, 'searchClient');
    }

    getRolesByPolicies(id: number){
        return this.GET(this.api.getURL('getRolesByPolicies', id), 'getRolesByPolicies').toPromise().catch(err => { });
    }

    getClientLight(id: number) {
        return this.GET(this.api.getURL('getClientLight', id), 'getClientLight').toPromise().catch(err => { });
    }
    getClient(id: number) {
        return this.GET(this.api.getURL('getClient', id), 'getClient').toPromise().catch(err => { });
    }

    create(client: FullClient) {
        return this.POST(this.api.getURL('create'), client, 'create', new HandleErrorOptions(true, true,'clientCpt'));
    }

    update(client: FullClient) {
        return this.POST(this.api.getURL('update'), client, 'update', new HandleErrorOptions(true, true,'clientCpt'));
    }

    matchClient(searchParam: ClientUrlSearchParams) {
        if (searchParam) { 
            let params = new URLSearchParams();
            params.set("name", searchParam.name);
            params.set("birthday", searchParam.birthday);
            if(searchParam.exclude){
                params.set("exclude", searchParam.exclude);
            }            
            return this.getWithOptions(this.api.getURL('matchClient'), 'matchClient', { search: params }).toPromise();
        } else {
            return Observable.throw('Url parameter is required.').toPromise();
        }

    }

    canClientDeathBeNotified(id: number){
        return this.GET(this.api.getURL('canClientDeathBeNotified', id), 'canClientDeathBeNotified');
    }

    notifyDeath(ccd : ClientClaimsDetail){
        if (ccd && ccd.cliId){
            return this.POST(this.api.getURL('notifyDeath'), ccd,'notifyDeath', new HandleErrorOptions(true, true,'clientDeathClaimModal'));
        } else {
            return Observable.throw('ClientClaimsDetail.cliId is required.');
        }
    }

    getClientRoles(id: number) {
        return this.GET(this.api.getURL('getClientRoles', id), 'getClientRoles').toPromise().catch(err => { });
    }
    

}

//criteria object for the search service
export class ClientSearchCriteria extends AbstractSearchCriteria {
    date: string;  // represent the client birthday
    type: number;
    status: number;
    includeDeceased: boolean;
}