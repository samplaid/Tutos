import { BrokerChangeForm } from '../../models/broker-change-form';
import { BsModalService } from 'ngx-bootstrap';
import { BrokerChangeService } from '../../services/broker-change-step.service';
import { ActivatedRoute } from '@angular/router';
import { AbstractWorkflowComponent } from '../../../workflow/components/workflow-component';
import { OnInit, Component } from '@angular/core';
import { MessageService } from '../../../utils';
import { WorkflowService, EditingService } from '../../../_services';
import { HeaderService } from '../../../workflow/services/header.service';
import { Title } from '@angular/platform-browser';
import { BrokerChangeStep, StepName } from '../../../_models';
import { BrokerChangeFormService } from '../../services/broker-change-form.service';
import { simulationAnimation } from '../../../workflow/models/animations';
import { Observable } from 'rxjs';
import { DocumentTypeValues } from '../../../workflow/models/document-type';
import { SimulationService } from '../../../workflow/services/simulation.service';
import { CheckDataService } from '../../../workflow/services/check-data.service';

const stepsSimulationAvailable = [StepName.Analysis, StepName.Validate_Broker_Change];
const stepsSimulationOnInit = [StepName.Validate_Broker_Change];

@Component({
    selector: 'broker-change',
    templateUrl: 'broker-change.tpl.html',
    animations: [simulationAnimation]
})
export class BrokerChangeComponent extends AbstractWorkflowComponent<BrokerChangeForm> implements OnInit {
    
    viewSimulation: boolean;
    simulationObservable: Observable<ArrayBuffer>;
    isSimulationAvailable : boolean;
    isDocumentationAvailable: boolean = false;
    
    constructor(
        route: ActivatedRoute,
        messageService: MessageService,
        workflowService: WorkflowService,
        stepWorkflowService: BrokerChangeService,
        private formService: BrokerChangeFormService,// This service should not be autowired here. the operation needed should be externalized.
        headerService: HeaderService,
        private titleService: Title,
        modalService: BsModalService,
        private simulationService: SimulationService,
        checkDataService: CheckDataService,
        editingService : EditingService 
    ) { 
        super(messageService, stepWorkflowService, modalService, route, workflowService, headerService, checkDataService, editingService);
    }

    protected prepareForm(): void {
        this.formService.removeEmptyPartnerForms(this.step.formData);
        this.formService.setAmendmentStatusToPartnerForms(this.step.formData);
    }

    ngOnInit(): void {
        this.titleService.setTitle("Broker change");
        this.init();
    }

    protected initSpecificStep(): void {
        this.isSimulationAvailable = this.isStepInList(stepsSimulationAvailable) && this.isPolicySaved();
        this.isDocumentationAvailable = BrokerChangeStep.toOrderedStep(this.step.stepWorkflow).isAfterOrEqual(BrokerChangeStep.Check_documentation);
        if(this.isStepInList(stepsSimulationOnInit)) {
            this.simulate();
        } else {
            this.onSimulationClose();
        }
    }

    private isPolicySaved(): boolean {
        return !!this.step.polId;
    }

    protected isStepUpdatable(stepName: string): boolean {
        return stepName === BrokerChangeStep.Analysis.stepName;
    }

    protected initSpecificModes(updatable: boolean, stepName: string): void {
        //TODO
    }

    generateDocumentation(): void {

        this.subs.push(this.editingService.createWorkflowDocument(this.step.workflowItemId, DocumentTypeValues.CHANGE_BROKER)
        .subscribe((response: any) => {
            if(response) {
                this.messageService.addAlertSuccess("Generate documentation request has been sent successfully", true); 
            }
        }));
    }

    refresh(): void {
        this.simulationObservable = this.simulationService.getSimulation(this.step.workflowItemId, DocumentTypeValues.CHANGE_BROKER);
    }

    simulate(): void {
        this.viewSimulation = true;
        this.refresh();
    }

    onSimulationClose(): void {
        this.viewSimulation = false;
    }

    private isStepInList(steps: string[]) : boolean {
        return steps.some(anyStep => anyStep === this.step.stepWorkflow);
    }
}