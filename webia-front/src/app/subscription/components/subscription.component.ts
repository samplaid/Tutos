import { Observable } from 'rxjs/Rx';

import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { DatePipe } from '@angular/common';
import { Title }     from '@angular/platform-browser';
import { Subscription } from 'rxjs/Subscription';
import { WorkflowService, EditingService } from '../../_services';
import { KeycloakService } from "../../_guards/keycloak.service";
import { Step, StepName, Fund, WorkFlowStatus, SubscriptionStep, AppForm, StepComment } from '@models/index';
import { MessageService, StateMode, DateUtils, ApiService } from "../../utils";
import * as _ from "lodash";
import { BsModalService, BsModalRef } from "ngx-bootstrap";
import { User } from '../../_models/user';
import { StepService } from "../services/step.service";
import { ModalRejectStep } from "../../utils/components/modal-reject-step.component";
import { PolicyService } from '../../policy/services/policy.service';
import { StepLight } from '../../_models/step/step-light';
import { StepWorkflowService } from '../../workflow/services/step-workflow.service';
import { Http } from '@angular/http';
import { SubscriptionService } from '../services/subscription-step.service';

@Component({
    selector: 'subscription',
    templateUrl: 'subscription.tpl.html'
})

export class SubscriptionComponent implements OnInit {
    mode = StateMode.edit;
    stateMode = StateMode;
    busy;
    busyLoad;
    busyRecreate: Subscription;
    busySave;
    busyReject;
    busyCancel;
    busyGenerateDocumentation;
    subs:Array<Subscription>;
    step:Step<AppForm>;
    StepName;
    firstCpsUser: User;
    secondCpsUser : User;
    showPolicyLink: boolean;
    canGenerateDocumentation: boolean;
    canPrintAcceptanceReport: boolean;
    canSendProgressStatusMail: boolean;
    canSave: boolean;
    canAbort: boolean;
    canRecreate: boolean;    
    //workFlowStatus: number;
    commentsData: StepComment[];
    rejectData: any;
    rejectModal: BsModalRef;
    allSteps: StepLight[];

    showCommentForm = true;
    showStepComments = true;
    commentFormClosing = false;
    today = new Date().setHours(0,0,0,0);

    public static readonly WORKFLOW_ITEM_TYPE_ID: number = 7;

    constructor(private route: ActivatedRoute, 
                private router: Router, 
                private stepService:StepService, 
                private policyService:PolicyService,
                private messageService: MessageService,
                private titleService: Title,
                private modalService: BsModalService, 
                private workflowService:WorkflowService,
                private keycloakService:KeycloakService,
                private editingService:EditingService,
                private stepWorkflowService: SubscriptionService,
                private datePipe: DatePipe) { }

    ngOnInit(){        
        this.getQueryParam();
        this.StepName = StepName;
        this.subs = [];
    }

    public setTitle( newTitle: string) {
        this.titleService.setTitle( newTitle );
    }

    getQueryParam(){
        this.route.queryParams.subscribe((query: Params)=>this.init(query));
    }    

    init(query: Params) { 
        this.checkWebiaUser(query);

        if (query['workflowItemId'] && query['workflowItemId']>0){ 
            this.step = null;  
            this.busyLoad = this.stepService.loadStep(query['workflowItemId'], query['stepWorkflow'], query['userToken'] ||'')
                                             .subscribe(step => {
                                                   this.unsubscribe(this.busyLoad);
                                                   this.afterStepLoad(step, query);
                                                }, e=>{}) ;

            // TODO : maybe merge the observable
            this.stepWorkflowService.getAllStepsByWorkflowType(SubscriptionComponent.WORKFLOW_ITEM_TYPE_ID).subscribe(result => this.allSteps = result);
        } else {
            let newStep = new Step<AppForm>();
            newStep.stepId = 1;
            this.step = this.stepService.initStep(newStep);
        }

        if (query['mode'])
            this.mode = query['mode'];
    }

    afterStepLoad(step: Step<AppForm>, query: Params) {
        this.step = step;
        if (!!step.errors && step.errors.length>0)
            this.messageService.error(step.errors);
        if (step.updatable == false)
            this.mode = StateMode.readonly;
        this.setTitle(step.stepWorkflow + "-" + step.formData.workflowItemId);
        this.getCPSNames();
        this.stepService.getRejectData(query['workflowItemId']).then(rejectData => this.initRejectData(rejectData));
        //this.stepService.getCommentsHistory(query['workflowItemId'], step.workflowItemTypeId).then(data => this.initCommentData(data), e=>{});
        this.getWorkflowComments(query['workflowItemId']);
        
        if (step.polId) {
            this.policyService.isExist(step.polId).toPromise().then(flag => this.showPolicyLink = (flag == true)) // because false is treated like an empty object. i.e {});
        }
        this.canGenerateDocumentation = step.stepWorkflow === StepName.Check_documentation || step.stepWorkflow === StepName.Generate_documentation || step.stepWorkflow === StepName.Update_Input || step.stepWorkflow === StepName.Sending || step.stepWorkflow === StepName.Follow_up;
        this.canSave = step && (step.workFlowStatus == 1 ||  step.workFlowStatus == 5); // if workflow is 'ACTIVE' or in 'REGISTRATION' state
        this.canAbort = step && step.stepAbortable && (step.workFlowStatus == 1 ||  step.workFlowStatus == 5); // if workflow is 'ACTIVE' or in 'REGISTRATION' state
        this.canRecreate = step && step.workFlowStatus == 4 && SubscriptionStep.toOrderedStep(step.stepWorkflow).isRecreatable(); // if workflow is 'Ended'       
        this.canPrintAcceptanceReport = step && (step.workFlowStatus == 1 ||  step.workFlowStatus == 5) && SubscriptionStep.Acceptance.isBeforeOrEqual(SubscriptionStep.toOrderedStep(step.stepWorkflow));
        this.canSendProgressStatusMail = step.stepWorkflow === StepName.Account_Opening_Request || step.stepWorkflow === StepName.Awaiting_Account_Opening || step.stepWorkflow === StepName.Premium_Transfer_Request;
    }

    // Check if the user comming from E-Lissia is the same as the user logged in WEBIA.
    checkWebiaUser(query: Params) {
        if(query['userToken']) {
                this.busy = this.workflowService.getWorkflowUserWithToken(query['userToken']).subscribe(data =>{
                    this.unsubscribe(this.busy);
                    if (data && data.loginId) {
                        if(data.loginId.toLowerCase() !== this.keycloakService.getLogin().toLowerCase()) {
                            this.keycloakService.logout();
                        }
                    }     
                })
        }
    }

    getCPSNames(){
        if (this.step){
            if (this.step.firstCpsUser || this.step.secondCpsUser)
                this.busy = this.workflowService.getWorkflowUsers().subscribe(data =>{
                    this.unsubscribe(this.busy);
                    if (data && data.users){
                        this.firstCpsUser = data.users.find(cps => cps.usrId == this.step.firstCpsUser);
                        this.secondCpsUser = data.users.find(cps => cps.usrId == this.step.secondCpsUser);
                    }     
                })
        }
    }

    /**Comment have been moved from checkStep to a dedicated table */
    // initCommentData(checkStep){
    //     //get last update date group by checkId
    //     if (checkStep){
    //         let today = new Date();
    //         let tmp = [];
    //         let previous = null;
    //         checkStep.forEach(row => {
    //                      if (row.check.checkId != previous){
    //                          let date;
    //                          let user;
    //                          let html;
    //                          if (row.check && row.check.checkData){
    //                              let  d = row.check.checkData;
    //                             date = (d.creationDt) ? d.creationDt : d.updateDt || today;
    //                             user = (d.creationUser) ? d.creationUser : d.updateUser ;
    //                             html = d.dataValueText;
    //                          }
    //                         let stepName = (row.step)? row.step.stepWorkflow : "";
    //                         tmp.push({date, stepName, user, html});
    //                      }
    //                      previous = row.check.checkId; 
    //                  } );
    //         tmp.sort((a,b)=> a.date - b.date );
    //         this.commentsData = tmp.length> 0 ? tmp : null; 
    //         this.showCommentsHistory()           
    //     }
    // }

    showCommentsHistory(){
        if (this.commentsData){
            this.showStepComments = true;
            let htmls = [];
            this.commentsData.forEach(r => htmls.push(this.formatCommentText(r)) );
            this.messageService.addAlert(htmls,'info',true, 'commentsHistory');
        }
    }

    formatCommentText(c:StepComment){
        let dueDateText = (!c.nextDueDate) ? "" : "<br> Due date set to "+this.datePipe.transform(c.nextDueDate, 'dd/MM/yyyy');
        return this.datePipe.transform(c.creationDt, 'dd/MM/yyyy')+" Step&nbsp;<u>"+c.step.stepWorkflow+"</u>&nbsp;commented by "+c.creationUser+"&nbsp;:&nbsp; <div class='line-breaker paddingHorizontal12 marginBottom2'>"+c.comment+dueDateText+"</div>";
    }

    initRejectData(data){
        this.rejectData = data;
        if (data && data.length){
            if (this.step.rejectReason){ //TODO : to replace by a flag indicating if a rejection has been done
                let lastRejection = data[0];
                this.messageService.addAlertWarning(this.formatRejectText(lastRejection), true, 'rejectHistory');
            }
        }
    } 

    showRejectHistory(){
        if (this.rejectData){
            let htmls = [];
            this.rejectData.forEach(r => htmls.push(this.formatRejectText(r)) );
            this.messageService.addAlertWarning(htmls,true, 'rejectHistory');
        }
    }

    formatRejectText(reject){
        return "<div class='push-left inline-flex marginTop'>"+this.datePipe.transform(reject.creationDt, 'dd/MM/yyyy')+" Step&nbsp;<u>"+reject.step.stepWorkflow+"</u>&nbsp;rejected&nbsp;<div class='inline-block push-left line-breaker'>"+reject.rejectComment+"</div></div>";
    }

    save(complete){
        this.messageService.clearAlert();          
        this.busySave=this.stepService.save(this.step, complete, this.allSteps).subscribe( 
                                    (step:Step<AppForm>) => this.resolveSave(step, complete) , 
                                          error => this.rejectSave(error), // reload as step can be changed
                                          ()=>{ this.unsubscribe(this.busySave);}
                                    ); 
    }

    resolveSave(step: Step<AppForm>, complete){
        this.unsubscribe(this.busySave);
        if(step){
            if(step.workFlowStatus == WorkFlowStatus.COMPLETED){
                this.messageService.addAlertSuccess('The id ' + step.workflowItemId + ' is completed.', true);                 
            } else {
                this.messageService.addAlertSuccess('Step has been updated successfully.', true); 
            }
            if (complete)
                this.getQueryParam();
            else
                this.step = step;                           
        } 
    }

    rejectSave(error){  
        this.unsubscribe(this.busySave); 
        let resp = null;
        if (error._body){
            resp = JSON.parse(error._body);
        } 
        if( resp && typeof  resp.source === 'object' && _.hasIn(resp.source,'stepId')) {
            this.step = resp.source;
        }  else {
            this.route.queryParams.subscribe((query: Params)=>{
                if(query && query['workflowItemId'] && query['workflowItemId']>0) {
                    this.busyLoad = this.stepService.loadStep(query['workflowItemId'], query['stepWorkflow'], query['userToken'] ||'')
                                            .subscribe((step: Step<AppForm>) => {                                                                                       
                                                this.unsubscribe(this.busyLoad); 
                                                this.refreshAppForm(step);    
                                                if(step.workFlowStatus == WorkFlowStatus.COMPLETED){
                                                    this.messageService.addAlertSuccess('The id ' + step.workflowItemId + ' is completed.', true);                                                                                 
                                                }
                                            });               
                }                    
            });
        }  
    }

    unsubscribe(busy:Subscription){
         if (busy && !busy.closed)
            busy.unsubscribe();         
    }

    refreshAppForm(reloadedStep:Step<AppForm>){
        if (reloadedStep && this.step ){
            let mergeStep = _.clone(this.step);
            if (reloadedStep.checkSteps && (reloadedStep.checkSteps.findIndex(d=> (!!d.check && !!d.check.checkData && !!d.check.checkData.checkDataId))>=0) )
                mergeStep.checkSteps = reloadedStep.checkSteps;
            if (reloadedStep.formData && reloadedStep.formData.formId)
                mergeStep.formData = reloadedStep.formData;
            if (reloadedStep.polId)
                Object.assign(mergeStep,{polId:reloadedStep.polId, productCd: reloadedStep.productCd, productName: reloadedStep.productName, stepWorkflow: reloadedStep.stepWorkflow, updatable:reloadedStep.updatable})
            
            mergeStep.workFlowStatus = reloadedStep.workFlowStatus;
            this.step = mergeStep;

        }
        //this.stepService.refreshAppForm(this.step, reloadedStep);
    }

    cancel(){
        this.messageService.clearAlert();
        this.stepService.abortStep(this.step).subscribe(
            (step) => {    
                this.messageService.addAlertSuccess("Step has been aborted successfully", true);
                this.getQueryParam();
            },
            error => { }
        ); 
    }

    generateDocumentation() {
        this.busyGenerateDocumentation=this.editingService.generateDocumentation(this.step.formData).subscribe( 
                                    (editionResponse) => { this.unsubscribe(this.busyGenerateDocumentation); 
                                            this.messageService.addAlertSuccess("Generate documentation request has been sent successfully", true); 
                                        } , 
                                        error => {}
                                    ); 
    }

    formatMessageTextWhenMailSend(mailType:String, workFlowStepName:String, trigramme:String, date:Date){
        return "Mail Send "+ '"'+ mailType + '"' + " on " + this.datePipe.transform(date, 'dd/MM/yyyy') + " to " + trigramme + ", on step "+ workFlowStepName + "";
    }

    sendSourcriptionFollowUpMail(){
        let trigramme = this.keycloakService.getLogin().toUpperCase();
        let stepName = this.step.stepWorkflow.toUpperCase();
        let today = new Date();
        let title = this.editingService.getFollowUpTitleByStepName(this.step.stepWorkflow);
        this.editingService.generateSouscriptionFollowUp(this.step.formData)
        .subscribe((editionResponse: any) => {
            //if( editionResponse.request) {
                this.messageService.addAlertSuccess( this.formatMessageTextWhenMailSend(title,stepName, trigramme,today ), true); 
           // } 
        });

    }

    
    printAcceptanceReport(): void {
        this.editingService.createAcceptanceReportEditingRequest(this.step.productCd, this.step.polId, this.step.workflowItemId)
            .subscribe((editionResponse) => {
                this.messageService.addAlertSuccess("Acceptance report document generation request has been successfully sent.", true); 
            });
    }

    reasonReject() {
           this.rejectModal =   this.modalService.show(ModalRejectStep, {class:'modal-sm'});
           this.rejectModal.content.onClose.take(1).subscribe((reason) => {
               this.reject(reason);
            }, (reason) => {}); 
    }

    reject(reason:string){
        this.step.rejectReason = reason;
        this.messageService.clearAlert();
        this.busyReject=this.stepService.previousStep(this.step).subscribe( 
                                    (step:Step<AppForm>) => { this.unsubscribe(this.busyReject); 
                                                     this.messageService.addAlertSuccess("Step has been rejected successfully", true);
                                                     this.getQueryParam();} , 
                                          error => this.getQueryParam() // reload as step can be changed
                                    ); 
    }

    recreate(): void {
        this.busyRecreate = this.stepService.recreatePolicy(this.step)
                .subscribe(step => {
                    this.messageService.addAlertSuccess("The policy was successfully recreated", true);
                    this.getQueryParam();
                }, err => {}, () => {this.unsubscribe(this.busyRecreate)});
    }

    /** ******************
     * Comment funtions 
     * ******************/
    addComment(){
        this.showCommentForm = true;
    }

    closeCommentForm(){
        this.commentFormClosing = true;
        setTimeout( ()=> {this.showCommentForm =false; this.commentFormClosing = false;} , 300);
    }  

    getWorkflowComments(workflowItemId:number){
        this.workflowService.getStepComments(workflowItemId).subscribe(
            comments => {
                this.commentsData = comments.length > 0 ? comments : null; 
                this.showCommentsHistory()}
            , e=>{});
    }

    addStepComment(comment:String, nextDueDate:Date){ 
        this.workflowService.addStepComment(this.step.workflowItemId,this.step.stepId, comment, nextDueDate).subscribe(comment => {
            this.commentsData = [comment,...this.commentsData];
            this.showCommentsHistory();
        }, e=>{});
    }
    /** *******************/
}