import { StateMode } from './../../../utils/mode';
import { OnInit, Input, Output, EventEmitter, Component, SimpleChanges } from '@angular/core';
import { PolicyService } from '../../../policy/services/policy.service';
import { LoadableComponent } from '../../../workflow/components/loadable.component';
import { Policy, WithdrawalStep, PolicyEntreeFees, Currency, StepName } from '../../../_models';
import { User } from '../../../_models/user';
import { TransactionForm } from '../../models/withdrawal-form';
import { WithdrawalFormService } from '../../services/withdrawal-form.service';
import { CurrencyService } from '../../../_services';
import { amountTaxTypeList } from '../../models/amount-tax-type';
import { DateUtils } from '../../../utils/date-utils';
import { paymentType } from '../../models/payment-type';
import { paymentAnimation } from '../../../workflow/models/animations';
import { TransferCandidate } from '../../models/transfer-candidate';
import { PolicyValuation } from '../../../_models/policy/policy-valuation';

@Component({
    selector: 'withdrawal-analysis',
    templateUrl: 'withdrawal-analysis.tpl.html',
    styles:[`
        .amount {
            margin-left: 4px;
        }
    `],
    animations: [paymentAnimation]
})
export class WithdrawalAnalysisComponent extends LoadableComponent implements OnInit {
    
    @Input() policyIdDisabled: boolean;
    @Input() usersList: User[];
    @Input() mode: string;
    @Input() paymentsMode: string;
    @Input() workflowItemId: number;
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

    valuation: any;
    policy: Policy;
    readonly stateMode = StateMode;
    showEditionSection: boolean;
    currencyList:Currency[] = [];
    valuationOnDate: PolicyValuation;
    readonly amountTaxTypeList = amountTaxTypeList; //TODO : fetch from a service.
    readonly Object = Object;
    showCps2: boolean;
    readonly paymentType = paymentType;

    constructor(private policyService: PolicyService,
                private formService: WithdrawalFormService,
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
        const currentStep = WithdrawalStep.toOrderedStep(stepName);
        this.showEditionSection = currentStep.isAfterOrEqual(WithdrawalStep.Analysis);
        this.showCps2 = currentStep.isAfterOrEqual(WithdrawalStep.Analysis);
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