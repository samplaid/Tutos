import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { AgentStatusType, AgentContact, Page, AgentBankAccount, AgentHierarchy, defaultPageSize, AgentHierarchyRequest, AgentHierarchyStatus, AgentLiteAdapter, AgentContactStatus, AgentBankAccountStatusType, AssetManagerStrategy, Roles, OperationEntrySearchCriteria } from '../../_models';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { MessageService, OrderByPipe, UserService, StateMode } from '../../utils';
import { AgentService } from '../agent.service';
import { Observable } from 'rxjs/Observable';
import { FullAgent, AgentCategoryEnum } from '../../_models/agent/index';
import { UoptDetailService, CountryService, OptionDetailService, OperationEntryService } from "../../_services";
import { Subscription } from "rxjs/Subscription";
import { PaginationService } from "../../utils/services/pagination.service";
import { ConfigService } from '../../utils';


//type StateMode = 'readOnly' | 'waiting' | 'edit' | 'create';
const DEFAULT_PAGE_SIGE = 10;

@Component({
    selector: 'agent',
    templateUrl: './agent.component.html'
})
export class AgentComponent implements OnInit, OnDestroy {
    
    subs: Subscription[];
    saveAgentSub: Subscription;
    busy: any;
    title: string = '';
    agent: FullAgent;
    ptCode: string;    
    payments: any[] = [];
    countries: any[];
    orderAccounts: any[] = [];
    languages: any[];
    paymentPage: Page;   
    operationEntriesPage: Page;   
    agentContactPage: Page;    
    agentContactStatus: string;
    agentBankAccountStatus: number;
    contactFunctions: any[] = [];
    stateMode = StateMode;
    appRoles = Roles;
    saving: boolean;
    agtCat = AgentCategoryEnum;
    operationEntryBusy: any;
    page = 1;
    pageSize = DEFAULT_PAGE_SIGE;

    constructor(protected agentService: AgentService,
        protected route: ActivatedRoute,
        protected router: Router,
        protected uoptDetailService: UoptDetailService,
        protected optionDetailService: OptionDetailService,
        protected countryService: CountryService,
        protected messageService: MessageService,
        protected titleService: Title,
        protected paginationService: PaginationService,
        protected userService: UserService,
        protected operationEntryService: OperationEntryService,
        protected config: ConfigService ) { }
    
    @Input() inModal:boolean;
    @Input() agentId: string;
    @Input() mode = StateMode.edit;

    @Input() dbMode = StateMode.readonly;
    @Input() subBrokerMode = StateMode.readonly;
    @Input() bkMode = StateMode.readonly;
    @Input() disabledMode = StateMode.readonly;

    ngOnInit() {
        this.subs = [];
        this.countries =[];
        this.languages =[];
        this.contactFunctions = [];
        
        //this.agent = new FullAgent(); // Only to use on Creation mode
        this.initPagination();        
        this.agentContactStatus = AgentContactStatus.ENABLED;
        this.agentBankAccountStatus = AgentBankAccountStatusType.ENABLED;
        
        this.subs.push(this.optionDetailService.getPaymentModes().subscribe(payments => this.payments = payments));
        this.subs.push(this.uoptDetailService.getLanguages().subscribe(languages => this.languages = languages));
        this.subs.push(this.countryService.loadAllCountries().subscribe(countries => this.countries = countries));
        this.subs.push(this.uoptDetailService.getTypeOfAgentContact().subscribe(contactFunctions => this.contactFunctions = contactFunctions));
              
        this.subs.push(this.agentService.getAgentFoyer().subscribe(agent=> {
            if(agent.bankAccounts){
                this.orderAccounts = agent.bankAccounts
                            .filter(ba=> ba && ba.status == 1)
                            .map(ba => ba.bic)
                            .filter((item, i, self) => self.indexOf(item) === i)
                            .sort((a,b)=>(a <= b)?-1:1) ;                            
            }
        }));

         
        /// This code should not be used on creation mode. Future feature: create a new broker.
        if(!this.inModal){
            //////// call from url with paramId        
            this.route.params.subscribe((urlParams: Params) => {
                this.agentId = (urlParams && urlParams['id']) ? urlParams['id'] : this.agentId;
                this.busy = (this.agentId) ? this.getAgent(this.agentId) : this.busy;
            });
        } else {
            this.busy = (this.agentId) ? this.getAgent(this.agentId) : this.busy;
        }  
        
        this.setMode();

       let synchroDRM = this.config.getProps('synchroDRM');
       if(synchroDRM == 0){
           this.disabledMode = StateMode.update;
       }

    }

    setMode(){     
        if(!this.inModal) {
            this.route.params.subscribe((urlParams: Params) => {               
                if (this.userService.hasRole(Roles.AGENT_BD_EDIT)){
                    this.dbMode = (urlParams && urlParams['id']) ? StateMode.update : StateMode.create ;                    
                } else {
                    this.dbMode = StateMode.readonly;
                }
                
                if (this.userService.hasRole(Roles.AGENT_SB_EDIT)){
                    this.subBrokerMode = (urlParams && urlParams['id']) ? StateMode.update : StateMode.create ;                    
                } else {
                    this.subBrokerMode = StateMode.readonly;
                }

                if (this.userService.hasRole(Roles.AGENT_BK_EDIT)){
                    this.bkMode = (urlParams && urlParams['id']) ? StateMode.update : StateMode.create ;                    
                } else {
                    this.bkMode = StateMode.readonly;
                }
            });
        }        
    }

    getAgent(id){
        this.agentService.getAgent(id).then((agt: FullAgent) =>{ 
            if(agt) {
                this.agent = agt;
                if(!this.inModal)
                    this.titleService.setTitle(agt.name);                
                this.title = agt.name;
                if(agt.bankAccounts) {
                    this.paymentPage = this.paginationService.nextPage(agt.bankAccounts, 1, DEFAULT_PAGE_SIGE);
                }

                this.printAgentContact(agt);
                if(this.agent.category == 'BK' || this.agent.category == 'DB' || this.agent.category == 'AM') {
                    this.getOperationForPartner(1);
                }

            } else {
                this.agent = new FullAgent();
            }
        });  
        
    }

    printAgentContact(agt: FullAgent){
        if(agt.agentContacts){
            agt.agentContacts = this.agentService.sortContacts(agt.agentContacts);
            this.agentContactPage = this.paginationService.nextPage(agt.agentContacts, 1, DEFAULT_PAGE_SIGE);
        }
    }
    
    deactiveContact(agcParam: AgentContact){
        if(agcParam){   
            let foundAgc = this.agent.agentContacts.find(agc=>agc.agcId == agcParam.agcId);        
            if(foundAgc){
                foundAgc.status = AgentContactStatus.DISABLED;
            }
            this.agentContactPage = this.paginationService.nextPage(this.agent.agentContacts, this.agentContactPage.number, DEFAULT_PAGE_SIGE);
        }              
    }

    contactChange(agentContact: AgentContact){
        if(agentContact){   
            this.getAgent(agentContact.agentId);            
        }        
    }

    agentChange(agent){
        if(agent) {
            this.agent = agent;
            this.agentContactPage = this.paginationService.nextPage(this.agent.agentContacts, 1, DEFAULT_PAGE_SIGE);
        }        
    }

    contactActiveChange(event) {
        this.agentContactStatus = (event.target.checked) ? AgentContactStatus.ENABLED : undefined;
        this.agentContactPage = this.paginationService.nextPage(this.agent.agentContacts, 1, DEFAULT_PAGE_SIGE);
    }
    filterAgc(){
        return (this.agentContactStatus == AgentContactStatus.ENABLED) ? 'item.status=="1"' : 'true';
    }
    bankAccountActive(event) {
         this.agentBankAccountStatus = (event.target.checked) ? AgentBankAccountStatusType.ENABLED : undefined;
    }
    filterAgb(){
        return (this.agentBankAccountStatus == AgentBankAccountStatusType.ENABLED) ? 'item.status==1' : 'true';
    }

    searchAgc(text: string){
        if(text) {
            if(this.agent && this.agent.agentContacts){
                let filteredArray = this.agent.agentContacts.filter(agc=>{
                    return (agc && agc.contact && ( (agc.contact.name && agc.contact.name.toUpperCase().includes(text.toUpperCase())) || (agc.contact.firstname && agc.contact.firstname.toUpperCase().includes(text.toUpperCase())) ));
                });
                this.agentContactPage = this.paginationService.nextPage(filteredArray,1, DEFAULT_PAGE_SIGE);
            }
        } else {
            this.agentContactPage = this.paginationService.nextPage(this.agent.agentContacts, 1, DEFAULT_PAGE_SIGE);
        }
        
    }

    clearSearchAgc(inputSearchAgc){
        inputSearchAgc.value = '';
        this.agentContactPage = this.paginationService.nextPage(this.agent.agentContacts, 1, DEFAULT_PAGE_SIGE);
    }

    save() {  
        this.saving = true;
        return this.agentService.saveAgent(this.agent).then(agent => {
            this.agent = Object.assign({}, agent); 
            this.goToContactPage(this.agentContactPage.number, this.agent.agentContacts);
            this.saving = false;
        }, e => { this.saving = false; });
    }

    initPagination(){
        this.paymentPage = new Page();
        this.paymentPage.size = DEFAULT_PAGE_SIGE;
        this.agentContactPage = new Page();
        this.agentContactPage.size = DEFAULT_PAGE_SIGE;
        this.operationEntriesPage = new Page();
        this.operationEntriesPage.pageSize = DEFAULT_PAGE_SIGE;
    }

    goToPage(pageNumber){
        if(this.agent && this.agent.bankAccounts){
            this.paymentPage = this.paginationService.nextPage(this.agent.bankAccounts, pageNumber, DEFAULT_PAGE_SIGE);
        }
    }

    getOperationForPartner(pageNumber){
        this.operationEntriesPage.currentPage = pageNumber;
        this.operationEntryBusy = this.operationEntryService.getOperationForPartner(this.agent.agtId, this.agent.category).subscribe(operations => {
            this.operationEntriesPage.content = (!!operations) ? operations : [];
            this.operationEntriesPage.totalRecordCount = this.operationEntriesPage.content.length;
        }, e => { });
    }

    goToContactPage(pageNumber, array){
        if(this.agent && this.agent.agentContacts){
            this.agent.agentContacts = this.agentService.sortContacts(this.agent.agentContacts);
            this.agentContactPage = this.paginationService.nextPage(this.agent.agentContacts, pageNumber, DEFAULT_PAGE_SIGE);            
        }
    }
    
    statusChange(flag) {
        this.agent.status = (flag ? AgentStatusType.ENABLED : AgentStatusType.DISABLED);
    }

    removeContact(index) {
        if (this.agent.agentContacts && this.agent.agentContacts[index]) {
            this.agent.agentContacts[index].status="2";
            this.agent.agentContacts = [...this.agent.agentContacts]; //force the refresh of the filter in template
        }
    }
  
    refresh(){
        this.ngOnInit();
    }

    ngOnDestroy(): void {
        if(this.subs){
            this.subs.forEach(sub => sub && sub.unsubscribe());
        }

        if(this.saveAgentSub && !this.saveAgentSub.closed) {
            this.saveAgentSub.unsubscribe();
        }
    }

    translateFunction(code: string): string {
        let contactFunction = this.contactFunctions.find( contactFunction => code && contactFunction && contactFunction.keyValue && contactFunction.keyValue.toUpperCase() == code.toUpperCase());
        if(contactFunction){
            return contactFunction.description;
        } else {
            return ''
        }
    }

    updateHierarchies(hierarchies: Array<AgentHierarchy>): void {
        console.log(hierarchies);
    } 

    isCategoryIn(category: string, categories: string[]): boolean {
        return this.agentService.isCategoryIn(category, categories);
    }
}