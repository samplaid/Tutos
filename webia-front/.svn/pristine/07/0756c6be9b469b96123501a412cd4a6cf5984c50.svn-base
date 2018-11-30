import { OnChanges } from '@angular/core/src/metadata/lifecycle_hooks';
import { SurveyService } from './../../../survey/survey.service';
import { AppForm } from './../../../_models/formData/appForm';
import { whole_life, year_term } from './../../../_models/constant';
import { StateMode } from './../../../utils/mode';
import { Input, Component, OnInit, SimpleChanges} from '@angular/core';
import { User } from '../../../_models/user';
import { LoadableComponent } from "../../../workflow/components/loadable.component";
import { PolicyService } from '../../../policy/services/policy.service';
import { CurrencyService, EditingService } from '../../../_services';
import { AdditionalPremiumFormService } from "../../services/additional-premium-form.service";
import { FundService } from '../../../fund/fund.service';
import { MessageService } from '../../../utils';
import { Policy } from '../../../_models/policy/policy';
import { PolicyEntreeFees } from '../../../_models/policy/policy-entry-fees';
import { StepName, AdditionalPremiumStep } from '../../../_models/step';
import { Fund } from '../../../_models/fund';
import { CreateEditingResponse } from '../../../_models/editing/editing-response';
import { FundFormService } from '../../../_services/fund-form.service';
import { PolicyValuation } from '../../../_models/policy/policy-valuation';

@Component({
    selector: 'additional-premium-analysis',
    templateUrl: 'additional-premium-analysis.tpl.html'
})
export class AdditionalPremiumAnalysisComponent extends LoadableComponent implements OnInit, OnChanges {

    @Input() usersList: User[];
    @Input() form: AppForm;
    policy: Policy;
    valuation: any;
    entryFees: PolicyEntreeFees;
    readonly stateMode = StateMode;
    currencyList: any[];
    @Input() mode: string;
    @Input() paymentMode: string;
    @Input() cpsMode: string;
    policyPickerMode: string;
    @Input() workflowItemId: number;
    @Input() policyIdDisabled: boolean;
    @Input() set stepName(value: string) {
        this._stepName = value;
        this.updateEditionSection(value);
    }
    _stepName: string;
    get stepName(): string {
        return this._stepName;
    }
    generateMandatDeGestion: boolean = false;
    showValuationAmount: boolean = false;
    showEditionSection: boolean;
    showRegistrationSection: boolean = false;
    showFeesSection: boolean;
    showInvestmentSection: boolean;
    showPaymentSection: boolean;
    term: string;
    showCps1: boolean;
    showCps2: boolean;
    undeletableFundIds: string[] = [];

    private loadPolicyById(policyId: string): void {
        const policySub = this.policyService.getPolicy(policyId).subscribe(result => {
            this.initProductInfo(result);
            this.policy = result;
        });
        this.addSubscription(policySub);
    }

    private loadHoldings(policyId: string): void {

        const holdingSub = this.policyService.getPolicyValuation(policyId).subscribe(result => {
            this.valuation = result;
            this.updateUndeletableFunds(result);
        });
        this.addSubscription(holdingSub);
    }

    private updateUndeletableFunds(valuation: PolicyValuation): void {
        if(valuation && valuation.holdings) {
            this.undeletableFundIds = valuation.holdings
                .filter(holding => this.fundFormService.isUndeletable(holding.fundSubType))
                .map(holding => holding.fundId);
        }
    }

    private loadEntryFees(policyId: string): void {
        const entryFeesSub = this.policyService.getEntryFees(policyId).subscribe(result => {
            this.entryFees = result;
        });
        this.addSubscription(entryFeesSub);
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
                this.loadEntryFees(policyId);
                this.refreshSurvey();
            }
        }
    }

    private updateEditionSection(stepName: string): void {
        // define if the lower part (edition section) can be displayed or not
        const currentStep = AdditionalPremiumStep.toOrderedStep(stepName);
        this.generateMandatDeGestion = currentStep.isAfterOrEqual(AdditionalPremiumStep.Generate_Management_Mandate);
        this.showRegistrationSection = StepName.Registration === stepName;
        this.showFeesSection = currentStep.isAfterOrEqual(AdditionalPremiumStep.Analysis) || StepName.Registration === stepName;
        this.showInvestmentSection = currentStep.isAfterOrEqual(AdditionalPremiumStep.Analysis);
        this.showPaymentSection = currentStep.isAfterOrEqual(AdditionalPremiumStep.Analysis);
        this.showEditionSection = this.showRegistrationSection || this.showFeesSection || this.showInvestmentSection || this.showPaymentSection;
        this.showCps1 = StepName.Waiting_Dispatch === stepName;
        this.showCps2 = AdditionalPremiumStep.Waiting_Dispatch.isBeforeOrEqual(currentStep);
        this.showValuationAmount = currentStep.isAfterOrEqual(AdditionalPremiumStep.Premium_input_and_nav);
    }

    constructor(private policyService: PolicyService, 
                private formService: AdditionalPremiumFormService,
                private fundService: FundService,
                private surveyService: SurveyService,
                private currencyService: CurrencyService,
                private messageService: MessageService,
                private fundFormService: FundFormService,
                private editingService: EditingService) {
        super();
    }

    onPolicyChange(policy: Policy): void {

        this.policy = policy;

        if(policy != null) {
            this.initForm(policy);
            this.initProductInfo(policy);
            this.loadHoldings(policy.polId);
            this.loadEntryFees(policy.polId);
        } else {
            this.clearForm();
        }        
    }
    
    clearForm(): void {
        this.form.policyId = null;
    }

    initForm(policy: Policy): void {
        const formSub = this.formService.initAdditionalPremiumForm(policy.polId, this.workflowItemId).subscribe((result: AppForm) => {
            Object.assign(this.form, result);
            this.refreshSurvey();
        });
        this.addSubscription(formSub);
    }

    initProductInfo(policy: Policy): void {
        // complete the product
        this.term = policy.firstPolicyCoverages.term == 0 ? whole_life : year_term;
    }

    refreshSurvey(): void {
        this.surveyService.refreshFundCodes(this.form.fundForms);
    }

    public applyManagementMandateDocRequest(fund: Fund): void {
        let request = this.editingService.prepareMandatManagementEditingRequest(fund, this.form);
        this.subs.push(this.editingService.applyManagementMandateDocRequest(request)
                .subscribe((response: CreateEditingResponse) => {    
                    if(response && response.requestId) {
                        this.messageService.addAlertSuccess('Generate "Mandat de Gestion" has been sent successfully', true); 
                    }                                                        
        }));
    }

    onPaymentDtChange(event: Date) {
        this.form.paymentDt = event;
        this.fundService.updateValuations(this.form.fundForms, event);
    }
}