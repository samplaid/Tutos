import { Component, OnInit, Input } from '@angular/core';
import { Pricing } from "./pricing";
import { days } from "../../../_models";
import { Observable } from "rxjs/Observable";
import { UoptDetailService } from '../../../_services';
import { StateMode } from "../../../utils";

@Component({
    selector: 'pricing',
    templateUrl: './pricing.component.html'
})
export class PricingComponent implements OnInit {
    disabled: boolean; 
    pricingFrequencyList: any[];
    pricingDays: { id: number, label: string }[];
    fwdPriceReportDaysList: number[];
    priceDelayList: number[];
    tmpPricingDayOfMonth: number;
    tmpPricingDay: number;
    pricingFreq: number;
    
    @Input() pricing: Pricing;
    @Input() mode = StateMode.readonly;
    
    constructor(protected uoptDetailService: UoptDetailService) { }

    ngOnInit() { 
        this.pricingDays = days;
        this.priceDelayList = [];
        this.fwdPriceReportDaysList = [];
        this.uoptDetailService.getCtxPricingFrequencies().subscribe(res => this.pricingFrequencyList = res);
        this.fwdPriceReportDaysList = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31];
        this.priceDelayList = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31];
    }
    
    princingFrequencyChanged(pricingFrequency){
        if(!!this.pricing){
            switch (pricingFrequency) {
                case 2:
                case 9:                   
                    this.pricing.pricingDayOfMonth = undefined;
                    break;
                case 3:                    
                    this.pricing.pricingDay = undefined;
                    break;
                default:
                    this.pricing.pricingDayOfMonth = undefined;
                    this.pricing.pricingDay = undefined;
                    break;
            }
        }
        
    }
}