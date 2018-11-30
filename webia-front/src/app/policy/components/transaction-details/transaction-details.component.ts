import { Component, OnInit, Input } from '@angular/core';
import { Transfer } from "../../../_models";
import * as _ from "lodash";

@Component({
    selector: 'transaction-details',
    templateUrl: './transaction-details.tpl.html',
    styles: [`.details{ border: 1px solid #d4d4d4; padding-top: 7px;}`]
})

export class TransactionDetailsComponent implements OnInit {

    readonly Object = Object;

    @Input() set transfers(value: Transfer[]) {
        if(value) {
            this.groupedTransfers = _.groupBy(value, transfer => transfer.transferType);
        }
    }

    groupedTransfers;

    ngOnInit() { 
        
    }
}