import { Component, OnInit, Input, OnDestroy, Output, EventEmitter } from '@angular/core';
import { AgentStatusType, FullAgent, Roles } from "../../../_models/index";
import { Subscription } from "rxjs/Subscription";
import { CountryService } from "../../../_services/country.service";
import { UoptDetailService } from "../../../_services/uopt-detail.service";
import { ActivatedRoute } from "@angular/router";
import { AgentService } from "../../agent.service";
import { MessageService, StateMode, UserService } from "../../../utils/index";
import { Title } from "@angular/platform-browser";
import { Observable } from "rxjs/Observable";

@Component({
	selector: 'agent-basic-form',
	templateUrl: './agent-basic-form.component.html'
})

/**
 * Represent a basic form for all agent. This component contains 
 * mainly the agent address, its status and its code.
 * @Input: agent: FullAgent, countries: any[].
 */
export class AgentBasicFormComponent implements OnInit, OnDestroy {
	
	////// Variables declaration ////////	
	agent: FullAgent;
	subs: Array<Subscription>;
	busy: Subscription;
	agentCategories:any[] = [];
	disabledMode = ['readOnly'];
	stateMode = StateMode;
	
	////// Input declaration ////////	
	@Input() category: string;
	@Input() agentId: string;
	@Input() inModal: boolean;
	@Input() mode = StateMode.readonly;
	@Input() set title(value: string) { 
		this.titleService.setTitle(value);
		this.titleChange.emit(value);
	};
	get title(): string{ return this.titleService.getTitle();}
	@Output() agentChange: EventEmitter<FullAgent> = new  EventEmitter<FullAgent>();
	@Output() titleChange: EventEmitter<string> = new  EventEmitter<string>();

	////// Constructor declaration ////////
	constructor(protected route: ActivatedRoute,
			protected agentService: AgentService,
			protected uoptDetailService: UoptDetailService,
			protected messageService: MessageService,
			protected titleService: Title,
			protected userService: UserService){ }
	
	////// Angular methods ////////
	ngOnInit() {	
		this.subs = new Array<Subscription>();			
		if(this.inModal){
			this.loadOrInitializeAgent(this.agentId);
		} else {
			this.route.params.subscribe(urlParam => {
				this.agentId = (urlParam.id) ? decodeURIComponent(urlParam.id): undefined;	
				this.loadOrInitializeAgent(this.agentId);
			});			
		}	
	}

	ngOnDestroy(): void {
		this.subs.forEach(sub => sub.unsubscribe());
	}

	////// Our methods declaration ////////
	
	/**
	 * If the agent id is not defined, the method will initialize a new active introducer agent.
	 * @param agentId the agent id
	 */
	loadOrInitializeAgent(agentId: string): void{
		if(agentId){
			this.agentService.getAgent(agentId).then((agent: FullAgent) => {
				if(agent){										
					this.agent = agent;
					if(this.mode != StateMode.readonly){
						this.title = 'Update ' + this.title;					
					}					
					this.agentChange.emit(this.agent);
				} else {
					this.agent = this.initializeAgent();
				}				
			});	
		} else {
			this.agent = this.initializeAgent();
		}
		
	}
	/**
	 * Create a new active agent with category introducer.
	 */
	initializeAgent(): FullAgent{		
		this.title = 'Create ' + this.title;		
		let newAgent = new FullAgent();
		newAgent.status = undefined;
		newAgent.category = this.category;		
		this.agentChange.emit(newAgent);
		return newAgent;
	}
	/**
	 * Send the form data to the service that will be perfom the update, creation.
	 */
	save(): Promise<FullAgent>{
		this.agent.status = AgentStatusType.ENABLED;
		return this.agentService.saveAgent(this.agent).then((newAgent: FullAgent)=> {
			if(newAgent){
				this.agent = newAgent;
				this.agentChange.emit(this.agent);
			}		
			return newAgent;
		});
	}
	/**
	 * REfresh the form by loading the data from the server.
	 */
	refresh(){
		this.busy = Observable.fromPromise(this.agentService.getAgent(this.agentId)).subscribe((agent: FullAgent) => this.agent = (agent) ? agent : this.initializeAgent());
	}
}