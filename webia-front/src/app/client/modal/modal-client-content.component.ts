import { FullClient, Roles, ClientType } from './../../_models';
import { PersonPhysicalComponent } from './../form/person-physical/person-physical.component';
import { PersonMoralComponent } from './../form/person-moral/person-moral.component';
import { Component, OnInit, Input, ViewChild, SimpleChanges } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { StateMode, UserService } from "../../utils";
import { Subject } from "rxjs/Subject";

@Component({
    selector: 'modal-client-content',
    template: `
    <div>
        <div class="modal-header">
            <button type="button" class="close pull-right" aria-label="Close" (click)="activeModal.hide()"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">{{ clientTitle }}</h4>
        </div>
        <div class="modal-body">
            <person-physical *ngIf="isPhysicalClientType(clientType)" [(clientId)]="cliId" [showInModal]="false"></person-physical>
            <person-moral *ngIf="isMoralClientType(clientType)" [(clientId)]="cliId" [showInModal]="false"></person-moral>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-xs btn-primary" (click)="saveClient()"><i class="fa fa-floppy-o" [desactive]="mode"></i>Save</button> 
            <button type="button" class="btn btn-xs btn-default" (click)="activeModal.hide()"><i class="fa fa-times"></i>Cancel</button>
        </div>
    </div>
    `
})
export class ModalClientContent implements OnInit {    
    
    clientTitle: string;

    @Input() clientType : number;
    @Input() cliId : number;
    @ViewChild(PersonPhysicalComponent) physicalClient: PersonPhysicalComponent;
    @ViewChild(PersonMoralComponent) moralClient: PersonMoralComponent;
    
    mode = StateMode.readonly;
    StateMode = StateMode;

    public onClose: Subject<any>;

    constructor(public activeModal: BsModalRef, private userService:UserService) { }

    private getPersonTypeName(clientType: number): string {
        let personTypeName = '';
        
        if(this.clientType === ClientType.PHYSICAL) {
            personTypeName = 'Client';
        } else if(this.clientType === ClientType.MORAL){
            personTypeName = 'Entity';
        }

        return personTypeName;
    }

    ngOnInit() {
        this.onClose = new Subject();
        this.setMode(); 
        if(this.cliId) {
            this.clientTitle = 'Update' + this.getPersonTypeName(this.clientType) + ' ' + this.cliId;
        } else {
            this.clientTitle = 'Create ' + this.getPersonTypeName(this.clientType);
        }
    }

    setMode(){     
       if (this.userService.hasRole(Roles.CLIENT_EDIT)){
            this.mode = (this.cliId) ? StateMode.update : StateMode.create;
       } else {
           this.mode = StateMode.readonly;
       }
    }

    saveClient(){
        if (this.mode != StateMode.readonly){
            if (this.isPhysicalClientType(this.clientType)){
                this.physicalClient.save().then( (success:FullClient) => this.created(success), e =>{});
            }
            if (this.isMoralClientType(this.clientType)){
                this.moralClient.save().then( (success:FullClient) => this.created(success), e =>{});
            }
        }           
    }

    created(success:FullClient){
        this.cliId = success.cliId; 
        this.add();
    }

    add(){
        this.onClose.next(this.cliId);
        this.activeModal.hide();
    }

    isPhysicalClientType(clientType: number): boolean {
        return clientType == ClientType.PHYSICAL;
    }

    isMoralClientType(clientType: number): boolean {
        return clientType == ClientType.MORAL;
    }

}