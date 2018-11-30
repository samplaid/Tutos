import { Input, Component, OnInit } from '@angular/core';
import { Product } from "../../../_models/product";
import { BenefClauseForm } from "../../../_models/index";
import { clauseTypeList }  from "../../../_models/constant";
import { StateMode } from '../../../utils';

@Component({
    selector: 'policy-clauses-input',
    templateUrl: 'policy-clauses-input.tpl.html',
    styles: ['.add-clause-line{margin-bottom:1.5em}']
})
export class PolicyClausesInputComponent implements OnInit {

    readonly clauseTypeList = clauseTypeList;

    constructor() {}

    @Input() product: Product;
    @Input() formId: number;
    @Input() benefClauseStdList: any[];
    @Input() clauseForms: BenefClauseForm[];
    @Input() clauseType: 'D'|'L';
    @Input() mode: string;
    clauseTitle: string;
    readonly stateMode = StateMode;

    ngOnInit(): void {
        this.clauseTitle = this.clauseType === 'D' ? 'Death Clauses' : 'Life Clauses';
    }

    /**
     * Reset the clause when the type changes.
     * @param formBean 
     */
    onClauseTypeChange(formBean : BenefClauseForm) {
        formBean.clauseCd = null;  
        formBean.clauseText = null;
    }

    /**
     * Add a clause to the model
     */
    addClause(){
        this.clauseForms.push(new BenefClauseForm(this.formId, this.clauseType));
    }

    /**
     * Remove a clause from the model
     * 
     * @param index the index (position) of the clause
     */
    removeClause(index){
        this.clauseForms.splice(index,1);
    }
}