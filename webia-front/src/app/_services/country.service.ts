import { Injectable }                               from '@angular/core';
import { Http, Response }                           from '@angular/http';
import { ApiService, MessageService, HttpService }  from '../utils';
import {Observable}                                 from 'rxjs/Observable';
import { Country } from '../_models';

@Injectable()
export class CountryService extends HttpService {
    // caching data
    dataStore;

    constructor($http: Http, messageService: MessageService, private api: ApiService) { 
        super( $http,  messageService);
        this.domain = 'CountryService';
        this.initStore();
    }

    initStore(){
        this.dataStore = Object.assign({country : {}, countryXHR : {}});
    }

    /**
     * Lazy loads the list of the country.
     *  @return : the collection of countries
     */
    loadAllCountries(): Observable<Country[]>{
        if (!this.dataStore.countries) {
        	return this.GET(this.api.getURL('getCountries'), 'loadAllCountries').do((data:any) => this.dataStore.countries = data)
        	.map((data) => this.dataStore.countries);
        } else {
           return Observable.of(this.dataStore.countries);
        }
    }

    getCountry(ctyId: string) {
            if (!ctyId)
                return Observable.of(null);
            if (this.dataStore.country[ctyId])
                return Observable.of(this.dataStore.country[ctyId]);
            if (this.dataStore.countryXHR[ctyId])
                return this.dataStore.countryXHR[ctyId];

            this.dataStore.countryXHR[ctyId] = this.GET(this.api.getURL('getCountry', ctyId), 'getCountry').map((data) => {
                    this.dataStore.countryXHR[ctyId] = null;
                    this.dataStore.country[ctyId] = data;
                    return this.dataStore.country[ctyId];
                }).share()
            return this.dataStore.countryXHR[ctyId];
        }
    
}