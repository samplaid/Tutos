import {ClientForm} from './clientForm';

export class BeneficiaryForm extends ClientForm {
    irrevocable: boolean;
    separatePropertyRights: boolean;
    separatePropertyNoRights: boolean;
    acceptant: boolean; 
    usufructuary: boolean;
    bareOwner: boolean;
    isEqualParts?: boolean;

    // Those fields are dervied field. They do not use as json field
    partMode?: string;
    canShowEqualParts?: boolean;

    constructor(formId: number, clientRelationTp: number){
        super(formId, clientRelationTp);
    }
    [other: string]: any;
}