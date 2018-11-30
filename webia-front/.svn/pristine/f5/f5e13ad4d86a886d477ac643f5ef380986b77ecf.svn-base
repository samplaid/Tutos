import { Component, OnInit, Input, SimpleChanges } from '@angular/core';
import { paymentAnimation } from '@workflow/models/animations';
import { StateMode } from '../../../utils';
import { transferStatus } from '../../../_models';
import { TransferForm } from '../../../withdrawal/models/transfer';
import { TransferCandidate } from '../../../withdrawal/models/transfer-candidate';
import { SecurityTransferLine } from '../../../withdrawal/models/securities-transfer';
import { UUID } from 'angular2-uuid';
import { ibanMask } from '@workflow/models/masks';

@Component({
    selector: 'securities-transfer',
    templateUrl: 'securities-transfer.tpl.html',
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
        .securities {
            margin-top: 2px;
        }
    `],
    animations: [paymentAnimation]
})
export class SecuritiesTransferComponent implements OnInit {

    mode = StateMode.readonly;
    @Input("mode")
    parentMode: string;
    @Input()
    payment: TransferForm;
    @Input()
    transferCandidates: TransferCandidate[];
    @Input()
    showSecurities: boolean;    
    @Input()
    fromEditable: boolean;        
    @Input()
    bicEditable: boolean;        
    readonly uuid = UUID.UUID();
    readonly ibanMask = ibanMask;

    candidatesMap : { [key:string]: TransferCandidate; } = {};
    readonly transferStatus = transferStatus;
    readonly stateMode = StateMode;

    constructor() { }

    ngOnInit(): void { }

    addSecurityTransferLine(): void {
        const newLine: SecurityTransferLine = {};
        if(this.payment.transferSecurities == null){
            this.payment.transferSecurities = [];
        }
        this.payment.transferSecurities.push(newLine);
    }

    candidateChange(fundId: string): void {
        if(fundId != null) {
            let selectedCandidate: TransferCandidate = this.candidatesMap[fundId];
            this.payment.fdsId = selectedCandidate.fundId;
            this.payment.libDonOrd = selectedCandidate.fundName;

            if(selectedCandidate.fundSubType == "FID" || selectedCandidate.fundSubType == "FAS") {
                this.payment.libDonOrd = "WEALINS S.A.";
            } else {
                this.payment.libDonOrd = selectedCandidate.fundName;
            }
            
            this.payment.ibanDonOrd = selectedCandidate.iban;
            this.payment.swiftDonOrd = selectedCandidate.bic;
        } else {
            this.payment.fdsId = null;
            this.payment.libDonOrd = null;
            this.payment.ibanDonOrd = null;
            this.payment.swiftDonOrd = null;
        }
    }

    deleteSecurityTransferLine(index: number) : void {
        this.payment.transferSecurities.splice(index, 1);
    }

    setMode(){
        if (this.parentMode != StateMode.readonly){
            let p = this.payment;
            if (p && (p.transferStatus == transferStatus.NEW || p.transferStatus == transferStatus.READY)){
                this.mode = StateMode.edit;
                return;
            }
        }
        this.mode = StateMode.readonly;
    }

    ngOnChanges(changes: SimpleChanges): void {
        if(changes['transferCandidates']) {
            // refresh candidates map
            this.candidatesMap = {};
            for(let candidate of changes['transferCandidates'].currentValue) {
                this.candidatesMap[candidate.fundId] = candidate;
            }
        }
        if (changes['mode'] || changes['payment']){
            this.setMode();
        }
    }
}