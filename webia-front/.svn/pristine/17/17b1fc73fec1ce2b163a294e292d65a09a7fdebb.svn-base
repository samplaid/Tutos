import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { AgentBasicFormComponent } from "../agent-basic-form.component";
import { FullAgent, Roles } from "../../../../_models/index";
import { AgentService } from "../../../agent.service";
import { UserService, StateMode } from "../../../../utils";
import { Subject } from "rxjs/Subject";

@Component({
    selector: 'agent-basic-modal',
    template: `
    <div>
        <div class="modal-header">
            <button type="button" class="close pull-right" aria-label="Close" (click)="dismiss('canceled')"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title inline-block">{{ internalTitle }}</h4>
            <label class="header-form-status">
                <span class="label label-primary w-label" *ngIf="agent && agent.status && agent.status == 1">Active</span>                
                <span class="label label-primary w-label" *ngIf="agent && agent.status && agent.status !=1 ">Inactive</span>
            </label>
            <label class="header-form-code" *ngIf="agentId"><b>Code</b><span class="paddingHorizontal2">{{ agentId }}</span></label>                                
        </div>
        <div class="modal-body">
            <agent-basic-form [agentId]="agentId" [mode]="mode" [inModal]="true" [category]="category" [title]="title" (titleChange)="titleChange($event)"></agent-basic-form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-xs btn-primary" [disabled]="mode===stateMode.readonly || saving" (click)="save()"><i class="fa fa-floppy-o"></i>Save</button> 
            <button type="button" class="btn btn-xs btn-default" (click)="dismiss('canceled')"><i class="fa fa-times"></i>Cancel</button>
        </div>
    </div>
    `,
    styles: [`
        .inline-block{
            display: inline-block;
        }
    `]
})
export class AgentBasicModalContent implements OnInit {  
    saving: boolean;
    ;
    agent: FullAgent;   
    internalTitle: string;
    agtId: any;
    stateMode = StateMode;

    public onClose: Subject<FullAgent>;
    
    @ViewChild(AgentBasicFormComponent) agentBasicFormComponent: AgentBasicFormComponent;
    
    @Input() mode = StateMode.readonly;
    @Input() title: string = '';
    @Input() set agentId(agtId){
        if(agtId) {
            this.agtId = agtId;
            this.agentService.getAgent(agtId).then(agent => {
                if(agent){ this.agent = agent; }
            });
        }
       
    };
    get agentId(){
        return this.agtId;
    }
    
    @Input() category: string;

        //public modalService: NgbModal
    constructor(private modalRef: BsModalRef,
                protected agentService: AgentService,
                protected userService: UserService) { }

    ngOnInit() { 
        this.onClose = new Subject();
     }

    titleChange(event){
        this.internalTitle = event;
    }

    save(){       
        this.saving = true; 
        this.agentBasicFormComponent.save().then(newAgent => {
            this.agent = newAgent;
            this.saving = false;
            this.close(this.agent);
        }, e => { this.saving = false; });
    }
      
    close(value){
        this.onClose.next(value);
        this.modalRef.hide();
    }

    dismiss(value){
        this.onClose.error("canceled");
        this.modalRef.hide();
    }
}
