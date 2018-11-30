import { Component, OnInit, Input, Output, EventEmitter, SimpleChanges } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { Observable } from "rxjs/Observable";

import { CurrencyService, ProductService, CountryService} from '../_services';
import { AgentService, SearchCriteria } from '../agent';
import { AppForm, PartnerForm, AgentContact, AgentLite, AgentCategoryEnum } from '../_models';
import { SelectedAgentHolder } from '../agent/selected-agent-holder';

@Component({
    selector: 'registration',
    templateUrl: 'registration.tpl.html'
})
export class RegistrationComponent implements OnInit {
    r: AppForm;

    protected selectedAgent: SelectedAgentHolder;

    currencyList: any[];
    countryManagerList: any[];
    remainCountryManagerList: any[];
    allProducts: any[];
    allCountries: any[];
    partner_tmp:any;
    showAdditionalCM:boolean;
    agentFoyer:AgentLite;
    agtCat = AgentCategoryEnum;

    subs:Array<Subscription> = [];

    isEntryFeesPCT:boolean;
    disableFees:boolean;

    selectedType: string;

    @Output() registrationChange = new EventEmitter<any>();
    @Input() mode: "readOnly"|"waiting"|"edit"|"create";
    @Input("registration") set registration(value: AppForm) {
        this.r = value;
        this.isEntryFeesPCT = (!isNaN(parseFloat('' + this.r.entryFeesAmt)) && this.r.contractCurrency && isNaN(parseFloat('' + this.r.entryFeesPct)))?false:true;
        if (!this.r.broker)
            this.r.broker = new PartnerForm(this.r.formId, 'BK');
        if (!this.r.businessIntroducer)
            this.r.businessIntroducer = new PartnerForm(this.r.formId, 'IN');
        if (!this.r.brokerContact)
            this.r.brokerContact = new PartnerForm(this.r.formId, 'PR');
        if (!this.r.countryManagers)
            this.r.countryManagers = [];
        this.registrationChange.emit(this.r);
    }

    get registration() {
        return this.r;
    }

    constructor( private currencyService: CurrencyService,
                 private agentService:AgentService,
                 private productService: ProductService,
                 private countryService: CountryService ) {
        this.selectedAgent = {};
        // load all currencies
        this.subs.push(this.currencyService.getCurrencies().subscribe( t => this.currencyList = t));

        // load agent type of 'FS' (Country Manager)
        let criteriaFS = new SearchCriteria();
        criteriaFS = Object.assign(criteriaFS, {status:1, filter:"",categories:["FS"], pageNum:"1", pageSize:"600"});
        this.subs.push(this.agentService.findByCriteria(criteriaFS).subscribe(agents=>{
            this.countryManagerList=agents;
            this.setRemainCountryManagerList();
        },e=>{}));

        //load agent foyer to set fees to Zero
        this.subs.push(this.agentService.getAgentFoyer().subscribe(agent=>{this.agentFoyer=agent;this.checkFoyerAgent();},e=>{}));

        // load products
        this.subs.push( this.productService.loadAllProductLight( ).subscribe( results => this.allProducts = results ) );

        // load countries
        this.subs.push( this.countryService.loadAllCountries( ).subscribe( results => this.allCountries = results ) );
    }

    ngOnInit() {
        this.showAdditionalCM = false;
        this.disableFees = false;
    }

    addCountryManager(id){
        let newPartner = new PartnerForm(this.r.formId, 'FS');
        newPartner.partnerId = id;
        this.r.countryManagers.push(newPartner);
        this.partner_tmp = null;
        this.setRemainCountryManagerList();
        this.showAdditionalCM = false;
    }

    removeCountryManager(partner) {
        this.r.countryManagers = this.r.countryManagers.filter(p => p != partner);
        this.setRemainCountryManagerList();
    }

    setRemainCountryManagerList(){
        this.remainCountryManagerList = this.countryManagerList.filter(p => this.r.countryManagers.findIndex(o=>o.partnerId==p.agtId)<0);
    }

    name_status(agent:AgentLite){
        return (+agent.status != 1 ? agent.name+" <div class='text-danger inline-block text-sm'>&nbsp;&nbsp;&#9888;&nbsp;Inactive</div>" :agent.name);
    }

    changeFeesType(event){
        this.updateCompanyEntryFees();
        this.updateCompanyManagementFees();
    }

    unityChange(unity){
        this.isEntryFeesPCT = (unity=='PERCENT');
    }
    updateCompanyEntryFees(){
        Observable.of({}).delay(1).toPromise().then(t=>{
            if (this.isEntryFeesPCT) {
                if (!isNaN(parseFloat('' + this.r.broker.entryFeesAmt)) )
                    this.r.broker.entryFeesPct = this.r.broker.entryFeesAmt;
                this.r.companyEntryFeesPct = this._bugfix(this.r.entryFeesPct - this.r.broker.entryFeesPct);
                this.r.companyEntryFeesAmt = null;
                this.r.broker.entryFeesAmt = null;
            } else {
                if (!isNaN(parseFloat('' + this.r.broker.entryFeesPct)))
                    this.r.broker.entryFeesAmt = this.r.broker.entryFeesPct;
                this.r.companyEntryFeesAmt = this._bugfix(this.r.entryFeesAmt - this.r.broker.entryFeesAmt);
                this.r.companyEntryFeesPct = null;
                this.r.broker.entryFeesPct = null;
            }
            this.checkFoyerAgent();
        });
    }

    updateCompanyManagementFees(){
         Observable.of({}).delay(1).toPromise().then(t => {this.r.companyMngtFeesPct = this._bugfix(this.r.mngtFeesPct - this.r.broker.mngtFeesPct);});
    }

    _bugfix(value){  // workaround tofix javascript approximation in float calculation
        if (!value) return 0;
        let Nieme = 100000000;
        return Math.round(value*Nieme)/Nieme;
    }

    checkFoyerAgent(){
        if (this.agentFoyer && this.r && this.r.broker ){
            this.disableFees = this.isWealins(this.r.broker);
            if (this.disableFees){
                this.r.broker.mngtFeesPct = 0;
                this.r.broker.entryFeesAmt = 0;
                this.r.broker.entryFeesPct = 0;
            }
        }
    }

    setBroker(agent: AgentLite){
        this.selectedAgent.broker = agent;    
    }

    cleanDerogation(canCleanExplainOverFees){
        if(canCleanExplainOverFees && this.r && this.r.broker){
            this.r.broker.explainOverFees = undefined;
        }
    }

    isWealins(partner: PartnerForm): boolean {
        return partner && this.agentService.isWealins(partner.partnerId);
    }

    ngOnDestroy(){
         this.subs.forEach((sub: Subscription) => sub && !sub.closed && sub.unsubscribe());
    }

}