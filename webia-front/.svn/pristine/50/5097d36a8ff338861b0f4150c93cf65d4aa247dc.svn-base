import { StateMode } from './../../utils/mode';
import { LoadableComponent } from "./loadable.component";
import { MessageService } from "../../utils/index";
import { Step, StepLight, StepName, WorkFlowStatus, StepComment } from '@models/index';
import { StepWorkflowService } from "../services/step-workflow.service";
import { BsModalService, BsModalRef } from "ngx-bootstrap";
import { ModalRejectStep } from "../../utils/components/modal-reject-step.component";
import { Router, ActivatedRoute, Params } from '@angular/router';
import * as _ from "lodash";
import { User } from "../../_models/user/user";
import { WorkflowService } from "../../_services/workflow.service";
import { RejectData } from '../models/reject-data';
import { HeaderService } from '../services/header.service';
import { CheckDataService } from '../services/check-data.service';
import { EditingService } from '../../_services';
import { DocumentTypeValues } from '../models/document-type';

export abstract class AbstractWorkflowComponent<T> extends LoadableComponent  {
    commentsData: StepComment[];
    rejectsData: RejectData[];
    formsMode: string;
    headerMode: string;
    surveyMode: string;
    firstCpsUser: User;
    secondCpsUser: User;
    readonly stepName = StepName;
    step: Step<T>;
    rejectModal: BsModalRef;
    usersList: User[];
    allSteps: StepLight[];
    canPrintAcceptanceReport : boolean;

    constructor(protected messageService: MessageService,
                protected stepWorkflowService: StepWorkflowService<T>,
                protected modalService: BsModalService,
                private route: ActivatedRoute,
                protected workflowService: WorkflowService,                
                private headerService: HeaderService,
                protected checkDataService: CheckDataService,
                protected editingService: EditingService) {
        super();
    }

    protected abstract initSpecificStep(): void;
    protected abstract isStepUpdatable(stepName: string): boolean;

    /** Remove incomplete objects before sending the form to the backend */
    protected prepareForm(): void {}

    protected init(): void {
        this.route.queryParams.subscribe((query: Params) => {
            if (query['workflowItemId']) {
                const workflowItemId = Number(query['workflowItemId']);
                this.addSubscription(this.stepWorkflowService.getStepByWorkflow(workflowItemId).subscribe(result => this.initStep(result)));
                this.addSubscription(this.workflowService.getWorkflowUsers().subscribe(result => this.usersList = result.users));
            }
        });
    }

    save(): void {
        this.messageService.clearAlert();
        this.prepareForm();
        this.addSubscription(this.stepWorkflowService.update(this.step).subscribe(result => this.afterSave(result),
                                                                                  error => this.refreshOnError(error)));
    }

    abort(): void {
        this.messageService.clearAlert();
        this.addSubscription(this.stepWorkflowService.abort(this.step).subscribe(result => this.afterAbort(),
                                                                                 error => this.refreshOnError(error)));
    }

    next(): void{
        this.messageService.clearAlert();
        this.prepareForm();
        this.addSubscription(this.stepWorkflowService.next(this.step, this.allSteps).subscribe(result => this.aftercomplete(result),
                                                                                error => this.refreshOnError(error)));
    }    

    reject(): void {
        this.rejectModal =   this.modalService.show(ModalRejectStep, {class:'modal-sm'});
        this.rejectModal.content.onClose.take(1).subscribe((reason) => {
            this.rejectWithReason(reason);
        }, (reason) => {}); 
    }

    rejectWithReason(reason:string) {
        this.step.rejectReason = reason;
        this.messageService.clearAlert();
        this.addSubscription(this.stepWorkflowService.previous(this.step).subscribe(result => this.afterReject(result),
                                                                                 error => this.refreshOnError(error)));
    }

    addStepComment(request:{comment:string, nextDueDate:Date}) {
        let wid = this.step.workflowItemId;
        let stepId = this.step.stepId;
        this.addSubscription(this.workflowService.addStepComment(wid, stepId, request.comment, request.nextDueDate).subscribe(comment => {
            if(this.commentsData.find(commentFromArray => commentFromArray.stepCommentId == comment.stepCommentId)) { // update of a comment
                this.commentsData.splice(0, 1);
            }            
            this.commentsData = [comment,...this.commentsData]
        }, e => {}));
    }

    private afterSave(step: Step<T>): void {
        this.messageService.addAlertSuccess('Step has been updated successfully.', true); 
        this.initStep(step);
    }

    private aftercomplete(step: Step<T>): void {
        if(step.workFlowStatus == WorkFlowStatus.COMPLETED){
            this.messageService.addAlertSuccess('The id ' + step.workflowItemId + ' is completed.', true);                 
        } else {
            this.messageService.addAlertSuccess('Step has been updated successfully.', true); 
        }
        this.init();
    }

    private afterReject(step: Step<T>): void {
        this.messageService.addAlertSuccess("Step has been rejected successfully", true);
        this.init();
    }

    private afterAbort(): void {
        this.messageService.addAlertSuccess("Workflow has been aborted successfully", true);
        this.init();
    }


    /**
     * Refresh the form when an occurs and the original data are sent back by the back-end
     * 
     * @param error the error object sent by the backend
     */
    protected refreshOnError(error) {  
        if (error._body){
            const resp = JSON.parse(error._body);
            if( resp && typeof  resp.source === 'object' && _.hasIn(resp.source,'stepId')) {
                this.step = resp.source;
            }
        }
    }    

    protected initCps(firstCpsUser: string, secondCpsUser: string): void {
        if(firstCpsUser) {
            this.addSubscription(this.workflowService.getWorkflowUserByUserId(firstCpsUser).subscribe(user => this.firstCpsUser = user));
        }
        if(secondCpsUser) {
            this.addSubscription(this.workflowService.getWorkflowUserByUserId(secondCpsUser).subscribe(user => this.secondCpsUser = user));
        }
    }

    protected initStep(step: Step<T>) {
        this.step = step;
        this.initModes(step.updatable, step.stepWorkflow);
        this.initCps(step.firstCpsUser, step.secondCpsUser);
        //this.addSubscription(this.headerService.getCommentsHistory(this.step.workflowItemId, this.step.workflowItemTypeId).subscribe(comments => this.commentsData = comments));
        this.addSubscription(this.workflowService.getStepComments(this.step.workflowItemId).subscribe(comments => this.commentsData = comments));
        this.addSubscription(this.headerService.getRejectsHistory(this.step.workflowItemId).subscribe(rejects => this.rejectsData = rejects));
        this.addSubscription(this.stepWorkflowService.getAllStepsByWorkflowType(this.step.workflowItemTypeId).subscribe(result => this.allSteps = result));
        this.initAcceptanceReportVisibility();
        this.initSpecificStep();
    }

    private initAcceptanceReportVisibility(): void {
        this.stepWorkflowService.canGenerateAcceptanceDocument(this.step.workflowItemId).subscribe(            
            canGenerateAcceptanceDocumentResult => this.canPrintAcceptanceReport = canGenerateAcceptanceDocumentResult
        );
    }

    printAcceptanceReport(): void {        
        this.editingService.createAcceptanceReportEditingRequest(this.step.productCd, this.step.polId, this.step.workflowItemId)
            .subscribe((editionResponse) => {
                this.messageService.addAlertSuccess("Acceptance report document generation request has been successfully sent.", true); 
            });
    }

    private initModes(updatable: boolean, stepName: string) {
        //TODO : should compare to false because the backend sends either true or null if it is editable !
        this.headerMode = this.getEditableModeOnCondition(updatable != false);
        this.surveyMode = this.getEditableModeOnCondition(updatable != false);
        this.formsMode = this.getEditableModeOnCondition(updatable != false && this.isStepUpdatable(stepName));
        this.initSpecificModes(updatable, stepName);
    }

    protected abstract initSpecificModes(updatable: boolean, stepName: string): void;

    protected getEditableModeOnCondition(condition: boolean): string {
        return condition ? StateMode.edit : StateMode.readonly;
    }
}