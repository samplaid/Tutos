
import { StateMode } from './../../../utils/mode';
import { Input, Component, OnInit, Output,EventEmitter } from '@angular/core';
import { Observable } from 'rxjs/Observable';

@Component({
    selector: 'policy-premium-fees',
    templateUrl: 'policy-premium-fees.tpl.html'
})
export class PolicyPremiumFeesComponent implements OnInit {

    constructor() {}
    
    @Input() entryFeesPct: number;
    @Input() brokerEntryFeesAmt: number;
    @Input() brokerEntryFeesPct: number;
    @Input() entryFeesAmt: number;
    @Input() currencyList: any[];
    @Input() brokerAdminFeesPct: number;
    @Input() adminFeesPct: number;
    
    @Input() contractCurrency: string;
    @Input() taxRate?: number;
    @Input() showTax: boolean;
    @Input() hasBroker: boolean;
    @Input() expectedPremium?:number;

    isEntryFeesPCT: boolean;
    @Input() mode: "readOnly"|"waiting"|"edit"|"create";

    @Output() entryFeesPctChange = new EventEmitter<number>();
    @Output() brokerEntryFeesAmtChange= new EventEmitter<number>();
    @Output() brokerEntryFeesPctChange= new EventEmitter<number>();
    @Output() entryFeesAmtChange= new EventEmitter<number>();
    @Output() adminFeesPctChange = new EventEmitter<number>();
    @Output() brokerAdminFeesPctChange = new EventEmitter<number>();
    
    @Output() taxRateChange = new EventEmitter<number>();
    @Output() expectedPremiumChange= new EventEmitter<number>();
    readonly stateMode = StateMode;
    

    ngOnInit(): void {

        this.isEntryFeesPCT = (!isNaN(parseFloat('' + this.entryFeesAmt)) && this.contractCurrency && isNaN(parseFloat('' + this.entryFeesPct)))?false:true;
    }

    //TODO : delete this unused attribute : isEntryFeesPCT ?
        unityChange(unity){     
            this.isEntryFeesPCT = (unity=='PERCENT');
        }
        onEntryFeesPctChange(event: number): void {
            this.entryFeesPct = event;
            this.entryFeesPctChange.emit(event);
        }
        onBrokerEntryFeesAmtChange(event: number): void {
            this.brokerEntryFeesAmt = event;
            this.brokerEntryFeesAmtChange.emit(event);
        }
        onBrokerEntryFeesPctChange(event: number): void {
            this.brokerEntryFeesPct = event;
            this.brokerEntryFeesPctChange.emit(event);      
        }
        onEntryFeesAmtChange(event: number): void {
            this.entryFeesAmt = event;
            this.entryFeesAmtChange.emit(event);          
        }
        onAdminFeesPctChange(event: number): void {
            this.adminFeesPct = event;
            this.adminFeesPctChange.emit(event);          
        }
        onBrokerAdminFeesPctChange(event: number): void {
            this.brokerAdminFeesPct = event;
            this.brokerAdminFeesPctChange.emit(event);          
        }
        onExpectedPremiumChange(event: number): void {
            this.expectedPremium = event;
            this.expectedPremiumChange.emit(event);              
        }
        onTaxRateChange(event: number): void {
            this.taxRate = event;
            this.taxRateChange.emit(event);
        }
}