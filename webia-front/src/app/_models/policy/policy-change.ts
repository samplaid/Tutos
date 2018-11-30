import { AppliParamService } from '../../_services';

export class PolicyChange {

	id?:number;
	type?:string;  
	workflowItemId?:number;
	policyId?:string;
	status?:PolicyChangeStatus;
	cancelDate?:Date;
	dateOfChange?:Date;

    [other: string]: any;
}

export class PolicyChangeType {
    public static readonly CBN= <any>"Beneficiary Change";
    public static readonly CAP= <any>"Broker Change";
     
}

export enum PolicyChangeStatus {
    IN_FORCE = "Completed",
    PENDING = "Pending",
    TERMINATED = "Terminated"
}
