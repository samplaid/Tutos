import { Component, Input, Output, EventEmitter, OnInit, AfterViewInit, ElementRef, ViewChild, SimpleChanges, ComponentFactoryResolver } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { AgentService, SearchCriteria, popupAgents, newTabsAgents } from '../agent.service'
import { Page, AgentStatusType, defaultPageSize, AgentCategoryEnum, AgentLite } from '../../_models';
import { Observable } from "rxjs/Observable";
import { Subscription } from "rxjs/Subscription";
import { Title } from "@angular/platform-browser";
import { AgentBasicModalContent } from "../components/basic-form/modal/agent-basic-modal-content.component";
import { StateMode } from "../../utils/index";
import { AdAgentLabelDirective } from '../../utils/directives/ad-agent-label.directive';
import { SearchAgentDisplayNameComponent } from './search-agent-display-name.component';

@Component({
  selector: 'search-agent',
  providers: [],
  templateUrl: './search-agent.tpl.html',
  styleUrls: ['search-agent.style.css'],
  entryComponents: [ SearchAgentDisplayNameComponent ]  
})
export class SearchAgentComponent implements OnInit {
  
  private _labelFn: Function;
  _label: string;
  _title: string;
  _agentId: string;
  nbbtn:number;
  private _bindedAgentId: string;
  private modalRef: BsModalRef;
  agentCategories$: Observable<any>;
  agentCategoriesSub: Subscription;
  header = 'Agent';
  selected: AgentLite;
  busy;
  searchCriteria: SearchCriteria;
  page: Page;
  noResult: boolean = false;
  _currentAgent: AgentLite;
  hasMultipleTypes: boolean = false;
  oldIntroducerTitle: string = '';

  @ViewChild(AdAgentLabelDirective) adAgtLabel: AdAgentLabelDirective;

  @Input() allowClear: boolean = true;
  @Input() required: boolean = false;
  @Input() disabled: boolean = false;
  @Input() css; 
  @Input() showLabel: boolean;

  @Input() searchForAgent: string;

  @Input() set labelFn(labelFn: Function) {
    this._labelFn = labelFn;
  }

  @Input() set agentId(value: string) {
    this._agentId = value;
    this._bindedAgentId = value;
    this.agentIdChange.emit(this._agentId);
  }

  get agentId() {
    return this._bindedAgentId;
  }

  @Input() set type(type: string | string[]) {
    if (type){
      if (Array.isArray(type))
        this.searchCriteria.categories = type;
      else
        this.searchCriteria.categories = [type];
    } else { this.searchCriteria.categories = [ AgentCategoryEnum.ASSET_MANAGER ];  }
      
  };

  @Input() set status(status: number){
    this.searchCriteria.status = (status && status > 0) ? status : AgentStatusType.ENABLED;
  }

  @Output() agentIdChange = new EventEmitter<any>();
  @Output() onChange = new EventEmitter<any>();

  constructor(private modalService: BsModalService, 
    private agentService: AgentService,  
    protected titleService: Title,
    private componentFactoryResolver: ComponentFactoryResolver) {
    this.searchCriteria = new SearchCriteria();
    this.searchCriteria.status = AgentStatusType.ENABLED;
    this.searchCriteria.pageNum = 1;
    this.searchCriteria.pageSize = defaultPageSize;
    this.page = new Page();
    this.agentCategories$ = this.agentService.getAllAgentCategories();
  }

  private setLabel(agent: AgentLite) {
    this._currentAgent = agent;
    if (!agent) {
      this._label = '';
      if(this.adAgtLabel && this.adAgtLabel.viewContainerRef){
        this.adAgtLabel.viewContainerRef.clear();
      }
    } else {
      //this._label = agent.name + " - <small><i>" + agent.agtId + "</i></small>";;
      this._label = agent.name;

      if (this._labelFn) {
        this._label = this._labelFn.call(null, agent);
      }
      
      this._title = this._label.replace(/<[^>]+>/gm, '');
      let componentFactory = this.componentFactoryResolver.resolveComponentFactory(SearchAgentDisplayNameComponent);
      let viewContainerRef  = this.adAgtLabel.viewContainerRef;
      viewContainerRef.clear();
      let componentRef  = viewContainerRef.createComponent(componentFactory);        
      componentRef.instance.agtId = agent.agtId;
      componentRef.instance.categoryCode = agent.category;
      componentRef.instance.label = this._label;
      componentRef.instance.title = this._title;
      componentRef.instance.showLabel = this.showLabel;

      if(popupAgents.findIndex((cat)=> cat && agent.category && cat.trim() == agent.category.trim()) > -1){
        componentRef.instance.displayMode = 'POP_UP';
      } else if(newTabsAgents.findIndex((cat)=> cat && agent.category && cat.trim() == agent.category.trim()) > -1){
        componentRef.instance.displayMode = 'LINK';
      } 
      
    }
    
  }

  ngOnInit() {
    this.initInterface();
    this.nbbtn = 2;    
    if (!this.allowClear) this.nbbtn--;
    if (this.disabled) this.nbbtn = 0;
    this.css += this.nbbtn>0? ' nbbtn'+this.nbbtn : '';
  }

  initInterface() {
    switch (this.searchCriteria.categories[0]) {
      case AgentCategoryEnum.ASSET_MANAGER: this.header = 'Asset Manager'; break;
      case AgentCategoryEnum.BROKER: this.header = 'Broker'; break;
      case AgentCategoryEnum.WEALINS_SALES_PERSON: this.header = 'Country Manager'; break;
      case AgentCategoryEnum.DEPOSIT_BANK: this.header = 'Custodian Bank'; break;
      case AgentCategoryEnum.PRESTATION_SERVICE_INVESTMT: this.header = 'Financial Advisor'; break;
      case AgentCategoryEnum.INDEPENDENT_FINACIAL_INTERMDIARY: this.header = 'Financial Advisor'; break;
      case AgentCategoryEnum.INTRODUCER: this.header = 'Business Introducer'; break;
      case AgentCategoryEnum.SUB_BROKER: this.header = 'Sub-Broker'; break;
      case AgentCategoryEnum.PERSON_CONTACT: this.header = 'Person Contact'; break;
      default: this.header = 'Agent'; break;
    }

  }

  ngOnChanges(changes: SimpleChanges) {
    if (!this._currentAgent || (changes['agentId'] && (this._currentAgent.agtId != changes['agentId'].currentValue))) {
      this.loadAgent(this._agentId);
    }
  }

  loadAgent(id) {
    if (id)
      this.agentService.getAgent(id).then(agent => {
        this.setLabel(agent);
        this.onChange.emit(agent);
      });
    else
      this.setLabel(null);
  }

  openBasicAgent(category: string, mode){
    if (!this.disabled){
        this.oldIntroducerTitle = this.titleService.getTitle();
        let initialState = { title: (this._currentAgent) ? this._currentAgent.name :'Introducer', mode, category, agentId: this.agentId};
        this.modalRef = this.modalService.show(AgentBasicModalContent, {class:'modal-sm'}); 
    }
  }

  open(content) {
    if (!this.disabled) {
      this.modalRef = this.modalService.show(content, { class: 'modal-lg' });
    }
  }

  tryOpen(content){
    if (!this.disabled && !this.agentId && (!this._label ||  this._label=='') ) {
      this.open(content);
    } else {
      if(popupAgents.findIndex((cat)=> cat && this._currentAgent && this._currentAgent.category && cat.trim() == this._currentAgent.category.trim()) > 0){
         this.openBasicAgent(popupAgents[this._currentAgent.category], StateMode.readonly);
      }      
    }
  }

  search(page) {
    if (page)
      this.searchCriteria.pageNum = page;
    let sc = Object.assign({},this.searchCriteria);  
    sc.filter = sc.filter ? '%'+sc.filter+'%' : sc.filter;

    if(this.isFinAdvisorSearch(this.searchForAgent)) {
      this.busy = this.agentService.searchFinAdvisor(sc).subscribe(page => this.setResult(page), e => { });
    } else {
      this.busy = this.agentService.searchAgent(sc).subscribe(page => this.setResult(page), e => { });
    }
    
  }

  isFinAdvisorSearch(searchForAgent: string): boolean {
    return searchForAgent === 'FA';
  }

  setResult(page) {
    this.hasMultipleTypes = (this.searchCriteria.categories && this.searchCriteria.categories.length>1)? true:false;
    this.page = page;
    this.noResult = (!page.content || page.content.length < 1);
  }

  selectItem(agent: AgentLite) {
    this.selected = agent;
    this._agentId = agent.agtId;
  }

  clear() {
    if (this.allowClear) {
      this.agentId = null;
      this.setLabel(null);
      this.onChange.emit(null);
    }
  }

  updateResult(agent){
        this.agentId = agent.agtId;
        this.setLabel(agent);
        this.onChange.emit(agent);
        this.searchCriteria.filter = null;
        this.page = new Page();
  }

  close(selected: any) {
    this.updateResult(selected);
    this.modalRef.hide();
  }

  dismiss(obj: any) {
    this.modalRef.hide();
  }

  ngOnDestroy() {
    if (this.busy)
      this.busy.unsubscribe();
  }
  
}