import { StateMode } from './../../../utils/mode';
import { OnInit, Input, Output, EventEmitter, Component, SimpleChanges } from '@angular/core';
import { PolicyService } from '../../../policy/services/policy.service';
import { LoadableComponent } from '../../../workflow/components/loadable.component';
import { Policy, SurrenderStep, PolicyEntreeFees, Currency, StepName, transactionWithdrawalCode } from '../../../_models';
import { User } from '../../../_models/user';
import { CurrencyService } from '../../../_services';
import { DateUtils } from '../../../utils/date-utils';
import { paymentAnimation } from '../../../workflow/models/animations';
import { PolicyValuation } from '../../../_models/policy/policy-valuation';
import { SurrenderFormService } from '../../services/surrender-form.service';
import { TransactionForm } from '../../../withdrawal/models/withdrawal-form';
import { TransferCandidate } from '../../../withdrawal/models/transfer-candidate';
import { paymentType } from '../../../withdrawal/models/payment-type';
import { Transaction, TransactionsCommissions, TransactionsHistoryDetails, PolicyTransactionsDetailsOutput } from '@models/transaction';

const stepsPaymentsVisible = [SurrenderStep.Check_Documentation_And_Payment, SurrenderStep.Validate_documentation, SurrenderStep.Sending];
const stepsTransactionVisible = [...stepsPaymentsVisible];
@Component({
    selector: 'surrender-analysis',
    templateUrl: 'surrender-analysis.tpl.html',
    styles:[`
        .amount {
            margin-left: 4px;
        }
    `],
    animations: [paymentAnimation]
})
export class SurrenderAnalysisComponent extends LoadableComponent implements OnInit {
    
    @Input() policyIdDisabled: boolean;
    @Input() usersList: User[];
    @Input() mode: string;
    @Input() paymentsMode: string;
    @Input() securitiesTransferMode: string;    
    @Input() workflowItemId: number;
    surrenderDetails: PolicyTransactionsDetailsOutput;
    @Input() set stepName(value: string) {
        this._stepName = value;
        this.updateEditionSection(value);
    }
    _stepName: string;
    get stepName(): string {
        return this._stepName;
    }
    @Input() form: TransactionForm;
    @Input() transferCandidates: TransferCandidate[];
    @Output() formChange = new EventEmitter<TransactionForm>();
    @Output() onAddPayment = new EventEmitter<void>();
    @Output() onPolicyChoice = new EventEmitter<boolean>();
    showSecuritiesTransfer: boolean;
    showCashTransfer: boolean;    
    readonly paymentType = paymentType;
    valuation: any;    
    policy: Policy;
    readonly stateMode = StateMode;
    showEditionSection: boolean;
    currencyList:Currency[] = [];
    valuationOnDate: PolicyValuation;
    showFees: boolean;
    showTransaction: boolean;
    transaction: Transaction;
    
    readonly Object = Object;
    showCps2: boolean;

    constructor(private policyService: PolicyService,
                private formService: SurrenderFormService,
                private currencyService: CurrencyService) {
        super();
    }

    ngOnInit(): void {
        this.addSubscription(this.currencyService.getCurrencies().subscribe(result => this.currencyList = result));   
    }

    ngOnChanges(changes: SimpleChanges) {
        if(changes['form']) {
            const policyId = changes['form'].currentValue.policyId;
            if(policyId) {
                this.loadPolicyById(policyId);
                this.loadHoldings(policyId);        
                let effectiveDate: Date = changes['form'].currentValue.effectiveDate != undefined ? new Date(changes['form'].currentValue.effectiveDate) : new Date();
                this.onEffectiveDateChange(effectiveDate, policyId);
            }
        }
    }

    private loadPolicyById(policyId: string): void {
        const policySub = this.policyService.getPolicy(policyId).subscribe(result => {
            this.policy = result;
        });
        this.addSubscription(policySub);
    }

    private loadHoldings(policyId: string): void {

        const holdingSub = this.policyService.getPolicyValuation(policyId).subscribe(result => {
            this.valuation = result;
            this.showFees = result && result.holdings && result.holdings.length > 0;
            this.onPolicyChoice.emit(this.valuation.holdings.every(holding => holding.fundSubType.trim() == 'FE'));
        });
        this.addSubscription(holdingSub);
    }

    initForm(policy: Policy): void {
        const formSub = this.formService.initWithdrawalForm(policy.polId, this.workflowItemId, this.form.secondCpsUser).subscribe((result: TransactionForm) => {                       
            this.form = result;
            this.formChange.emit(this.form);         
        });
        this.addSubscription(formSub);
    }

    onPolicyChange(policy: Policy): void {

        this.policy = policy;

        if(policy != null) {
            //init from policy.
            this.initForm(policy);
            this.loadHoldings(policy.polId);        
        } else {
            this.clearForm();
        }        
    }

    private updateEditionSection(stepName: string): void {
        // define if the lower part (edition section) can be displayed or not
        const currentStep = SurrenderStep.toOrderedStep(stepName);
        this.showEditionSection = currentStep.isAfterOrEqual(SurrenderStep.Analysis);
        this.showCps2 = currentStep.isAfterOrEqual(SurrenderStep.Analysis);
        this.showSecuritiesTransfer = currentStep.isAfterOrEqual(SurrenderStep.Analysis);
        this.showCashTransfer = stepsPaymentsVisible.includes(currentStep);        
        this.showTransaction = stepsTransactionVisible.includes(currentStep);    
        if(this.showTransaction && this.workflowItemId) {
            //The uggliest code I have ever written. but how on earth is it possible to do better with this code ????
            const sub = this.formService.getSurrenderDetails(this.workflowItemId)
                .subscribe(result => {
                    this.transaction = result.transaction;
                    this.surrenderDetails = this.policyService.mapTransactionDetails(result.transaction, result.details);
                });
            this.addSubscription(sub);
        }    
    }

    clearForm(): void {
        this.form = new TransactionForm();
        this.formChange.emit(this.form);
    }

    onEffectiveDateChange(event: Date, policyId: string) {
        
        if(isNaN(event.getTime())) {
            return;
        }
        
        this.addSubscription(this.policyService.getPolicyValuation(policyId, '', DateUtils.formatToIsoDate(event)).subscribe(
            result => this.valuationOnDate = result
        ));
    }

    onSpecificAmountRadioChange() {
        this.form.amountType = null;
        this.form.amount = null;
    }

    onAddPaymentEvent(): void {
        this.onAddPayment.emit();
    }
}