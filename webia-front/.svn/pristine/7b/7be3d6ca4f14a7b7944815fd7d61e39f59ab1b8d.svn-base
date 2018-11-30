import { Injectable } from '@angular/core';
import { FundType } from "../fund/index";
import { AgentService } from "../agent/agent.service";
import { Fund, FundForm, AgentLite } from "../_models";
import { FundService } from '../fund/fund.service';

/// Constants declaration
const NEW_FAS='NEW_FAS';
const NEW_FID='NEW_FID';
const FID='FID';
const FAS='FAS';

/**
 * A service class that hold method used to control the component.
 */
@Injectable()
export class InvestmentService {

    _wealinsAssetManager: AgentLite;

    constructor(private agentService:AgentService, private fundService: FundService ) {
        agentService.getWealinsAssetManager().subscribe(agent => this._wealinsAssetManager = agent);
    }

    /**
     * Check if at least one field has been filled in case of a new fid was created.
     * @param regFund the fund object
     */
    isNewFidFilledInAtLeastOne(regFund: FundForm) : boolean {
        if( !regFund ) return false;
        let fund = regFund.fund;
        return (regFund.fundTp == FID && fund && (  !!fund.assetManager || 
                                                    !!fund.assetManagerFee || 
                                                    !!fund.finFeesFlatAmount || 
                                                    !!fund.depositBank || 
                                                    !!fund.bankDepositFee ||
                                                    !!fund.depositBankFlatFee));
    }

    /**
     * Check if at least one field has been filled in case of a new fid was created.
     * @param regFund the fund object
     */
    isNewFasFilledInAtLeastOne(regFund: FundForm) : boolean {
        if(!regFund) return false;
        let fund = regFund.fund;
        // WARNING: Do not add the asset manager id here as it always has a default value --> (in DB param 'wealins_Asset_manager_id') .
        // The asset manager is mapped to the company field.
        return (regFund.fundTp == FAS && fund && (  !!fund.assetManagerFee || 
                                                    !!fund.finFeesFlatAmount ||
                                                    !!fund.financialAdvisor ||
                                                    !!fund.finAdvisorFee ||
                                                    !!fund.depositBank || 
                                                    !!fund.bankDepositFee ||
                                                    !!fund.depositBankFlatFee));
    }


    /**
     * Remove from the list of the registration fund all new funds which a fund fields are all empties.
     * @param regFunds a set of registration funds
     */
    removeDirtyFunds(regFunds: FundForm[]) : FundForm[] {
        if(!regFunds) return [];
        return regFunds.filter( (regFund, i) => { return (this.isNewFidFilledInAtLeastOne(regFund) || this.isNewFasFilledInAtLeastOne(regFund) || !regFund.fund); });
    }

    /**
     * Create a new fund
     * @param fundTypeKey 
     * @param registrationId 
     */
    createNewFund(fundTypeKey: string, formId: number) : FundForm {
        let newFundForm=undefined;
        if (fundTypeKey == NEW_FAS) { 
            newFundForm = new FundForm(formId);          
            newFundForm.fundTp = FundType.FAS.value;      
            newFundForm.fund.fundSubType = FundType.FAS.value;
            newFundForm.fund.fundType = 3;
            newFundForm.fund.investCashLimit = 100;
            /// Default the asset manager to the value retrieved form DB PARAM 'wealins_Asset_manager_id'
            newFundForm.fund.assetManager = this._wealinsAssetManager.agtId;
            newFundForm.fund.assetManagerAgent = this._wealinsAssetManager;
        } else if (fundTypeKey == NEW_FID) {
            newFundForm = new FundForm(formId);          
            newFundForm.fundTp = FundType.FID.value;
            newFundForm.fund.fundSubType = FundType.FID.value;
            newFundForm.fund.fundType = 3;
            newFundForm.fund.investCashLimit = 100;
        } 

        return newFundForm;
    }


    validInvestmentFunds(fundForms:FundForm[]){
        let promises = [];
        if (fundForms){
            let funds = fundForms.map(ff=> ff.fund);
            funds.forEach(fund=> promises.push(this.fundService.validateFund(fund).toPromise()));
        }
        return Promise.all(promises);
        
    }
    
    initFundCurrency(fundForms:FundForm[], currency:string){
        if (currency && fundForms){
            fundForms.forEach(ff=>{
                if (!ff.fund.currency) 
                    ff.fund.currency = currency;
            })
        }
    }

}