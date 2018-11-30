import { Component, OnInit, EventEmitter, Output, Input, OnDestroy, OnChanges, SimpleChanges } from '@angular/core';
import { AgentService } from "../../agent.service";
import { Subscription } from "rxjs/Subscription";
import { AgentContact, FullAgent, AgentContactStatus, wealinsId } from "../../../_models";
import { UoptDetailService } from '../../../_services';
import { Observable } from 'rxjs/Observable';
import { StateMode } from '../../../utils';

@Component({
    selector: 'agc-add-select',
    templateUrl: './agent-contact-add-select.component.html'
})
export class AgentContactAddSelectComponent implements OnInit, OnDestroy, OnChanges {
    
    initTitle: string = 'Contact creation';
    title:string = this.initTitle;
    agentOwner:FullAgent;  // the private parent agent
    selectedAgent:FullAgent; // the private selected contact
    emptyAgent = new FullAgent();
    isActive:boolean = true;
    contactFunctions = [];
    funcSubscribe: any;
    wealinsId = wealinsId;
    editMode: string;
    showAddContact: boolean = true;

    @Input() set disabled(value: boolean) {
        //FIXME: Temporary solution, to avoid blowing up everything, this component should be refactored!
        this.editMode = value ? StateMode.readonly : StateMode.edit;
    }
    @Input() initialFunction: string = 'OTHERF';

    @Input() css = '';
    @Input() set agent(inputAgt){ // the parent/owner agent with its contacts list  -->  set into private agentOwner variable  
        if(inputAgt){
            this.setTitle(inputAgt.name);

            if (this.agentOwner && this.agentOwner.agtId != inputAgt.agtId)
                this.selectContact(null); //on parent change then clear also selected contact person
            if (!inputAgt.agentContacts) { // if inputAgt is not a FullAgent with its contact then complete the full data
                this.loadOwnerAgent(inputAgt.agtId).then(agent => { 
                    this.agentOwner = agent;                    
                    // if (this.contactId) // when contacts are loaded, then try to find the selected contact in there
                    //     this.selectContact(this.contactId);
                 });
            } else {
                this.agentOwner = Object.assign({},inputAgt,{agentContacts : this.getFiltredContacts(inputAgt, this.registrationLimitedList, this.fidFasLimitedList)});
                // if(this.agentOwner) {
                //     if(this.contactId)
                //         this.selectContact(this.contactId)
                // }                
            }
        } else if (inputAgt == null){ // clear parent Agent then clear also selected contact person
            this.selectContact(null);
        } else { 
            this.agentOwner = new FullAgent();
        }
    }
    get agent(){
        return this.agentOwner;
    }

    ///////////////////////OPTIONAL : the agent or the agentId can be use as @input ////////////////////////////
    @Input() set agentId(agtId:string){ // the parent/owner agentId   
        if(agtId && !this.agent)
            this.loadOwnerAgent(agtId).then(agent => { 
                this.agentOwner = agent;
                //if (this.contactId) // when contacts are loaded, then try to find the selected contact in there
                    this.selectContact(this.contactId);
                     
            });
        else if (agtId == null){
            // clear parent Agent then clear also selected contact person
            this.selectContact(null);
            
        }
    }
    get agentId(){
        return (this.agentOwner ? this.agentOwner.agtId : null);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Input("contactId") set contactId(id:string){ // the selected contact agentId to bind
        this.selectedAgent = Object.assign({},this.emptyAgent);
        if (id){
            this.selectedAgent.agtId = id;
            
            this.selectContact(id); // try already to find the selected contact in the parent contacts list if loaded !
        } else {
            
            this.selectContact(null);
        }
    }; 
    get contactId(){
        return this.selectedAgent ? this.selectedAgent.agtId : null;
    }
    
    @Input() registrationLimitedList: boolean = false;
    @Input() fidFasLimitedList: boolean = false;
    @Input() set showAdd(value: boolean) {
      this.showAddContact = value;
    }

    @Output() contactIdChange = new EventEmitter<string>();
    @Output() onAdd = new EventEmitter<AgentContact>();

    constructor(private agentService:AgentService, protected uoptDetailService: UoptDetailService) { 
     }

    ngOnChanges(changes: SimpleChanges): void {
       
        if(changes['contactId'] && this.contactId) {
            this.selectContact(this.contactId);
        }

        if(changes['agent'] && this.agentOwner) {
            this.setExtraFields(this.agentOwner.agentContacts);
        }
    }

    ngOnInit() { 
        this.funcSubscribe = this.uoptDetailService.getTypeOfAgentContact().subscribe(contactFunctions => this.contactFunctions = contactFunctions);
    }

    setTitle(name:string){
        if (name)
            this.title = this.initTitle + ' of '+name;
    };

    loadOwnerAgent(agtId): Promise<any>{
        return this.agentService.getAgent(agtId).then((agentOwner:FullAgent) => {
            if(agentOwner) {
                this.setTitle(agentOwner.name);
                agentOwner.agentContacts = this.getFiltredContacts(agentOwner, this.registrationLimitedList, this.fidFasLimitedList);
                this.setExtraFields(agentOwner.agentContacts);
            }       
            return agentOwner;
        });

        
    }

    setExtraFields(agContacts: AgentContact[]): void {
        if(agContacts){
            agContacts.forEach(agc => this.setValueOfExtraFields(agc));
        }
    }

    private setValueOfExtraFields(agContact: AgentContact): void {
        if(agContact){
            agContact.contactEnabled = (+agContact.status == 1);
            agContact.contactDisplayName = '';

            if(agContact.contact) {
                agContact.contactDisplayName = (agContact.contact.name + ' ' || '') + (agContact.contact.firstname + ' ' || '');
            }

            let functionlDesc = this.toContactFunctionDesc(agContact.contactFunction);

            if((+agContact.status == 1)) {
                if(functionlDesc){
                    agContact.contactDisplayName += '(' + functionlDesc + ')';
                }
            } else {
                if(functionlDesc) {
                    agContact.contactDisplayName += '(' + functionlDesc + ')';
                } 
                agContact.contactDisplayName += ' &nbsp;&nbsp;&#9888;&nbsp;Inactive';
            }
        }
    }

    selectContact(agentId){
        if (!agentId){
           
            this.isActive=true;
            
            if (!!this.contactId){
                this.contactIdChange.emit(null);
            }
           
            this.selectedAgent = Object.assign({},this.emptyAgent);
            
            if(this.agentOwner && this.agentOwner.agentContacts) {
           
                let start = this.agentOwner.agentContacts.findIndex(ct => !ct.contactEnabled);
                if(start > -1) {
                    this.agentOwner.agentContacts.splice(start, 1);
                }

               this.agentOwner.agentContacts = Object.assign([], this.agentOwner.agentContacts);
      
               this.setExtraFields(this.agentOwner.agentContacts);
            }
            
        } else if (this.agentOwner && this.agentOwner.agentContacts){
            
            this.setExtraFields(this.agentOwner.agentContacts);
            let source = this.agentOwner.agentContacts.find(a=> a && a.contact && a.contact.agtId==agentId );
            
            if (source) {
                
                this.selectedAgent = source.contact;
                this.isActive =  (+source.status == 1 && source.contact.status == 1);
                this.contactIdChange.emit(this.selectedAgent.agtId);
            } else { 
                           
                this.agentService.getAgent(agentId).then(inactiveContact => {  
                    this.isActive = false;                    
                    let inactiveAgContact = new AgentContact(this.agentId);
                    inactiveAgContact.status = AgentContactStatus.DISABLED;
                    inactiveAgContact.contact = inactiveContact;
                    this.setValueOfExtraFields(inactiveAgContact);
                    this.agentOwner.agentContacts = this.agentOwner.agentContacts.concat(inactiveAgContact);                   
                    this.selectedAgent = inactiveContact;
                    this.contactIdChange.emit(this.selectedAgent.agtId);                    
                }).catch(e => { this.isActive = false; throw e; });
            }
        }
    }
    
    addContact(agtContact: AgentContact){   
        if(agtContact && agtContact.contact){
            this.loadOwnerAgent(this.agentId).then(activeAgent => {
                this.selectContact(agtContact.contact.agtId);
                this.onAdd.emit(agtContact)
            });
        } else {
            this.onAdd.emit(agtContact);
        }
    }

    getFiltredContacts(agent:FullAgent, registrationLimitedList: boolean, limitedListFIDFAS: boolean){
        return (agent.agentContacts ? agent.agentContacts.filter(a=> this.filterCondition(a, registrationLimitedList, limitedListFIDFAS)) :[]);
    }

    filterCondition(agentContact: AgentContact, registrationLimitedList: boolean, limitedListFIDFAS: boolean) {
        let limitedcontactFunctions: string[] = [];
        if(registrationLimitedList){
            limitedcontactFunctions = ["OTHERF", "CPS"];
        }
        if(limitedListFIDFAS){
            limitedcontactFunctions = ["OTHERF"];
        }
        return (registrationLimitedList || limitedListFIDFAS) ? +agentContact.status == 1 && limitedcontactFunctions.includes(agentContact.contactFunction) : +agentContact.status == 1;
    }

    toContactFunctionDesc(contactFunctionKey: string){
       return this.agentService.toContactFunctionDescByKey(contactFunctionKey, this.contactFunctions);
    }

    toContactFunctionDescByContactId(contactId: string) {
        return (this.agentOwner) ? this.agentService.toContactFunctionDescByContactId(contactId, this.agentOwner.agentContacts, this.contactFunctions): '';
    }

    ngOnDestroy(): void { 
        if(this.funcSubscribe)
            this.funcSubscribe.unsubscribe();
     }
}