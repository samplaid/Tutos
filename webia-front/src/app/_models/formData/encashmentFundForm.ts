/**
 * Represents the registration Encashment Fund Form model.
 */

export class EncashmentFundForm {
    cashFundFormId?: number;
    formId?: number;
    fundId?: string;
    cashAmt?: number;
    cashDt?: Date;
    cashCurrency?: string;
    cashStatus?: string;

    creationUser?: string;
    creationDt?: Date;
    updateUser?: string;
    updateDt?: Date;

    // allow to add property dynamically
    [ other: string ]: any;

    constructor(formId: number){
        this.formId=formId;
    }
}