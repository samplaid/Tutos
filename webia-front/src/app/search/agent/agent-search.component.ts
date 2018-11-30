import { AgentSearchCriteria } from './agent-search-criteria';
import { Component, OnInit } from '@angular/core';
import { SearchService } from '../search.service';
import { Page } from './../../_models/page';
import { AgentStatusType, defaultPageSize } from "../../_models";
import { AgentService, popupAgents, newTabsAgents } from "../../agent/agent.service";
import { StateMode } from "../../utils";

@Component({
  templateUrl: 'agent-search.tpl.html'
})

/**
 * Home component
 * 
 * Contains :
 */
export class AgentSearchComponent implements OnInit {
  busy;
  page: Page;
  noResult: boolean;
  partnerRoles: any[];
  category;
  categories$;
  stateMode = StateMode;
  mode: string;

  /////////// Criteria form variables ////////////
  pageNum;
  frmFilter;
  frmStatus;
  frmPartnerRole;
  ///////////////////////////////////////////////

  constructor(private searchService: SearchService, private agentService: AgentService) { }

  ngOnInit() {
    this.pageNum = 1;
    this.frmStatus = AgentStatusType.ENABLED;
    this.page = new Page();
    this.categories$ = this.agentService.getAllAgentCategories();
  }

  statusChange(event) {
      this.frmStatus = (event.target.checked) ? AgentStatusType.ENABLED : undefined;
      if (this.frmFilter || this.frmPartnerRole)
        this.search(1);
  }

  search(page) {
    let criteria = new AgentSearchCriteria(this.pageNum, defaultPageSize);
    if (page)
      criteria.pageNum = page;
    
    criteria.status = this.frmStatus;
    criteria.filter = this.surroundWithPercent(this.frmFilter);
    criteria.categories = this.frmPartnerRole ? [this.frmPartnerRole] : [];
    
    this.busy = this.searchService.searchAgent(criteria).toPromise().then(pageItem => {
        this.page = pageItem;
        this.category =this.frmPartnerRole;
        this.noResult = (!pageItem.content || pageItem.content.length < 1);
    }, e => { });
  }

  surroundWithPercent(value: string) : string {
        return value ? ("%" + value + "%") : value;
  }

  setResult(page) {
    if (page) {
      this.page = page;
      this.noResult = (!page.content || page.content.length < 1);
    }
  }

  eraseFields(){
    this.frmStatus = AgentStatusType.ENABLED;
    this.frmFilter=undefined;
    this.frmPartnerRole=undefined;
  }

  ngOnDestroy() {
  }

  canOpenInTab(agent){
    return newTabsAgents.findIndex((cat)=> cat && agent.category && cat.trim() == agent.category.trim()) > -1;
  }

  canOpenInPopup(agent){    
    return popupAgents.findIndex((cat)=> cat && agent.category && cat.trim() == agent.category.trim()) > -1;
  }

}


