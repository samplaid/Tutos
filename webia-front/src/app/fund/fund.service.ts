import { Http }                           from '@angular/http';
import { ApiService, MessageService, HttpService, AbstractSearchCriteria, HandleErrorOptions, StateMode, DateUtils } from '../utils';
import {Injectable}                                 from '@angular/core';
import {Observable}                                 from 'rxjs/Observable';
import { FundValuationSearchCriteria } from "../search/fund/fund-valuation-search-criteria"

import { Fund, Page, FundForm } from '../_models'

@Injectable()
export class FundService extends HttpService {

    // caching data
    dataStore; 

    constructor($http: Http, messageService: MessageService, private api: ApiService) {        
        super( $http,  messageService);
        this.domain = 'FundService';
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

    loadFund(id:string): Observable<Fund>{
        if (!id){
            return Observable.of(null);
        }
        return this.GET(this.api.getURL('loadFund', id.trim()),'loadFund'); 
    }

    createFund(fund:Fund){
        return this.POST(this.api.getURL('createFund'),fund,'createFund', new HandleErrorOptions(true, true,'fundCpt'));
    }

    updateFund(fund:Fund){
        return this.POST(this.api.getURL('updateFund'),fund,'updateFund', new HandleErrorOptions(true, true,'fundCpt'));
    }

    /**
     * Create or update the fund depending the mode in paramater.
     * @param mode 
     * @param fund 
     */
    save(mode: string, fund: Fund): Observable<Fund> {
        let save$: Observable<Fund> = Observable.empty();
        this.validateAllDates(fund);
        this.removeIbanSpace(fund.iban);
        
        this.messageService.clearAlert('fundCpt');

        if (mode === StateMode.create){
            save$ = this.createFund(fund).do(fund => {                 
                if(fund.status === 0) {
                    this.messageService.addAlertSuccess("The new fund has been successfully created but not yet enabled.", false,'fundCpt'); // this.messageService.success("The new fund has been successfully created but not yet activated.","Fund creation","sm");      
                } else if(fund.status === 1) {
                    this.messageService.addAlertSuccess("The new fund has been successfully created and enabled.", false,'fundCpt'); //this.messageService.success("The new fund has been successfully created and activated.","Fund creation","sm");
                }
            });
        } else if (mode === StateMode.update){
            save$ = this.updateFund(fund).do(fund => {
                this.messageService.addAlertSuccess( "Fund has been updated successfully and is "+(fund.status==1?"enabled.":"disabled."),false,'fundCpt');  
            });
        }

        return save$;
    }

    public validateDate(date: Date): Date {
        let dateStr = DateUtils.formatToddMMyyyy(new Date(date),"/");
        return (DateUtils.WEALINS_NULL_DATE_SHORT_DATE == dateStr) ? null : date;
    }

    public validateAllDates(fund: Fund): void {
        fund.poaDate = this.validateDate(fund.poaDate);
        fund.pocDate = this.validateDate(fund.pocDate);
        fund.riskProfileDate = this.validateDate(fund.riskProfileDate);
    }

    public removeIbanSpace(iban: string): string {
        let ibanCopy: string = iban;
        if (ibanCopy) {
            ibanCopy = ibanCopy.replace(/[\s+]/g, '');
        }
        return ibanCopy;
    }

    /**
     * @deprecated: move to search.service
     * @param criteria 
     */
    searchFund(criteria: FundSearchCriteria) : Observable<Page>{
        return this.POST(this.api.getURL('searchFund'), criteria, 'searchFund');
    }

    searchFundValuation(searchCriteria: FundValuationSearchCriteria) {
        return this.POST(this.api.getURL('searchFundValuation'), searchCriteria, 'searchFundValuation');
    }

    searchLastFundPricesBefore(searchCriteria: FundPriceSearchCriteria):Promise<Page> {
        if (JSON.stringify(searchCriteria) == JSON.stringify(this.dataStore.fundPriceCriteria)){
             this.dataStore.fundPriceCriteria = searchCriteria;
             return Promise.resolve(Object.assign({},this.dataStore.fundPrice) );
        }
        this.dataStore.fundPriceCriteria = searchCriteria;
        return this.POST(this.api.getURL('searchLastFundPricesBefore'), searchCriteria, 'searchLastFundPricesBefore')
                        .map(data => {this.dataStore.fundPrice = data; return data}).toPromise();

    }

    validateFund(fund:Fund){
        return this.POST(this.api.getURL('validateFund'),fund,'validateFund', new HandleErrorOptions(true, true));
    }

    canAddFundValuationAmount(fundId: string, valuationDate: Date, priceType: number): Observable<boolean> {
        return this.GET(this.api.getURL('canAddFIDorFASFundValuationAmount', fundId, DateUtils.formatToIsoDate(valuationDate), priceType), 'canAddFIDorFASFundValuationAmount' );
    }

    updateValuations(fundForms: FundForm[], valuationDate: Date): void {
        if(!fundForms) {
            return;
        }
        fundForms.forEach((fundForm: FundForm) => {
            this.canAddFundValuationAmount(fundForm.fundId, valuationDate, 1)
            .subscribe(addOn => {
                if(fundForm.fund && fundForm.fund.isValorized) {
                    fundForm.addOnValuableAmount = (addOn === true) // becuase it may returns null object;
                }
            });
        });
    }
  
    validateFunds(fdsIds: string[]){
        return this.POST(this.api.getURL('validateFunds'),fdsIds,'validateFunds', new HandleErrorOptions(true, true));
    }


}

/**
 * This class contains the fund search criteria properties
 */
export class FundSearchCriteria extends AbstractSearchCriteria {
    type: string;
    brokerId: string;
    onlyBroker: boolean;
}

export class FundPriceSearchCriteria extends AbstractSearchCriteria {
    fdsId: string;
    date : Date;
    types: number[];
    constructor(pageNumber: number, pageSize: number){
        super();
        this.pageNum = pageNumber;
        this.pageSize = pageSize;
        this.types = [];
    }
}
