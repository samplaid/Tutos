import { BsModalService } from 'ngx-bootstrap';
import { ActivatedRoute } from '@angular/router';
import { AbstractWorkflowComponent } from '@workflow/components/workflow-component';
import { OnInit, Component } from '@angular/core';
import { MessageService } from '@utils/index';
import { WorkflowService, EditingService } from '@services/index';
import { HeaderService } from '@workflow/services/header.service';
import { Title } from '@angular/platform-browser';
import { simulationAnimation } from '@workflow/models/animations';
import { Observable } from 'rxjs';
import { StepName } from '@models/step/step.enum';
import * as _ from "lodash";
import { SimulationService } from '@workflow/services/simulation.service';
import { CheckDataService } from '@workflow/services/check-data.service';
import { SurrenderService } from 'app/surrender/services/surrender-step.service';
import { TransactionForm } from 'app/withdrawal/models/withdrawal-form';
import { TransferForm, SurrenderTransferFormData } from 'app/withdrawal/models/transfer';
import { TransferCandidate } from 'app/withdrawal/models/transfer-candidate';
import { TransactionStatus } from 'app/withdrawal/models/transaction-status';
import { TransferService } from 'app/withdrawal/services/transfer.service';
import { SurrenderStep } from '../../../_models/step/step.enum';
import { CreateEditingRequest } from '../../../_models/editing/editing-request';
import { WithdrawalSimulationService } from 'app/withdrawal/services/withdrawal-simulation.service';
import { CheckStep } from '@models/survey/checkStep';

const stepsFormUpdatable = [SurrenderStep.New.stepName, SurrenderStep.Analysis.stepName, SurrenderStep.Request_To_Client_Partner.stepName, SurrenderStep.Complete_Anaylsis.stepName, SurrenderStep.Awaiting_Cash_Transfer_fees.stepName, SurrenderStep.Awaiting_Cash_Wealins_Account.stepName];
const stepsPaymentsUpdatable = [StepName.Check_Documentation_And_Payment];
const stepsSecuritiesTransferUpdatable = [...stepsFormUpdatable];
const stepsSimulationAvailable = [SurrenderStep.Analysis.stepName, SurrenderStep.Validate_Analysis.stepName, SurrenderStep.Awaiting_Cash_Transfer_fees.stepName, 
                                    SurrenderStep.Awaiting_Cash_Wealins_Account.stepName, SurrenderStep.Validate_Input.stepName, SurrenderStep.Review_Documentation.stepName,
                                    SurrenderStep.Check_Documentation_And_Payment.stepName, SurrenderStep.Request_To_Client_Partner.stepName,
                                    SurrenderStep.Complete_Anaylsis.stepName, SurrenderStep.Validate_documentation.stepName];

@Component({
    selector: 'surrender',
    templateUrl: 'surrender.tpl.html',
    animations: [simulationAnimation]
})
export class SurrenderComponent extends AbstractWorkflowComponent<TransactionForm> implements OnInit {

    viewSimulation: boolean;
    viewMultipleDocumentSimulations: boolean;
    simulationObservable: Observable<ArrayBuffer>;
    isSimulationAccountingNotesAvailable: boolean = false;
    isSimulationTitleTransfersAvailable: boolean = false;
    isSimulationAssetManagerDocumentAvailable: boolean = false;
    isSimulationAmendmentAvailable: boolean = false;
    isSimulationDepositBankFaxAvailable: boolean = false;
    isAtLeastOneSimulationAvailable:boolean = false;
    cashTransferTemplate: TransferForm;
    documentType: string;
    transferCandidates: TransferCandidate[] = [];
    editingRequests: CreateEditingRequest[];
    paymentsMode: string;
    securitiesTransferMode: string;

    constructor(
        route: ActivatedRoute,
        messageService: MessageService,
        workflowService: WorkflowService,
        stepWorkflowService: SurrenderService,
        headerService: HeaderService,
        private titleService: Title,
        modalService: BsModalService,
        private transferService: TransferService,
        private simulationService: SimulationService,
        checkDataService: CheckDataService,
        editingService : EditingService,
        private withdrawalSimulationService: WithdrawalSimulationService
    ) { 
        super(messageService, stepWorkflowService, modalService, route, workflowService, headerService, checkDataService, editingService);
    }

    protected initSpecificStep(): void {
		this.addSubscription(this.transferService.initFormDataForSurrender(this.step.workflowItemId).subscribe(result => this.afterInitialFormDataLoad(result)));
		this.evaluateSimulationAvailibility();
    }   

    private afterInitialFormDataLoad(transferFormData: SurrenderTransferFormData): void {
        this.cashTransferTemplate = transferFormData.cashTransfer;
        this.transferCandidates = transferFormData.transferCandidates;
        if(this.step.stepWorkflow === StepName.Check_Documentation_And_Payment) {
            this.initTransferFormData();
        }
    }

    private initTransferFormData(): void {
        if(this.hasNoCashTransfer()) {           
            this.createCashTransfer();
        }     
    }

    private createCashTransfer(): void {        
        this.step.formData.payments = [];
        this.addCashTransfer();        
    }

    private addCashTransfer(): void {
        const payment = _.cloneDeep(this.cashTransferTemplate);
        this.step.formData.payments.push(payment);
    }

    clearSecuritiesTransfer(): void {
        this.step.formData.securitiesTransfer = null;
    }

    clearCashTransfer(): void {
        this.step.formData.payments = [];
    }

    private hasNoCashTransfer(): boolean {
        return !this.step.formData.payments || this.step.formData.payments.length == 0; 
    }

    ngOnInit(): void {
        this.titleService.setTitle("Surrender");
        this.init();
    }

    protected isStepUpdatable(stepName: string): boolean {
        return stepsFormUpdatable.includes(stepName) && this.isFormStatusEditable();
    }

    protected initSpecificModes(updatable: boolean, stepName: string): void {
        this.paymentsMode = this.getEditableModeOnCondition(updatable != false && stepsPaymentsUpdatable.includes(stepName));
        this.securitiesTransferMode = this.getEditableModeOnCondition(updatable != false && stepsSecuritiesTransferUpdatable.includes(stepName));        
    }

    private isFormStatusEditable(): boolean {
        return this.step.formData.status !== TransactionStatus.IN_FORCE;
    }

    refresh(): void {
        this.simulationObservable = this.simulationService.getSimulation(this.step.workflowItemId, this.documentType);
    }

    simulate(documentType: string): void {
        this.viewSimulation = true;
        this.viewMultipleDocumentSimulations = false;
        this.documentType = documentType;
        this.refresh();
    }

    onSimulationClose(): void {
        this.viewSimulation = false;
    }

    evaluateSimulationAvailibility(): void {
        let currentStep: SurrenderStep = SurrenderStep.toOrderedStep(this.step.stepWorkflow);
        const inSimulationAvailableStep =  stepsSimulationAvailable.includes(currentStep.stepName);
        const securitiesTransferBeforeDebitOfFinancialFees: boolean = this.checkDataService.isSecuritiesTransferBeforeDebitOfFinancialFeesChecked(this.step.checkSteps);

        this.isSimulationAssetManagerDocumentAvailable = inSimulationAvailableStep && currentStep.isAfterOrEqual(SurrenderStep.Analysis);
        this.isSimulationDepositBankFaxAvailable = inSimulationAvailableStep && (
                                                        (securitiesTransferBeforeDebitOfFinancialFees && currentStep.isAfterOrEqual(SurrenderStep.Analysis))
                                                     || (!securitiesTransferBeforeDebitOfFinancialFees && currentStep.isAfterOrEqual(SurrenderStep.Awaiting_Cash_Transfer_fees))
                                                   );
        this.isSimulationAmendmentAvailable = currentStep.stepName == SurrenderStep.Review_Documentation.stepName;

        this.isAtLeastOneSimulationAvailable = this.isSimulationAccountingNotesAvailable || this.isSimulationTitleTransfersAvailable
                                            || this.isSimulationAssetManagerDocumentAvailable || this.isSimulationAmendmentAvailable
                                            || this.isSimulationDepositBankFaxAvailable;
    }

    simulateAssetManagerDocuments(): void {
        this.viewSimulation = false;
        this.viewMultipleDocumentSimulations = true;

        this.addSubscription(this.withdrawalSimulationService.createEnoughCashEditings(this.step.workflowItemId).subscribe(
            editingRequests => this.editingRequests = editingRequests
        ));
    }

    simulateDepositBankFax(): void {
        this.viewSimulation = false;
        this.viewMultipleDocumentSimulations = true;
        this.addSubscription(this.withdrawalSimulationService.createFaxSurrenderDepositBankEditings(this.step.workflowItemId).subscribe(
            editingRequests => this.editingRequests = editingRequests
        ));
    }

    closeAssetManagerDocuments() : void {
        this.viewMultipleDocumentSimulations = false;
    }

    onPolicyChoice(onlyFE: boolean): void {
        // update value of the securities transfer question (if present)
        const securitiesTransferCheckStep: CheckStep = this.step.checkSteps.find(checkstep => checkstep.metadata == 'SECURITIES_TRANSFER');
        if(securitiesTransferCheckStep != undefined) {
            securitiesTransferCheckStep.check.checkData.dataValueYesNoNa = onlyFE ? 'NO' : null;
        }
    }
}