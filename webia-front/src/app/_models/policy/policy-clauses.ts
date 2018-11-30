export class PolicyClauses {

    death : PolicyClause[];
    maturity: PolicyClause[];
    constructor(){
        this.death = [];
        this.maturity = [];
    }

}

export class PolicyClause {

    code?: string;
    fkPoliciespolId?: string;
    pbcId?:number;
    percentageSplit?:number;
    policy?: string;
    rank?:number;
    status?:number;
    textOfClause?: string;
    type?: string;   // Death or Life
    typeOfClause?: string;  // F (Free), S (Standard), N (nominative)

}