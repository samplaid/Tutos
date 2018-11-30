import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { HttpService, MessageService, ApiService } from '../../utils';
import { Observable } from 'rxjs/Observable';
import { CheckData, CheckStep } from '../../_models';


@Injectable()
export class CheckDataService extends HttpService {
    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {
        super($http, messageService);
        this.domain = 'CheckDataService';  
    }

    getAcceptanceDecision(workflowItemId: number): Observable<CheckData> {
        return this.GET(this.api.getURL('acceptanceDecision', workflowItemId), 'acceptanceDecision');
    }

    isSecuritiesTransferBeforeDebitOfFinancialFeesChecked(checkSteps: CheckStep[]): boolean {
        return !!checkSteps && checkSteps.some(checkStep => !!checkStep && !!checkStep.check &&  checkStep.check.checkCode == 'SEC_TFT_BEFORE_FF' && !!checkStep.check.checkData && checkStep.check.checkData.dataValueYesNoNa === 'YES');
    }
}