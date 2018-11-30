import { Component, OnInit, Input, OnDestroy, ViewChild, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UoptDetailService, CountryService } from '../../_services';
import { MessageService } from '../../utils';
import { AgentService } from '../agent.service';
import { AgentContact, FullAgent, AgentContactStatus } from "../../_models/index";
import { Observable } from "rxjs/Observable";
import { AgentAddressFormComponent } from "../components/address/agent-address-form.component";

@Component({
    selector: 'agent-contact',
    templateUrl: './agent-contact.component.html'
})
export class AgentContactComponent implements OnInit, OnDestroy {    
    localAgent: FullAgent;
    title: string = 'Contact creation';
    ptCode: string;
    busy: any;
    ac: AgentContact;
    subs: any[];
    titles: any[];
    languages: any[];
    countries: any[];
    typeOfAgentContacts: any[];
    contactId: number;
    disabledMode = ['readOnly']; //['edit','readOnly'];
    isFormValid: boolean = true;
   

    @ViewChild('agaForm') agentAddressForm: AgentAddressFormComponent;
    @Input() mode: 'readOnly' | 'waiting' | 'edit' | 'create';
    @Input() defaultFunction = 'OTHERF';
    @Input() inModal: boolean;
    @Input() set contact(value: AgentContact){
        if (value) {
            this.ac = value;
        }
    } 

    @Input() set agent(value: FullAgent) {// parent agent to refer
        this.localAgent =  value;
    };  

    @Output() agentChange: EventEmitter<FullAgent> = new EventEmitter<FullAgent>();
    @Output() contactChange: EventEmitter<AgentContact> = new EventEmitter<AgentContact>();

    get agent(){
        return this.localAgent;
    }
   
    constructor(protected route: ActivatedRoute,
        protected agentService: AgentService,
        protected router: Router,
        protected countryService: CountryService,
        protected uoptDetailService: UoptDetailService,
        protected messageService: MessageService) { }

    ngOnInit() {
        this.subs = (this.subs) ? this.subs : new Array<any>();
        this.subs.push(this.uoptDetailService.getTitles().subscribe(titles => this.titles = titles));
        this.subs.push(this.uoptDetailService.getLanguages().subscribe(languages => this.languages = languages));
        this.subs.push(this.countryService.loadAllCountries().subscribe(countries => this.countries = countries));
        this.subs.push(this.uoptDetailService.getTypeOfAgentContact().subscribe(contact => this.typeOfAgentContacts = contact));

        if (!this.inModal) {
            this.route.params.subscribe(urlParam => {
                this.contactId = (urlParam) ? +urlParam['id'] : this.contactId;
            });
        }

        // init new contact
        if (!this.ac || !this.ac.agcId){
            this.ac = new AgentContact(this.agent.agtId);
            this.initContact();
        }
    }

    initContact(){
        //this will iniialize the contact address with its parent address (usualy the same)
        
        if (this.ac.contact && !this.ac.contact.agtId && this.agent && !this.ac.agcId){
            this.ac.contact.addressLine1 = this.agent.addressLine1;
            this.ac.contact.addressLine2 = this.agent.addressLine2;
            this.ac.contact.addressLine3 = this.agent.addressLine3;
            this.ac.contact.addressLine4 = this.agent.addressLine4;
            this.ac.contact.town = this.agent.town;
            this.ac.contact.country = this.agent.country;
            this.ac.contact.postcode = this.agent.postcode;
            this.ac.contact.documentationLanguage = this.agent.documentationLanguage;            
            if(!this.ac.contactFunction){
                this.ac.contactFunction = !!this.defaultFunction ? this.defaultFunction : 'OTHERF'; // 48: 12-Sep 17: Bug Mettre Other function par défaut
            }
        }     
    }

    statusChange(flag) {
        if (this.ac)
            this.ac.status = flag ? '1' : '2';
    }
  
    save(){      
        //check if there is already an comissio  linked to the agent                
        if(this.validBeforeSave()){
            return this.agentService.saveContactAndUpdateAgent(this.agent, this.ac).then( result => {
                this.agent = result.agent;
                this.ac = result.agentContact; 
                this.messageService.addAlertSuccess('The agent contact with id ' + result.agentContact.contact.agtId + ' was successfully created.', true,'agentCpt');  
                this.agentChange.emit(this.agent);
                return this.ac;
            }, e => Promise.reject(e));
        } else {            
            return Promise.reject('FormNotValidException');
        }
    }

    validBeforeSave(){   
        this.isFormValid = true;   
        if(!this.agent){
            this.isFormValid = false;        
            this.messageService.addAlertError('No master broker is not defined.', true,'agentCpt'); 
            return this.isFormValid;
        }
        if(this.agent && !this.agent.agtId){
            this.isFormValid = false;        
            this.messageService.addAlertError('The master broker has no identifier.', true,'agentCpt'); 
            return this.isFormValid;
        }
        if(this.ac && this.ac.contact && !this.ac.contact.name){   
            this.isFormValid = false;  
            this.messageService.addAlertError('The contact name is mandatory.', true,'agentCpt'); 
            return this.isFormValid;
        }
        if(this.agent.agentContacts && this.agent.agentContacts.findIndex(ac => ac && !ac.contactFunction) > 0){
            this.isFormValid = false;  
            this.messageService.addAlertError('The master broker with id = ' + this.agent.agtId +' contains a contact with an empty function.', true,'agentCpt'); 
            return this.isFormValid;
        }
        if(this.ac.contactFunction == 'COMMISSI' && this.hasMoreActiveCommission()){     
            this.isFormValid = false;        
            this.messageService.addAlertError('The master broker with id = ' + this.agent.agtId +' must not contain more than one active contact with \'Commission\' function.', true,'agentCpt'); 
            return this.isFormValid;
        }
        if(!this.agentAddressForm.isFormValid){     
            this.isFormValid = false;        
            this.messageService.addAlertError('The email is not valid.', true,'agentCpt'); 
            return this.isFormValid;
        }        
        return this.isFormValid;
    }

    hasMoreActiveCommission(){
        let array = this.agent.agentContacts.filter((agc: AgentContact) => agc.contactFunction == 'COMMISSI' && AgentContactStatus.ENABLED == agc.status);        
        return (this.ac  && array && array.length > 1);
    }

    isEmptyContact(ac: AgentContact) {
        return (ac) ? (
            !ac.agcId &&
            (!ac.contact || (
                !ac.contact.addressLine1 &&
                !ac.contact.addressLine2 &&
                !ac.contact.addressLine3 &&
                !ac.contact.addressLine4 &&
                !ac.contact.name &&
                !ac.contact.firstname &&
                !ac.contact.town &&
                !ac.contact.country &&
                !ac.contact.postcode &&
                !ac.contact.telephone &&
                !ac.contact.email &&
                !ac.contact.mobile &&
                !ac.contact.documentationLanguage &&
                !ac.contact.title)
            ) 
        ) : true;
    }

    ngOnDestroy(): void {
       if(this.subs){
           this.subs.forEach(sub => sub && !sub.closed && sub.unsubscribe());
       }
    }

}