import { DisplayableName } from '../displayable-name';

export class ClientLite implements DisplayableName {
    
    cliId: number;
    name?: string;
    firstName?: string;
    maidenName?: string;
    type?: number;
	status?: number;
    dateOfBirth?: string;
	addressLine1?: string;
	addressLine2?: string;	
	addressLine3?: string;	
	addressLine4?: string;	
	town?: string;	
	country?: string;	
	postCode?: string;	
	displayName?: string;

    // allow to add property dynamically.
	[other: string]: any;
}