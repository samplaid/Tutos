import { Input, Component, OnInit, Output, EventEmitter, SimpleChanges } from '@angular/core';
import { CommentData } from "../../index";
import { DatePipe } from "@angular/common";
import { MessageService } from "../../../utils/index";
import { User } from '@models/user';
import { BeneficiaryChangeStepWorkflowService } from "../../../beneficiary-change/services/beneficiary-change-step-workflow.service";
import { RejectData, StepLightDTO } from "../../models/reject-data";
import { StepComment } from '@models/index';


@Component({
    selector: 'workflow-header',
    templateUrl: 'header.tpl.html'
})
export class HeaderComponent implements OnInit {

    @Input() workflowName : string;
    @Input() workflowItemId : number;
    @Input() workFlowStatus : number;
    @Input() polId : string;
    @Input() secondCpsUser : User;
    @Input() assignedTo : string;
    @Input() applicationForm : string;
    @Input() productCd : string;
    @Input() productName : string;
    @Input() commentsData: StepComment[] = [];
    @Input() rejectsData: RejectData[] = [];
    @Input() firstCpsUser: User;
    @Input() rejectable : boolean;
    @Input() mode : string;
    @Input() canAbort: string;

    @Output() onSave    = new EventEmitter<void>();
    @Output() onAbort   = new EventEmitter<void>();
    @Output() onNext    = new EventEmitter<void>();
    @Output() onReject  = new EventEmitter<void>();
    @Output() onAddStepComment  = new EventEmitter<{comment, nextDueDate}>();

    showCommentForm = true;
    showStepComments = true;
    commentFormClosing = false;
    today = new Date().setHours(0,0,0,0);

    constructor(private datePipe: DatePipe, private messageService: MessageService) {
    }

    ngOnInit(): void {
    }

    reject(): void {
        this.onReject.emit();
    }

    printAcceptanceReport(): void {
        //TODO
    }
    
    save(): void {
        this.onSave.emit();
    }

    next(): void {
        this.onNext.emit();
    }

    abort(): void {        
        this.messageService.confirm("Do you really want to abort this workflow ?").subscribe(confirmation => {
            if(confirmation) {
                this.onAbort.emit();
            }
        });
    }

    ngOnChanges(changes: SimpleChanges): void {
        if(changes['commentsData'] && this.commentsData && this.commentsData.length > 0) {
            this.showCommentsHistory();
        }
    }

    showCommentsHistory(){
        this.showStepComments = true;
        let htmls = [];
        this.commentsData.forEach(r => htmls.push(this.formatCommentText(r)) );
        this.messageService.addAlert(htmls,'info',true, 'commentsHistory');
    }

    showRejectsHistory(): void {
        let htmls = [];
        this.rejectsData.forEach(r => htmls.push(this.formatRejectText(r)) );
        this.messageService.addAlertWarning(htmls,true, 'rejectsHistory');
    }

    formatCommentText(c:StepComment){
        let dueDateText = (!c.nextDueDate) ? "" : "<br> Due date set to "+this.datePipe.transform(c.nextDueDate, 'dd/MM/yyyy');
        return this.datePipe.transform(c.creationDt, 'dd/MM/yyyy')+" Step&nbsp;<u>"+c.step.stepWorkflow+"</u>&nbsp;commented by "+c.creationUser+"&nbsp;:&nbsp; <div class='line-breaker paddingHorizontal12 marginBottom2'>"+c.comment+dueDateText+"</div>";
    }

    formatRejectText(reject){
        return "<div class='push-left inline-flex marginTop'>" + this.datePipe.transform(reject.creationDt, 'dd/MM/yyyy')+" Step&nbsp;<u>" + reject.step.stepWorkflow + "</u>&nbsp;rejected&nbsp;<div class='inline-block push-left line-breaker'>" + reject.rejectComment + "</div></div>";
    }

    /** ******************
     * Comment funtions 
     * ******************/
    addComment(){ // to show the form
        this.showCommentForm = true;
    }

    closeCommentForm(){
        this.commentFormClosing = true;
        setTimeout( ()=> {this.showCommentForm =false; this.commentFormClosing = false;} , 300);
    }  

    addStepComment(comment:String, nextDueDate:Date){
        this.onAddStepComment.emit({comment, nextDueDate});
        this.closeCommentForm();
    }
    /** *******************/

}