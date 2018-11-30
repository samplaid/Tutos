
import { Title } from '@angular/platform-browser';
import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from "rxjs/Observable";

import { FundService } from '../fund.service';
import { CurrencyService, UoptDetailService, WebiaService } from '../../_services';
import { days, PricingFrequency } from '../../_models';
import { AgentService } from '../../agent';
import { MessageService, UserService } from "../../utils";

import { FundComponent } from '../fund.component';
import { FundType } from '../_partial/fund-type';


@Component({
    selector: 'external-fund',
    templateUrl: 'external-fund.component.html',
    styles:[`
        .fund.fe .pricing {
            margin: auto 8%;
        }
    `]
})

export class ExternalFundComponent extends FundComponent {        
    pricingDays: {id: number, label: string}[];
    priceDelayList: any[];
    fwdPriceReportDaysList: any[];
    

    constructor(protected route: ActivatedRoute, protected router: Router,
        protected fundService: FundService, protected currencyService: CurrencyService,
        protected uoptDetailService: UoptDetailService, protected agentService: AgentService,
        protected messageService: MessageService, protected userService: UserService, protected titleService: Title, protected webiaService: WebiaService) {
            super(route,router,fundService,currencyService,uoptDetailService,agentService,messageService,userService,titleService, webiaService );
    }

    initFund() {        
        super.initFund();      
        this.fund.fundType = 1;
        this.fund.fundSubType = FundType.FE.key; 
        this.fund.pricingFrequency = PricingFrequency.DAILY_FWD_PRICED; // Daily Historical
    }

    ngOnInit(): void {
        super.ngOnInit();
        this.pricingDays = days;
        this.priceDelayList = [];
        this.fwdPriceReportDaysList = [];        
        this.subs.push(Observable.range(1, 31).subscribe(res => { this.priceDelayList.push(res); this.fwdPriceReportDaysList.push(res); }));
    }

    setUcits(event){        
        if(this.fund) {
            const DEFAULT_CAA:number = 100;
            this.fund.ucits = (event.target.value == 'true');
            if(!this.fund.fdsId && this.fund.ucits){
                this.fund.maxAllocationPercent = DEFAULT_CAA;
                this.fund.maxAllocationA = DEFAULT_CAA;
                this.fund.maxAllocationB = DEFAULT_CAA;
                this.fund.maxAllocationC = DEFAULT_CAA;
                this.fund.maxAllocationD = DEFAULT_CAA;
                this.fund.maxAllocationN = DEFAULT_CAA;
            }
        }
    }

    isAllCAAEmpty(){
        return !!this.fund && (!this.fund.maxAllocationPercent 
                            && !this.fund.maxAllocationA
                            && !this.fund.maxAllocationB
                            && !this.fund.maxAllocationC
                            && !this.fund.maxAllocationD
                            && !this.fund.maxAllocationN)
    }

}