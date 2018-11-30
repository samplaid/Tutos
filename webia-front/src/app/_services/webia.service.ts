import { Http, Response }                           from '@angular/http';
import { ApiService, MessageService, HttpService }  from '../utils';
import {Injectable}                                 from '@angular/core';
import {Observable}                                 from 'rxjs/Observable';


@Injectable()
export class WebiaService extends HttpService {

    // caching data
    dataStore;        
    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {        
        super($http,  messageService);
        this.domain = 'WebiaService';
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

    getCodeLabel(code:string){
        return this.functionWithStore('CL_'+code, 'getCodeLabel',code);
    }

    getClientCodeLabels(){
        return this.getCodeLabel('CLIENT').toPromise().then( data =>  {
            let codeMapper:any[] = new Array<any>();
            if (data){
                data.forEach( cl => codeMapper[cl.code]=cl.label);
            }
            return codeMapper;
        }).catch(e=>{})
    }

 
}