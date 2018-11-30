import { Observable } from 'rxjs/Observable';
import { Subscription } from 'rxjs/Subscription';
import { SurveyService, CheckCode } from './survey.service';
import { WorkflowService } from './../_services/workflow.service';
import { Component,OnInit, Input, Output, EventEmitter } from '@angular/core';
import { CheckStep, CheckData } from '../_models';

@Component({
    selector: 'survey',
    templateUrl: 'survey.tpl.html',
    styles: [`
    .fa.fa-arrow-down:first-child {
        display:none;
    }
        
    `]
})

export class SurveyComponent implements OnInit {
    surveys;
    _index:number =0;
    _currentStepIndex:number;
    steps:any[];
    _workflowItemId:number;
    oldSurveys = {};
    displayedSurveys;
    _subs:Subscription[];
    busy;
    hideCodes:string[];
    hideCodesBusy;
    delay;
    activeRow;

    constructor(private workflowService:WorkflowService, private surveyService:SurveyService) { }

    //This input is only used when the components lifecycle triggers refresh hide code before that the parent provides the surveys.
    @Input() hotReload: boolean;
    @Output() valueChange = new EventEmitter<any>();    
    @Input() mode: "readOnly"|"waiting"|"edit"|"create";
    @Input() set workflowItemId(value:number){
        this._workflowItemId = value;
    };
    get workflowItemId(){
        return this._workflowItemId;
    }
    @Input() set inputData(surveys:CheckStep[]){
        this.surveys = surveys; 
        if(this.hotReload && this.hideCodes) {
            this.updateAllSurvey();
        }
        this.valueChange.emit(surveys);
    };

    get inputData() {
        return this.surveys;
    }

    @Input() set stepWorkflow(name:string){
        if (name && this._workflowItemId)
            this.busy = this.workflowService.getWorkflow(this._workflowItemId).subscribe(response => this.initSteps(response.actions), e=>{} );
    };

    ngOnInit():void { 
        this._subs = [];
        this.hideCodes=[];
        this.hideCodesBusy = this.surveyService.hideCodes.subscribe(data => {
                    if (data){                            
                            this.hideCodes = data;
                            this.updateAllSurvey();
                    }
            });
    }

    initSteps(actions){
        let steps = [];
        actions.forEach(s => this.addStep(s,steps));
        this.steps = steps;
        this.oldSurveys = {};
    }

    addStep(s, steps) {
        s.index = ++this._index;
        if (s.workflowItemStatus>=1){
        	this._currentStepIndex = this._index;
        	this.displayedSurveys = s;
        }  	
        steps.push(s);
        
    }

    gotoStep(step){
        if (step.index < this._currentStepIndex){ // action only for old step
            if (this.oldSurveys[step.action]){
                this.displayedSurveys = step;
            } else {
                this.displayedSurveys = step;
                this._subs[step.index] = this.surveyService.loadCheckSteps(this._workflowItemId,step.action).subscribe(data => {
                    this.oldSurveys[step.action] = this.initSurvey(data);  
                });
            }
        } 
        if (step.index == this._currentStepIndex){
            this.displayedSurveys = step;
        } 
    }

    initSurvey(data:CheckStep[]){
        if (this.delay)
            clearTimeout( this.delay );   
        if(!data) {
            return [];
        }         
        data.forEach(q=> {
            let wasHide = (q.hide>0);
            q.hide = (this.hideCodes.indexOf(q.check.checkCode)>-1) ? 1: (wasHide ? -1:0);
            if (q.hide>0){
                if(!q.check.checkData) 
                    q.check.checkData = new CheckData(q.check.checkId, this.workflowItemId);
                q.check.checkData.dataValueYesNoNa = 'NA';
            } else {
                if (wasHide){
                    q.check.checkData.dataValueYesNoNa = null;
                    this.delay = setTimeout(()=>q.hide=0 ,302);
                }
                    
            }
        });
        return [...data]; // to force the refresh
    }

    updateAllSurvey(){
        this.surveys = this.initSurvey(this.surveys);
        Object.keys(this.oldSurveys).forEach(key=>this.oldSurveys[key] = this.initSurvey(this.oldSurveys[key]));
    }

    ngOnDestroy(){
         this._subs.forEach((sub: Subscription) => sub.unsubscribe());
         if (this.hideCodesBusy)
            this.hideCodesBusy.unsubscribe();
         if (this.busy)
            this.busy.unsubscribe();  
    }   

    onUp(event){
        if (!isNaN(this.activeRow)){
            this.activeRow = this.activeRow > 0 ? this.activeRow-1 : 0;
            while((!this.surveys[this.activeRow].isUpdatable || this.surveys[this.activeRow].hide) && this.activeRow >0){
                 this.activeRow --;
            }
        }  
    }

    onDown(event){
        if (!isNaN(this.activeRow)){
            this.activeRow = this.activeRow < this.surveys.length-1 ? this.activeRow+1 : this.surveys.length-1;
            while((!this.surveys[this.activeRow].isUpdatable || this.surveys[this.activeRow].hide) && this.activeRow < this.surveys.length-1){
                 this.activeRow ++;
            }
        }          
    }

    selectYesNo(event){
        if (!isNaN(this.activeRow) && this.surveys[this.activeRow]){
            let check = this.surveys[this.activeRow].check;
            if (check.checkType =='YesNo'  ){
                check.checkData.dataValueYesNoNa = check.checkData.dataValueYesNoNa == 'YES' ? 'NO':'YES' ;
            }
            if (check.checkType=='YesNoNa'){
                if (check.checkData.dataValueYesNoNa == 'NO')
                    check.checkData.dataValueYesNoNa = 'NA';
                else 
                    check.checkData.dataValueYesNoNa = check.checkData.dataValueYesNoNa == 'YES' ? 'NO':'YES' ;
            }
        } 
    }
    
}