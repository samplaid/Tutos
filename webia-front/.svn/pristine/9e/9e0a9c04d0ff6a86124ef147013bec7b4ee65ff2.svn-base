import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { HttpService, MessageService, ApiService } from '../../utils';
import { Observable } from 'rxjs/Observable';
import { TransactionForm } from '../../withdrawal/models/withdrawal-form'; //TODO: move transaction form to an upper level.
import { SurrenderTransactionDetailsDTO } from '../models/surrender-details';

@Injectable()
export class SurrenderFormService extends HttpService {    

    constructor($http: Http, messageService: MessageService, private api: ApiService) {
        super($http, messageService);
        this.domain = 'SurrenderFormService';  
    }

    initWithdrawalForm(policyId: string, workflowItemId: number, secondCps: string): Observable<any> {
        return this.GET(this.api.getURL('initSurrenderForm', policyId, workflowItemId), 'initSurrenderForm').map((result: TransactionForm) => {            
            result.secondCpsUser = secondCps;
            return result;
        });
    }

    getSurrenderDetails(workflowItemId: number): Observable<SurrenderTransactionDetailsDTO> {
        return this.GET(this.api.getURL('getSurrenderDetails', workflowItemId), 'getSurrenderDetails');
    }
}