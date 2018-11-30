import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { Subscription } from "rxjs/Subscription";
import { Observable } from "rxjs/Observable";

import { Component, OnInit } from '@angular/core';
import { fundClassifierRegExp, clientClassifierRegExp, AgentContact, Policy, FullClient, Insured, PolicyHolder, defaultTranslationLanguage } from "../../../_models";
import { WebiaService, DeathCoverageService, UoptDetailService } from '../../../_services';
import { MessageService, DateUtils } from '../../../utils';
import { popupAgents, AgentService } from "../../../agent/agent.service";
import { PolicyService } from '../../services/policy.service';

@Component({
    selector: 'policy',
    templateUrl: './policy.tpl.html'
})

export class PolicyComponent implements OnInit {
    contactFunctions = [];
    funcSubscribe: any;
    p: Policy;
    valuable: any;
    clauses: any;
    valuableDate: Date;
    previousDate: string;
    fundNameRegXp: RegExp;
    clientRegXp: RegExp;
    codeMapper:any[]=[];
    deathCoverageTxt:string;
    brokerAgentContacts: AgentContact[] = [];

    ///////// Busy variables ////////////
    subs: any;
    // clauseBusy: any;
    valuationBusy: any;
    ////////////////////

    constructor(private route: ActivatedRoute,
        private router: Router,
        private policyService: PolicyService,
        private messageService: MessageService,
        private titleService: Title,
        private webiaService:WebiaService,
        private deathCoverageService : DeathCoverageService,
        private agentService:AgentService,
        protected uoptDetailService: UoptDetailService) { }

    ngOnInit() {    
        this.subs = []   ;
        this.brokerAgentContacts = [];
        this.webiaService.getClientCodeLabels().then((codeMapper:any[])=> this.codeMapper = codeMapper);
        this.funcSubscribe = this.uoptDetailService.getTypeOfAgentContact().subscribe(contactFunctions => this.contactFunctions = contactFunctions);
        this.p = new Policy();
        this.getQueryParam();
        this.valuableDate = new Date();
        this.previousDate = DateUtils.formatToIsoDate(new Date());
        this.fundNameRegXp = fundClassifierRegExp;
        this.clientRegXp = clientClassifierRegExp;
        this.deathCoverageTxt="";
    }

    public setTitle(newTitle: string) {
        this.titleService.setTitle(newTitle);
    }

    getQueryParam() {
        this.route.queryParams.subscribe((query: Params) => this.init(query));
    }

    init(query: Params) {
        if (query && query['id']) {
            this.p.polId = query['id'];
            this.loadPolicy(query['id']);
        } else {
            this.messageService.error("There is no policy Identifier in this URL.", "Wrong URL", "sm");
        }
    }

    loadPolicy(id) {
        let policyObs : Observable<Policy> = this.policyService.getPolicy(id);
        let deceasedInsuredObs: Observable<Insured[]> = this.policyService.getDeceasedInsureds(id);
        let deceasedHolderObs: Observable<PolicyHolder[]> = this.policyService.getDeceasedHolders(id);
        this.subs.push(this.policyService.getPolicyValuation(id).subscribe(v => this.setValuation(v)));
        this.subs.push(this.deathCoverageService.getPolicyDeathCoverage(id).subscribe(DCClause => this.deathCoverageTxt = DCClause ));
        this.subs.push(Observable.forkJoin(policyObs, deceasedInsuredObs, deceasedHolderObs)
            .subscribe((res) => {
                // policy
                let p = res[0];
                this.p = p;
                this.setTitle("Policy - " + p.polId);
                this.replaceValuationAgent();   
               
                let lang = defaultTranslationLanguage;
                this.policyService.getPolicyClauses(p.polId,p.product.prdId, lang).toPromise().then(c => {this.clauses = c });

                // dead insureds
                let deadInsureds = res[1];
                // init deathClaim for the dead insureds
                deadInsureds.forEach(deadInsured => {
                    if (deadInsured.clientClaimsDetails && deadInsured.clientClaimsDetails.length === 1) {
                        deadInsured.deathClaim = deadInsured.clientClaimsDetails[0];
                    }
                });
                this.p.insureds = this.p.insureds.concat(deadInsureds);

                // dead holders
                let deadHolders = res[2];
                // init deathClaim for the dead holders
                deadHolders.forEach(deadHolder => {
                    if (deadHolder.clientClaimsDetails && deadHolder.clientClaimsDetails.length === 1) {
                        deadHolder.deathClaim = deadHolder.clientClaimsDetails[0];
                    }
                });
                this.p.policyHolders = this.p.policyHolders.concat(deadHolders);
            }
        ));
    }

    findValuationByPolIdAndDate(polId: string, date: any) {
        if (polId && date) {
            let newDate = DateUtils.formatToIsoDate(new Date(date));
            if (newDate != this.previousDate) {
                this.valuationBusy = this.policyService.getPolicyValuation(polId, '', newDate).subscribe(v => this.setValuation(v));
                this.previousDate = newDate;
            }
        }
    }

    setValuation(data){
        this.valuable = data;
        this.replaceValuationAgent();        
    }
      
    /**
     * @Deprecated
     */
    replaceValuationAgent(){
        if (this.p.brokers && this.valuable && this.valuable.holdings && this.valuable.holdings.length > 0){
            this.valuable.holdings.forEach(h => { 
                if(h){
                    h.agentName = this.getBrokerInPolicy(h.agent);
                }
            });
        }
    }
    
    /**
     * @Deprecated
     */
    getBrokerInPolicy(agtId: string){
        let bk = this.p.brokers.find(b =>  b && b.agent && b.agent.agtId == agtId );        
        return (bk && bk.agent) ? bk.agent.name : agtId;
    }

    canOpenInPopup(agent){    
        return popupAgents.findIndex((cat)=> cat && agent.category && cat.trim() == agent.category.trim()) > -1;
    }

    toContactFunctionDescByKey(contactFunctionKey: string){
        return this.agentService.toContactFunctionDescByKey(contactFunctionKey, this.contactFunctions);
    }
 
    toContactFunctionDescByContactId(contactId: string) {
        let brokers = this.p.brokers;
        let result = '';
        if(brokers) {
            let newArray = brokers.map(broker => (broker && broker.agent) ? this.agentService.toContactFunctionDescByContactId(contactId, broker.agent.agentContacts, this.contactFunctions) : '');
            if(newArray && newArray.length > 0){
                result = newArray.reduce((prevValue, currValue) => prevValue + ',' + currValue);
            }
        } 
        return result;
    }

    ngOnDestroy() {
        if (this.valuationBusy) this.valuationBusy.unsubscribe();       
        this.subs.forEach((sub:Subscription) =>{
            if(!sub.closed){
                sub.unsubscribe();
            }
        });

        if(this.funcSubscribe)
            this.funcSubscribe.unsubscribe();
    }

    hasCessionRole(otherClient: FullClient): boolean {
       return this.policyService.isInCessionRole(otherClient.roleNumber);
    }

}