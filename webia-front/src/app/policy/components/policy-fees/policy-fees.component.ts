import { PolicyValuation } from './../../../_models/policy/policy-valuation';
import { Component, OnInit, Input } from '@angular/core';
import { fundClassifierRegExp } from '../../../_models/constant';

@Component({
    selector: 'policy-fees',
    templateUrl: 'policy-fees.tpl.html',
    styles: ['.fund-fees-table { margin-top: 0.4em;}']
})

export class PolicyFeesComponent implements OnInit {

    fundNameRegXp: RegExp;

    @Input() displayEntryFeesLine: boolean = true;
    @Input() contractCurrency: string;
    @Input() entryFees: number;
    @Input() brokerEntryFees: number;
    @Input() valuation: PolicyValuation;
    @Input() isFeesPercentage: boolean;    
    @Input() showSectionTitle: boolean = true;

    constructor() { }

    ngOnInit(): void {
        this.fundNameRegXp = fundClassifierRegExp;
    }
}