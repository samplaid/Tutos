import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { HttpService, MessageService, ApiService } from '../../utils';
import { Observable } from 'rxjs/Observable';
import { TransferFormData, TransferForm, SurrenderTransferFormData } from '../models/transfer';


@Injectable()
export class TransferService extends HttpService {
    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {
        super($http, messageService);
        this.domain = 'TransferService';  
    }

    initFormDataForWithdrawal(workflowItemId: number): Observable<TransferFormData> {
        return this.GET(this.api.getURL('initFormDataForWithdrawal', workflowItemId), 'initFormDataForWithdrawal');
    }

    initFormDataForSurrender(workflowItemId: number): Observable<SurrenderTransferFormData> {
        return this.GET(this.api.getURL('initFormDataForSurrender', workflowItemId), 'initFormDataForSurrender');
    }
}