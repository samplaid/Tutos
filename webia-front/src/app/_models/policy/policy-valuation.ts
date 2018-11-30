import { PolicyValuationHolding } from './policy-valuation-holding';

export interface PolicyValuation {
    policyId: string;
	date: Date;
	policyCurrency: string;
	otherCurrency: string;				
	totalPolicyCurrency: number;
	totalOtherCurrency: number;
	holdings: PolicyValuationHolding[];
}