import { Http, Response }                           from '@angular/http';
import { ApiService, MessageService, HttpService }  from '../utils';
import {Injectable}                                 from '@angular/core';
import {Observable}                                 from 'rxjs/Observable';


@Injectable()
export class DeathCoverageService extends HttpService {

    // caching data
    dataStore;        
    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {        
        super($http,  messageService);
        this.domain = 'DeathCoverageService';
        this.initStore();
    }

    initStore(){
        this.dataStore = Object.assign({});
    }

    functionWithStore( storeName:string, urlName:string, ...args){
        if (!this.dataStore[storeName]) {
            return this.GET(this.api.getURL(urlName, ...args), urlName).do((data:any) => this.dataStore[storeName] = data)
        	.map((data) => this.dataStore[storeName]);
        } else {
           return Observable.of(this.dataStore[storeName]);
        }
    }

    /**
     * Get the death coverage terms available for a given product
     */
    getDeathCoverageClauses(productId:string){
        return this.GET(this.api.getURL('getDeathCoverageClauses', productId), 'getDeathCoverageClauses');
    }

    /**
     * Get the death coverage text for a given policy
     */
    getPolicyDeathCoverage(polId:string){
        return this.GET(this.api.getURL('getPolicyDeathCoverage', polId), 'getPolicyDeathCoverage').map((data) => data.clause);
    }
 
}