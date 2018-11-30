import { Component, OnInit, Input, ViewChild, OnDestroy, EventEmitter, Output, AfterViewInit } from '@angular/core';
import { AgentContactComponent } from "../agent-contact.component";
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { Subscription } from "rxjs/Subscription";
import { AgentContact, FullAgent, Roles } from "../../../_models/index";
import { Observable } from "rxjs/Observable";
import { StateMode, UserService } from "../../../utils";
import { log } from 'util';

const closed: string = 'closed';
const canceled: string = 'canceled';

@Component({
    selector: 'agc-modal',
    template: `
        <ng-template #agentContact>
            <div>
                <div class="modal-header">                    
                    <button type="button" class="close pull-right" aria-label="Close" (click)="modalRef.hide()"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title inline-block">{{ title }}</h4>
                    <label class="header-form-status">
                        <span class="label label-primary w-label" *ngIf="contact && contact.contact && contact.contact.status && contact.contact.status == 1">Active</span>                
                        <span class="label label-primary w-label" *ngIf="contact && contact.contact && contact.contact.status && contact.contact.status !=1 ">Inactive</span>
                    </label>
                    <label class="header-form-code" *ngIf="contact && contact.contact && contact.contact.agtId"><b>Code</b><span class="paddingHorizontal2">{{ contact?.contact?.agtId}}</span></label> 
                </div>
                <div class="modal-body">
                    <agent-contact #agc [defaultFunction]="initialFunction" [inModal]="true" [agent]="agent" [contact]="contact" (agentChange)="agentChanged($event)" [mode]="mode"></agent-contact>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-primary" [disabled]="!agc.ac.contactFunction || !agc.ac.contact.name || saving" [desactive]="mode" (click)="save(agc)"><i class="fa fa-floppy-o"></i>Save</button>                    
                    <button type="button" class="btn btn-xs btn-default" (click)="modalRef.hide()"><i class="fa fa-times"></i>Close</button>
                </div>
            </div>
        </ng-template>
    
        <ng-container *ngIf="!disabled">
            <a  [ngClass]="{'btn btn-sm btn-primary': asLink!=true, 'disabled': disabled==true, 'clickable': disabled!=true}" 
                [attr.title]="((mode == stateMode.edit)?'Update the contact function':'Create a new contact') +' '" 
                (click)="open(agentContact)">
                <ng-content></ng-content>
            </a>
        </ng-container>
        
    `,

    styles:[`
        .inline-block{
            display: inline-block;
        }        
    `]
})
export class AgentContactModal implements OnInit, OnDestroy  {  

    modalRef: BsModalRef;
    mode = StateMode.edit;
    stateMode = StateMode;
    saving: boolean;
    agContact: AgentContact;
    
    @Output() close = new EventEmitter<AgentContact>();
    @Output() agentChange = new EventEmitter<FullAgent>();
    
    @ViewChild(AgentContactComponent) contactComponent: AgentContactComponent;

    @Input() title: string = '';
    @Input() initialFunction: string = 'OTHERF';    
    @Input() isNews: boolean;
    @Input() agent: FullAgent;  // parent agent to refer
    @Input() disabled: boolean;
    @Input() asLink: boolean = false;
    
    @Input() set contact(agContact: AgentContact) {
        this.agContact = Object.assign({}, agContact);
    }

    get contact() {
        return this.agContact;
    }
    
    constructor(public modalService: BsModalService, protected userService: UserService,) { }

    ngOnInit() { }
 
    open(templateRef){
        if (!this.disabled){
            if(this.isNews) {
                this.agContact = undefined;
            }
            this.modalRef = this.modalService.show(templateRef,{class:'modal-sm'}); 
        }
    }

    save(agc:AgentContactComponent){        
        this.saving = true;
        agc.save().then((agentContact: AgentContact) => {
            this.saving = false;
            this.resolve(agentContact);
            this.modalRef.hide();
        }, e => { 
            this.saving = false;
            this.reject(e);
         });
    }

    agentChanged(agent) {
        this.agent = agent;
    }

    resolve(param: any) {
        // only emit data during the click of the save button.
        if(param !== closed) {
            this.close.emit(param);
            this.agentChange.emit(this.agent);
        } 
    }
    
    reject(reason){ }
   
    ngOnDestroy() {  }

}