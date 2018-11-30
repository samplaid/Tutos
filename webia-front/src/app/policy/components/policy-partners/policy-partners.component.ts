import { Component, OnInit, Input } from '@angular/core';
import { PolicyAgentShare } from '../../../_models/policy/policy-agent-share';
import { AgentLite } from '../../../_models/agent/agent-lite';
import { popupAgents, AgentService } from "../../../agent/agent.service";

@Component({
    selector: 'policy-partners',
    templateUrl: 'policy-partners.tpl.html'
})

export class PolicyPartnersComponent implements OnInit {

    @Input() displayBrokers: boolean = true;
    @Input() displayCountryManagers: boolean = true;
    @Input() displayBusinessIntroducer: boolean = true;
    @Input() broker: PolicyAgentShare;
    @Input() brokerContact: PolicyAgentShare;
    @Input() subBroker: PolicyAgentShare;
    @Input() brokerRefContract: string;
    @Input() countryManagers: PolicyAgentShare[];
    @Input() businessIntroducer: PolicyAgentShare;
    @Input() contactFunctions: any[] = [];

    constructor(private agentService:AgentService) {}

    /**
     * Define if the provided agent can ben open in a popup or not.
     * 
     * @param agent the agent to inspect
     */
    canOpenInPopup(agent: AgentLite): boolean {
        return popupAgents.findIndex((cat)=> cat && agent.category && cat.trim() == agent.category.trim()) > -1;
    }

    /**
     * Retrieve the function description corresponding to the contact id provided.
     * 
     * @param contactId the contact id
     */
    toContactFunctionDescByContactId(contactId: string) {
        return this.agentService.toContactFunctionDescByContactId(contactId, this.broker.agent.agentContacts, this.contactFunctions);        
    }

    ngOnInit(): void {}
}