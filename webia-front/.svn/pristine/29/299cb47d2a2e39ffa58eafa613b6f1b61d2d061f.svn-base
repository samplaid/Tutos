import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { HttpService, MessageService, ApiService } from '../../utils';
import { Observable } from 'rxjs/Observable';

import { TransactionForm } from '../models/withdrawal-form';

@Injectable()
export class WithdrawalFormService extends HttpService {    

    constructor($http: Http, messageService: MessageService, private api: ApiService) {
        super($http, messageService);
        this.domain = 'WithdrawalFormService';  
    }

    initWithdrawalForm(policyId: string, workflowItemId: number, secondCps: string): Observable<TransactionForm> {
        return this.GET(this.api.getURL('initWithdrawalForm', policyId, workflowItemId), 'initWithdrawalForm').map((result: TransactionForm) => {            
            result.secondCpsUser = secondCps;
            return result;
        });
    }
}