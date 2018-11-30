import { Observable } from 'rxjs/Observable';
import { Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewEncapsulation } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { CurrencyService, ProductService, CountryService, OptionDetailService, UoptDetailService, ClauseService, AppliParamService, WebiaService, DeathCoverageService } from '../_services';
import { AgentService, SearchCriteria } from '../agent';
import { SurveyService } from './../survey';
import { FundForm, PartnerForm, AppForm, BenefClauseForm, ClientForm, PolicyHolderForm, InsuredForm, BeneficiaryForm, DCClauseRegex, AgentContact, wealinsId, Fund, CreateEditingRequest, EditingDocumentType, CreateEditingResponse, StepName, AgentLite, ClientLite, ClientRoleActivationFlag, CliOtherRelationShipRole, AgentCategoryEnum, agentMailSendingRuleCodes, year_term, whole_life, SubscriptionStep } from '../_models';
import { MessageService, StateMode } from '../utils/index';
import { GenerateMandatDeGestionService } from '../generate-mandat-de-gestion/index';
import { AnalysisService } from './analysis.service';
import { OnChanges } from '@angular/core/src/metadata/lifecycle_hooks';
import { SelectedClientHolder } from './selected-client-holder';
import { SelectedAgentHolder } from '../agent/selected-agent-holder';
import { FundService } from '../fund/fund.service';

@Component({
    selector: 'analysis',
    templateUrl: 'analysis.tpl.html',
    encapsulation: ViewEncapsulation.None
})

export class AnalysisComponent implements OnInit, OnChanges {    
    a: AppForm; // the analysis object

    protected selectedClient: SelectedClientHolder;
    protected selectedAgent: SelectedAgentHolder;

    product:any = {capiList : [], isNotCapi : undefined};
    wealinsId = wealinsId;

    benefActivationFlag: ClientRoleActivationFlag;
    policyHolderActivationFlag: ClientRoleActivationFlag;
    agtCat = AgentCategoryEnum;
    benefPartSplitMode: string;
    
    term:string;
    belgiumInitTax:any;
    subs: Array<Subscription> = [];
    isEntryFeesPCT: boolean;
    showAdditionalCM: boolean;
    showAdditionalPH: boolean;
    showAdditionalINS: boolean;
    showAdditionalDEA: boolean;
    showAdditionalMAT: boolean;
    showAdditionaldeathSUC: boolean;
    showAdditionallifeSUC: boolean;
    showMail2Agent: boolean;
    showSubBroker: boolean;
    disableFees: boolean;
    disabledLives: boolean;
    disableDeathCoverage: boolean = false;
    currencyList: any[];
    countryManagerList: any[];
    remainCountryManagerList: any[];
    relationShipList: any[];
    subBrokerList: any[];
    partner_tmp: any;
    agentFoyer: AgentLite;
    clauseTypeList = ["Free","Standard"];
    livesList = [];
    CPRRoleList = [];
    sendingRuleList = [];
    mail2AgentList = [];
    benefClauseStdList = [];
    nbHoldersPerCountry = {ESP:null, BEL:null, FRA:null, ITA:null};
    allCountries: any[];
    deathCoverageClauses: any[];
    hideCodes: string[];
    codeMapper:any[] = [];
    businessRulesMsg: { [key:string]:string; } = {}

    DCClauseRegex = DCClauseRegex;
    brokerAgentContact: AgentContact;
    stateMode = StateMode;
    showBenefLifeClause: boolean;
    readonly whole_life = whole_life;
    readonly year_term = year_term;

    @Input() useFormClass: boolean = true;
    @Input() stepName: string;
    @Input() mode: "readOnly"|"waiting"|"edit"|"create";
    @Input("analysis") set analysis(value:AppForm) {
        this.isEntryFeesPCT = (!isNaN(parseFloat('' + value.entryFeesAmt)) && value.contractCurrency && isNaN(parseFloat('' + value.entryFeesPct)))?false:true;
        if (!value.broker) value.broker = new PartnerForm(value.formId, 'BK');
        if (!value.subBroker) value.subBroker = new PartnerForm(value.formId, 'SB');
        if (!value.businessIntroducer) value.businessIntroducer = new PartnerForm(value.formId, 'IN');
        if (!value.brokerContact) value.brokerContact = new PartnerForm(value.formId, 'PR');

        if (!value.countryManagers) value.countryManagers = [];
        if (!value.policyHolders) value.policyHolders = [];
        if (!value.insureds) value.insureds = [];
        if (!value.deathBeneficiaries) value.deathBeneficiaries = [];
        if (!value.lifeBeneficiaries) value.lifeBeneficiaries = [];
        if (!value.otherClients) value.otherClients = [];
        this.initBenefClause(value);
        if (!value.fundForms) value.fundForms = [];
        if (value.paymentTransfer==null) value.paymentTransfer = false;
        if (value.policyTransfer==null) value.policyTransfer = false;
        if(!value.surrenderFees) value.surrenderFees = 0;
        this.a = value;
        this.checkMail2Agent();
        this.refreshHideCodes();
        this.bindTermFromInput(this.a.term);
        this.filterDeathClauseType();
        this.checkBrokerHierarchie();
        //this.setBrokerAgentContact(this.a.broker, this.a.brokerContact);
        this.analysisChange.emit(value);   
    }

    get analysis() {
        return this.a;
    }

    @Input() set productCd( prdId){
        if (prdId){
            this.subs.push(this.productService.getProduct(prdId).subscribe(p => this.initProduct(p),e=>{}));
            this.subs.push(this.clauseService.getBenefClauseStd(prdId).subscribe(clauses => this.benefClauseStdList=clauses ,e=>{}));
            this.subs.push(this.optionDetailService.getLives(prdId).subscribe(t => {
                    this.livesList = t;
                    this.checkLives();
                }, e=>{})); 
            this.subs.push(this.deathCoverageService.getDeathCoverageClauses(prdId).subscribe(results=>{
                    this.deathCoverageClauses=results;
                    if ( !(results.alternativeClauses && results.alternativeClauses.length>0  && results.standardClauses  && results.standardClauses.length>0) )
                        this.disableDeathCoverage = true;
                    if (this.a.deathCoverageStd==null){ // init value if not setted
                        this.a.deathCoverageStd = (results.standardClauses  && results.standardClauses.length>0)?true:false;
                    }else{ //update default value of the selected clause
                        if (this.a.deathCoverageStd){
                            this.deathCoverageClauses['standardClauses'].forEach(c=>{
                                if (c.deathCoverageTp == this.a.deathCoverageTp)
                                    c.defaultValue = (c.inputType == 'PERCENTAGE')? this.a.deathCoveragePct : this.a.deathCoverageAmt;
                            })
                        } else {
                            this.deathCoverageClauses['alternativeClauses'].forEach(c=>{
                                if (c.deathCoverageTp == this.a.deathCoverageTp)
                                    c.defaultValue = (c.inputType == 'PERCENTAGE')? this.a.deathCoveragePct : this.a.deathCoverageAmt;
                            })
                        }
                    }
                    this.autoSelectDeathCoverage();
                }, e=>{}));
            this.subs = [...this.subs];
        }
    }
    get productCd(){
        return this.productCd;
    }

    @Output() analysisChange = new EventEmitter<any>();

    constructor(private currencyService: CurrencyService,
        private agentService: AgentService,
        private productService: ProductService,
        private countryService: CountryService,
        private optionDetailService:OptionDetailService,
        private uoptDetailService:UoptDetailService,
        private clauseService:ClauseService,
        private appliParamService:AppliParamService,
        private deathCoverageService:DeathCoverageService,
        private surveyService:SurveyService,
        private webiaService:WebiaService,
        private messageService: MessageService,
        private generateMandatDeGestionService: GenerateMandatDeGestionService,
        private analysisService: AnalysisService,
        private fundService: FundService) {

            this.selectedClient = { 
                policyHolders: [],
                insureds: [],
                deathBeneficiaries: [],
                lifeBeneficiaries: []
            };

            this.selectedAgent = {};
        
            webiaService.getClientCodeLabels().then((codeMapper:any[])=> this.codeMapper = codeMapper);

            this.subs.push(this.currencyService.getCurrencies().subscribe(t => this.currencyList = t));

            // load agent type of 'FS' (Country Manager)
            let criteriaFS = new SearchCriteria();
            criteriaFS = Object.assign(criteriaFS, { status:1, filter: "", categories: ["FS"], pageNum: "1", pageSize: "199" });
            this.subs.push(this.agentService.searchAgent(criteriaFS).subscribe(page => { this.countryManagerList = page.content; this.setRemainCountryManagerList() }, e => { }));
            
            //load agent foyer to set fees to Zero
            this.subs.push(this.agentService.getAgentFoyer().subscribe(agent=>{this.agentFoyer=agent;this.checkFoyerAgent();},e=>{}));

            //load roles list for OtherClient
            // this.subs.push(this.optionDetailService.getCPRRoles().subscribe(roles=>this.CPRRoleList=roles ,e=>{}));
            
            //load the list of sending rules
            this.subs.push(this.uoptDetailService.getSendingRules().subscribe(rules=>this.sendingRuleList=rules ,e=>{}));

            this.subs.push(this.appliParamService.getApplicationParameter('PRODUCT_CAPI').subscribe(appliParam => this.initProduct({"capiList":appliParam.value.split(",").map(s=>s.trim().toUpperCase())}),e=>{}));

            this.subs.push(this.appliParamService.getApplicationParameter('TAX_BELGIUM').subscribe(appliParam => this.belgiumInitTax=appliParam.value ,e=>{}));

            // load countries
            this.subs.push( this.countryService.loadAllCountries( ).subscribe( results => this.allCountries = results ) );
            this.subs = [...this.subs];

            // load resources for the business rules
            this.subs.push(this.appliParamService.getApplicationParameter('RULES_2_POLICYHOLDERS_NORD').subscribe(appliParam => this.businessRulesMsg["RULES_2_POLICYHOLDERS_NORD"] = appliParam.value, e=>{}));

    }

    ngOnChanges(changes: SimpleChanges): void {
       if(this.stepName === StepName.Update_Input) {
           this.mode = 'readOnly';
       }      
    }

    ngOnInit() {
        this.showAdditionalCM = false;
        this.showAdditionalPH = false;
        this.showAdditionalINS = false;
        this.showAdditionalDEA = false;
        this.showAdditionalMAT = false;
        this.showAdditionaldeathSUC = false;
        this.showAdditionallifeSUC = false;
        this.disableFees = false;
        this.showSubBroker = false;
    }

    initProduct(object){
        Object.assign(this.product, object);

        if (this.product['capiList'] && this.product['prdId']){
            this.product['isNotCapi'] = ( this.product['capiList'].findIndex(s=> s == this.product['prdId'].toUpperCase()) < 0 );
            if (this.product['isNotCapi']===false)
                this.selectTermByCode(year_term);
        } 
        if (this.a) {
            // because getApplicationParameter('PRODUCT_CAPI') can be from cache and then set before this.a
            this.a['product'] = this.product;
            this.canShowBeneficiaryLifeClause(this.product, this.term);
        } 

        if(this.product && this.product.nlCountry) {
            this.analysisService.solveClientRoleActivation(this.product.nlCountry).subscribe(benefActivationFlag => this.benefActivationFlag = benefActivationFlag);                       
            this.analysisService.solvePolicyHolderActivation(this.product.nlCountry, !this.product['isNotCapi'],  !!this.a.term).subscribe(policyHolderActivationFlag => this.policyHolderActivationFlag = policyHolderActivationFlag);                       
        }

        if(this.product && this.product.prdId) {         
            this.analysisService.getClientRolesByProduct(this.product.prdId, !this.product['isNotCapi'],  !!this.a.term).subscribe(roles=>this.CPRRoleList=roles ,e=>{});            
        }
        
    }
     

    initBenefClause(input){
        if (!input.deathBenefClauseForms || input.deathBenefClauseForms.length==0)
            input.deathBenefClauseForms = (this.mode!= "readOnly" && this.mode!="waiting") ? [new BenefClauseForm(input.formId, 'D')] : [];           

        if (!input.lifeBenefClauseForms || input.lifeBenefClauseForms.length==0 )
            input.lifeBenefClauseForms = (this.mode!= "readOnly" && this.mode!="waiting") ? [new BenefClauseForm(input.formId, 'L')] : [];
    }

    addCountryManager(id) {
        if (id){
            let newPartner = new PartnerForm(this.a.formId, 'FS');
            newPartner.partnerId = id;
            this.a.countryManagers.push(newPartner); 
        }
        this.partner_tmp = null;
        this.setRemainCountryManagerList();
        this.showAdditionalCM = false;
    }

    removeCountryManager(partner) {
        this.a.countryManagers = this.a.countryManagers.filter(p => p != partner && !!p.partnerId);
        this.setRemainCountryManagerList();
    }

    setRemainCountryManagerList() {
        this.remainCountryManagerList = this.countryManagerList.filter(p => !this.a || !this.a.countryManagers || this.a.countryManagers.findIndex(o => o.partnerId == p.agtId) < 0);
    }
 

    filterDeathClauseType(){
        if(this.a.deathBeneficiaries && this.a.deathBeneficiaries.length > 1) {       
            this.clauseTypeList = ["Free","Standard"];
        } else {
            this.clauseTypeList = ["Free","Standard"]; //["Free"];
        }
    }

    private resetFlag( flag ) {
        if ( flag )
            this[ 'showAdditional' + flag ] = false;
    }

    private addClient(client: ClientLite, destination: string, flag) {        
        if (client && destination){
            let clientForm = this.createClientForm(destination);
            clientForm.clientId = client.cliId;            
            this.a[destination].push(clientForm);
            this.resetFlag(flag);
        }     
               
        this.filterDeathClauseType();
    }

    addlifeBeneficiaries(client: ClientLite, flag) : void {
        this.selectedClient.lifeBeneficiaries.push(client);
        this.addClient(client, 'lifeBeneficiaries', flag);
    }

    addDeathBeneficiaries(client: ClientLite, flag) : void {
        this.selectedClient.deathBeneficiaries.push(client);
        this.addClient(client, 'deathBeneficiaries', flag);
    }

    addInsureds(client: ClientLite, flag) : void {
        this.selectedClient.insureds.push(client);
        this.addClient(client, 'insureds', flag);
        this.checkLives();
    }
    addPolicyHolder(client: ClientLite, flag): void {
        this.selectedClient.policyHolders.push(client);
        this.addClient(client, 'policyHolders', flag);
        this.refreshHoldersPerCountry();
    }


    setTax(): void {
        if (this.nbHoldersPerCountry['BEL']){
            this.a.tax = true;
            if(!this.a.taxRate && this.a.taxRate!=0 && this.belgiumInitTax){
                this.a.taxRate=this.belgiumInitTax;
            }
        } else {
            this.a.tax = false;
            this.a.taxRate=null;
        }
    }

    updatePolicyHolder(selectedClient: ClientLite) {
        let pos = this.selectedClient.policyHolders.findIndex(client => client.cliId === selectedClient.cliId);
        if(pos < 0) {
            this.selectedClient.policyHolders.push(selectedClient);  
        } else {
            this.selectedClient.policyHolders[pos] = selectedClient;
        }        
        this.updateFormData();
    }

    updateFormData(){
        this.refreshHoldersPerCountry();
        this.setTax();
        this.checkBuisnessRules();
    }

    bindTermFromInput(inValue){
        if((inValue && inValue != 0) 
            || this.product.isNotCapi===false 
            || (inValue==null && this.a.policyHolders && this.a.policyHolders.findIndex(p=> !!p.clientId)>=0 )){ // check if value is saved with null (check if form has already been saved by checking a saved policyholder ?)
            this.term = year_term;
        } else {
            this.selectTermByCode(whole_life);
        }
    }

    selectTermByCode(inCode){
        if(inCode == whole_life && this.product.isNotCapi!==false){     
            this.term = whole_life; // For template, we set the term to whole life
            this.a.term = 0; // we always set the term (model) to 0 for whole life
        } else {
            this.term = year_term; // For template, we set the term to year life
            if(!this.a.term || this.a.term == 0)
                this.a.term = undefined; // we don't need to initialize the form
        }

        this.checkClientRolesAvailibility();
        this.canShowBeneficiaryLifeClause(this.product, this.term);
    }

    checkClientRolesAvailibility() {
         if(this.product && this.product.prdId) {
            this.analysisService.getClientRolesByProduct(this.product.prdId, !this.product['isNotCapi'],  !!this.a.term)
            .subscribe(roles=>{
                this.CPRRoleList=roles;
                if(this.a.otherClients){
                    this.a.otherClients.forEach(cli => {
                        if((<any[]>roles).every(role => cli && cli.clientRelationTp && cli.clientRelationTp != role.number)){
                            cli.clientRelationTp = null;
                        }
                    });
                }
             } , e=>{});
        }
    }

    createClientForm(type): ClientForm {
        let newClient;
        let clientRelationTp;
        switch(type){
            case "policyHolder" :
            case "policyHolders" : 
                clientRelationTp =  (this.a.policyHolders.length<2 ? this.a.policyHolders.length+1 : 3);
                newClient = new PolicyHolderForm(this.a.formId, clientRelationTp); 
                break;
            case "insured" :
            case "insureds" : 
                clientRelationTp = (this.a.insureds.length<2 ? this.a.insureds.length+4 : 6);
                newClient = new InsuredForm(this.a.formId, clientRelationTp); 
                break; 
            case "deathBeneficiaries" : newClient = new BeneficiaryForm(this.a.formId, 7); break; 
            case "lifeBeneficiaries" : newClient = new BeneficiaryForm(this.a.formId, 16); break;             
            default : newClient = new ClientForm(this.a.formId, null); break; 
        }
        return newClient;
    }

    addOtherClient(destination: string, flag) {
        this.addObject(destination, flag, new ClientForm(this.a.formId,null));
    }

    addClause(destination: string, clauseType){
        this.addObject(destination, null, new BenefClauseForm(this.a.formId,clauseType));
    }

    addObject( destination: string, flag, newObj ){
        if ( destination ) {
            this.a[destination].push( newObj );
        }
        this.resetFlag(flag);
    }

    remove(client, destination) {
        if(destination)
            this.a[destination] = this.a[destination].filter(c =>  !(c.clientId == client.clientId && c.clientRelationTp==client.clientRelationTp));
        if (destination=='insureds')
            this.checkLives();
        this.refreshHideCodes();

        if(destination === 'deathBeneficiaries'){
            this.onDeathBenefRankChange(client);
        } 
        
        if(destination === 'lifeBeneficiaries'){
            this.onLifeBenefRankChange(client);
        }
       
    }

    removeIndex(index, destination){
        if(destination && Array.isArray(this.a[destination]))
            this.a[destination].splice(index,1);
        if (destination=='insureds')
            this.checkLives();
        this.refreshHideCodes();
    }

    removeClose(index, destination, clauseType){
        this.removeIndex(index, destination);
        if (this.a[destination].length == 0)
            this.addClause(destination, clauseType);
    }

    updateBroker(agent: AgentLite, broker, partnerCat: string){
        this.selectedAgent.broker = agent;  // create property 'agent' to be able to populate the mail2AgentList afterwards        
        this.refreshMail2AgentList();
        //need the full agent to check its hierarchie
        this.checkBrokerHierarchie();
    }

    checkBrokerHierarchie(){
        if (this.a.broker && this.a.broker.partnerId ){
            this.agentService.getSubBroker(this.a.broker.partnerId).then(list => {
                if (list){
                    this.subBrokerList = list.sort((a,b)=> (b.name+'' <= a.name+'')?1:-1 ); 
                    if (this.a.subBroker.partnerId){
                        //if selected subBroker is not active anymore then add it directly in the list flaged as Inactive
                        if (this.subBrokerList.findIndex(a=> a.agtId == this.a.subBroker.partnerId )<0){
                            this.agentService.getAgentLite(this.a.subBroker.partnerId).subscribe(agent => {
                                agent.name += "&nbsp;&nbsp;&#9888;&nbsp;Inactive"; 
                                this.subBrokerList.push(agent);
                            })
                        }
                    }
                }
                this.showSubBroker = (this.subBrokerList.length > 0) });
        } else {
            this.showSubBroker = false;
        }
    }

    updateInsureds(i, event) {
        if (i < 0) { // add a new Insured = to placeholder
            this.a.insureds.push(this._clonePolicyholderToInsured(this.a.policyHolders[this.a.insureds.length])); 
            this.showAdditionalINS = false;
        } else {
            if (event.target.checked){
                this.a.insureds[i] = this._clonePolicyholderToInsured(this.a.policyHolders[i]);
            } else
                this.a.insureds[i] = new InsuredForm(this.a.formId, ((i<2) ? (i+4) : 6));
        }
        this.checkLives();
    }

    private _clonePolicyholderToInsured(source){
            let newInsured = new InsuredForm(this.a.formId, source.clientRelationTp + 3);  // clone object
            newInsured.clientId = source.clientId; 
            return  newInsured;
    }

    changeFeesType(event){   
        this.updateCompanyEntryFees();
        this.updateCompanyManagementFees();
    }

    unityChange(unity){        
        this.isEntryFeesPCT = (unity=='PERCENT');
        this.checkFoyerAgent();
    }

    updateCompanyEntryFees(){
        Observable.of({}).delay(1).toPromise().then(t=>{
            if (this.isEntryFeesPCT){
                this.a.companyEntryFeesPct = this._bugfix(this.a.entryFeesPct - this.a.broker.entryFeesPct);
                this.a.companyEntryFeesAmt = null;
                this.a.broker.entryFeesAmt = null;
            } else {
                this.a.companyEntryFeesAmt = this._bugfix(this.a.entryFeesAmt - this.a.broker.entryFeesAmt);
                this.a.companyEntryFeesPct = null;
                this.a.broker.entryFeesPct = null;
            }  
        }); 
    }

    updateCompanyManagementFees(){
         Observable.of({}).delay(1).toPromise().then(t => {this.a.companyMngtFeesPct = this._bugfix(this.a.mngtFeesPct - this.a.broker.mngtFeesPct);}); 
    }

    _bugfix(value){  // workaround tofix javascript approximation in float calculation
        if (!value) return 0;
        let Nieme = 100000000;
        return Math.round(value*Nieme)/Nieme;
    }

    refreshMail2AgentList(){
        let tmp : AgentLite[] = [];
        const observables : Observable<any>[] = [];

        if (this.selectedAgent.broker) {
            tmp.push(this.selectedAgent.broker);
        }

        if (this.a.subBroker && this.subBrokerList){
            let subBrokerAgent = this.subBrokerList.find(sb=> sb.agtId==this.a.subBroker.partnerId);
            
            if (subBrokerAgent) {
                tmp.push(subBrokerAgent);
            }
        }    

        this.a.fundForms.forEach((ff: FundForm) => {
            if (ff.fundTp=='FID' || ff.fundTp=='FAS' || ff.fundTp=='FIC'){
                
                //add depositBank agent to list of mailToAgent allowed list
                if (ff.fund && tmp.findIndex(a=> a.agtId==ff.fund.depositBank) < 0) {
                    observables.push(this.agentService.getAgentLite(ff.fund.depositBank).map((agent: AgentLite) => tmp.push(agent)));                
                }
                
                //add assetManager agent to list of mailToAgent allowed list
                if (ff.fund && tmp.findIndex(a=> a.agtId==ff.fund.assetManager) < 0) {
                    observables.push(this.agentService.getAgentLite(ff.fund.assetManager).map((agent: AgentLite) => tmp.push(agent)));
                }
            }
        });

        this.mail2AgentList = tmp;
        Observable.forkJoin(observables).subscribe(o=> {
            this.mail2AgentList = tmp;
            //remove the selected value if it doesn't exists anymore
            if (this.a.mailToAgent && (this.mail2AgentList.findIndex(a=> a.agtId == this.a.mailToAgent) < 0)) {
                this.a.mailToAgent = null;
            }                 
        });
    }

    onChangeInvestment(){
        this.refreshMail2AgentList();
        this.refreshHideCodes();
    }

    refreshHoldersPerCountry(){
        let nbHoldersPerCountry = {ESP:null, BEL:null, FRA:null, ITA:null};
        this.selectedClient.policyHolders.forEach((client: ClientLite) => {
            if(client) {
                if (nbHoldersPerCountry[client.country])
                    nbHoldersPerCountry[client.country]++;
                else
                    nbHoldersPerCountry[client.country] = 1;
            }
        });

        this.nbHoldersPerCountry = nbHoldersPerCountry;

        /* reset values in case of removing a client ?
        * EX : set noSurrenderClause=null if no holder in 'ESP' ???  */
    }

    name_status(agent: AgentLite){
        return (+agent.status != 1 ? agent.name+" <div class='text-danger inline-block text-sm'>&nbsp;&nbsp;&#9888;&nbsp;Inactive</div>" :agent.name);
    }

    checkFoyerAgent(){
        if (this.agentFoyer && this.a && this.a.broker ){
            this.disableFees = this.isWealins(this.a.broker);
            if (this.disableFees){
                 this.a.broker.mngtFeesPct = 0;
                 this.a.broker.entryFeesAmt = (this.isEntryFeesPCT)? null : 0;
                 this.a.broker.entryFeesPct = (this.isEntryFeesPCT)? 0 : null;
                 this.a.broker.partnerAuthorized = true;
            }
        }
    }

    checkLives(){
        if (!this.a.insureds || this.a.insureds.length<2){
            this.a.lives = 1;
            this.disabledLives = true;
        } else {
            this.disabledLives = false;
        }
    }

    checkMail2Agent(){
        this.showMail2Agent = (agentMailSendingRuleCodes.indexOf(this.a.sendingRules) > -1);
    }

    // list the roles in the policy to contextualize the survey
    refreshHideCodes(){
        let clients: BeneficiaryForm[];
        clients = [...this.a.deathBeneficiaries, ...this.a.lifeBeneficiaries, ...<BeneficiaryForm[]>this.a.otherClients];

        let hasFAS=false;
        this.a.fundForms.forEach(ff=> {if (ff.fundTp=='FAS') hasFAS = true; })

        this.surveyService.refreshHideCodes(clients, hasFAS);
    }

    deathClauseDesccontains(clause: string, fieldType: string) {
        return (clause && clause.indexOf(fieldType)  > -1);
    }

    changeCoverageTp(event, DCClause){
        if (event==true || (event && event.target.checked) ){
            this.a.deathCoverageTp = DCClause.deathCoverageTp;
            this.setCoverage(DCClause, DCClause.defaultValue);
        }
    }

    changeCoverageValue(value, DCClause){
        if (value != DCClause.defaultValue){
            this.setCoverage(DCClause, value);
            DCClause.defaultValue = value;
        }
    }

    changeCoverageStd(){
        this.a.deathCoverageTp = null;
        this.a.deathCoverageAmt = null;
        this.a.deathCoveragePct = null;
        this.autoSelectDeathCoverage();
    }

    setCoverage(DCClause, value){
        if (DCClause.inputType=='AMOUNT'){
            this.a.deathCoverageAmt = value;
            this.a.deathCoveragePct = null;
        } else {
            this.a.deathCoverageAmt = null;
            this.a.deathCoveragePct = value;
        }
    }
    //try to auto select if there is only one death coverage
    autoSelectDeathCoverage(){
        let standardClauses = this.deathCoverageClauses['standardClauses'];
        let alternativeClauses = this.deathCoverageClauses['alternativeClauses'];
        if (this.deathCoverageClauses && this.a.deathCoverageStd!=null){
            if (this.a.deathCoverageStd && standardClauses && standardClauses.length==1){
                this.changeCoverageTp(true, standardClauses[0]);
            }     
            if (!this.a.deathCoverageStd && alternativeClauses && alternativeClauses.length==1){
                this.changeCoverageTp(true, alternativeClauses[0]);
            }

        }
    }

    brokerContactChange(agentContact){
        this.brokerAgentContact = agentContact;
    }

    applyManagementMandateDocRequest(fund: Fund){
        let request = new CreateEditingRequest();
        request.fund = fund.fdsId;
        request.policy = this.a.policyId;
        request.product = this.a.productCd;
        request.documentType = EditingDocumentType.MANAGEMENT_MANDATE;
        this.subs.push(this.generateMandatDeGestionService.applyManagementMandateDocRequest(request)
                                                        .subscribe((response: CreateEditingResponse) => {    
                                                            if(response && response.requestId) {
                                                                this.messageService.addAlertSuccess('Generate "Mandat de Gestion" has been sent successfully', true); 
                                                            }                                                        
        }));
        
    }

    canGenerateMandatDeGestionInStep(stepName: string): boolean {
        return stepName === StepName.Awaiting_Premium ||
                stepName === StepName.Premium_input_and_nav ||
                stepName === StepName.Check_documentation ||
                stepName === StepName.Sending ||
                stepName === StepName.Follow_up || stepName === StepName.Update_Input;
    }

    canShowValuationAmount(stepName: string): boolean {
        return SubscriptionStep.Premium_input_and_nav.isBeforeOrEqual(SubscriptionStep.toOrderedStep(stepName));
    }

    isGenerateMandateStep(): boolean {
        return this.stepName === StepName.Generate_Mandat_de_Gestion;
    }

    isSurrRightsCessionary(otherClient: ClientForm): boolean {
        return CliOtherRelationShipRole.CESSION_SURRENDER_RIGTHS == otherClient.clientRelationTp;
    }
   
    isCapiLux(product: any): boolean {
        return this.analysisService.isCapiLux(product);
    }

    canShowBeneficiaryLife(product: any, term: string): boolean {
        return this.analysisService.canShowBeneficiaryLife(product, term);
    }

    canShowBeneficiaryLifeClause(product: any, term: string): void {
        this.showBenefLifeClause = this.analysisService.canShowBeneficiaryLifeClause(product, term);
    }

    enablePolicyHolderRole(): boolean{
        return this.policyHolderActivationFlag && (this.policyHolderActivationFlag.activatedSuccessionLife || 
            this.policyHolderActivationFlag.activatedUsufructuary || 
            this.policyHolderActivationFlag.activatedBareOwner || 
            this.policyHolderActivationFlag.activatedSuccessionDeath);
    }

    /** Check business Rules    */
    checkBuisnessRules() {

        // add specific first rank free clause for swedish and finnish products (under special conditions)
        if(this.product && (this.product.nlCountry == 'FIN' || this.product.nlCountry == 'SWE')) {
            if(this.a.policyHolders.length >= 2 && this.a.lives == 3) { // fyi (3 -> Joint 2nd Death)

                let emptyDeathClause = function(deathClause){
                    return deathClause.rankNumber == undefined || deathClause.rankNumber == '' && deathClause.clauseTp == 'Standard' && deathClause.clauseText == undefined || deathClause.clauseText == '';
                };
                
                let specificNordicDeathClause = (businessRulesMsg: string) => {
                    return (deathClause: BenefClauseForm, index: number) => {
                            return deathClause.rankNumber == 1 && deathClause.clauseTp == 'Free' && deathClause.clauseText == businessRulesMsg;
                        }
                }
 
                if(!this.a.deathBenefClauseForms.some( specificNordicDeathClause( this.businessRulesMsg['RULES_2_POLICYHOLDERS_NORD']) )) {
                    let newClauseIndex = 0;
                    if(this.a.deathBenefClauseForms.some(emptyDeathClause)){
                        newClauseIndex = this.a.deathBenefClauseForms.findIndex(emptyDeathClause);
                    } else {
                        this.addClause('deathBenefClauseForms','D');
                        newClauseIndex = this.a.deathBenefClauseForms.length - 1;
                    }

                    this.a.deathBenefClauseForms[newClauseIndex].rankNumber = 1;
                    this.a.deathBenefClauseForms[newClauseIndex].clauseTp = 'Free';
                    this.a.deathBenefClauseForms[newClauseIndex].clauseText = this.businessRulesMsg['RULES_2_POLICYHOLDERS_NORD'];
                }
            }
        }
    }

    isWealins(partner: PartnerForm): boolean {
        return partner && this.agentFoyer && partner.partnerId === this.agentFoyer.agtId;
    }

    enableMode(stepName: string): string {
        return (StepName.Update_Input === stepName) ? StateMode.edit : this.mode;
    }

    readOnlyMode(stepName: string): string {
        return (StepName.Update_Input === stepName) ? StateMode.readonly : this.mode;
    }

    enableFor(stepName: string, modes: string[]=[]): boolean {
        return (StepName.Update_Input === stepName) ? false : modes.some(mode => mode == this.mode);
    }

    disableFor(stepName: string, modes: string[]=[]): boolean {
        return (StepName.Update_Input === stepName) ? true : modes.some(mode => mode == this.mode);
    }

    updateValuationAmount(valuationDate: Date): void {
        this.fundService.updateValuations(this.a.fundForms, valuationDate);
    }
  

    mapBeneficiariesByRank(beneficiaries: BeneficiaryForm[]): Map<number, BeneficiaryForm[]> {
        let benefByRank: Map<number, BeneficiaryForm[]> = new Map<number, BeneficiaryForm[]>();
        beneficiaries.map(beneficiary => {
            if(benefByRank.has(beneficiary.rankNumber)){
                benefByRank.get(beneficiary.rankNumber).push(beneficiary);
            } else {
                benefByRank.set(beneficiary.rankNumber, [beneficiary]);
            }
        });

        return benefByRank;
    }

    solvePart(isEqualPart: boolean, beneficiary: BeneficiaryForm): void {
        if(isEqualPart) {
            beneficiary.isEqualParts = true; // same rank and checked
            beneficiary.split = null;
            beneficiary.partMode = StateMode.readonly;
        } else {
            beneficiary.isEqualParts = false; // same rank but unchecked
            beneficiary.partMode = this.enableMode(this.stepName);
        }
    }

    onLifeBenefEqualPartChange(source: BeneficiaryForm): void {
        this.onEqualPartChange(source, this.a.lifeBeneficiaries);
    }

    onDeathBenefEqualPartChange(source: BeneficiaryForm): void {
        this.onEqualPartChange(source, this.a.deathBeneficiaries);
    }
    
    onEqualPartChange(source:BeneficiaryForm, beneficiaries: BeneficiaryForm[]): void {
        if(beneficiaries){ 
            beneficiaries.filter(benef => benef.rankNumber === source.rankNumber)
                        .forEach(beneficiary => this.solvePart(source.isEqualParts === true, beneficiary));  
        }
    }

    onLifeBenefRankChange(client: BeneficiaryForm): void {
        this.onRankChange(client, this.a.lifeBeneficiaries);
    }

    onDeathBenefRankChange(client: BeneficiaryForm): void {
        this.onRankChange(client, this.a.deathBeneficiaries);
    }

    onRankChange(client: BeneficiaryForm, beneficiaries: BeneficiaryForm[]): void {
        let beneficiariesGroupByRank = this.mapBeneficiariesByRank(beneficiaries);
        beneficiariesGroupByRank.forEach((beneficiaries, rankNumber) => {
            if(beneficiaries.length > 1) {
                // show all the equal part component having the same rank with this client and leave part value as it is.
                let hasCheckedEqualPart = beneficiaries.some(beneficiary => beneficiary.isEqualParts === true && beneficiary.clientId != client.clientId);
                beneficiaries.forEach(beneficiary => {

                    // Do not show all equal part components that is grouped by an empty rankNumber.
                    if(rankNumber) {
                        beneficiary.canShowEqualParts = true;
                    } else {
                        beneficiary.canShowEqualParts = false;
                    }
                    
                    this.solvePart(hasCheckedEqualPart, beneficiary);                    
                });                               
            } else if(beneficiaries.length === 1){
                 // do not show this client equal part component, set equal part to null as initial value and leave part value as it is.
                beneficiaries[0].canShowEqualParts = false;
                beneficiaries[0].isEqualParts = null;
                beneficiaries[0].partMode = this.enableMode(this.stepName);
            }
        });
    }

    ngOnDestroy(){
         this.subs.forEach((sub: Subscription) => sub && !sub.closed && sub.unsubscribe());
    }

    onClauseTypeChange(formBean : BenefClauseForm) {
        formBean.clauseCd = null;  
        formBean.clauseText = null;
    }
}