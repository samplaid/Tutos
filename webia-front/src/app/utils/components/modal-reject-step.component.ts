import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { Subject } from "rxjs/Subject";
import { BsModalRef } from "ngx-bootstrap";

@Component({
    selector: 'modal-reject',
    template: `
    <div class="modal-reject">
        <div class="modal-header">
            <button type="button" class="close pull-right" aria-label="Close" (click)="activeModal.hide()"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Reason of reject</h4>
        </div>
        <div class="modal-body">     
               <textarea autofocus autosize class="form-control" [(ngModel)]='reason' rows="4" maxlength="1000"></textarea>      
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-xs btn-danger" [disabled]="!reason || reason==''" (click)="close(reason)"><i class="fa fa-floppy-o"></i>Reject</button> 
            <button type="button" class="btn btn-xs btn-default" (click)="activeModal.hide()"><i class="fa fa-times"></i>Cancel</button>
        </div>
    </div>
    `
})
export class ModalRejectStep implements OnInit {

    public reason:string;
    public onClose: Subject<any>;

    constructor(public activeModal: BsModalRef) { }

    ngOnInit() { 
        this.onClose = new Subject();
        this.reason = "";
    }

    public close(data:any){
      this.onClose.next(data);
      this.activeModal.hide();
    }

}