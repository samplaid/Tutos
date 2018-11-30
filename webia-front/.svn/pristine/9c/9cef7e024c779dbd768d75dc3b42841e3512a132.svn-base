import { FundForm } from './../_models/formData/fundForm';
import {Injectable}                                 from '@angular/core';
import { fundFasCode, fundFidCode } from '../_models';


@Injectable()
export class FundFormService {

    canShowNewFundQuestions(fundForm: FundForm): boolean {
        return !!fundForm.fund && this.isNotActive(fundForm.fund.status) && (this.isFid(fundForm.fund.fundSubType) || this.isFas(fundForm.fund.fundSubType));
    }

    isUndeletable(type: string): boolean {
        return this.isFid(type) || this.isFas(type);
    }

    private isFas(type: string): boolean {
        return type === fundFasCode;
    }

    private isFid(type: string): boolean {
        return type === fundFidCode;
    }

    private isNotActive(status: number): boolean {
        return status === 0;
    }    
 
}