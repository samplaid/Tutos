import { Observable } from 'rxjs/Rx';
import { StepLight } from './../../_models/step/step-light';
import { Injectable } from '@angular/core';
import { Step, WorkFlowStatus, workflowStatusCompleted } from '../../_models';
import { ConfigService, HttpService, MessageService } from '../../utils';
import { Http } from '@angular/http';
import { WorkflowService } from '../../_services';

@Injectable()
//TODO : This service contains timeout and business logic that should be handled in the backend.
export class ElissiaChangeService extends HttpService {

    TIMEOUT:number;
    
    constructor($http: Http, messageService: MessageService,private config: ConfigService, private workflowService: WorkflowService) { 
        super($http, messageService);
        this.domain = 'ElissiaChangeService';
        this.TIMEOUT = config.getProps('timeoutELissia');
    }

    private isStepAutomatic(stepName: string, stepsLight: StepLight[]): boolean {
        return stepsLight.some(step => step.stepWorkflow === stepName && step.stepAutomatic);
    }

    


    /**
     * Automatically complete the steps long as the current step is marked as automatic
     * 
     * @param step data to provide to the backend when completing the step
     * @param actionObsLambda a lambda that provide the observable that will be call in order to perform the complete
     * @param stepsLight the array of all the steps of the workflow, defining which steps are automatic or not
     * @param refreshStepObs an observable that is called to refresh the data after a complete
     */
    //TODO : add type to the actionObs lambda.
    pushAutomatically(step:Step<any>, actionObsLambda: (any) => (Observable<any>), stepsLight: StepLight[], refreshStepObs: Observable<Step<any>>): Observable<Step<any>> {
        //We could have a timeout after calling push step. we should consider a max number of successive calls.
        return this.pushStep(step, actionObsLambda(step)).switchMap((pushedStep:Step<any>) => {
            const isStepAutomatic = this.isStepAutomatic(pushedStep.stepWorkflow, stepsLight);
            if(isStepAutomatic && this.isWorkflowNotCompleted(pushedStep.workFlowStatus)) {
                return refreshStepObs.switchMap(refreshedStep => {
                    return this.pushAutomatically(refreshedStep, actionObsLambda, stepsLight, refreshStepObs);
                });
            } else {
                return Observable.of(pushedStep);
            }
        });
    }

    //This check is important when pushing an automatic step. because if the automatic step is at the end of the workflow
    //The first push will complete the workflow and keep the current step and we don't want another push.
    isWorkflowNotCompleted(workFlowStatus: number): boolean {
        return workFlowStatus !== workflowStatusCompleted;
    }

    pushStep(step:Step<any>, action:Observable<any>): Observable<Step<any>> {
        let isWaitingForStepChange = false;
        return new Observable(observer => {
            let s1 = action.subscribe((newStep) => { 
                isWaitingForStepChange = true;                                                                                    
                let s2 = this.waitForStepChange(step)                                                                                        
                    .toPromise()
                    .then((currentAction:any) => {                                                                                                    
                        newStep.stepWorkflow = currentAction.action;
                        newStep.workFlowStatus = currentAction.workflowItemStatus;
                        observer.next(newStep);
                        observer.complete();
                    },                                                                                                 
                    error => { 
                        this.messageService.error(error, "Step updated", "sm");
                        observer.error(error);observer.complete();
                    });
            },
            (error) => { 
                observer.error(error);
                observer.complete();
            },
            () => { 
                if (!isWaitingForStepChange) {
                    observer.complete();
                }
            }
        )});
    }

    /**
     * As E-Lissia use a trigger every 5sec to update a WorkItem from a step to the next
     * this function will check every second if the step has been pushed by E-Lissia 
     * return : Observable is complete or an error is thrown after 15sec (TIMEOUT)
     */
    private waitForStepChange(step:Step<any>){
        return new Observable(observer => {
            // TIMEOUT after 15sec
            let loop2 = setInterval(() => {
                    clearInterval(loop) ; 
                    observer.error("Step has been updated successfully but E-lissia has not push to the next step (TIMEOUT)");}
            , (this.TIMEOUT || 15000) ); 
            // check every 1sec if step has been pushed by E-Lissia's trigger
            let loop = setInterval(() => {
              this.workflowService.getWorkflow(step.formData.workflowItemId).toPromise().then((data:any) => {
            	  		let currentAction = data.actions.find(x => x.workflowItemStatus >= 1);
                        if (currentAction.action != step.stepWorkflow 
                            || (currentAction.workflowItemStatus !=step.workFlowStatus && currentAction.workflowItemStatus!=WorkFlowStatus.LOCKED) ){
                            clearInterval(loop);
                            clearInterval(loop2);
                            observer.next(currentAction);
                            observer.complete();
                        }
                    }, (error)=>{
                        clearInterval(loop);
                        clearInterval(loop2);
                        observer.error("Error on service workflowService.getWorkflow");
                    })
            }, 250);

        });

    }
}