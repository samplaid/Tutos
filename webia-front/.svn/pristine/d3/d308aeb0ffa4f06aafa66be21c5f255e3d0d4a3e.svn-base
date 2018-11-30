import { FullClient, Roles, ClientType, ClientLite, ClientClaimsDetail } from './../../_models';
import { PersonPhysicalComponent } from './../form/person-physical/person-physical.component';
import { PersonMoralComponent } from './../form/person-moral/person-moral.component';
import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { StateMode, UserService, MessageService } from "../../utils";
import { UoptDetailService } from "../../_services/index";
import { ClientService } from "../client.service";
import { Subject } from "rxjs/Subject";

@Component({
    selector: 'modal-client-content',
    template: `
    <div>
        <div class="modal-header">
            <button type="button" class="close pull-right" aria-label="Close" (click)="activeModal.hide()"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Notify the death of {{client.displayName}}</h4>
        </div>
        <div class="modal-body">
            <ngbd-alert-container ref='clientDeathClaimModal'></ngbd-alert-container>
            <div class="row marginTop">
                <label class="col-xs-3 text-right marginTop">Date Death Notified</label>
                <div class="col-xs-3"><datepicker [(date)]="deathClaim.dateDeathNotified" [disabled]="mode == StateMode.readonly"></datepicker> </div>
                <label class="col-xs-2 text-right marginTop">Cause/Reason</label>
                <div class="col-xs-3">
                    <select class="form-control input-sm text-left" [(ngModel)]="deathClaim.causeOfDeath" id="deathReason" name="deathReason" [desactive]="mode">
                        <option *ngFor="let c of (deathCauseList | orderBy: ['description'])" [value]="c.keyValue">{{ c.description }}</option>
                    </select>                
                </div>
            </div>
            <div class="row marginTop">
                <label class="col-xs-3 text-right marginTop">Date Of Death</label>
                <div class="col-xs-3"><datepicker [(date)]="deathClaim.dateOfDeath" [disabled]="mode == StateMode.readonly"></datepicker> </div>
            </div>
            <div class="row marginTop">
                <label class="col-xs-3 text-right marginTop">Date Death Certificate Received</label>
                <div class="col-xs-3"><datepicker [(date)]="deathClaim.deathCertificate" [disabled]="mode == StateMode.readonly"></datepicker> </div>
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-xs btn-primary" (click)="notifyDeath()" [disabled]="!deathClaim?.dateDeathNotified || !deathClaim?.dateOfDeath"><i class="fa fa-floppy-o" [desactive]="mode"></i>Notify death</button> 
            <button type="button" class="btn btn-xs btn-default" (click)="activeModal.hide()"><i class="fa fa-times"></i>Cancel</button>
        </div>
    </div>
    `
})
export class ModalClientDeathNotify implements OnInit {
    
    @Input() client: FullClient;
    @Input() mode: string;

    deathClaim: ClientClaimsDetail; 
    
    StateMode = StateMode;
    deathCauseList : any[];
    today = new Date();

    public onClose: Subject<any>;
    
    constructor(public activeModal: BsModalRef,
                private clientService: ClientService,
                private userService: UserService, 
                private uoptDetailService:UoptDetailService, 
                private messageService:MessageService) { 
        this.getDeathCauseList();
    }

    ngOnInit() {
        this.onClose = new Subject();
        this.setMode();
        this.initDeathClaim(); 
    }

    initDeathClaim(){
        if (this.client && this.client.cliId)
            this.deathClaim = new ClientClaimsDetail(this.client.cliId);
    }

    getDeathCauseList(){
        this.uoptDetailService.getDeathCauses().subscribe(list => this.deathCauseList = list , e=>{});
    }

    setMode(){     
       if (this.userService.hasRole(Roles.CLIENT_EDIT)){
            this.mode = StateMode.create;
       } else {
           this.mode = StateMode.readonly;
       }
    }

    notifyDeath(){
          if (this.validForm()){
              this.clientService.notifyDeath(this.deathClaim).subscribe(ccd => this.close(ccd), e=>{});
          }
    }

    close(data){
        this.onClose.next(data);
        this.activeModal.hide();
    }

    /**
     *  Les dates Date Death Notified et Date of Death sont obligatoires.
     *  Elles ne peuvent être supérieures à la date du jour.
     *  La date de notification ne peut être strictement inférieure à la date de décès.
     */
    validForm(){
        
        if (!this.deathClaim.dateOfDeath || !this.deathClaim.dateDeathNotified){
            this.messageService.addAlertError( 'The "Date Death Notified" and The "Date Of Death" are required.' , true, 'clientDeathClaimModal');
            return false;  
        }

        if (this.deathClaim.dateOfDeath > this.today){
            this.messageService.addAlertError( 'The "Date Of Death" cannot be in the future.' , true, 'clientDeathClaimModal');
            return false;  
        }

        if (this.deathClaim.dateDeathNotified > this.today){
            this.messageService.addAlertError( 'The "Date Death Notified" cannot be in the future.' , true, 'clientDeathClaimModal');
            return false;  
        }

        if (this.deathClaim.dateOfDeath > this.deathClaim.dateDeathNotified){
            this.messageService.addAlertError( 'The "Date Of Death" be earlier than the "Date Death Notified".' , true, 'clientDeathClaimModal');
            return false;  
        }

        return true;
    }



}