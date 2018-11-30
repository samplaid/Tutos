export class Transaction {
	effectiveDate : any;	
	eventName: string;
	eventType: number;
	grossAmount: number;
	netAmount: number;
	feeAmount: number;
	taxAmount: number;	
	status: string;	
	statusCode: number;
	currency: string;	
	coverage: number;
	frenchTaxable: boolean;
	eventCanBeReported: boolean;
	eventDateEligible: boolean;
	lastTrnId: number;
	policyClientCountry: string;

	event?:string; // use to wrap events
	[other: string]: any;
}