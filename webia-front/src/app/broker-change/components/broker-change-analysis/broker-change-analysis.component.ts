import { StateMode } from './../../../utils/mode';
import { OnInit, Input, Output, EventEmitter, Component, SimpleChanges } from '@angular/core';
import { PolicyService } from '../../../policy/services/policy.service';
import { LoadableComponent } from '../../../workflow/components/loadable.component';
import { Policy, BrokerChangeStep, PartnerForm, UoptDetails, AgentLite } from '../../../_models';
import { BrokerChangeForm } from '../../models/broker-change-form';
import { User } from '../../../_models/user';
import { UoptDetailService, AppliParamService } from '../../../_services';
import { BrokerChangeFormService } from '../../services/broker-change-form.service';
import { Observable } from 'rxjs/Observable';
import { AgentService } from '../../../agent';
import { PolicyValuation } from '../../../_models/policy/policy-valuation';
import { BrokerChangeService } from '../../services/broker-change-step.service';
import { BrokerChange } from '../../models/broker-change';

@Component({
    selector: 'broker-change-analysis',
    templateUrl: 'broker-change-analysis.tpl.html'
})
export class BrokerChangeAnalysisComponent extends LoadableComponent implements OnInit {
    
    @Input() policyIdDisabled: boolean;
    @Input() usersList: User[];
    @Input() mode: string;
    @Input() workflowItemId: number;
    @Input() isWorkflowCompleted: boolean = false;
    @Input() set stepName(value: string) {
        this._stepName = value;
        this.updateEditionSection(value);
    }
    _stepName: string;
    get stepName(): string {
        return this._stepName;
    }
    @Input() form: BrokerChangeForm;
    @Output() formChange = new EventEmitter<BrokerChangeForm>();
    policy: Policy;
    valuation: PolicyValuation;
    readonly stateMode = StateMode;
    showEditionSection: boolean;
    contactFunctions: UoptDetails[] = [];
    sendingRuleList: UoptDetails[] = [];
    mail2AgentList: AgentLite[];
    wealinsBrokerId: string;
    brokerChange : BrokerChange;

    constructor(private policyService: PolicyService,
                private formService: BrokerChangeFormService,
                private BCService: BrokerChangeService,
                private agentService: AgentService,
                private uoptDetailService: UoptDetailService,
                private appliParamService: AppliParamService) {
        super();
    }

    ngOnInit(): void {
        this.addSubscription(this.uoptDetailService.getTypeOfAgentContact().subscribe(contactFunctions => this.contactFunctions = contactFunctions));
        this.addSubscription(this.uoptDetailService.getSendingRules().subscribe(rules=> this.sendingRuleList = rules));
        this.addSubscription(this.appliParamService.getApplicationParameter('WEALINS_BROKER_ID').subscribe(wealinsBrokerId => this.wealinsBrokerId = wealinsBrokerId.value));
    }

    ngOnChanges(changes: SimpleChanges) {
        if(changes['form']) {
            const policyId = changes['form'].currentValue.policyId;
            if(policyId) {
                this.loadPolicyById(policyId);
                if ( this.isWorkflowCompleted ){
                    this.addSubscription(this.BCService.getBrokerChangeBefore(this.workflowItemId).subscribe(bc => {
                        this.brokerChange = bc;
                        this.loadHoldings(policyId, this.dayBefore(bc.changeDate));
                    }));
                } else {
                    this.loadHoldings(policyId);
                }
                
            }
            this.formService.updateNullPartnerForms(this.form);            
        }
    }

    /**
     * get date yyyy-MM-dd and return the day before as string on format yyyy-MM-dd
     * @param text - date yyyy-MM-dd
     */
    private dayBefore(text:string):string{
        let dateString = '';
        try {
            let day = new Date(text);
            let daybefore = new Date(day.getTime() + -24*3600*1000);
            return daybefore.toISOString().substring(0,10);
        } catch (e){
            console.error("Fail to get dayBefore of date string '"+text+"' (expecting format yyyy-MM-dd)");
        }
        return dateString;
    }

    private loadHoldings(policyId: string, date: string = ''): void {

        const holdingSub = this.policyService.getPolicyValuation(policyId, '',date).subscribe(result => {
            this.valuation = result;
        });
        this.addSubscription(holdingSub);
    }

    private loadPolicyById(policyId: string): void {
        const policySub = this.policyService.getPolicy(policyId).subscribe(result => {
            this.policy = result;
        });
        this.addSubscription(policySub);
    }

    initForm(policy: Policy): void {
        const formSub = this.formService.initBrokerChangeForm(policy.polId, this.workflowItemId, this.form.secondCpsUser).subscribe((result: BrokerChangeForm) => {                       
            this.form = result;
            this.formChange.emit(this.form);         
            this.updateMail2AgentList();
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
        const currentStep = BrokerChangeStep.toOrderedStep(stepName);
        this.showEditionSection = currentStep.isAfterOrEqual(BrokerChangeStep.Analysis);
    }

    clearForm(): void {
        this.form = new BrokerChangeForm();
        this.formChange.emit(this.form);
    }

    updateMail2AgentList(): void {
        let mailToAgent = this.form.mailToAgent;
        const agentCommSub = this.agentService.getCommunicationAgents(this.form.broker.partnerId, this.form.subBroker.partnerId, this.form.policyId).subscribe(result => {
                this.mail2AgentList = result.filter( agent => agent.agtId != this.wealinsBrokerId );
                this.form.mailToAgent = this.mail2AgentList.map(agent => agent.agtId ).includes(mailToAgent) ? mailToAgent : null;
        });
        this.addSubscription(agentCommSub);
    }

    onBrokerChange(event: PartnerForm): void {
        this.form.broker = event;        
        this.updateMail2AgentList();

        // if selected broker is Wealins
        if(this.form.broker != null && this.form.broker.partnerId == this.wealinsBrokerId) {
            this.handleWealinsBroker();
        }
    }

    onSubBrokerChange(event: PartnerForm): void {        
        this.form.subBroker = event;
        this.updateMail2AgentList();
    }

    /**
     * Set/Reset some values if the selected broker is Wealins
     */
    private handleWealinsBroker(): void {
        this.form.brokerRefContract = "";
        this.form.broker.partnerAuthorized = null;
        this.form.brokerContact = new PartnerForm(null, 'PR');
        this.form.subBroker = new PartnerForm(null, 'SB');
        if(this.form.policyValuation) {
            for (let holding of  this.form.policyValuation.holdings) {
                holding.commissionRate = 0;
            }
        }
        // the filtering of mail2AgentList is alreday performed in updateMail2AgentList method
    }
}