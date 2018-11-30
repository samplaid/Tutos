import { StateMode } from './../../../utils/mode';
import { Input, Component, OnInit } from '@angular/core';
import { BeneficiaryForm, ClientRoleActivationFlag, ClientLite, ClientForm } from "../../../_models";

@Component({
    selector: 'beneficiary-input',
    templateUrl: 'beneficiary-input.tpl.html'
})
export class BeneficiaryInputcomponent implements OnInit {

    constructor() {}

    @Input() beneficiaries: BeneficiaryForm[];
	@Input() benefActivationFlag: ClientRoleActivationFlag;
    @Input() formId: number;
    @Input() mode: string;
    @Input() benefTypeSuffix: string;
    readonly stateMode = StateMode;
    showAdditional: boolean;
    tmpBenef;//TODO : should probably be removed.
    ngOnInit(): void {}

    onRankChange(client: BeneficiaryForm): void {

        let beneficiariesGroupByRank = this.mapBeneficiariesByRank();
        beneficiariesGroupByRank.forEach((beneficiaries, rankNumber) => {
            if(beneficiaries.length > 1) {
                // show all the equal part component having the same rank with this client and leave part value as it is.
                let hasCheckedEqualPart = beneficiaries.some(beneficiary => beneficiary.isEqualParts === true && beneficiary.clientId != client.clientId);
                beneficiaries.forEach(beneficiary => {

                    // Do not show all equal part components that is grouped by an empty rankNumber.
                    if(rankNumber) {
                        beneficiary.canShowEqualParts = true;
                    } else {
                        beneficiary.canShowEqualParts = false;
                    }
                    
                    this.solvePart(hasCheckedEqualPart, beneficiary);                    
                });                               
            } else if(beneficiaries.length === 1){
                 // do not show this client equal part component, set equal part to null as initial value and leave part value as it is.
                beneficiaries[0].canShowEqualParts = false;
                beneficiaries[0].isEqualParts = null;
                //beneficiaries[0].partMode = this.enableMode(this.stepName);
            }
        });
    }

    mapBeneficiariesByRank(): Map<number, BeneficiaryForm[]> {
        let benefByRank: Map<number, BeneficiaryForm[]> = new Map<number, BeneficiaryForm[]>();
        this.beneficiaries.map(beneficiary => {
            if(benefByRank.has(beneficiary.rankNumber)){
                benefByRank.get(beneficiary.rankNumber).push(beneficiary);
            } else {
                benefByRank.set(beneficiary.rankNumber, [beneficiary]);
            }
        });
        return benefByRank;
    }

    solvePart(isEqualPart: boolean, beneficiary: BeneficiaryForm): void {
        if(isEqualPart) {
            beneficiary.isEqualParts = true; // same rank and checked
            beneficiary.split = null;
            //beneficiary.partMode = StateMode.readonly;
        } else {
            beneficiary.isEqualParts = false; // same rank but unchecked
            //beneficiary.partMode = this.enableMode(this.stepName);
        }
    }

    onEqualPartChange(source:BeneficiaryForm): void {        
            this.beneficiaries.filter(benef => benef.rankNumber === source.rankNumber)
                .forEach(beneficiary => this.solvePart(source.isEqualParts === true, beneficiary));  
        
    }

    remove(client) {
        const index = this.beneficiaries.findIndex(c =>  c.clientId == client.clientId && c.clientRelationTp==client.clientRelationTp);
        this.beneficiaries.splice(index, 1);
        this.onRankChange(client);
    }

    addBeneficiaries(client: ClientLite) : void {
        this.addClient(client);
    }
    
    private addClient(client: ClientLite) {        
        if (client){
            let clientForm = this.createClientForm(this.benefTypeSuffix == 'death' ? 'deathBeneficiaries' : 'lifeBeneficiaries');
            clientForm.clientId = client.cliId;            
            this.beneficiaries.push(clientForm);
            this.resetFlag();
        }             
    }

    createClientForm(type): BeneficiaryForm {
        return new BeneficiaryForm(this.formId, 7);
    }

    private resetFlag() {
        this.showAdditional = false;
    }

    removeLastbenef():void{
        this.showAdditional = true;
    }
}