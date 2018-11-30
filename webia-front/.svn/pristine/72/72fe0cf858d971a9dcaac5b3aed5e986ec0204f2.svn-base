/**
 * Represents the registration fund model.
 */
import { AppForm, EncashmentFundForm } from "./index";
import { Fund } from "../index";
import { FundLite } from "../fund-lite";

export class FundForm {
    fundFormId?: number;
    formId?: number;
    fund?: FundLite;
    fundId?: string;
    fundTp?: string;
    split?: number;
    encashmentFundForms?: EncashmentFundForm[];
    creationUser?: string;
    creationDt?: Date;
    updateUser?: string;
    updateDt?: Date;
    isCashFundAccount?: boolean;  // 0= encaissement sur compte général   1= encaissement surcompte du FID/FAS 
    valuationAmt?: number;
    addOnValuableAmount?: boolean;

    // allow to add property dynamically
    [ other: string ]: any;

    constructor(formId: number){
        this.formId=formId;
        this.fund = new Fund();
    }
}