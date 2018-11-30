import { Component, OnInit, OnDestroy, Input, Output, EventEmitter, SimpleChanges, OnChanges } from '@angular/core';
import { AgentService } from "../../agent";
import { FundForm, Fund, fundClassifierRegExp, PartnerForm, StepName } from "../../_models";
import { CurrencyService, ExchangeRateService } from "../../_services/index";
import { StateMode } from '../../utils';
import { FundLite } from '../../_models/fund-lite';

const VALUABLE_STEPS = [ StepName.Premium_input_and_nav ];

@Component({
    selector: 'analysis-invest',
    templateUrl: './analysis-invest.tpl.html'
})
export class AnalysisInvestComponent implements OnInit, OnDestroy, OnChanges {
        
    /// Member declaration
    fundRegXp= fundClassifierRegExp;
    brokerId : string;
    currencyList: any[];
    valuationMode: string;
    
    
    /// Output declaration
    @Output() fundFormsChange = new EventEmitter<FundForm[]>();

    /// Output declaration
    @Output() generateMandateChange = new EventEmitter<Fund>();

    /// Input declaration
    @Input() stepName: string;
    @Input() valuationDate: Date;
    @Input() formId;        
    @Input() generateMandatDeGestion: boolean = false;        
    @Input() disabled: boolean = false;
    @Input() mode: "readOnly"|"waiting"|"edit"|"create";
    @Input() fundForms: FundForm[] = [];
    @Input() currency: string; // the policy currency
    @Input() totalInvestment: number; // the policy currency
    @Input() set broker(brokerPF:PartnerForm){
        if (brokerPF && brokerPF.partnerId)
            this.brokerId = brokerPF.partnerId;
    };
    @Input() undeletableFundIds: string[] = [];
    @Input() showValuationAmount: boolean = false;
    readonly StateMode = StateMode;
    
    // Constructor declarations
    constructor(private currencyService: CurrencyService, 
                private agentService:AgentService, 
                private exchangeRateService:ExchangeRateService) {
        this.currencyService.getCurrencies().toPromise().then(t => this.currencyList = t);
    }

    /// Getter setter

    /// Angular funtion declaration

    ngOnInit() { }
    ngOnDestroy(): void { }
    ngOnChanges(changes: SimpleChanges): void {
        this.valuableStepMode = this.stepName;
    }
    /// Custom funtion declaration
    select(items: FundLite[]) {
        if(!this.fundForms) this.fundForms = [];

        items.forEach((selectedFund: FundLite) => { 
            let index = this.fundForms.findIndex(fundForm => fundForm && fundForm.fund && fundForm.fund.fdsId == selectedFund.fdsId);
            
            if ( index < 0 ){
                let newFundForm = new FundForm(this.formId);
                newFundForm.fund =  selectedFund;
                newFundForm.fundTp = selectedFund.fundSubType;
                newFundForm.fundId = selectedFund.fdsId;
                let promises = [];

                //add Agents (depositBank,assetManager ) information to populate MailToAgent list
                if (selectedFund.depositBank)
                    promises.push(this.agentService.getAgentLite(selectedFund.depositBank).subscribe(agent => newFundForm['depositBank'] = agent));
                if (selectedFund.assetManager && selectedFund.depositBank != selectedFund.assetManager)
                    promises.push(this.agentService.getAgentLite(selectedFund.assetManager).subscribe(agent => newFundForm['assetManager'] = agent));
                
                //wait for thoses 2 promises to push the new object in the parent fund list
                Promise.all(promises).then(fund => {
                    this.fundForms = this.fundForms.concat(newFundForm);
                    this.fundFormsChange.emit(this.fundForms);
                })
            }
                  
        });
    }

    remove(index) {
        this.fundForms.splice(index,1); 
        this.fundFormsChange.emit(this.fundForms);      
    }

    generateMandate(fund: Fund) {
        this.generateMandateChange.emit(fund);
    }

    openFundDetail(f: Fund) {        
        if(f) {            
            let url = './#/fund/' +f.fundSubType+'/'+ f.fdsId;
            window.open(url,url);
        }
    }

    set valuableStepMode(stepName: string) { 

        if(VALUABLE_STEPS.some(step => step === stepName)) {
            this.valuationMode = StateMode.edit;
        } else {
            this.valuationMode = this.mode;
        }
    }
}