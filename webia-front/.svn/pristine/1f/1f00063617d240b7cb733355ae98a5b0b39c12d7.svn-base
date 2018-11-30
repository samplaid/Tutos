import { StateMode } from './../../../utils/mode';
import { Input, Component, OnInit } from '@angular/core';
import { ClientRoleActivationFlag, ClientLite, PolicyHolderForm, Product, year_term } from "../../../_models";

// List of the mode properties that can ben overriden
const overridable: string[] =  ['usufructuary', 'bareOwner', 'deathSuccessor', 'lifeSuccessor', 'search-client'];

@Component({
    selector: 'policy-holder-input',
    templateUrl: 'policy-holder-input.tpl.html'
})
export class PolicyHolderInputcomponent implements OnInit {

    constructor() {}

    @Input() policyHolders: PolicyHolderForm[] = [];
    activationFlag: ClientRoleActivationFlag;
    @Input() showDead: boolean = true;
    enablePolicyHolderRole: boolean;
    @Input() set policyHolderActivationFlag(activationFlag: ClientRoleActivationFlag) {
        this.activationFlag = activationFlag;
        this.enablePolicyHolderRole = activationFlag && (activationFlag.activatedSuccessionLife || activationFlag.activatedUsufructuary || activationFlag.activatedBareOwner || activationFlag.activatedSuccessionDeath);
    } 
    @Input() formId: number;

    /****    Modes management    ***/
    modes:object = {};
    private _mode: string;
    @Input() set mode(mode: string) {
        this._mode = mode;
        for(let key in overridable) {
            this.modes[overridable[key]] = mode;
        }

        // force the reapply of modeOverride when mode changes (usually on step change...)
        if(this._modesOverride != null) {
            Object.assign(this.modes, this._modesOverride);
        }
    }
    get mode() :string{
        return this._mode;
    }
    private _modesOverride: object;
    @Input() set modesOverride(modesOverride: object) {
        this._modesOverride = modesOverride;
        Object.assign(this.modes, modesOverride);
    }

    readonly stateMode = StateMode;
    showAdditional: boolean;
    tmpPH;  //TODO : should probably be removed.
    @Input() product: Product;
    @Input() term: string;
    readonly year_term = year_term;

    ngOnInit(): void {}

    addPolicyHolder(client: ClientLite): void {
        this.addClient(client);
    }

    private addClient(client: ClientLite) {        
        if (client) {
            let clientForm = this.createClientForm();
            clientForm.clientId = client.cliId;            
            this.policyHolders.push(clientForm);
            this.resetFlag();
        }
    }

    createClientForm(): PolicyHolderForm {
        let clientRelationTp =  (this.policyHolders.length < 2 ? this.policyHolders.length + 1 : 3);
        return new PolicyHolderForm(this.formId, clientRelationTp);
    }

    remove(client) {
        const index = this.policyHolders.findIndex(c =>  c.clientId == client.clientId && c.clientRelationTp==client.clientRelationTp);
        this.policyHolders.splice(index, 1);
    }

    private resetFlag() {
        this.showAdditional = false;
    }

    removeLast():void{
        this.showAdditional = true;
    }
}