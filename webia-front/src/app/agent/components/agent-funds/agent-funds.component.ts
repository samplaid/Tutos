import { AgentService } from '../../agent.service';
import { Component, Input } from '@angular/core';
import { WebiaService } from '../../../_services';
import { Page, FundSearchCriteria, SearchPolicyCriteria, fundClassifierRegExp, AgentLite, AgentCategoryEnum } from './../../../_models';

@Component({
    selector: 'agent-funds',
    templateUrl: './agent-funds.tpl.html'
})
export class AgentFundsComponent  {
;
    busy;
    page:Page;
    pageNum = 1;
    pageSize = 10;
    codeMapper:any[];
    sc:FundSearchCriteria;
    morePoliciesFlag:string = '...';
    regXp = fundClassifierRegExp ;
    
    portfolio = 0;
    currency = 'EUR';
    
    @Input() statusArray: string;
    @Input() set agent(val: AgentLite){       
        if (val){
            this.init(val);
            this.searchFunds(1);
        }
    };


    constructor( private agentService:AgentService, private webiaService:WebiaService) { 
        this.webiaService.getClientCodeLabels().then((codeMapper:any[])=>this.codeMapper=codeMapper);
    }

    init(agent: AgentLite){       
        this.page = new Page();
        this.sc = new FundSearchCriteria(1,10);
        this.sc.excludeTerminated = true;

        switch (agent.category) {
            case AgentCategoryEnum.DEPOSIT_BANK:
                this.sc.depositBank = agent.agtId;
                break;
            case AgentCategoryEnum.ASSET_MANAGER:
                this.sc.assetManager = agent.agtId;
                break;
            case AgentCategoryEnum.INDEPENDENT_FINACIAL_INTERMDIARY:
                this.sc.financialAdvisor = agent.agtId;
                break;
            case AgentCategoryEnum.PRESTATION_SERVICE_INVESTMT:
                this.sc.financialAdvisor = agent.agtId;
                break;            
            default:
                break;
        }
        
    }

    searchFunds(page) {
        if (page)
            this.sc.pageNum = page;
        let criteria = Object.assign({},this.sc);
        this.busy = this.agentService.advanceSearchFund(criteria).then(page => {
            this.page = page;
            this.page.content.forEach(f=> {
                f['policies'] = ['Loading...'];
                this.searchPolicies(f);
            });
        }, e => { });
    }

    searchPolicies(fund){
        if (!fund || !fund.fdsId) return;
        let criteria = new SearchPolicyCriteria();
        criteria.pageNum = 1;
        criteria.pageSize = 20; // limit to 20 and give an indicator if more can be find
        criteria.fundId = fund.fdsId;
        this.agentService.searchPolicies(criteria).then(page => {fund['policies'] = (page.totalPageCount>1)? [...page.content.map(p=>p.polId),this.morePoliciesFlag] : page.content.map(p=>p.polId);}, e => { }); 
    }

    encodeUri(uri: any){
        return encodeURIComponent(uri);
    }

}