import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { FullAgent, AgentCategory } from "../../../_models/index";
import { AgentService } from "../../agent.service";

import { Store } from '../../../utils/index';
import { StoreName } from '../../../utils/services/store-name';


@Component({
	selector: 'agt-label',
	template: `
		<ng-container *ngFor="let category of categories">
			<span class="label label-primary w-label text-nowrap" *ngIf="category[categoryCode] "> {{ category[categoryCode] }} </span>			
		</ng-container>
	`,
	styles:[``]
})

export class AgentLabelComponent implements OnInit, OnDestroy {	
	categories: any[];

	@Input() categoryCode: string;
	
	constructor(private agentService: AgentService, private store: Store){ 
		store.select(StoreName.AGENT_CAT_LIST).subscribe(payload => this.categories = (<Array<any>>payload).map( cat => JSON.parse('{"' + cat.acaId + '":"'+ cat.name + '"}')) );
	}

	ngOnInit() { 
	}

	ngOnDestroy() {			
	}
}