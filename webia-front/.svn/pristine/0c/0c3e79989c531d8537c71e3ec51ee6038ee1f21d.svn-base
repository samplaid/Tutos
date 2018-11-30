import { BenefClauseForm } from './../_models/formData/benefClauseForm';
import { Injectable } from '@angular/core';
import { Http } from "@angular/http";
import { Observable } from "rxjs/Observable";

import { HttpService, MessageService, ApiService } from "../utils";
import { BasicFormDataService } from "../_services";
import { Step, AppForm, CliOtherRelationShipRole, CountryCodeEnum, year_term } from "../_models";
import { ClientRoleActivationFlag } from '../_models/client/client-role-activation-flag';
import { OptionDetail } from '../_models/option';

const cliOtherRelationCessions = [CliOtherRelationShipRole.CESSION_CHANGING_STRATEGY_RIGTHS, CliOtherRelationShipRole.CESSION_SURRENDER_RIGTHS, CliOtherRelationShipRole.CESSION_SWITCH_RIGTHS]

@Injectable()
export class AnalysisService extends BasicFormDataService<Step<AppForm>>{

    constructor($http: Http, messageService: MessageService, public api: ApiService) {
        super($http, messageService, api);
        this.domain = 'AnalysisService';
    }

    save(step: Step<AppForm>, complete?: boolean): Observable<Step<AppForm>> {        
        this.postTreatment(step);
        if(step && step.formData && step.formData['product']) {
            delete step.formData['product'];
        }
        return super.save(step, complete);
    }

    abort(appForm: AppForm) {
        // actions to perform before trying to abort step
        this.removeDirties(appForm);
    }

    postTreatment(step:Step<AppForm>){
        if(step && step.formData){
            let a = step.formData;
            this.removeforWholeLife(a);
            this.removeDirties(a);
            this.fixClientsOrder(a);
        }
    }

    removeforWholeLife(a: AppForm){
            // whole life then remove life actors and clauses
            if (a.term == 0 ){ // || !a.term     //NAL: we want to keep null value
                a.lifeBeneficiaries =  [];
                a.lifeBenefClauseForms = [];
                //a.term = 0; // by default, set the term (model) to zero. avoid to store the undefined value.    //NAL: we want to keep null value
            }
    }

    removeDirties(a: AppForm){
        /// remove text or code regarding the clause type Free/Standard
        a.deathBenefClauseForms.forEach(o => this.clearInputClause(o));

        /// remove empty clauses
        if(a && a.deathBenefClauseForms)
            a.deathBenefClauseForms = a.deathBenefClauseForms.filter(o => !!o && (!!o.clauseText || !!o.rankNumber || !!o.clauseCd));

        if(a && a.lifeBenefClauseForms){
            a.lifeBenefClauseForms = a.lifeBenefClauseForms.filter(o => !!o && (!!o.clauseText || !!o.rankNumber));
        }

        /// remove empty other client
        if(a && a.otherClients){
            a.otherClients = a.otherClients.filter(o => !!o && !!o.clientRelationTp && !!o.clientId);
        }

    }


    clearInputClause(clause:BenefClauseForm){
        if (!clause || !clause.clauseTp) return;
        if (clause.clauseTp.toUpperCase()=='STANDARD'){
            clause.clauseText=null;
        }
        if (clause.clauseTp.toUpperCase()=='FREE'){
            clause.clauseCd = null;
            if (clause.clauseText)
                clause.clauseText = clause.clauseText.trim();
        }
    }

    /**
     * Fix the order of Policyholders and Insureds in each array
     */
    fixClientsOrder(a: AppForm){
        for (var i = 0, len = a.policyHolders.length; i < len; i++) {
            a.policyHolders[i].rankNumber = i+1;
            a.policyHolders[i].clientRelationTp =  (i<2 ? i+1 : 3);
        }
        for (var i = 0, len = a.insureds.length; i < len; i++) {
            a.insureds[i].rankNumber = i+1;
            a.insureds[i].clientRelationTp =  (i<2 ? i+4 : 6);
        }       
    }

    isCessionary(clientRelationTp: number): boolean {
        return cliOtherRelationCessions.some(cession => cession == clientRelationTp);
    }

    isCapiLux(product: any): boolean {
        return (product && product.isNotCapi === false && product.nlCountry == CountryCodeEnum.LUXEMBOURG);
    }

    canShowBeneficiaryLife(product: ProductClauseRequest, term: string): boolean {
        return product && product.isNotCapi && term == year_term || this.isCapiLux(product);
    }

    canShowBeneficiaryLifeClause(product: ProductClauseRequest, term: string): boolean {
        return this.canShowBeneficiaryLife(product, term);
    }

    solveClientRoleActivation(countryCode: string): Observable<ClientRoleActivationFlag> {
        return this.GET(this.api.getURL('solveActivation', countryCode), 'solveActivation');
    }

    solvePolicyHolderActivation(countryCode: string, productCapi: boolean, yearTerm: boolean) {
        return this.GET(this.api.getURL('solvePolicyHolderRoleActivation', countryCode, productCapi, yearTerm), 'solvePolicyHolderRoleActivation');
    }

    getClientRolesByProduct(productId: string, productCapi: boolean, yearTerm: boolean): Observable<OptionDetail[]>{
        return this.GET(this.api.getURL('getClientRolesByProduct', productId, productCapi, yearTerm), 'getCPRRoles');
    }

}

export interface ProductClauseRequest {
    isNotCapi: boolean;
    nlCountry: string;
}