import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { FundComponent } from './../fund.component';
import { FundService } from '../fund.service';
import { Subscription, Subject } from 'rxjs';

@Component({
    selector: 'modal-new-fid',
    template: `
    <div class="modal-new-fid">
        <div class="modal-header">
            <button type="button" class="close pull-right" aria-label="Close" (click)="activeModal.hide()"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">{{(!fundId)?'Create':'Update'}} Fund</h4>
        </div>
        <div class="modal-body">       
            <fund [fundId]="fundId" [subType]="subType"></fund>         
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-xs btn-primary marginRight4" [disabled]="!fundId" (click)="add(null)"><i class="fa fa-reply"></i>Add</button>
            <button type="button" class="btn btn-xs btn-primary" (click)="saveFund()" [disabled]="saving"><i class="fa fa-floppy-o"></i>Save</button> 
            <button type="button" class="btn btn-xs btn-default" (click)="activeModal.hide()"><i class="fa fa-times"></i>Cancel</button>
        </div>
    </div>
    `
})
export class ModalFundContent implements OnInit {    
    busySaveFund: Subscription;
    saving: boolean;
    
    @Input() subType: 'FID' | 'FAS' | 'FE' | 'FIC';
    @Input() fundId: number;
    @ViewChild(FundComponent) fundComponent: FundComponent;

    constructor(public activeModal: BsModalRef, private fundService: FundService) { }

    public onClose: Subject<any>;

    ngOnInit() { 
        this.onClose = new Subject();
    }

    cancelPreviousRequestIfPossible() {
        if(this.busySaveFund && !this.busySaveFund.closed) {
            this.busySaveFund.unsubscribe();
        }
    }

    saveFund() {
        this.saving = true;
        this.cancelPreviousRequestIfPossible();
        this.busySaveFund = this.fundService.save(this.fundComponent.mode, this.fundComponent.fund)
                                            .subscribe(fund => this.add(fund), e => {
                                                this.saving = false;
                                            }, () => {
                                                this.saving = false;
                                            });
    }

    add(fund) {
        if (!fund)
            fund = this.fundComponent.fund;
        this.close(fund);
    }

    public close(data:any){
      this.onClose.next(data);
      this.activeModal.hide();
    }

}