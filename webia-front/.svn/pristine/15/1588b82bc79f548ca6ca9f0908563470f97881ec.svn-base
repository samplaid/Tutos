import { StateMode } from './../../../utils/mode';
import { Input, Component, OnInit } from '@angular/core';
import { PolicyValuation } from '../../../_models/policy/policy-valuation';
import { fundClassifierRegExp } from '../../../_models/constant';
import { PolicyValuationHolding } from '../../../_models/policy/policy-valuation-holding';

@Component({
    selector: 'policy-fund-fees-input',
    templateUrl: 'policy-fund-fees-input.tpl.html',
    styles: ['.fees-input {min-width:95px;}']
})
export class PolicyFundFeesInputComponent implements OnInit {

    constructor() {}

    @Input() mode: string;
    @Input() valuation: PolicyValuation;
    readonly stateMode = StateMode;
    fundNameRegXp: RegExp;

    ngOnInit(): void {
        this.fundNameRegXp = fundClassifierRegExp;
    }

    applyToAll(holding: PolicyValuationHolding): void {
        const commissionRate = holding.commissionRate;
        const feeRate = holding.feeRate;
        this.valuation.holdings.forEach(holding => {
            holding.feeRate = feeRate;
            holding.commissionRate = commissionRate;
        });
    }
}