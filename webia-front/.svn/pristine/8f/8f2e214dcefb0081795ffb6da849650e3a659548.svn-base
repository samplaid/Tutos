import { LoadableComponent } from '@workflow/components/loadable.component';
import { Component, OnInit, Input, SimpleChanges } from '@angular/core';
import { fundClassifierRegExp } from '@models/constant';

import * as _ from "lodash";

import { paymentAnimation } from '@workflow/models/animations';
import { PolicyValuation } from '@models/policy/policy-valuation';
import { FundTransactionForm } from '../../../withdrawal/models/withdrawal-fund-transaction';
import { InputTypes } from '../../../withdrawal/models/input-type'; //TODO move this models to a higher level.

@Component({
    selector: 'fund-transaction',
    templateUrl: 'fund-transaction.tpl.html',
    styles:[`
        .row-details {
            margin-top: 3px;
        }
        .fund-transaction-table td {
            position: relative
        }
        .fund-transaction-table td .cell-input {
            position:absolute;
            text-align: center;
            bottom: 0;            
            margin-bottom: 0.8em;
            margin-right: 0.8em;
        }
    `],
    animations: [paymentAnimation]
})
export class FundTransactionComponent extends LoadableComponent implements OnInit {
    
    readonly fundNameRegXp: RegExp = fundClassifierRegExp;
    @Input() mode: string;
    @Input() showSectionTitle: boolean = true;
    @Input() specificAmountByFund: boolean;
    @Input() fundTransactions: FundTransactionForm[] = [];
    @Input() policyCurrency :string;
    @Input() showInputs: boolean;
    @Input() set valuation(value: PolicyValuation) {
        if(value && value.holdings) {
            this.groupedHoldings = _.groupBy(value.holdings, transfer => transfer.fundId);
            
            if(!this.fundTransactions || this.fundTransactions.length == 0) {
                this.fundTransactions = [];
                for(let holdingFundId in this.groupedHoldings) {
                    this.fundTransactions.push({fundId:holdingFundId, fundTransactionFormId: 0})
                }
            }

            this.totalPolicyCurrency = value.totalPolicyCurrency;
            this.calculateShare();
            this.containsFidOrFas = this.fundTransactions.some(fundTransactionForm => fundTransactionForm.fundTp == 'FID' || fundTransactionForm.fundTp == 'FAS');
        }
    }
    readonly inputTypes = InputTypes;
    readonly Object = Object;
    groupedHoldings;
    totalPolicyCurrency: number;
    containsFidOrFas:boolean = false;

    constructor() { 
        super();
    }

    ngOnInit(): void {

    }

    ngOnChanges(changes: SimpleChanges) {
        if(changes['specificAmountByFund']) {
            if(changes['specificAmountByFund'].currentValue) {
                for(let fundTransaction of this.fundTransactions) {
                    fundTransaction.split = null;
                }
            } else {
                for(let fundTransaction of this.fundTransactions) {
                    fundTransaction.type = null;
                    fundTransaction.units = null;
                    fundTransaction.percentage = null;
                }
            }
        }
    }

    /**
     * Clean the other values of a fund transaction form when type changes.
     * 
     * @param selectedType the selected type
     * @param fundTransaction the fundTransaction to update
     */
    onTypeChange(fundTransaction: FundTransactionForm): void {
        switch(fundTransaction.inputType) {
            case 'GROSS_AMOUNT':
                fundTransaction.units = null;
                fundTransaction.percentage = null;
                break;
            case 'UNITS':
                fundTransaction.amount = null;
                fundTransaction.percentage = null;
                break;
            case 'ALL_FUND':
                fundTransaction.amount = null;
                fundTransaction.units = null;
                fundTransaction.percentage = null;
                break;
            case 'PERCENTAGE':
                fundTransaction.amount = null;
                fundTransaction.units = null;
                break;
        }
    }

    calculateShare(): void {
        this.fundTransactions.forEach(fundTransaction => {
            const holdingValue = this.getHoldingValue(fundTransaction);
            const relativeShare = !!this.totalPolicyCurrency ? ( holdingValue / this.totalPolicyCurrency) * 100 : 0;
            fundTransaction.relativeShare = Number(relativeShare.toFixed(2));
        });
    }

    private getHoldingValue(fundTransaction: FundTransactionForm) {
        if(this.groupedHoldings[fundTransaction.fundId]) {
            return this.groupedHoldings[fundTransaction.fundId][0].holdingValuePolicyCurrency || 0;
        }
        return 0;
    }
}