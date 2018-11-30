export class BenefClauseForm {

    benefClauseFormId?:number;
	pbcId?:number;  // id in Lissia used in the input update step!
    formId?: number;
    clauseFormTp?:string;  // 'D' (death) or 'L' (Life)
    clauseTp?:string;   // 'Standard'  or 'Free'
    clauseCd?:string;
    rankNumber?:number;
    clauseText?:string;
    creationUser?: string;
    creationDt?: Date;
    updateUser?: string;
    updateDt?: Date; 

    constructor(formId:number, clauseFormTp:string){
        this.formId = formId;
        this.clauseFormTp = clauseFormTp;
        this.clauseTp = (clauseFormTp=='L') ? 'Free' : 'Standard';
    }
}