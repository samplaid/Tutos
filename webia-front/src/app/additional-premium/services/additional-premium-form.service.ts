import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { HttpService, MessageService, ApiService } from '../../utils';
import { Observable } from 'rxjs/Observable';
import { AppForm } from '../../_models';

@Injectable()
export class AdditionalPremiumFormService extends HttpService {
    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {
        super($http, messageService);
        this.domain = 'AdditionalPremiumFormService';  
    }

    initAdditionalPremiumForm(policyId: string, workflowItemId: number): Observable<AppForm> {
        return this.GET(this.api.getURL('initAdditionalPremiumForm', policyId, workflowItemId), 'initAdditionalPremiumForm');
    }
}