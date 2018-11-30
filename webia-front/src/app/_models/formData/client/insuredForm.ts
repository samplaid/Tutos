import {ClientForm} from './clientForm';
import { CliPolRelationship } from "../../index";

export class InsuredForm extends ClientForm {
    economicBeneficiary: boolean;

    constructor(formId: number, clientRelationTp: number){
        super(formId, clientRelationTp);
    }
}