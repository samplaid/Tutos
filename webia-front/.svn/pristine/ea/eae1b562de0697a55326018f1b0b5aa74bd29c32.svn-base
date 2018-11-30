import { Injectable }                               from '@angular/core';
import { Http, Response }                           from '@angular/http';
import { ApiService, MessageService, HttpService }  from '../utils';
import {Observable}                                 from 'rxjs/Observable';
import { User } from '../_models/user';
import { Users } from '../_models/user/users';
import { StepComment } from '@models/step/step-comment';

@Injectable()
export class WorkflowService extends HttpService {
    // caching data
    dataStore;

    constructor($http: Http, messageService: MessageService, private api: ApiService) { 
        super( $http,  messageService);
        this.domain = 'WorkflowService';
        this.initStore();
    }

    initStore(){
        this.dataStore = Object.assign({});
    }

    /**
     * Lazy loads the list of Lissia users.
     *  @return : the collection of users
     */
    getWorkflowUsers(): Observable<Users>{
        return this.functionWithStore('getWorkflowUsers' ,this.api.getURL('getWorkflowUsers'), 'getWorkflowUsers');
    }

    getWorkflowUserWithToken(userToken:string){
        return this.GET(this.api.getURL('getWorkflowUserWithToken', userToken), 'getWorkflowUserWithToken');
    }    
    
    getWorkflow(workflowItemId:number){
        return this.GET(this.api.getURL('getWorkflow', workflowItemId), 'getWorkflow');
    }

    getWorkFlowStatus(workflowItemId:number){
        return this.GET(this.api.getURL('getWorkFlowStatus', workflowItemId), 'getWorkFlowStatus');
    }

    getWorkflowUserByUserId(userId: string): Observable<User>{
        return this.functionWithStore('getWorkflowUserByUserId'+userId,this.api.getURL('getWorkflowUserByUserId', userId), 'getWorkflowUserByUserId');
    }

    /**
     * functions call stepComment but linked to the whole workflow
     */
    getStepComments(workflowItemId:number): Observable<StepComment[]>{
        return this.GET(this.api.getURL('getStepComments', workflowItemId), 'getStepComments')
    }

    addStepComment(workflowItemId:number,stepId:number, comment:String, nextDueDate:Date): Observable<StepComment>{
        let postData = {workflowItemId, stepId, comment, nextDueDate};
        return this.POST(this.api.getURL('addStepComment'), postData, 'addStepComment')
    }
    /****** ****/

}