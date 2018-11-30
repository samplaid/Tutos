import { StateMode } from './../../../utils/mode';
import { Input, Component, OnInit } from '@angular/core';
import { BeneficiaryForm, ClientRoleActivationFlag, ClientLite, ClientForm, CliOtherRelationShipRole } from "../../../_models";
import { OptionDetail } from '../../../_models/option';

@Component({
    selector: 'other-relations-input',
    templateUrl: 'other-relations-input.tpl.html'
})
export class OtherRelationsInputComponent implements OnInit {

    constructor() {}

    @Input() otherClients: ClientForm[];
    @Input() mode: string;
    @Input() formId: number; 
    @Input() cPRRoleList: OptionDetail[];
    readonly cessionSurrenderRole = CliOtherRelationShipRole.CESSION_SURRENDER_RIGTHS;
    readonly stateMode = StateMode;

    ngOnInit(): void {}

    addOtherClient(): void {
        this.otherClients.push(new ClientForm(this.formId,null));
    }

    removeIndex(index: number) {
        this.otherClients.splice(index,1);
    }
}