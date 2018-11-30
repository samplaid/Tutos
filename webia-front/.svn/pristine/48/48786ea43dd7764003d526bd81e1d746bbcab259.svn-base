import { StateMode } from './../../../utils/mode';
import { Input, Component, OnInit, Output, EventEmitter } from '@angular/core';
import { PartnerForm, AgentLite } from '../../../_models';
import { AgentService } from '../../../agent';
import { SelectedAgentHolder } from '../../../agent/selected-agent-holder';
import { outputFile } from 'fs-extra';

@Component({
    selector: 'policy-broker-input',
    templateUrl: 'policy-broker-input.tpl.html'
})
export class PolicyBrokerInputComponent implements OnInit {

    constructor(private agentService: AgentService,) {}

    selectedAgent: SelectedAgentHolder = {};
    @Input() mode: string;
    @Input() broker: PartnerForm;
    @Input() brokerRefContract: string;
    @Input() brokerContact:PartnerForm;
    @Input() subBroker:PartnerForm;
    @Input() canShowMandateTransmission: boolean = true;
    @Input() canShowContactPerson: boolean = true;
    @Input() canShowSubBroker: boolean = true;
    @Input() canShowContactRef: boolean = true;
    readonly stateMode = StateMode;
    @Output() brokerChange = new EventEmitter<PartnerForm>();
    @Output() brokerRefContractChange = new EventEmitter<string>();
    @Output() brokerContactChange = new EventEmitter<PartnerForm>();
    @Output() subBrokerChange = new EventEmitter<PartnerForm>();    

    subBrokerList: any[];

    name_status(agent: AgentLite){
        return (+agent.status != 1 ? agent.name+" <div class='text-danger inline-block text-sm'>&nbsp;&nbsp;&#9888;&nbsp;Inactive</div>" :agent.name);
    }

    updateSubBrokerList(): void {
        if (this.broker && this.broker.partnerId ) {
            this.agentService.getSubBroker(this.broker.partnerId).then(list => {
                if (list) {
                    this.subBrokerList = list.sort((a,b) => (b.name+'' <= a.name + '') ? 1 : -1 ); 
                    if (this.subBroker.partnerId){
                        //if selected subBroker is not active anymore then add it directly in the list flaged as Inactive
                        if (this.subBrokerList.findIndex(a => a.agtId == this.subBroker.partnerId ) < 0){
                            this.agentService.getAgentLite(this.subBroker.partnerId).subscribe(agent => {
                                agent.name += "&nbsp;&nbsp;&#9888;&nbsp;Inactive"; 
                                this.subBrokerList.push(agent);
                            })
                        }
                    }
                }
            });
        }
    }

    onBrokerRefContractChange(event: string): void {
        this.brokerRefContract = event;
        this.brokerRefContractChange.emit(event);
    }

    onBrokerContactChange(event: PartnerForm): void {
        this.brokerContact = event;
        this.brokerContactChange.emit(event);
    }

    onSubBrokerChange(event: PartnerForm): void {                
        //let subBrokerAgent = this.subBrokerList.find(sb=> sb.agtId==this.a.subBroker.partnerId);
        // do not set the subbroker from the event, it is performed with ngModel of the component
        this.subBrokerChange.emit(this.subBroker);
    }

    onAgentIdChange(event: string): void {
        this.broker.partnerId = event;
        this.updateSubBrokerList();        
    }
    
    onBrokerChange(event: AgentLite): void {
        //the shitty search-agent component triggers two kind of outputs, this broker change output emit should be here!
        this.selectedAgent.broker = event;
        this.brokerChange.emit(this.broker);
    }

    ngOnInit(): void { }
}