
import { AgentService } from '../../agent.service';
import { Component, Input } from '@angular/core';
import { WebiaService } from '../../../_services';
import { Page, SearchPolicyCriteria, clientClassifierRegExp, defaultPageSize } from './../../../_models';

@Component({
    selector: 'broker-policies',
    templateUrl: './broker-policies.tpl.html'
})
export class BrokerPoliciesComponent  {

    policies: any[];
    _policies: any[];
    busy;
    page:Page;
    codeMapper:any[];
    sc:SearchPolicyCriteria;
    regXp = clientClassifierRegExp ;        

    portfolio = null;
    currency = 'EUR';
   
    @Input() set agtId(val:string){       
        if (val){
            this.init(val);
            this.searchPolicies(1);
        }
    };


    constructor( private agentService:AgentService, private webiaService:WebiaService) { 
        this.policies=[];
        this.webiaService.getClientCodeLabels().then((codeMapper:any[])=>this.codeMapper=codeMapper);
    }

    init(brokerId){
        this._policies = [];
        this.sc = new SearchPolicyCriteria();
        this.sc.pageNum = 1;
        this.sc.pageSize = defaultPageSize / 2;
        this.sc.brokerId = brokerId;
        this.page = new Page();
        this.agentService.getBrokerValuation(this.sc.brokerId).then(v => this.setValuation(v));
    }

    searchPolicies(page){
        this.sc.pageNum = page || 1;   
        let criteria = Object.assign({},this.sc);
        this.busy = this.agentService.getBrokerPolicies(criteria).then(page => {this.page = page;}, e => { });   
    }
    
    setValuation(valuation){
        if(valuation) {
            this.portfolio = (valuation.totalOtherCurrency != null) ? valuation.totalOtherCurrency : ((valuation.totalPolicyCurrency) ? valuation.totalPolicyCurrency : Number(0));
        } else {
            this.portfolio = null;
        }
    }
}