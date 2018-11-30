import { EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { StateMode } from './../../../utils/mode';
import { Input, Component, OnInit, Output } from '@angular/core';
import { agentMailSendingRuleCodes, UoptDetails, AgentLite } from '../../../_models';

@Component({
    selector: 'policy-sending-rules-input',
    templateUrl: 'policy-sending-rules-input.tpl.html'
})
export class PolicySendingRulesInputComponent implements OnInit, OnChanges {

    constructor() {}

    @Input() mode: string;
    @Input() sendingRules: string;
    @Input() mailToAgent: string;
    @Input() sendingRuleList: UoptDetails[] = [];
    @Input() mail2AgentList: AgentLite[] = [];

    @Output() sendingRulesChange = new EventEmitter<string>();
    @Output() mailToAgentChange = new EventEmitter<string>();

    readonly stateMode = StateMode;
    showMail2Agent: boolean;

    ngOnInit(): void { }

    ngOnChanges(changes: SimpleChanges) {
        if(changes['sendingRules']) {
            this.toggleMail2Agent();   
        }
    }

    onSendingRulesChange(event: string): void {
        this.sendingRules = event;
        this.sendingRulesChange.emit(event);        
    }

    onMailToAgentChange(event: string): void {
        this.mailToAgent = event;
        this.mailToAgentChange.emit(event);
    }

    toggleMail2Agent(): void {
        this.showMail2Agent = (agentMailSendingRuleCodes.indexOf(this.sendingRules) > -1);
        if(!this.showMail2Agent) {
            this.onMailToAgentChange(null);
        }
    }
}