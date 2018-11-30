import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { HttpService, MessageService, ApiService } from '../../utils';
import { Observable } from 'rxjs/Observable';
import { BeneficiaryChangeForm } from '../models/beneficiary-form';

@Injectable()
export class BeneficiaryChangeFormService extends HttpService {
    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {
        super($http, messageService);
        this.domain = 'BeneficiaryChangeFormService';  
    }

    initBeneficiaryChangeForm(policyId: string, workflowItemId: number): Observable<BeneficiaryChangeForm> {
        return this.GET(this.api.getURL('initBeneficiaryChangeForm', policyId, workflowItemId), 'initBeneficiaryChangeForm');
    }
}