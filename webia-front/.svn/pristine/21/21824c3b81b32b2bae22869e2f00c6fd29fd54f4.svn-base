import { CheckStep, AppForm } from '../index';



export class Step<T> {
    stepId: number;
    stepWorkflow: string;
    workflowItemTypeId: number;
    workFlowStatus: number;
    checkSteps: CheckStep[];
    formData: T;
    mode?:string;
    updatable?:boolean;
    stepRejectable?:boolean;
    stepAbortable?:boolean;

    workflowItemId?:number;
    polId?:string;
    applicationForm?:string;
    productName?:string;
    productCd?:string;
    productCountryCd?:string;
    firstCpsUser?: string;
    secondCpsUser?: string;
    assignedTo?: string;

    errors?:string[];

    rejectReason?:string; 

	 // if something else
	 [others: string]: any;


}