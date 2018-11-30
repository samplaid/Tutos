import {ClientForm} from './clientForm';
import { CliPolRelationship } from "../../index";

export class PolicyHolderForm extends ClientForm {
    usufructuary: boolean;
    bareOwner: boolean;
    deathSuccessor: boolean;
    lifeSuccessor: boolean;
    
    constructor(formId: number, clientRelationTp: number){
        super(formId, clientRelationTp);
    }

}