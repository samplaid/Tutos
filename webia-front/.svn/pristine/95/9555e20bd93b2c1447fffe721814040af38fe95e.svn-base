import { Http }                           from '@angular/http';
import {Injectable}                                 from '@angular/core';
import { HttpService, MessageService, ApiService, WDatePipe } from '../../../utils';
import { FilterCriteria } from '../models/filter-crtieria';


@Injectable()
export class FilterService{    
    constructor(private wDatePipe: WDatePipe) { }
 
    accepts(candidate: any, filterCriteria: FilterCriteria, query: string): boolean {
        if(!query || !query.trim()) {
            return true;
        }
        if(!candidate) {
            throw Error("Can't filter null object");
        }
        if(!filterCriteria) {
            throw Error("Can't filter without criteria");
        }
        query = query.trim();
        for(let stringField of filterCriteria.stringAndNumberFields) {
            if(this.match(candidate[stringField], query)) {
                return true;
            }
        }
        for(let dateField of filterCriteria.dateFields) {
            if(this.match(this.wDatePipe.transform(candidate[dateField]), query)) {
                return true;
            }
        }
        return false;
    }

    private match(value: string|number, query: string): boolean {
        return !!value && ("" + value).toUpperCase().indexOf(query.toUpperCase()) != -1;
    }
}
