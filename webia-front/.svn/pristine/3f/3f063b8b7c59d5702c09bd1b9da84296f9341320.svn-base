import { Observable } from 'rxjs/Observable';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BeneficiaryChangeStep, StepName } from '../../../_models';
import { WorkflowService, EditingService } from '../../../_services';
import { BeneficiaryChangeForm } from '../../models/beneficiary-form';
import { MessageService } from '../../../utils';
import { HeaderService } from "../../../workflow/services/header.service";
import { BeneficiaryChangeStepWorkflowService } from "../../services/beneficiary-change-step-workflow.service";
import { Title } from "@angular/platform-browser";
import { BsModalService } from "ngx-bootstrap";
import { AbstractWorkflowComponent } from "../../../workflow/components/workflow-component";
import { simulationAnimation } from '../../../workflow/models/animations';
import { SimulationService } from '../../../workflow/services/simulation.service';
import { DocumentTypeValues } from '../../../workflow/models/document-type';
import { CheckDataService } from '../../../workflow/services/check-data.service';

@Component({
    selector: 'beneficiary-change',
    templateUrl: 'beneficiary-change.tpl.html',
    animations: [simulationAnimation]
})
export class BeneficiaryChangeComponent extends AbstractWorkflowComponent<BeneficiaryChangeForm> implements OnInit {
    viewSimulation: boolean;
    isSimulationAvailable : boolean = false;
    simulationObservable: Observable<ArrayBuffer>;
    
    constructor(
        route: ActivatedRoute,
        messageService: MessageService,
        workflowService: WorkflowService,
        stepWorkflowService: BeneficiaryChangeStepWorkflowService,
        headerService: HeaderService,
        private titleService: Title,
        modalService: BsModalService,
        private simulationService: SimulationService,
        checkDataService: CheckDataService,
        editingService : EditingService        
    ) { 
        super(messageService, stepWorkflowService, modalService, route, workflowService, headerService, checkDataService, editingService);
    }

    ngOnInit(): void {
        this.titleService.setTitle("Beneficiary change");
        this.init();
    }

    initSpecificStep(): void {
        if(!this.step.formData) {
            this.step.formData = new BeneficiaryChangeForm();
            //TODO : this assignement should be removed when the backend stops using the workflowItemId coming from the form.
            this.step.formData.workflowItemId = this.step.workflowItemId;
        }
        if(this.step.formData && this.step.formData.policyId) {
            this.titleService.setTitle("Beneficiary change - " + this.step.formData.policyId);
        }
        this.isSimulationAvailable = BeneficiaryChangeStep.toOrderedStep(this.step.stepWorkflow).isBeforeOrEqual(BeneficiaryChangeStep.Generate_Documentation);
    }

    onSimulationClose(): void {
        this.viewSimulation = false;
    }

    refresh(): void {
        this.simulationObservable = this.simulationService.getSimulation(this.step.workflowItemId, DocumentTypeValues.CHANGE_BENEF);
    }

    simulate(): void {
        this.viewSimulation = true;
        this.refresh();
    }

    protected isStepUpdatable(stepName: string): boolean {
        return stepName == StepName.Analysis;
    }

    protected initSpecificModes(updatable: boolean, stepName: string): void {
        //Do nothing so far.
    }
}