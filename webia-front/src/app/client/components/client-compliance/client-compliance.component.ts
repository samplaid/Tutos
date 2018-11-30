import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FullClient, Roles } from "../../../_models";
import { UoptDetailService } from "../../../_services";
import { Subscription } from "rxjs/Subscription";
import { UserService, StateMode } from "../../../utils";

@Component({
    selector: 'client-compliance',
    templateUrl: './client-compliance.tpl.html'
})
export class ClientComplianceComponent implements OnInit {

    stateMode = StateMode;
    mode = StateMode.readonly;
    complianceRisks: any[];
    subs: Array<Subscription> = [];

    @Output() fcChange: EventEmitter<FullClient> = new EventEmitter<FullClient>();
    @Output() allowCanSave: EventEmitter<boolean> = new EventEmitter<boolean>();
    @Input() fc : FullClient;

    constructor( private uoptDetailService:UoptDetailService, private userService:UserService) { 
        this.getClientComplianceRisks();
    }


    ngOnInit() { 
        this.setMode();
    }

    setMode(){
        if (this.userService.hasRole(Roles.CLIENT_COMPLIANCE_EDIT)){
           if (this.fc.cliId){
                this.mode = StateMode.update;
           }else{
                this.mode = StateMode.create;
           }
           this.allowCanSave.emit(true);
       } else {
           this.mode = StateMode.readonly;
       }
    }

    getClientComplianceRisks(){
            // compliance risk
        this.subs.push(this.uoptDetailService.getClientComplianceRisks().subscribe(t => {            
            this.complianceRisks = [];
            if(t){
                (<any[]>t).forEach((uoptd, i) => {
                    if(uoptd.keyValue == 'LOW'){
                        this.complianceRisks[0] = uoptd;
                    } else if(uoptd.keyValue == 'MEDIUM'){
                        this.complianceRisks[1] = uoptd;
                    } else if(uoptd.keyValue == 'HIGH'){
                        this.complianceRisks[2] = uoptd;
                    }
                });
            }
            
        }));
    }

    ngOnDestroy() {
        this.subs.forEach((sub: Subscription) => sub && !sub.closed && sub.unsubscribe());
    }

}