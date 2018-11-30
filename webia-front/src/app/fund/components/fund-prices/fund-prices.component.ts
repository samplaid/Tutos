import { Component, OnInit, Input } from '@angular/core';
import { FundValuationSearchCriteria } from "../../../search/fund/fund-valuation-search-criteria"
import { Page } from "../../../_models/page";
import { FundService, FundPriceSearchCriteria } from '../../fund.service';
import { Fund, defaultPageSize } from '../../../_models';

const BID = 1;

@Component({
    selector: 'fund-prices',
    templateUrl: './fund-prices.tpl.html'
})
export class FundPricesComponent implements OnInit {
    pricesBusy: any;   
    fundPriceMaxDate = new Date((new Date()).toDateString());
    pricesPage: Page;

    _fundId;

    @Input() set fundId(value:string){
        if (value){
            this._fundId = value;
            this.searchFundPrices(value, 1 ,this.fundPriceMaxDate);
        } 
    };
    get fundId(){
        return this._fundId;
    }
    @Input() mode;
    
    constructor(protected fundService: FundService) { }

    

    ngOnInit() {  }

    /**
     * A function which handle a fund price.
     * @param page the page.
     */
    searchFundPrices(fundId:string, page: number, date:Date): void {
        if (fundId){
            let searchCriteria = new FundPriceSearchCriteria(page || 1, defaultPageSize / 2);
            searchCriteria.fdsId = fundId;
            searchCriteria.date = date;
            searchCriteria.types = [BID];
            this.pricesBusy = this.fundService.searchLastFundPricesBefore(searchCriteria).then(pageItem => {
                this.pricesPage = (!!pageItem) ? pageItem : this.pricesPage;
            }, e => { });
        }
    }

}