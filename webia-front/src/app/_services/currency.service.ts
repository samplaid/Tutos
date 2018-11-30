import { Http }                           from '@angular/http';
import { ApiService, MessageService, HttpService }  from '../utils';
import {Injectable}                                 from '@angular/core';
import {Observable}                                 from 'rxjs/Observable';
import { Currency } from '../_models';


@Injectable()
export class CurrencyService extends HttpService {

    // caching data
    dataStore;        
    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {        
        super( $http,  messageService);
        this.domain = 'CurrencyService';
        this.initStore();
    }

    initStore(){
        this.dataStore = Object.assign({});
    }

    functionWithStore( storeName:string, urlName:string){        
        if (!this.dataStore[storeName] ) {
            this.dataStore[storeName] = this.GET(this.api.getURL(urlName), urlName).publishReplay(1).refCount();
        }
        return this.dataStore[storeName];
    }

    /**
     *  Get the list of all the currencies linked to the current user
     *  @return : the collection of currencies
     */
    getCurrencies(): Observable<Currency[]>{
        return this.functionWithStore('getCurrencies', 'getCurrencies');
    }

}