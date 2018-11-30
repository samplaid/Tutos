import { Component, Input, EventEmitter, Output } from '@angular/core';
import { Subscription } from "rxjs/Subscription";
import * as _ from "lodash";
import { Fund, AgentContact, AgentLite } from '../../../_models/index';
import { CurrencyService } from "../../../_services/currency.service";
import { AgentService } from "../../../agent/agent.service";
import { FundLite } from '../../../_models/fund-lite';

/**
 * This component represents a partial of the FID component.
 */
@Component({
    selector: 'partialfid',
    templateUrl: './partialFid.tpl.html'
})

export class PartialFidComponent  {

    // The following filed must not be exposed out side of this class.
    // They are used are derived instance.
    protected managerAgent: AgentLite;
    protected depositBankAgent: AgentLite;

    busy;
    subs: Array<Subscription> = [];
    currencyList: any[];
    contacts: Array<AgentContact>;    

    @Output() modelChange = new EventEmitter<Fund>();

    @Input() disabled: boolean = false;
    @Input() fund: FundLite;
    @Input() defaultCurrency: string;

    constructor(private currencyService: CurrencyService, 
                private agentService:AgentService) { 
        this.subs.push(this.currencyService.getCurrencies().subscribe(t => this.currencyList = t));
    }

    /**
     * Set the selected asset manager to the current fund.
     * If the asset manager has been changed compared to the one previously selected,
     * empty the contact, fee or amount fee of the newly selected asset manage.
     * @param agent the asset manager
     */
    setManagerAgent(agent: AgentLite){
        if(this.fund && agent){
            //if select another agetn as Asset Manager then force to select a new contact and new fees
            if (this.fund.assetManager != agent.agtId ){                
                this.fund.salesRep = null;
                this.fund.assetManagerFee = null;
                this.fund.finFeesFlatAmount = null;
            }
            this.fund.assetManager = agent.agtId;
            this.managerAgent = agent;
        }
    } 
    
    /**
     * Assign the deposit bank to the fund.
     * @param agent the deposit bank
     */
    setDepositBankAgent(agent: AgentLite){
        if(this.fund && agent){            
            this.fund.depositBank = agent.agtId;
            this.depositBankAgent = agent;
        }
    }  

    ngOnDestroy() {        
        this.subs.forEach((sub: Subscription) => sub && !sub.closed && sub.unsubscribe());
    }
}