import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { HttpService, MessageService, ApiService } from '../../utils';
import { Observable } from 'rxjs/Observable';


@Injectable()
export class SimulationService extends HttpService {
    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {
        super($http, messageService);
        this.domain = 'WorkflowSimulationService';  
    }

    getSimulation(workflowItemId: number, documentType: string): Observable<ArrayBuffer> {
        return this.GET_BUFFER(this.api.getURL('workflowSimulation', workflowItemId, documentType), 'workflowSimulation');
    }
}