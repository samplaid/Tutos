import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpService, ApiService, MessageService } from '../../utils';
import { Http } from '@angular/http';
import { CheckStep } from "../../_models/index";
import { CommentData } from "../index";
import { RejectData } from "../models/reject-data";

@Injectable()
export class HeaderService extends HttpService {

    constructor($http: Http, messageService: MessageService, private api: ApiService) { 
        super($http, messageService);
    }

    getCommentsHistory(workflowItemId:number, workflowItemTypeId:number): Observable<CommentData[]> {
        return this.GET(this.api.getURL('getCommentsHistory',workflowItemId, workflowItemTypeId), 'getCommentsHistory')
            .map((checkSteps:CheckStep[]) => checkSteps.map(a => new CommentData(a.check.checkData.updateDt, a['step']['stepWorkflow'], a.check.checkData.creationUser, a.check.checkData.dataValueText))
            );
    }

    getRejectsHistory(workflowItemId:number): Observable<RejectData[]> {
        return this.GET(this.api.getURL('getRejectData', workflowItemId), 'getRejectData');
    }
}