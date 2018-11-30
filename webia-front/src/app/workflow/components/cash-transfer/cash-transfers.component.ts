import { Component, OnInit, Input, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { Currency, transferStatus } from '../../../_models';
import { paymentAnimation } from '@workflow/models/animations';
import { StateMode } from '../../../utils';
import { TransferForm } from '../../../withdrawal/models/transfer';
import { TransferCandidate } from '../../../withdrawal/models/transfer-candidate';

@Component({
    selector: 'cash-transfers',
    templateUrl: 'cash-transfers.tpl.html',
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
export class CashTransfersComponent implements OnInit {


    @Input() mode: string;
    @Input() currencyList: Currency[];
    @Input() payments: TransferForm[];
    @Input() transferCandidates: TransferCandidate[];
    
    candidatesMap : { [key:string]: TransferCandidate; } = {};

    @Output()
    paymentsChange = new EventEmitter<TransferForm[]>();
    @Output()
    onAddPayment = new EventEmitter<void>();

    readonly transferStatus = transferStatus;
    readonly stateMode = StateMode;

    constructor() { }

    ngOnInit(): void { }

    addPayment() : void {
        this.onAddPayment.emit();
    }

    deletePayment(index: number) : void {
        this.payments.splice(index, 1);
        this.paymentsChange.emit(this.payments);
    }

    candidateChange(fundId: string, payment: TransferForm): void {
        if(fundId != null) {
            let selectedCandidate: TransferCandidate = this.candidatesMap[fundId];
            payment.fdsId = selectedCandidate.fundId;

            if(selectedCandidate.fundSubType == "FID" || selectedCandidate.fundSubType == "FAS") {
                payment.libDonOrd = "WEALINS S.A.";
            } else {
                payment.libDonOrd = selectedCandidate.fundName;
            }

            payment.ibanDonOrd = selectedCandidate.iban;
            payment.swiftDonOrd = selectedCandidate.bic;
        } else {
            payment.fdsId = null;
            payment.libDonOrd = null;
            payment.ibanDonOrd = null;
            payment.swiftDonOrd = null;
        }
    }


    ngOnChanges(changes: SimpleChanges): void {
        if(changes['transferCandidates']) {
            // refresh candidates map
            this.candidatesMap = {};
            for(let candidate of changes['transferCandidates'].currentValue) {
                this.candidatesMap[candidate.fundId] = candidate;
            }
        }
    }
}