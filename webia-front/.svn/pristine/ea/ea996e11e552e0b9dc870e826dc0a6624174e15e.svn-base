import { Observable } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { AbstractWorkflowComponent } from '../../../workflow/components/workflow-component';
import { Step, AppForm, StepName, AdditionalPremiumStep } from '../../../_models';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService} from '../../../utils';
import { WorkflowService, EditingService } from '../../../_services';
import { AdditionPremiumService } from '../../services/additional-premium-step-workflow.service';
import { HeaderService } from '../../../workflow/services/header.service';
import { BsModalService } from 'ngx-bootstrap';
import { Title } from '@angular/platform-browser';
import { simulationAnimation } from '../../../workflow/models/animations';
import { SimulationService } from '../../../workflow/services/simulation.service';
import { DocumentTypeValues } from '../../../workflow/models/document-type';
import { CheckDataService } from '../../../workflow/services/check-data.service';

const stepUpdatable = [StepName.New_Business, StepName.Analysis, StepName.Registration, StepName.Waiting_Dispatch, StepName.Premium_Transfer_Request];
const stepUpdatablePayment = [StepName.Analysis, StepName.Premium_input_and_nav, StepName.Awaiting_Premium, StepName.Premium_Transfer_Request];
const stepUpdatableCps = [StepName.New_Business, StepName.Analysis, StepName.Registration, StepName.Waiting_Dispatch];

@Component({
    selector: 'additional-premium',
    templateUrl: 'additional-premium.tpl.html',
    animations: [simulationAnimation]
})
export class AdditionalPremiumComponent extends AbstractWorkflowComponent<AppForm> implements OnInit {

    simulationObservable: Observable<ArrayBuffer>;
    viewSimulation: boolean;
    isDocumentationAvailable: boolean = false;
    paymentMode: string;
    cpsMode: string;
    canRecreate: boolean;
    isSimulationAvailable : boolean;

    constructor(
        route: ActivatedRoute,
        messageService: MessageService,
        workflowService: WorkflowService,
        private additionPremiumService: AdditionPremiumService,
        headerService: HeaderService,
        private titleService: Title,
        modalService: BsModalService,
        private simulationService: SimulationService,
        editingService : EditingService,
        checkDataService: CheckDataService        
    ) { 
        super(messageService, additionPremiumService, modalService, route, workflowService, headerService, checkDataService, editingService);
    }

    ngOnInit(): void {
        this.titleService.setTitle("Additional Premium");
        this.init();             
    }

    protected initSpecificStep(): void {
        const currentOrderedStep = AdditionalPremiumStep.toOrderedStep(this.step.stepWorkflow);
        this.isSimulationAvailable = currentOrderedStep === AdditionalPremiumStep.Review_Documentation;
        if(this.isSimulationAvailable) {
            this.simulate();
        }
        this.isDocumentationAvailable = currentOrderedStep.isAfterOrEqual(AdditionalPremiumStep.Check_documentation);        
        this.canRecreate = this.step.workFlowStatus == 4 && currentOrderedStep.isRecreatable() && !!this.step.formData.coverage; // if workflow is 'Ended'                                              
    }

    protected isStepUpdatable(stepName: string): boolean {
        return stepUpdatable.some(anyStep => anyStep === stepName);
    }

    protected initSpecificModes(updatable: boolean, stepName: string): void {
        const canEditPayment = updatable && stepUpdatablePayment.some(anyStep => anyStep === stepName);
        const canEditCps= updatable && stepUpdatableCps.some(anyStep => anyStep === stepName);
        this.paymentMode = this.getEditableModeOnCondition(canEditPayment);
        this.cpsMode = this.getEditableModeOnCondition(canEditCps);
    }

    generateDocumentation(): void {

        this.subs.push(this.editingService.createWorkflowDocument(this.step.workflowItemId, DocumentTypeValues.ADD_PREMIUM)
        .subscribe((response: any) => {
            if(response) {
                this.messageService.addAlertSuccess("Generate documentation request has been sent successfully", true); 
            }
        }));
    }

    refresh(): void {
        this.simulationObservable = this.simulationService.getSimulation(this.step.workflowItemId, DocumentTypeValues.ADD_PREMIUM);
    }

    simulate(): void {
        this.viewSimulation = true;
        this.refresh();
    }

    onSimulationClose(): void {
        this.viewSimulation = false;
    }

    recreate(): void {
        this.additionPremiumService.recreate(this.step)
                .subscribe(step => {
                    this.messageService.addAlertSuccess("The workflow was successfully recreated", true);
                    this.init();
                });
    }
}