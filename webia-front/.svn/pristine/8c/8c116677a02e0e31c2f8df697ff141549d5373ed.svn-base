import { Http, Response }                           from '@angular/http';
import { ApiService, MessageService, HttpService }  from '../utils';
import {Injectable}                                 from '@angular/core';
import {Observable}                                 from 'rxjs/Observable';
import { ReplaySubject } from "rxjs/ReplaySubject";
import { OptionDetail } from '../_models/option';


@Injectable()
export class OptionDetailService extends HttpService {

    // caching data
    dataStore;        
    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {        
        super($http,  messageService);
        this.domain = 'OptionDetailService';
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

    getLives(productId:string){
        if (productId) productId = productId.toLocaleUpperCase();
        return this.GET(this.api.getURL('getLives', productId), 'getLives');
    }

    getAllLives(){
        return this.functionWithStore('allLives', 'getAllLives');
    }

    getCPRRoles(){
        return this.functionWithStore('CPRRoles', 'getCPRRoles');
    }

    getPaymentModes(){
        return this.functionWithStore('getPaymentModes', 'getPaymentModes');
    }

    getAccountStatus() : Observable<OptionDetail[]> {
        return this.functionWithStore('getAccountStatus', 'getAccountStatus');
    }

}