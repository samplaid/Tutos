import { FullAgent } from '../agent/full-agent';

export class PartnerForm {

    partnerFormId?: number;
    formId?: number;
    partnerCategory?: string;
    partnerId?: string;
    entryFeesPct?: number;
    entryFeesAmt?: number;
    mngtFeesPct?: number;
    isOverridedFees?: boolean;
    explainOverFees?: string;
    contactName?: string;
    split?:number;
    partnerAuthorized?: boolean;
    status: number;

    creationUser?: string;
    creationDt?: Date;
    updateUser?: string;
    updateDt?: Date;

    // allow to add property dynamically
    [other: string]: any;

    constructor(formId: number, partnerCategory: string) {
        this.formId = formId;
        this.partnerCategory = partnerCategory;
    }
}