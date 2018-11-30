import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Subscription } from "rxjs/Subscription";

import { Fund, AgentCategoryEnum, financialAdvisorAsRoles } from "../../../_models/index";
import { CurrencyService } from "../../../_services/currency.service";

@Component({
    selector: 'partialfas',
    templateUrl: './partialFas.component.html'
   
})
export class PartialFasComponent implements OnInit {
    readonly agentSearchTypes = financialAdvisorAsRoles;

    subs: Array<Subscription> = [];
    currencyList: any[];
    constructor(private currencyService: CurrencyService) { }

    @Output() modelChange = new EventEmitter<Fund>();
    
    @Input() disabled: boolean;
    @Input() defaultCurrency: string;
    @Input() fund;

    ngOnInit(): void {
        this.subs.push(this.currencyService.getCurrencies().subscribe(t => this.currencyList = t));        
    }

    ngOnDestroy() {
        this.subs.forEach((sub: Subscription) => sub && !sub.closed && sub.unsubscribe());
    }


}