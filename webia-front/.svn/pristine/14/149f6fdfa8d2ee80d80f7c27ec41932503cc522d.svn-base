import { BsModalService } from 'ngx-bootstrap';
import { ActivatedRoute } from '@angular/router';
import { AbstractWorkflowComponent } from '@workflow/components/workflow-component';
import { OnInit, Component } from '@angular/core';
import { MessageService, StateMode } from '@utils/index';
import { WorkflowService, EditingService } from '@services/index';
import { HeaderService } from '@workflow/services/header.service';
import { Title } from '@angular/platform-browser';
import { simulationAnimation } from '@workflow/models/animations';
import { Observable } from 'rxjs';
import { WithdrawalStep, StepName } from '@models/step/step.enum';
import { WithdrawalService } from '../../services/withdrawal-step.service';
import { TransactionForm } from '../../models/withdrawal-form';
import { TransferService } from '../../services/transfer.service';
import { TransferFormData } from '../../models/transfer';
import { paymentType } from '../../models/payment-type';
import * as _ from "lodash";
import { SimulationService } from '@workflow/services/simulation.service';
import { CheckDataService } from '@workflow/services/check-data.service';
import { TransferCandidate } from '../../models/transfer-candidate';
import { TransactionStatus } from '../../models/transaction-status';
import { WithdrawalSimulationService } from 'app/withdrawal/services/withdrawal-simulation.service';
import { CreateEditingRequest } from '@models/editing/editing-request';

const stepsFormUpdatable = [StepName.New_Business, StepName.Analysis, StepName.Request_To_Client_Partner, StepName.Complete_Anaylsis, StepName.Awaiting_Cash_Transfer];
const stepsPaymentsUpdatable = [...stepsFormUpdatable, StepName.Check_documentation];
const stepsSimulationAvailable = [WithdrawalStep.Analysis.stepName, WithdrawalStep.Validate_Analysis.stepName, WithdrawalStep.Validate_Input.stepName, WithdrawalStep.Complete_Anaylsis.stepName, WithdrawalStep.Awaiting_Cash_Transfer.stepName, WithdrawalStep.Validate_documentation.stepName];                                    
@Component({
    selector: 'withdrawal',
    templateUrl: 'withdrawal.tpl.html',
    animations: [simulationAnimation]
})
export class WithdrawalComponent extends AbstractWorkflowComponent<TransactionForm> implements OnInit {

    viewSimulation: boolean;
    viewInstructionsToAssetManagerSimulations: boolean;
    simulationObservable: Observable<ArrayBuffer>;
    isSimulationAccountingNotesAvailable: boolean = false;
    isSimulationTitleTransfersAvailable: boolean = false;
    isSimulationInstructionsToAssetManagerAvailable: boolean = false;
    isSimulationAmendmentAvailable: boolean = false;
    transferFormData: TransferFormData;
    documentType: string;
    transferCandidates: TransferCandidate[] = [];
    enoughCashEditingRequests: CreateEditingRequest[];
    paymentsMode: string;

    constructor(
        route: ActivatedRoute,
        messageService: MessageService,
        workflowService: WorkflowService,
        stepWorkflowService: WithdrawalService,
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
		this.addSubscription(this.transferService.initFormDataForWithdrawal(this.step.workflowItemId).subscribe(result => this.afterInitialFormDataLoad(result)));
		this.evaluateSimulationAvailibility();
}

    private afterInitialFormDataLoad(transferFormData: TransferFormData): void {
        this.transferFormData = transferFormData;
        this.transferCandidates = transferFormData.transferCandidates;
        if(this.step.stepWorkflow === StepName.Analysis) {
            this.initTransferFormData();
        }
    }

    private initTransferFormData(): void {
        if(this.isCashTransfer() && this.hasNoCashTransfer()) {
            this.createCashTransfer();
            //The clear is important in case of a reject.
            this.clearSecuritiesTransfer();
        } else if(this.isSecuritiesTransfer() && this.hasNoSecuritiesTransfer()) {
            this.createSecuritiesTransfer();
            //The clear is important in case of a reject.
            this.clearCashTransfer();
        }        
    }

    clearSecuritiesTransfer(): void {
        this.step.formData.securitiesTransfer = [];
    }

    clearCashTransfer(): void {
        this.step.formData.payments = [];
    }

    private createSecuritiesTransfer(): void {
        const securitiesTransfer = _.cloneDeep(this.transferFormData.securitiesTransfer);
        this.step.formData.securitiesTransfer = [securitiesTransfer];
    }

    private createCashTransfer(): void {        
        this.step.formData.payments = [];
        this.addCashTransfer();        
    }

    private addCashTransfer(): void {
        const payment = _.cloneDeep(this.transferFormData.cashTransfer);
        this.step.formData.payments.push(payment);
    }

    private isCashTransfer(): boolean {
        return this.step.formData.paymentType === paymentType.CASH_TRANSFER;
    }

    private isSecuritiesTransfer(): boolean {
        return this.step.formData.paymentType === paymentType.SECURITY_TRANSFER
    }

    private hasNoSecuritiesTransfer(): boolean {
        return !this.step.formData.securitiesTransfer || this.step.formData.securitiesTransfer.length == 0; 
    }

    private hasNoCashTransfer(): boolean {
        return !this.step.formData.payments || this.step.formData.payments.length == 0; 
    }

    ngOnInit(): void {
        this.titleService.setTitle("Withdrawal");
        this.init();
    }

    protected isStepUpdatable(stepName: string): boolean {
        return stepsFormUpdatable.includes(stepName) && this.isFormStatusEditable();
    }

    protected initSpecificModes(updatable: boolean, stepName: string): void {
        this.paymentsMode = this.getEditableModeOnCondition(updatable != false && stepsPaymentsUpdatable.includes(stepName));
    }

    private isFormStatusEditable(): boolean {
        return this.step.formData.status !== TransactionStatus.IN_FORCE;
    }

    refresh(): void {
        this.simulationObservable = this.simulationService.getSimulation(this.step.workflowItemId, this.documentType);
    }

    simulate(documentType: string): void {
        this.viewSimulation = true;
        this.viewInstructionsToAssetManagerSimulations = false;
        this.documentType = documentType;
        this.refresh();
    }

    simulateInstructionsToAssetManagerDocuments(): void {
        this.viewSimulation = false;
        this.viewInstructionsToAssetManagerSimulations = true;

        this.addSubscription(this.withdrawalSimulationService.createEnoughCashEditings(this.step.workflowItemId).subscribe({
            next: editingRequests => this.enoughCashEditingRequests = editingRequests,
            error: err => {
                this.viewSimulation = true;
                this.viewInstructionsToAssetManagerSimulations = false;
            }
        }));
    }

    onSimulationClose(): void {
        this.viewSimulation = false;
    }

    evaluateSimulationAvailibility(): void {
        let currentStep: WithdrawalStep = WithdrawalStep.toOrderedStep((this.step.stepWorkflow));

        const inSimulationAvailableStep =  stepsSimulationAvailable.includes(currentStep.stepName);

        this.isSimulationAccountingNotesAvailable =    inSimulationAvailableStep
                                                    && this.step.formData.paymentType == paymentType.CASH_TRANSFER
                                                    && this.step.formData.payments.some(payment => payment && payment.transferStatus == "READY");
        this.isSimulationTitleTransfersAvailable =     inSimulationAvailableStep
                                                    && this.step.formData.paymentType == paymentType.SECURITY_TRANSFER
                                                    && !!this.step.formData.securitiesTransfer 
                                                    && !!this.step.formData.securitiesTransfer[0]                                                   
                                                    && this.step.formData.securitiesTransfer[0].transferStatus == "READY";        
        this.isSimulationAmendmentAvailable = currentStep.stepName == WithdrawalStep.Review_Documentation.stepName;
        this.isSimulationInstructionsToAssetManagerAvailable = inSimulationAvailableStep && this.containsFidOrFas();
    }

    private containsFidOrFas(): boolean {
        return !!this.step && this.step.formData.fundTransactionForms.some(fundTransactionForm => fundTransactionForm.fundTp == 'FID' || fundTransactionForm.fundTp == 'FAS');
    }

    closeInstructionsToAssetManager() : void {
        this.viewInstructionsToAssetManagerSimulations = false;
    }
}