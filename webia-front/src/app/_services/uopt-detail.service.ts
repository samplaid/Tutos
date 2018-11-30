import { Http, Response }                           from '@angular/http';
import { ApiService, MessageService, HttpService }  from '../utils';
import {Injectable}                                 from '@angular/core';
import {Observable}                                 from 'rxjs/Observable';


@Injectable()
export class UoptDetailService extends HttpService {

    // caching data
    dataStore;        
    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {        
        super($http,  messageService);
        this.domain = 'UoptDetailService';
        this.initStore();
    }

    initStore(){
        this.dataStore = Object.assign({});
    }


    functionWithStore( storeName:string, urlName:string){
        // if (!this.dataStore[storeName]) {
        //     return this.GET(this.api.getURL(urlName), urlName).do((data:any) => this.dataStore[storeName] = data)
        // 	.map((data) => this.dataStore[storeName]);
        // } else {
        //    return Observable.of(this.dataStore[storeName]);
        // }
        
        if (!this.dataStore[storeName] ) {
            this.dataStore[storeName] = this.GET(this.api.getURL(urlName), urlName).publishReplay(1).refCount();
        }
        return this.dataStore[storeName];
    }

    

    getCircularLetters(){
        return this.functionWithStore('circularLetters', 'getCircularLetters');
    } 

    getRiskClasses(){
        return this.functionWithStore('riskClasses', 'getRiskClasses');
    } 

    getFundClassifications(){
        return this.functionWithStore('fundClassifications', 'getFundClassifications');
    }

    getRiskProfiles(){
        return this.functionWithStore('riskProfiles', 'getRiskProfiles');
    }

    getRiskCurrencies(){
        return this.functionWithStore('riskCurrencies', 'getRiskCurrencies');
    }

    getInvestmentCategories(){
        return this.functionWithStore('investmentCategories', 'getInvestmentCategories');
    }

    getAlternativeFunds(){
        return this.functionWithStore('alternativeFunds', 'getAlternativeFunds');
    }

    getTypePOAs(){
        return this.functionWithStore('typePOAs', 'getTypePOAs');
    }

    getSendingRules(){
        return this.functionWithStore('sendingRules', 'getSendingRules');
    }

    getTitles(){
        return this.functionWithStore('getTitles', 'getTitles');
    }
    
    getClientProfiles(){
        return this.functionWithStore('getClientProfiles', 'getClientProfiles');
    }

    getClientComplianceRisks(){
        return this.functionWithStore('getClientComplianceRisks', 'getClientComplianceRisks');
    }

    getClientActivitySectors(){
        return this.functionWithStore('getClientActivitySectors', 'getClientActivitySectors');
    }

    getClientProfessions(){
        return this.functionWithStore('getClientProfessions', 'getClientProfessions');
    }

    getMaritalStatus(){
        return this.functionWithStore('getMaritalStatus', 'getMaritalStatus');
    }

    getLanguages(){
        return this.functionWithStore('getLanguages', 'getLanguages');
    }

    getEntityType(){
        return this.functionWithStore('getEntityType', 'getEntityType');
    }

    getPricingFrequencies(){
        return this.functionWithStore('getPricingFrequencies', 'getPricingFrequencies');
    }

    getCtxPricingFrequencies(){
        return this.functionWithStore('getCtxPricingFrequencies', 'getCtxPricingFrequencies');
    }

    getTypeOfAgentContact(){
        return this.functionWithStore('getTypeOfAgentContact', 'getTypeOfAgentContact');
    }

    getCrsStatus(){
        return this.functionWithStore('getCrsStatus', 'getCrsStatus');
    }

    getCrsExactStatus(){
        return this.functionWithStore('getCrsExactStatus', 'getCrsExactStatus');
    }
    getTypeOfControl(){
        return this.functionWithStore('getTypeOfControl', 'getTypeOfControl');
    }

    getAgentTitle() {
        return this.functionWithStore('getAgentTitle', 'getAgentTitle');
    }

    getDeathCauses() {
        return this.functionWithStore('getDeathCauses', 'getDeathCauses');
    }

    getPersonLinked(keyValue:string) {
         if (!this.dataStore['getDetailForKeyValue'] ) {
            this.dataStore['getDetailForKeyValue'] = this.GET(this.api.getURL('getDetailForKeyValue', keyValue), 'getDetailForKeyValue').publishReplay(1).refCount();
        }
        return this.dataStore['getDetailForKeyValue'];
    }

    toPricingLabel(pricingNumber: number, pricingFrequencies: any[]) {        
        const key = 'pricing' + pricingNumber;
        if(!this.dataStore[key]){
            let pricing = pricingFrequencies.find(pricing => pricing.number === pricingNumber);
            if(pricing && pricing.description) {
                this.dataStore[key] = pricing.description;
            } else {
                this.dataStore[key] = pricingNumber;
            }          
        }
        return this.dataStore[key];
    }

}