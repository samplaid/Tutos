import { Component, OnInit, Input } from '@angular/core';
import { paymentAnimation } from '@workflow/models/animations';
import { TransferForm } from '../../../withdrawal/models/transfer';
import { TransferCandidate } from '../../../withdrawal/models/transfer-candidate';

@Component({
    selector: 'securities-transfers',
    templateUrl: 'securities-transfers.tpl.html',
    styles:[`       
        .payment-row {
            margin-bottom: 23px;
        }
    `],
    animations: [paymentAnimation]
})
export class SecuritiesTransfersComponent implements OnInit {

    @Input()
    mode: string;
    @Input()
    securitiesTransfers: TransferForm[];
    @Input()
    transferCandidates: TransferCandidate[];
    @Input()
    showSecurities: boolean;    
    @Input()
    fromEditable: boolean;        
    @Input()
    bicEditable: boolean;        

    candidatesMap : { [key:string]: TransferCandidate; } = {};

    constructor() { }

    ngOnInit(): void { }

	duplicateBeneficiary(securitiesTransfer: TransferForm[]): void {		
        securitiesTransfer.forEach(f=> {
            f.libBenef = securitiesTransfer[0].libBenef;
            f.ibanBenef = securitiesTransfer[0].ibanBenef;
            f.swiftBenef = securitiesTransfer[0].swiftBenef;
            })
    }
}