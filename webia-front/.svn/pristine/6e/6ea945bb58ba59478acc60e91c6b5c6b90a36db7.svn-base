import { whole_life, year_term, defaultTranslationLanguage } from './../../../_models/constant';
import { StateMode } from './../../../utils/mode';
import { Input, Component, OnInit,} from '@angular/core';
import { ClauseService, AppliParamService } from '../../../_services';
import { User } from '../../../_models/user';
import { Policy, ClientRoleActivationFlag, BeneficiaryChangeStep, PolicyClauses } from "../../../_models/index";
import { AnalysisService } from '../../../analysis';
import { ProductClauseRequest } from '../../../analysis/analysis.service';
import { OptionDetail } from '../../../_models/option';
import { LoadableComponent } from "../../../workflow/components/loadable.component";
import { BeneficiaryChangeFormService } from '../../services/beneficiary-change-form.service';
import { BeneficiaryChangeForm } from '../../models/beneficiary-form';
import { PolicyService } from '../../../policy/services/policy.service';
import { BeneficiaryChange } from '../../models/beneficiary-change';

@Component({
    selector: 'beneficiary-change-analysis',
    templateUrl: 'beneficiary-change-analysis.tpl.html'
})
export class BeneficiaryChangeAnalysisComponent extends LoadableComponent implements OnInit {

    @Input() usersList: User[];
    _form: BeneficiaryChangeForm;
    @Input() set form(formValue: BeneficiaryChangeForm) {
        this._form = formValue;
        if(!!formValue && !!formValue.policyId) {
            this.loadPolicyById(formValue.policyId);
        }
    }
    get form() : BeneficiaryChangeForm {
        return this._form;
    }
    policy: Policy;
    policyClauses: any;
    policyClausesBefore: any;
    benefClauseStdList: any;
    benefActivationFlag: ClientRoleActivationFlag;
    policyHolderActivationFlag: ClientRoleActivationFlag;
    readonly stateMode = StateMode;
    @Input() mode: string;
    policyPickerMode: string;
    showBenefLifeClause: boolean = false;
    capiProductCodes: string[] = [];
    isNotCapi: boolean;
    cPRRoleList: OptionDetail[];
    @Input() workflowItemId: number;
    @Input() isWorkflowCompleted: boolean = false;
    @Input() policyIdDisabled: boolean;
    @Input() set stepName(value: string) {
        this.updateEditionSection(value);
    }
    availableSubRoles: number[];    
    showEditionSection: boolean = true;
    term: string;
    beneficiaryChange: BeneficiaryChange;
    readonly maxDate = new Date();

    private loadPolicyById(policyId: string): void {
        const policySub = this.policyService.getPolicy(policyId).subscribe(result => {
            this.initProductInfo(result);
            this.policy = result;
        });
        this.addSubscription(policySub);
    }

    ngOnInit(): void {

        // retrieve the list of the capitalisation product codes
        this.addSubscription(this.appliParamService.getApplicationParameter('PRODUCT_CAPI').subscribe(appliParam => this.capiProductCodes = appliParam.value.split(",").map(s=>s.trim().toUpperCase()), e=>{}));

        // load resources for the business rules (i.e. the list of code of the available sub role in this workflow)
        this.addSubscription(this.appliParamService.getApplicationParameter('CHANGE_BENEF_AVAILABLE_SUB_ROLES').subscribe(appliParam => {
            this.availableSubRoles = appliParam.value.split(",").map(x => parseInt(x));
        }));
    }

    private updateEditionSection(stepName: string): void {
        this.showEditionSection = BeneficiaryChangeStep.toOrderedStep(stepName).isBefore(BeneficiaryChangeStep.Generate_Documentation);
    }

    constructor(private policyService: PolicyService, 
                private analysisService: AnalysisService,
                private clauseService: ClauseService,
                private formService: BeneficiaryChangeFormService,
                private appliParamService: AppliParamService) {
        super();
    }

    onPolicyChange(policy: Policy): void {

        this.policy = policy;

        if(policy != null) {
            this.initForm(policy);
            this.initProductInfo(policy);
        } else {
            this.clearForm();
        }        
    }
    
    clearForm(): void {
        this.form.policyId = null;
        this.form.changeDate = null;
        this.form.deathBenefClauseForms = [];
        this.form.lifeBenefClauseForms = [];
        this.form.deathBeneficiaries = [];     
        this.form.lifeBeneficiaries = [];
        this.form.otherClients = [];
        this.form.policyHolders = [];
    }

    initForm(policy: Policy): void {
        const formSub = this.formService.initBeneficiaryChangeForm(policy.polId, this.workflowItemId).subscribe((result: BeneficiaryChangeForm) => {
            Object.assign(this.form, result, { secondCpsUser: this.form.secondCpsUser});
        });
        this.addSubscription(formSub);
    }

    initProductInfo(policy: Policy): void {
        
        this.addSubscription(this.policyService.getPolicyClauses(policy.polId, policy.product.prdId, defaultTranslationLanguage).subscribe(clauses => this.policyClauses = clauses));
        if(this.isWorkflowCompleted)
            this.addSubscription(this.loadBeneficiaryChangeDetails(policy, this.workflowItemId));

        // infer the clauses and sub-roles available based on the product bound to the police
        this.addSubscription(this.clauseService.getBenefClauseStdByProductStateless(policy.product.prdId).subscribe(clauses => this.benefClauseStdList = clauses)); 
        this.addSubscription(this.analysisService.solveClientRoleActivation(policy.product.nlCountry).subscribe(benefActivationFlag => this.benefActivationFlag = benefActivationFlag));

        // complete the product
        this.isNotCapi = !this.capiProductCodes.includes(policy.product.prdId);
        this.term = policy.firstPolicyCoverages.term == 0 ? whole_life : year_term;
        const productClause: ProductClauseRequest = {isNotCapi : this.isNotCapi, nlCountry: policy.product.nlCountry};
        this.showBenefLifeClause = this.analysisService.canShowBeneficiaryLifeClause(productClause, this.term);
        this.addSubscription(this.analysisService.getClientRolesByProduct(policy.product.prdId, !this.isNotCapi,  !!this.term).subscribe(roles => {
            this.cPRRoleList = roles.filter(role => this.availableSubRoles.includes(role.number));
        }));

        this.addSubscription(this.analysisService.solvePolicyHolderActivation(policy.product.nlCountry, !this.isNotCapi, this.term === year_term).subscribe(policyHolderActivationFlag => {
            this.policyHolderActivationFlag = policyHolderActivationFlag;            
            // for the specific case of the beneficiary change, we must make sure that only the legalheirs are updatable
            this.policyHolderActivationFlag.activatedUsufructuary = false;
            this.policyHolderActivationFlag.activatedBareOwner = false;
        }));
    }
    
    private loadBeneficiaryChangeDetails(policy: Policy, workflowItemId:number){
        this.beneficiaryChange = null;
        return this.policyService.getBeneficiaryChange(policy.polId, workflowItemId, policy.product.prdId, defaultTranslationLanguage).subscribe((result: BeneficiaryChange) => { 
                              this.beneficiaryChange = result;
                              let policyClauses_tmp = new PolicyClauses();
                              policyClauses_tmp.death = result.deathBenefClauses.filter(clause => clause.status == 1) || []; // conaints only Textual clauses
                              policyClauses_tmp.maturity = result.lifeBenefClauses.filter(clause => clause.status == 1) || []; // conaints only Textual clauses
                              this.policyClausesBefore = policyClauses_tmp;
                           });
    }
    
}