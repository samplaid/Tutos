import { Http, Response }                           from '@angular/http';
import { ApiService, MessageService, HttpService }  from '../utils';
import { Injectable }                                 from '@angular/core';
import { Observable }                                 from 'rxjs/Observable';


@Injectable()
export class AppliParamService extends HttpService {

    // caching data
    dataStore;        
    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {        
        super($http,  messageService);
        this.domain = 'AppliParamService';
        this.initStore();
    }

    initStore(){
        this.dataStore = Object.assign({});
    }

/*
     ** CODE **	         ** DESCRIPTION **
    CPS_ID_GROUP	    Id workflow group for CPS
    EXEMPT_CPR_TYPE	    Rôles client à exclure
    FOYER_AGENT_ID	    Agent id for the Foyer broker
    PRODUCT_CAPI	    Liste des produits de capitalisation
*/

    functionWithStore( storeName:string, urlName:string, ...args){        
        if (!this.dataStore[storeName] ) {
            this.dataStore[storeName] = this.GET(this.api.getURL(urlName, ...args),urlName).publishReplay(1).refCount();
        }
        return this.dataStore[storeName];
    }

    getApplicationParameter(code:string){
        return this.functionWithStore(code, 'getApplicationParameter',code);
    }

 
}