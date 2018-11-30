import { Component, OnInit } from '@angular/core';
import { StateMode } from '../../../utils/mode';
import { UserService } from '../../../utils/index';
import { Roles } from '../../../_models/roles.enum';
import { Input, Output, EventEmitter } from '@angular/core';
import { FullClient } from '../../../_models';

@Component({
    selector: 'client-insider-trading',
    templateUrl: './client-insider-trading.tpl.html'
})
export class ClientInsiderTradingComponent implements OnInit {
    stateMode = StateMode;
    mode = StateMode.readonly;

    @Output() fcChange: EventEmitter<FullClient> = new EventEmitter<FullClient>();
    @Input() fc : FullClient;

    constructor(private userService: UserService){}

    ngOnInit(): void {
        this.setMode();
    }

    setMode(){
        if (this.userService.hasOneRole([Roles.CLIENT_EDIT, Roles.CLIENT_COMPLIANCE_EDIT])){
            this.mode = (this.fc.cliId) ? StateMode.update : StateMode.create;
       } else {
            this.mode = StateMode.readonly;
       }
    }

    clearInsiderTradingDetails(event) {
        if (this.fc.insiderTrading === 'NO') {
            this.fc.insiderTradingDetails = undefined;
        }
    }

}
