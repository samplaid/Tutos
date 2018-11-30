import { Http, Response, URLSearchParams }                           from '@angular/http';
import { ApiService, MessageService, HttpService, HandleErrorOptions } from '../utils';
import {Injectable}                                 from '@angular/core';
import {Observable}                                 from 'rxjs/Observable';
import {BehaviorSubject}                            from 'rxjs/BehaviorSubject';
import { Page, FullAgent, AgentContact, AgentHierarchyRequest, AgentHierarchy, SearchPolicyCriteria, FundSearchCriteria, AgentCategoryEnum, AssetManagerStrategy, AgentLite } from '../_models';
import { AgentContactStatus } from '../_models/agent/index';



export const popupAgents = [AgentCategoryEnum.SUB_BROKER, AgentCategoryEnum.PERSON_CONTACT, AgentCategoryEnum.INTRODUCER];
export const newTabsAgents = [AgentCategoryEnum.BROKER, AgentCategoryEnum.DEPOSIT_BANK, AgentCategoryEnum.INDEPENDENT_FINACIAL_INTERMDIARY, AgentCategoryEnum.PRESTATION_SERVICE_INVESTMT , AgentCategoryEnum.ASSET_MANAGER];


@Injectable()
export class AgentService extends HttpService {
     dataStore;
    
    constructor($http: Http, messageService: MessageService, private api: ApiService) {        
        super($http,  messageService);
        this.domain = 'AgentService';
        this.initStore();
        this.getAgentFoyer().subscribe(agent=> {}, e =>{ });
    }

    
    initStore(){
        this.dataStore = Object.assign({});
    }

    searchAgent(searchCriteria:SearchCriteria): Observable<Page>{
        return this.POST(this.api.getURL('searchAgent'),searchCriteria,'searchAgent');
    }

    searchFinAdvisor(searchCriteria: SearchCriteria): Observable<Page>{
        return this.POST(this.api.getURL('searchFinAdvisor'),searchCriteria,'searchFinAdvisor');
    }

    getAgent(id:string){    
        if(!id) {
            return Observable.empty().toPromise();
        }
        return this.GET(this.api.getURL('getAgent', id.trim()),'getAgent').toPromise().catch(err => {});
    }

    getAgentLite(id:string){    
        if(!id) {
            return Observable.empty();
        }
        return this.GET(this.api.getURL('getAgentLite', id.trim()),'getAgentLite');
    }    

    getAgentFoyer(){
        if (!this.dataStore.agentFoyer) {
        	return this.GET(this.api.getURL('getAgentFoyer'), 'getAgentFoyer').do((data:any) => this.dataStore.agentFoyer = data)
        	.map((data) => this.dataStore.agentFoyer);
        } else {
           return Observable.of(this.dataStore.agentFoyer);
        }
    }

    getAllAgentCategories(){
        if (!this.dataStore.allAgentCategories ) {
            this.dataStore.allAgentCategories = this.GET(this.api.getURL('getAllAgentCategories'), 'getAllAgentCategories').publishReplay(1).refCount();
        }
        return this.dataStore.allAgentCategories;
    }

    getWealinsAssetManager(){
         if (!this.dataStore.wealinsAssetManager) {
        	return this.GET(this.api.getURL('getWealinsAssetManager'), 'getWealinsAssetManager').do((data:any) => this.dataStore.wealinsAssetManager = data)
        	.map((data) => this.dataStore.wealinsAssetManager);
        } else {
           return Observable.of(this.dataStore.wealinsAssetManager);
        }
    }

    createAgent(agent: FullAgent){
        return this.POST(this.api.getURL('createAgent'), agent, 'create', new HandleErrorOptions(true, true,'agentCpt'));
    }

    updateAgent(agent: FullAgent){
        return this.POST(this.api.getURL('updateAgent'), agent, 'update', new HandleErrorOptions(true, true,'agentCpt'));
    } 

    saveAgent(agent: FullAgent): Promise<FullAgent> {      
        if(agent && agent.agtId){
            return this.updateAgent(agent).toPromise().then(updatedAgent => { 
                this.sendMessage('The agent with name ' + updatedAgent.name + ' and id ' + updatedAgent.agtId + ' was successfully updated.'); 
                return updatedAgent; 
            }, e => Promise.reject(e))
            // .catch(error => {
            //     return Promise.reject(error);
            // });
        } else if(agent && !agent.agtId){
            return this.createAgent(agent).toPromise().then(createdAgent => { 
                this.sendMessage('The agent with name ' + createdAgent.name + ' and id ' + createdAgent.agtId + ' was successfully created.');
                return createdAgent; 
            }, e => Promise.reject(e))
            // .catch(error => {
            //     return Promise.reject(error);
            // });
        } else {
            return Promise.reject('UnsavedObjectException');
        }
    }

    findAgentContact(agcId: number){
        return this.GET(this.api.getURL('findAgentContact', agcId),'findAgentContact').toPromise().catch(err => {});
    }

    saveAgentContact(agentContact: AgentContact){   
        return this.POST(this.api.getURL('saveAgentContact'), agentContact, 'saveAgentContact' , new HandleErrorOptions(true, true,'agentContact'))
    }
   
    saveContactAndUpdateAgent(agent: FullAgent, agentContact: AgentContact): Promise<any> {
        const updateAgent = (nextAgentContact: AgentContact) => {
            // Find and update the contacts in agent
            let refAgentContact = agent.agentContacts.find((eachAgentContact: AgentContact) => eachAgentContact.agentId === agent.agtId && eachAgentContact.agcId === nextAgentContact.agcId);
            if(refAgentContact){
                refAgentContact = nextAgentContact;
            } else {
                agent.agentContacts = [...agent.agentContacts, nextAgentContact];
            }

            return { 'agent': agent, 'agentContact': nextAgentContact };
        };
        
        return this.saveAgentContact(agentContact).map(updateAgent).toPromise();
    } 

    /** return active sub-broker only */
    getSubBroker(agtId:string){
        if (agtId)
            return this.GET(this.api.getURL('getSubBroker', agtId), 'getSubBroker').toPromise().catch(e=>{});
    }

    searchAgentHierarchies(request: AgentHierarchyRequest): Observable<Page>{
         return this.POST(this.api.getURL('searchAgentHierarchy'), request,'searchAgentHierarchy');
    }

    findAgentHierarchyByCriteria(request: AgentHierarchyRequest): Promise<AgentHierarchy[]>{
        return this.POST(this.api.getURL('findAgentHierarchyByCriteria'), request,'findAgentHierarchyByCriteria').toPromise();
    }

    sendMessage( message: string){
        this.messageService.addAlertSuccess(message, true,'agentCpt');    
    }

    addAgentHierarchy(agent: FullAgent): Promise<FullAgent>{
        if(agent && agent.agtId){
            return this.POST(this.api.getURL('addAgentHierarchy'), agent, 'addAgentHierarchy', new HandleErrorOptions(true, true,'agentCpt')).toPromise();
        }
    }

    addAgentContact(agent: FullAgent){
        if(agent && agent.agtId){
            return this.POST(this.api.getURL('addAgentContact'), agent, 'addAgentContact', new HandleErrorOptions(true, true,'agentCpt')).toPromise();
        } 
    }

    addAgentBankAccount(agent: FullAgent){
        if(agent && agent.agtId){
            return this.POST(this.api.getURL('addAgentBankAccount'), agent, 'addAgentBankAccount', new HandleErrorOptions(true, true,'agentCpt')).toPromise();
        }
    }

    getBrokerPolicies(searchCriteria:SearchPolicyCriteria): Promise<Page>{
        return this.POST(this.api.getURL('getBrokerPolicies'),searchCriteria, 'getBrokerPolicies').toPromise().catch(e=>{});
    }

    getBrokerValuation(agtId: string, currency: string = ''){
        return this.GET(this.api.getURL('getBrokerValuation',agtId,currency), 'getBrokerValuation').toPromise().catch(e=>{});
    }

    advanceSearchFund(criteria: FundSearchCriteria) : Promise<Page>{
        return this.POST(this.api.getURL('advanceSearch'), criteria, 'advanceSearch').toPromise().catch(e=>{});
    }

    sort(arr: AgentHierarchy[]): AgentHierarchy[]{
        if(!arr) return [];
        return arr.sort((a,b) => {
                if(!a && !b) return 0;
                if(!a && b)  return -1;
                if(a && !b)  return 1;                
                if(!a.subBroker && !b.subBroker) return 0;
                if(!a.subBroker && b.subBroker)  return -1;
                if(a.subBroker && !b.subBroker)  return 1;
                if(!a.subBroker.name && !b.subBroker.name) return 0;
                if(!a.subBroker.name && b.subBroker.name)  return -1;
                if(a.subBroker.name && !b.subBroker.name)  return 1;  
              
                return a.subBroker.name.localeCompare(b.subBroker.name);
        });
    }

    sortContacts(arr: AgentContact[]): AgentContact[]{
        if(!arr) return [];
        return arr.sort((a,b) => {
                if(!a && !b) return 0;
                if(!a && b)  return -1;
                if(a && !b)  return 1;                
                if(!a.contact && !b.contact) return 0;
                if(!a.contact && b.contact)  return -1;
                if(a.contact && !b.contact)  return 1;
                if(!a.contact.name && !b.contact.name) return 0;
                if(!a.contact.name && b.contact.name)  return -1;
                if(a.contact.name && !b.contact.name)  return 1;     
                
                return a.contact.name.localeCompare(b.contact.name);
        });
    }

    findByCriteria(searchCriteria:SearchCriteria): Observable<AgentLite[]> {
        return this.POST(this.api.getURL('findByCriteria'),searchCriteria,'findByCriteria');
    }

    getCategoryName(code:string): string{
        let name: string;
        switch (code) {
            case AgentCategoryEnum.ASSET_MANAGER : name = 'Asset Manager'; break;
            case AgentCategoryEnum.BROKER: name = 'Broker'; break;
            case AgentCategoryEnum.WEALINS_SALES_PERSON: name = 'Country Manager'; break;
            case AgentCategoryEnum.DEPOSIT_BANK: name = 'Custodian Bank'; break;
            case AgentCategoryEnum.INDEPENDENT_FINACIAL_INTERMDIARY: name = 'Independent financial intermediary'; break;
            case AgentCategoryEnum.PRESTATION_SERVICE_INVESTMT: name = 'Prestation service investment'; break;
            case AgentCategoryEnum.INTRODUCER: name = 'Business Introducer'; break;
            case AgentCategoryEnum.PERSON_CONTACT: name = 'Person Contact'; break;
            default: name = 'Agent'; break;
        }
        return name;
    }  

    searchPolicies(searchCriteria:SearchPolicyCriteria): Promise<Page>{
        return this.POST(this.api.getURL('searchPolicies'),searchCriteria,'searchPolicies').toPromise();
    }

    getAssetManagerStrategies(agtId: string){         
        return this.functionWithStore('AMStrat'+agtId, this.api.getURL('getAssetManagerStrategies', agtId), 'getAssetManagerStrategies');
    }

    saveOrUpdateAms(assetManagerStrategy: AssetManagerStrategy): Promise<AssetManagerStrategy> {
        return this.POST(this.api.getURL('saveOrUpdateAms'), assetManagerStrategy,'saveOrUpdateAms').toPromise();
    }

    bulkSaveOrUpdateAms(assetManagerStrategies: AssetManagerStrategy[]): Promise<AssetManagerStrategy[]> {
        return this.POST(this.api.getURL('bulkSaveOrUpdateAms'), assetManagerStrategies,'bulkSaveOrUpdateAms').toPromise();
    } 
    
    toContactFunctionDescByKey(contactFunctionKey: string, contactFunctions: Array<any>): string{        
        let contactFuntion = contactFunctions.find( el => el.keyValue === contactFunctionKey);
        return (contactFuntion && contactFuntion.description) ? contactFuntion.description : '';
    }

    toContactFunctionDescByContactId(contactId: string, agentContacts: AgentContact[], contactFunctions: Array<any>): string {
        const emtpyString = '';
        let description: string = emtpyString;       

        if(agentContacts){
           let agtContacts: AgentContact[] =  agentContacts.filter(ac => ac && ac.contact && ac.contact.agtId === contactId && ac.status === AgentContactStatus.ENABLED);           
            if(agtContacts){
                let newArray: any[] = agtContacts.map(value => this.toContactFunctionDescByKey(value.contactFunction, contactFunctions));
                return (newArray && newArray.length > 0) ? newArray.reduce((prevValue, currValue) => prevValue + ', ' + currValue): emtpyString;
            } 
        }
        
        return description;
    }

    isWealins(agentId: string): boolean {
        return this.dataStore.agentFoyer && agentId === this.dataStore.agentFoyer.agtId;
    }

    isCategoryIn(category: string, categories: string[]): boolean {
        return (categories) ? categories.some(cat => cat === category) : false;
    } 

    searchAccountRootPatternExample(depositBank: string): Observable<String[]> {
        return this.GET(this.api.getURL('searchAccountRootPatternExample', depositBank),'searchAccountRootPatternExample');
    }

    getCommunicationAgents(brokerId: string, subBrokerId:string, policyId: string) : Observable<any[]> {    
        return this.GET(this.api.getURL('getCommunicationAgents', brokerId, subBrokerId, policyId),'getCommunicationAgents');
    }
}

//criteria object for the search service
export class SearchCriteria {
    filter :string;   // search string, can be the third party id or its name
    categories :string[];  //FS (Country manager), DB (Custodian bank), AM (Asset manager) or FA (Financial advisor)
    pageNum :number;  //pagination page number, one base indexed
    pageSize :number;  //pagination page size 
    status: number;
}