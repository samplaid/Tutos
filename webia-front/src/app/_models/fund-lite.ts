
export class FundLite {

    fdsId?:string;
	currency?:string;
	name?:string;
	fundType?:number;
	fundSubType?:string;
	depositBank?:string;
	assetManager?:string;
	assetManagerFee?:number;
	finFeesFlatAmount?:number;
	financialAdvisor?:string;	
	performanceFee?: string;
	finAdvisorFee?:number;
	bankDepositFee?:number;
	depositBankFlatFee?:number;	
	assetManFeeCcy?: string;	
	bankDepFeeCcy?: string;
	broker?:string;
	status?:number;
	isValorized?: boolean; // boolean used to know if it is a new Fund not valorized yet
	iban?:string;
	accountRoot?:string;
	privateEquity?: boolean;
	poa?: boolean;
	pool?: boolean;
	isinCode?:string;
	displayName?:string;	
	salesRep?: string;	
	pricingFrequency?: number;
	exAssManNotes?: String;
	exDepBankNotes?: String;
	exRiskProfileNotes?: String ;
	exAllInFees?: boolean;	
	fwdPriceReportDays?: number;
	exDepositBankContact?: string; //aka payment contact
}
