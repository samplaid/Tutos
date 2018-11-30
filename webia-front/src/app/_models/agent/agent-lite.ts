export class AgentLite {
    agtId: string;
    category: string;
    name: string;
    status: string;
    addressLine1 : string;
    addressLine2: string;
    addressLine3: string;
    addressLine4: string;
    country: string;
    postcode: string;
    town: string;
    telephone: string;
	fax: string;
	email: string;
	mobile: string;
	documentationLanguage: number;
	title: string;
    firstname: string;
    desiredReport?: number;
    paymentCommission?: number;
    paymentAccountBic?: string;
    paymentMethod?: string;
    statementByEmail?: boolean;
	centralizedCommunication?: boolean;

    [other: string] : any
}
