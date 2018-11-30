import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { HttpService, MessageService, ApiService } from '../../utils';
import { Observable } from 'rxjs/Observable';
import { CreateEditingRequest } from '@models/editing/editing-request';

@Injectable()
export class WithdrawalSimulationService extends HttpService {    

    constructor($http: Http, messageService: MessageService, private api: ApiService) {
        super($http, messageService);
        this.domain = 'WithdrawalSimulationService';  
    }

    createEnoughCashEditings(workflowItemId: number): Observable<CreateEditingRequest[]> {
        return this.validateAndCreateEditings(workflowItemId, 'WITHDRAWAL_FOLLOWUP', 'createEnoughCashEditings');
    }

    createFaxSurrenderDepositBankEditings(workflowItemId: number): Observable<CreateEditingRequest[]> {
        return this.validateAndCreateEditings(workflowItemId, 'FAX_SURRENDER_DEPOSIT_BANK', 'createFaxSurrenderDepositBankEditings');
    }

    private validateAndCreateEditings(workflowItemId: number, documentType: string, creationUrl: string): Observable<CreateEditingRequest[]> {
        return this.GET(this.api.getURL('simulationValidation', workflowItemId, documentType), 'simulationValidation').flatMap(
            result => {
                if(result.length == 0 ) {
                    return this.GET(this.api.getURL(creationUrl, workflowItemId), creationUrl);
                } else {
                    this.messageService.addAlertError(result);
                    throw result;
                }
            }
        )
    }
}