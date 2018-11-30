import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { AgentBasicModalContent } from "./agent-basic-modal-content.component";
import { FullAgent } from "../../../../_models/index";
import { Title } from "@angular/platform-browser";
import { StateMode, UserService } from "../../../../utils";

@Component({
    selector: 'agt-basic-modal-button',
    template: `
      <button *ngIf="!asLink" [ngClass]="css" type="button" (click)="open()"><ng-content></ng-content></button>    
      <a *ngIf="asLink" [ngClass]="css" class="clickable" (click)="open()"><ng-content select="[asLink]"></ng-content></a>
    `,
    styles:[``]
})
export class AgentBasicModalButtonComponent implements OnInit {
    modalRef: BsModalRef;
    oldTitle: string = '';

    @Input() asLink: boolean;    
    @Input() agentId;   
    @Input() title: string = '';
    @Input() category: string;    
    @Input() css;
    @Input() disabled: boolean;
    @Input() mode = StateMode.readonly;
    
    @Output() close = new EventEmitter<FullAgent>();

    constructor(public modalService: BsModalService,
                protected titleService: Title) { }

    ngOnInit() { }

    open(){
        if (!this.disabled){
            this.oldTitle = this.titleService.getTitle();
            let initialState = {title: this.title, mode: this.mode, category: this.category, agentId: this.agentId};
            this.modalRef = this.modalService.show(AgentBasicModalContent, { initialState, class:'modal-sm'});         
            this.modalRef.content.onClose.take(1).subscribe(result => this.resolve(result), reason => this.reject(reason));
        }
    }

    resolve(result){ 
        this.titleService.setTitle(this.oldTitle);
        this.close.emit(result);
    }

    reject(reason){this.titleService.setTitle(this.oldTitle);}
}