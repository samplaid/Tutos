import { Beneficiary, OtherClient, PolicyHolder, PolicyClause } from '../../_models';

export class BeneficiaryChange extends FormData {


    workflowItemId?:number; //Fix ME : remove this property asap
    policyId?: string;
    changeDate?: Date;
    deathBeneficiaries?: Beneficiary[] = [];
    lifeBeneficiaries?: Beneficiary[] = [];
    deathBenefClauses: PolicyClause[] = [];
    lifeBenefClauses: PolicyClause[] = [];
    deathNominativeClauses: PolicyClause[] = [];
    lifeNominativeClauses: PolicyClause[] = [];
    policyHolders: PolicyHolder[] = [];
    otherClients: OtherClient[];
    readonly type ="CHANGE_BENEF";
}