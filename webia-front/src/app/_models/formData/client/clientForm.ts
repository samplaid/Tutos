import { DisplayableName } from "../../displayable-name";

export class ClientForm implements DisplayableName {

    clientFormId?:number;
    clientId?:number;
    clientRelationTp?:number;  // 1,2,3=policyHolder  4,5,6=insured   24=usufructuary  .... 
    formId?:number;
    addnFactor?:number;
    addnRpm?:number;
    ageRating?:number;
    split: number;
    rankNumber: number; 
    displayName?: string;
    dead: boolean;

    creationUser?: string;
    creationDt?: Date;
    updateUser?: string;
    updateDt?: Date;

    constructor(formId: number, clientRelationTp: number) {
        this.formId = formId;
        this.clientRelationTp = clientRelationTp;
    }

}