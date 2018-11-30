import { BeneficiaryForm, BenefClauseForm, ClientForm, FormData, PolicyHolderForm } from '../../_models';

export class BeneficiaryChangeForm extends FormData {
    changeDate?: Date;
    deathBeneficiaries?: BeneficiaryForm[] = [];
    lifeBeneficiaries?: BeneficiaryForm[] = [];
    deathBenefClauseForms: BenefClauseForm[] = [];
    lifeBenefClauseForms: BenefClauseForm[] = [];
    policyHolders: PolicyHolderForm[] = [];
    otherClients: ClientForm[];
    readonly type ="CHANGE_BENEF_FORM";
}