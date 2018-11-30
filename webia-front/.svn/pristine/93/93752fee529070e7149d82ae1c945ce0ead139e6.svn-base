import { Http } from "@angular/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs/Observable";

import { HttpService, MessageService, ApiService, HandleErrorOptions, DateUtils } from "../utils";


@Injectable()
export class ExchangeRateService extends HttpService{

    // caching data
    dataStore;   

    constructor($http: Http, messageService: MessageService, protected api: ApiService) {
        super($http, messageService);
        this.domain = 'ExchangeRateService';  
        this.initStore();
    }

    initStore(){
        this.dataStore = Object.assign({});
    }

    functionWithStore( storeName:string, urlName:string, ...args){        
        if (!this.dataStore[storeName] ) {
            this.dataStore[storeName] = this.GET(this.api.getURL(urlName, ...args),urlName).publishReplay(1).refCount();
        }
        return this.dataStore[storeName];
    }

    /**
     *  Get the list of all the currencies linked to the current user
     *  @return : the collection of currencies
     */
    getCurrencies(){
        
    }
    
    getAmountInCurrency(amount:number, fromCurrency:string, toCurrency:string, date:Date): Promise<any>{  
        let dateString = DateUtils.formatToIsoDate(date ||  new Date());
        return this.functionWithStore(amount+fromCurrency+toCurrency+dateString, 'getAmountInCurrency', amount, fromCurrency, toCurrency, dateString).toPromise();
    }
}


