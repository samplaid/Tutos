import { Component, OnInit, Input, OnChanges, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { Currency, transferStatus } from '../../../_models';
import { paymentAnimation } from '@workflow/models/animations';
import { StateMode } from '../../../utils';
import { UUID } from 'angular2-uuid';
import { TransferCandidate } from '../../../withdrawal/models/transfer-candidate';
import { TransferForm } from '../../../withdrawal/models/transfer';
import { ibanMask } from '@workflow/models/masks';

@Component({
    selector: 'cash-transfer',
    templateUrl: 'cash-transfer.tpl.html',
    styles:[`
        .currency-select {
            margin-left: 2px;
        }
        .payment-form > .row {
            margin-top: 2px;
        }
        .payment-row {
            margin-bottom: 23px;
        }
        .add-button {
            margin-right: 10px;
        }
        .no-padding-right {
            padding-right: 0px;
        }
        .no-padding-left {
            padding-left: 0px;
        }
        .currency-select-sm {
            width: 50px;
        }
    `],
    animations: [paymentAnimation]
})

export class CashTransferComponent implements OnInit {

    mode = StateMode.readonly;

    @Input("mode") parentMode: string;
    @Input() currencyList: Currency[];
    @Input() payment: TransferForm;
    @Input() candidatesMap: { [key:string]: TransferCandidate; };
    readonly uuid = UUID.UUID();
    @Input() deletable: boolean;
    @Output() onDelete = new EventEmitter<void>();
    readonly transferStatus = transferStatus;
    readonly stateMode = StateMode;
    @Input() transferCandidates: TransferCandidate[];
    readonly ibanMask = ibanMask;
    
    constructor() { }

    ngOnInit(): void { }

    candidateChange(fdsId: string): void {
        if(fdsId != null) {
            let selectedCandidate: TransferCandidate = this.candidatesMap[fdsId];
            if(selectedCandidate != null && selectedCandidate.fundName != "Wealins Account") {
                this.payment.fdsId = selectedCandidate.fundId;

                if(selectedCandidate.fundSubType == "FID" || selectedCandidate.fundSubType == "FAS") {
                    this.payment.libDonOrd = "WEALINS S.A.";
                } else {
                    this.payment.libDonOrd = selectedCandidate.fundName;
                }

                this.payment.ibanDonOrd = selectedCandidate.iban;
                this.payment.swiftDonOrd = selectedCandidate.bic;
            }
        } else {
            this.payment.fdsId = null;
            this.payment.libDonOrd = null;
            this.payment.ibanDonOrd = null;
            this.payment.swiftDonOrd = null;
        }
    }

    setMode(){
        if (this.payment){
            let mode = StateMode.readonly;
            if (this.parentMode != StateMode.readonly){
                let p = this.payment;
                if (p && (p.transferStatus == transferStatus.NEW || p.transferStatus == transferStatus.READY)){
                    mode = StateMode.edit;
                }
            } 
            this.mode = mode;
        }
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['mode'] || changes['payment']){
            this.setMode();
        }        
        
        if (changes['candidatesMap'] && this.payment && !!this.payment.fdsId){
            this.candidateChange(this.payment.fdsId);
        }
    }

    delete(): void {    
        this.onDelete.emit();    
    }
}