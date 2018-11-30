import { Component, OnInit, Input, AfterViewInit } from '@angular/core';
import { Observable } from "rxjs/Observable";
import { Subscription } from "rxjs/Subscription";
import { CountryService } from "../../../_services/country.service";
import { UoptDetailService } from "../../../_services/uopt-detail.service";
import { emailRegExp } from "../../../_models/constant";
import { StateMode } from "../../../utils";
import { AgentLite } from '../../../_models/index';

@Component({
	selector: 'agent-address-form',
	templateUrl: 'agent-address-form.component.html',
	styleUrls: ['./agent-address-form.component.css']
})

/**
 * This component represent the agent addresss.
 */
export class AgentAddressFormComponent implements OnInit {	
	////// Variables declaration ////////
	dataStore:any = {};
	internalAgent: AgentLite;
	ptCode: string;
	disabledMode = [ StateMode.readonly ];
	isFormValid: boolean = true;
	stateMode = StateMode;

	////// Input declaration ////////
	@Input() set agent(value: AgentLite){
		this.internalAgent = value;
		if(this.internalAgent){
			this.setPostalCodePrefix(this.internalAgent.country);
		}		
	}
	get agent(){
		return this.internalAgent;
	}
	@Input() countries: any[] = [];
	@Input() languages: any[] = [];
	@Input() mode = StateMode.readonly;

	////// Constructor declaration ////////
	constructor(protected countryService: CountryService,
				protected uoptDetailService: UoptDetailService){}

	////// Angular declaration ////////
	ngOnInit() {	
		this.getCountries().then(countries => this.dataStore.countries = countries);
		this.getLanguages().then(languages => this.dataStore.languages = languages);
	}	
	ngOnDestroy(): void {}


	////// Our declaration ////////
	getCountries(): Promise<any[]>{
		if(this.countries && this.countries.length > 0){
			return Promise.resolve(this.countries);			
		} 		
		if(!this.dataStore.countries){
			return this.countryService.loadAllCountries().toPromise();
		}
		return Promise.resolve(this.dataStore.countries);
	}
	getLanguages(){
		if(this.languages && this.languages.length > 0){
			return Promise.resolve(this.languages);			
		} 		
		if(!this.dataStore.languages){
			return this.uoptDetailService.getLanguages().toPromise();
		}
		return Promise.resolve(this.dataStore.languages);
	}
	
	checkEmail(event){
		if(this.agent && this.agent.email){
			if(this.agent.email.startsWith(';') || this.agent.email.startsWith(',')) {
				this.isFormValid = false;
			} else {
				const emails: string [] = this.agent.email.split(/[;,]/);
				let filteredEmail = emails.filter(email => !!email);
				this.isFormValid = filteredEmail.every((email: string) => (emailRegExp.test(email)));
				if(this.isFormValid){
					let rex = this.agent.email.match(/[;,]$/)
					if(rex){
						this.agent.email = rex.input.substring(0, rex.index);
					}
				}
			}			
		} else if(this.agent && !this.agent.email){
			this.isFormValid = true; // not mandatory
		}
	}

	/**
	 * This method will set the postal code of a country identified by the argument.
	 * A list of a countrie should be provided in input befaore call this method. 
	 * Otherwise, no action will happen.
	 * @param countryId the id of the country
	 */
	setPostalCodePrefix(countryId: string){
		this.getCountries().then(countries => {
			let country = countries.find(country => country && country.ctyId == countryId);
        	this.ptCode = (country) ? country.ptCode : undefined;
		});
	}
}