import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { ApiService } from './api.service';
import { HttpService } from './http.service';
import { ReplaySubject } from 'rxjs/ReplaySubject';
import { StoreName } from './store-name';

@Injectable()
export class Store {
    private dataContainer$: Map<string, ReplaySubject<any>> = new Map<string, ReplaySubject<any>>();
    
    constructor(private http: HttpService, 
                private api: ApiService) {
    }

    fetch(){      
        this.http.GET(this.api.getURL('getAllAgentCategories'), 'getAllAgentCategories').subscribe(agentCategories => {             
            this.store(StoreName.AGENT_CAT_LIST, agentCategories );
        });
    }

    store(key: string, payload?: any) {
        if(key) {   
            if(!this.dataContainer$.has(key)) {
                let replay = new ReplaySubject<any>(1);
                this.dataContainer$.set(key, replay);
                replay.next(payload);
            } else {
                this.dataContainer$.get(key).next(payload);
            }            
        }
    }

    storeObservable(key: string, observable: Observable<any>){
        if(key){
            if(!this.dataContainer$.has(key)) {
                let replay = new ReplaySubject<any>(1);
                this.dataContainer$.set(key, replay);
                observable.subscribe(data => replay.next(data));
            }
        }
    }

    remove(key: string): boolean {
        return key && this.dataContainer$.has(key) && this.dataContainer$.delete(key);
    }

    clear(){
        this.dataContainer$.clear();
    }

    select(key: string):  Observable<any> {
        return !key ? Observable.empty() : this.dataContainer$.get(key).asObservable();
    }
}

