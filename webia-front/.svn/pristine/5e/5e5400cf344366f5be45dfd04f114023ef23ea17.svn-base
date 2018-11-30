import { FullAgent } from "./index";
import { FundLite } from './fund-lite';

export class Fund extends FundLite {
	
	fdsId?: string;
	pricingDay?: number;
	pricingDayOfMonth?: number;
	priceBasis?: number;
	bidOfferSpread?: number;
	unitTypes?: number;
	groupingCode?: string;
	createdBy?: string;
	createdDate?: Date;
	createdTime?: Date;
	createdProcess?: string;
	modifyBy?: string;
	modifyDate?: Date;
	modifyTime?: Date;
	modifyProcess?: string;
	pricingTime?: Date;
	accountingStream?: number;
	documentationName?: string;
	nlProduct?: string;
	cutOffTime?: Date;
	ulFundType?: string;
	maxAllocationPercent?: number;
	country?: string;
	navEntryFee?: number;
	navExitFee?: number;
	riskProfile?: string;
	riskCurrency?: string;
	investCat?: string;
	investObjective?: string;
	alternativeFunds?: string;
	riskProfileDate?: Date;
	notes?: string;
	pricingDelay?: number;
	investCashLimit?: number;
	poaType?: string;
	poaDate?: Date;
	fundClassification?: string;
	classOfRisk?: string;
	privateEquityFee?: number;
	finFeesMinAmount?: number;
	finFeesMaxAmount?: number;
	assetManRiskProfile?: boolean;
	poc?: Boolean;
	pocDate?: Date;
	pocType?: string;
	consultant?: string;
	financialAdvisorName?: string; //id+name
	finAdvisorFeeCcy?: string;
	depositAccount?: string;
	mandateHolder?: string;
	assetManagerName?: string;  //id+Name
	depositBankName?: string;	// id+name
	ucits?: boolean;
	maxAllocationA?: number;
	maxAllocationB?: number;
	maxAllocationC?: number;
	maxAllocationD?: number;
	maxAllocationN?: number;
	hasTransaction?: boolean;
	validatableFund?: boolean = true;

	//add-on
	assetManagerAgent?: FullAgent;
	financialAdvisorAgent?: FullAgent;
	depositBankAgent?: FullAgent;
	brokerAgent?: FullAgent;

	// if something else
	[others: string]: any;
}