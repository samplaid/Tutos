import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { Subscription } from "rxjs/Subscription";
import { UoptDetailService } from "../../../_services/uopt-detail.service";
import { StateMode } from "../../../utils";
import { AgentLite } from '../../../_models/index';

@Component({
	selector: 'agent-identity-form',
	templateUrl: 'agent-identity-form.component.html'
})

/**
 * This component represents an agent identity. It contains the name, first name and other iformation that can be added.
 * @Input agent: AgentLite
 */
export class AgentIdentityFormComponent implements OnInit, OnDestroy {	
	////// Variables declaration ////////
	disabledMode = [ StateMode.readonly ];
	subs: Array<Subscription> = [];
	stateMode = StateMode;
	
	////// Input declaration ////////
	@Input() titles: any[]=[];
	@Input() agent: AgentLite;
	@Input() mode = StateMode.readonly;

	////// Constructor declaration ////////
	constructor(protected uoptDetailService: UoptDetailService){}

	////// Angular declaration ////////
	ngOnInit() { 		
		if(this.titles.length === 0){
			this.subs.push(this.uoptDetailService.getAgentTitle().subscribe(titles => this.titles = titles));
		}		
	}
	ngOnDestroy(): void {
		if(this.subs){
			this.subs.forEach(sub => sub.unsubscribe());
		}
	}

}