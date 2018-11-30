import { Component, Input, Output, EventEmitter, OnInit, OnChanges, SimpleChanges  } from '@angular/core';
import { UUID } from 'angular2-uuid/index.js';
import { CheckStep, CheckWorkFlow, CheckData, Step, CheckType } from '../_models';
import { WebiaService } from '../_services';
import { QuestionService } from './question.service';
import { Observable } from 'rxjs/Observable';

/**
 * Question component for survey forms
 *  accepted checkType : YesNo, YesNoNA, Text, Amount, Number, Date
 */
@Component({
    selector: 'question',
    templateUrl: 'question.tpl.html',    
    styles:[`
        .icon-info {
            cursor: help;
        }
    `]
})

export class QuestionComponent implements OnInit, OnChanges {
    
    q: CheckStep;
    uuid = UUID.UUID();
    options = [];
    questionScore: number;

    constructor(private questionService: QuestionService, private webiaService:WebiaService) {        
    }

    @Output() valueChange = new EventEmitter<any>();
    @Input() mode: "readOnly"|"waiting"|"edit"|"create";
    @Input() workflowItemId: number;
    @Input("checkWorkflow") set checkWorkflow(value) {
        this.initLinkedObjectCheck(value);
        this.valueChange.emit(value);
    }

    get checkWorkflow() {
        return this.q;
    }

    ngOnInit() {  }
    ngOnChanges(changes: SimpleChanges): void {
        this.questionService.answer(this.q.check, (response: any) => {
            this.setScore(this.q.check, response);
        });
    }

    
    initLinkedObjectCheck(q:CheckStep){
        if(q && q.check) {
            this.q = q;
            if(!q.check.checkData) 
                this.q.check.checkData = new CheckData(q.check.checkId, this.workflowItemId);
        }
        if (q.check.checkType=='List'){
            this.getOptions(q.check.checkCode);
        }

    }

    getOptions(typeCd:string){
        this.webiaService.getCodeLabel(typeCd).toPromise().then(list => this.options = list);
    }

    emit(q){
        this.valueChange.emit(q);
    }

    /**
     * Set the score of each question depending of the checkcode and the response.
     * AS mentioned in the specification (see the documentation Subscription 18.2.docx), 
     * do not show the score if its value is equal to 0.
     * @param question the question that contains the check code
     * @param reponse the response to the question.
     */
    setScore(question: CheckWorkFlow, reponse: any): void {       
        if(question) {
            this.questionScore = this.questionService.getQuestionScore(question.checkCode, reponse ,question.scoreBCFTs);           
            if(this.questionScore === 0){
                this.questionScore = undefined;
            }
        }
    }  
}