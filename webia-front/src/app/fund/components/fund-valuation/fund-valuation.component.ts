import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { FundValuationSearchCriteria } from "../../../search/fund/fund-valuation-search-criteria"
import { Page } from "../../../_models/page";
import { FundService, FundPriceSearchCriteria } from '../../fund.service';
import { Fund, defaultPageSize } from '../../../_models';

@Component({
    selector: 'fund-valuation',
    templateUrl: './fund-valuation.tpl.html'
})
export class FundValuationComponent implements OnInit, OnChanges {
    valuationBusy: any;
    page: Page;
    
    @Input() mode: string;
    @Input() fund: Fund;

    
    constructor(protected fundService: FundService) { }

    ngOnChanges(changes: SimpleChanges ) {
        if(changes.fund && changes.fund.currentValue && changes.fund.currentValue.fdsId) {
            this.searchFundValuation(1);
        }
    }

    ngOnInit() {
     }

     /**
     * A function which handle a fund valuation.
     * @param page the page.
     */
    searchFundValuation(pageNum): void {
        let searchCriteria = new FundValuationSearchCriteria(pageNum || 1, defaultPageSize / 2);        
        searchCriteria.fdsId = this.fund.fdsId;
        this.valuationBusy = this.fundService.searchFundValuation(searchCriteria).subscribe(pageItem => {
            this.page = (!!pageItem) ? pageItem : this.page;
        }, e => { });
        
    }   

}