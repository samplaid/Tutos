import { Title } from '@angular/platform-browser';
import { Component, Input, OnInit, Output, EventEmitter, ViewEncapsulation } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';

import { FundService } from './fund.service';
import { CurrencyService, UoptDetailService, WebiaService } from '../_services';
import { Fund, AgentContact, AssetManagerStrategy, Roles, CodeLabelValue, financialAdvisorAsRoles, UoptDetails, AssetManagerStrategyCode } from '../_models';
import { AgentService } from '../agent';
import { MessageService, StateMode, UserService } from "../utils";

import { AmStrategyListOption } from '../agent/components/am-strategy-list/am-strategy-list-option';
import { FundType } from './_partial/fund-type';


@Component({
    selector: 'fund',
    templateUrl: 'fund.component.html',
    styles: [ ` 
        .percent-amount {
            width: 100% !important;
        }
    ` ],
    encapsulation: ViewEncapsulation.None
})

/**
 * Fund component
 * 
 * Contains :
 */
export class FundComponent implements OnInit {        
    readonly financialAdvisorTypes = financialAdvisorAsRoles;
    static wealinsAgentId: any;
    mode = StateMode.readonly;
    StateMode = StateMode;
    params: Params;
    title: string;
    busy;
    busySaveFund: Subscription;
    busyStrategy;
    currencyList: any[];
    circularLetterList: any[];
    fundClassificationList: any[];
    riskClassList: any[];
    fundStore: Fund;
    typePOAList: any[];
    subs: Array<Subscription> = [];
    fund: Fund;
    inModal:boolean = false;
    brokerOrFinancialAdvisor:string;
    amStrategiesList:AssetManagerStrategy[];
    riskProfileOpt: AmStrategyListOption;
    riskCurrencyOpt: AmStrategyListOption;
    investCatOpt: AmStrategyListOption;
    alterFundOpt: AmStrategyListOption;
    _fundId: string;
    accountRootPattern: string = "";

    @Output() createChange = new EventEmitter<Fund>();
    @Input() subType: 'FID'|'FAS'|'FE'|'FIC';
    @Input() set fundId(value: string) {
        this._fundId = value;
        this.inModal = true;
    }
    get fundId(){
        return this._fundId;
    }

    constructor(protected route: ActivatedRoute, protected router: Router,
        protected fundService: FundService, protected currencyService: CurrencyService,
        protected uoptDetailService: UoptDetailService, protected agentService: AgentService,
        protected messageService: MessageService, protected userService: UserService, protected titleService: Title,
        protected webiaService: WebiaService) {
            this.fundStore = { };
            this.subs.push(this.currencyService.getCurrencies().subscribe(t => this.currencyList = t));
            this.subs.push(this.uoptDetailService.getFundClassifications().subscribe(t => this.fundClassificationList = t));
            this.subs.push(this.uoptDetailService.getRiskClasses().subscribe(t => this.riskClassList = t));
            this.subs.push(this.uoptDetailService.getTypePOAs().subscribe(t => this.typePOAList = t));
            this.riskProfileOpt = { riskType: CodeLabelValue.PROFIL, config: { } };
            this.riskCurrencyOpt = { riskType: CodeLabelValue.CURRENCY };
            this.investCatOpt = { riskType: CodeLabelValue.INVEST_CAT };
            this.alterFundOpt = { riskType: CodeLabelValue.ALTER_FUND };
    }

    ngOnInit(): void {             
        this.initFund();
        if (this.inModal){
            this.params = { id : (!!this.fundId) ? decodeURIComponent(this.fundId) : undefined, subType: this.subType }; 
            this.setProperties(this.fund, this.subType);            
            this.freezeFieldsForType(this.fund, this.subType);
            this.filterStrategies(this.riskProfileOpt, this.subType);
            if (this.params.id) {
                this.load(this.params.id);
            }
            this.setWealinsAssetManager(this.fund); 
        } else {
            this.route.params.subscribe((params: Params) => {
                this.subType = this.route.snapshot.data['subType'].toUpperCase();  
                this.params = { id : (params['id']) ? decodeURIComponent(params['id']) : undefined, subType: this.subType };
                this.freezeFieldsForType(this.fund, this.subType);
                this.setProperties(this.fund, this.subType);
                this.filterStrategies(this.riskProfileOpt, this.subType);
                if (this.params.id) {
                    this.load(this.params.id);
                } 
                this.setWealinsAssetManager(this.fund);
            });
        }
   }
  
   filterStrategies(strategyOption: AmStrategyListOption, fundSubType: string): void {
       if (strategyOption && strategyOption.config) {
            const filterList = (value$: Observable<UoptDetails[]>) => {
                let result$: Observable<UoptDetails[]>;
                if (fundSubType == FundType.FAS.key) {
                    result$ = value$.map(items => items.filter(uopDet => uopDet.uddId && uopDet.uddId.startsWith(FundType.FAS.key)));
                } else if (fundSubType == FundType.FID.key) {
                    result$ = value$.map(items => items.filter(uopDet => uopDet.uddId && !uopDet.uddId.startsWith(FundType.FAS.key)));
                } else {
                    result$ = value$;
                }
                return result$;
            };
           strategyOption.config.treatOtherList$ = filterList;
           strategyOption.config.treatBlueList$ = filterList;
       }
   }



   setMode(){     
       if ( (this.userService.hasRole(Roles.FUND_FID_FAS_EDIT) && (this.subType == FundType.FID.key || this.subType == FundType.FAS.key))
          ||(this.userService.hasRole(Roles.FUND_FE_FIC_EDIT) && (this.subType == FundType.FE.key || this.subType == FundType.FIC.key))   ){
           if (this.params['id'] || (this.fund && this.fund.fdsId) )
                this.mode = StateMode.update;
            else
                this.mode = StateMode.create;
       }
   }

   readOnlyModeForType(subType: string): string{
       let result = this.mode;
        if(subType === FundType.FAS.key){
            result = StateMode.readonly; 
        }

        return result;
   }

   setProperties(fund: Fund, subType: string){
        this.setMode();   
        fund.fundSubType = subType;
        this.setTitle();
   }

   freezeFieldsForType(fund: Fund, subType: string): void{
        if(subType === FundType.FAS.key) {
            fund.investCat = AssetManagerStrategyCode.CCAT2;
            fund.riskCurrency = AssetManagerStrategyCode.CURRALL;
        }
   }

   protected setTitle() {
        let newTitle = "Create "+ ( this.fund && this.fund.fundSubType ? this.fund.fundSubType : "fund");
        if (this.mode == StateMode.update){
            this.title = 'Update ' + this.fund.fundSubType;
            newTitle = "Fund - "+ (this.params['id'] || this.fund.fdsId);
        } else if (this.mode == StateMode.readonly){
            this.title = this.fund.fundSubType;
            newTitle = "Fund - "+ (this.params['id'] || this.fund.fdsId ||'');
        } else {
            this.title = 'Creation ' + this.fund.fundSubType;
        }
        if(!this.inModal)
            this.titleService.setTitle( newTitle );
   }

    initFund() {        
        this.fund = new Fund();      
        // retour 03/09/2017: Seul le champs invest limit cash doit être initialisé (pas la devise, risk ou autre Circular) 
        //this.fund.currency = 'EUR';
        this.fund.investCashLimit = 100.00;              
        this.fund.status = 0;      
        this.fund.fundType = 3;
        this.fund.fundSubType = FundType.FID.key;
    }

    setWealinsAssetManager(fund: Fund){
        if (fund.fundSubType == FundType.FAS.key){ // set asset manager as WEALINS agent
            if(fund && !fund.assetManager) {
                this.subs.push(this.agentService.getAgentFoyer().subscribe(agent=>{ 
                    FundComponent.wealinsAgentId = agent.agtId;
                    fund.assetManager = FundComponent.wealinsAgentId;
                },e=>{}));
                
            }
        }
    }

    refresh(){    
        this.load((this.params && this.params['id']) ? this.params['id'] : ((!!this.fundId) ? this.fundId : ((!!this.fund) ? this.fund.fdsId : undefined)));
    }

    load(fdsId: string){
        this.accountRootPattern = "";
        if(fdsId){
            this.busy = this.fundService.loadFund(fdsId).subscribe(fund => {                
                this.freezeFieldsForType(fund, fund.fundSubType);
                this.fundService.validateAllDates(fund);
                this.fund.iban = this.fundService.removeIbanSpace(fund.iban);          
                this.setWealinsAssetManager(fund);
                this.storeStrategy(fund);
                this.fund = Object.assign({}, fund);
                if (this.fund.depositBank) {
                    this.busy = this.agentService.searchAccountRootPatternExample(this.fund.depositBank).subscribe(patterns => {
                        this.accountRootPattern = "("  + patterns.join(",") + ")";
                    });
                }
            }, e => { });

        }        
    }

    storeStrategy(fund: Fund) {
        this.fundStore = fund;
    }

    save() {
        // Cancel the previous http request if exists.
        if(this.busySaveFund && !this.busySaveFund.closed) {
            this.busySaveFund.unsubscribe();
        }

        // set null the class of risk
        if((FundType.FID.value === this.fund.fundSubType 
            || FundType.FAS.value === this.fund.fundSubType) 
            && !this.fund.riskProfile){
            this.fund.classOfRisk = null;
        }

        this.busySaveFund = this.fundService.save(this.mode, this.fund)
                                            .subscribe(fund => { 
                                                if(fund){
                                                    this.reloadPage(fund); 
                                                    this.createChange.emit(fund);
                                                }                            
                                            }, e => { });
    }

    reloadPage(fund) {
        if (!this.inModal){
            this.fund = fund;
            this.setMode();
            this.setTitle();
        }
    }
    
    checkChange(input) {
        this.fund.status = input.checked ? 1 : 2;
    }


    poaChecked(event) {
        this.fund.poaType = '';
        this.fund.poaDate = null;
        this.fund.mandateHolder = '';
    }

    privateEquityChecked(privateEquity) {
        this.fund.privateEquityFee = null;
    }

    isDisabled() {
        return false;
    }

    setContacts(contacts:Array<AgentContact>, agentType: string){
        if(this.fund && this.fund[agentType]){
            this.fund[agentType].agentContacts = contacts;
        }
    }

    setAgent(agent, type:string){
        if(this.fund){   
            if (type=='assetManagerAgent'){
                if (this.fundStore[type] && !!agent){
                    if(this.fundStore[type].agtId != agent.agtId ) {
                        //if select another agetn as Asset Manager then force to select a new contact and new fees and new strategy
                        this.resetAllAssetManagerProperty(this.fund);
                    } else {
                        this.restaureAssetManagerProperties(this.fund);
                    }
                }
            }      

            this.fund[type] = agent;
        }
    }
	
	setAssetManagerAgent(agent, type:string){
        if(this.fund){   
            if (type=='assetManagerAgent'){
                if (this.fundStore[type] && !!agent){
                    if(this.fundStore[type].agtId != agent.agtId ) {
                        //if select another agetn as Asset Manager then force to select a new contact and new fees and new strategy
                        this.resetPartialAssetManagerProperties(this.fund);
                    } else {
                        this.restaureAssetManagerProperties(this.fund);
                    }
                }
            }      

            this.fund[type] = agent;
        }
    }

	
	private resetAllAssetManagerProperty(fund: Fund) {
        fund.salesRep = null;
        fund.assetManagerFee = null;
        fund.finFeesFlatAmount = null;
        fund.alternativeFunds = null;
        fund.riskProfile = null;
        fund.riskCurrency = null;
        fund.investCat = null;
        fund.classOfRisk = null;
    }

    private resetPartialAssetManagerProperties(fund: Fund) {
        fund.salesRep = null;
        fund.alternativeFunds = null;
        fund.riskProfile = null;
        fund.riskCurrency = null;
        fund.investCat = null;
        fund.classOfRisk = null;
    }

    private restaureAssetManagerProperties(fund: Fund) {
        fund.salesRep = this.fundStore.salesRep;
        fund.assetManagerFee = this.fundStore.assetManagerFee;
        fund.finFeesFlatAmount = this.fundStore.finFeesFlatAmount;
        fund.alternativeFunds = this.fundStore.alternativeFunds;
        fund.riskProfile = this.fundStore.riskProfile;
        fund.riskCurrency = this.fundStore.riskCurrency;
        fund.investCat = this.fundStore.investCat;
        fund.classOfRisk = this.fundStore.classOfRisk;
    }
    resetFundRiskFields(riskField: string, array: any[]): void{
        if(!array.some(rp => rp.keyValue === this.fund[riskField])){
            this.fund[riskField] = null;
        }
    }

    switchBrokerOrFinancialAdvisor(agent, type:'financialAdvisorAgent'|'brokerAgent'){
        this.setAgent(agent, type);
        if (agent==null && !!this.brokerOrFinancialAdvisor) 
            this.brokerOrFinancialAdvisor = null;

        if (!!this.brokerOrFinancialAdvisor){
            if (type != this.brokerOrFinancialAdvisor){
                if (type=='financialAdvisorAgent'){
                    this.fund.broker = null;
                    this.fund.brokerAgent = null;
                    this.fund.salesRep = null;
                } else {
                    this.fund.financialAdvisor = null;
                    this.fund.financialAdvisorAgent = null;
                    this.fund.finAdvisorFee = null;
                    this.fund.salesRep = null;
                }
            }
        }
        if (!this.fund.financialAdvisor && !this.fund.broker)
            this.fund.salesRep = null;
        if (!this.fund.financialAdvisor)
            this.fund.finAdvisorFee = null;

        setTimeout(()=> {this.brokerOrFinancialAdvisor = type},0);
    }
        
    ngOnDestroy() {
        this.subs.forEach((sub: Subscription) => sub && !sub.closed && sub.unsubscribe());
    }

}