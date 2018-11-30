import { Component, OnInit, Input } from '@angular/core';
import { PolicyTransactionsDetailsOutput, Transaction } from '@models/transaction';

@Component({
    selector: 'lissia-transaction',
    templateUrl: 'lissia-transaction.tpl.html',
    styles: [`    
        .transaction-amount {
            padding-top: 3px;
        }
    `]    
})
export class LissiaTransactionComponent implements OnInit {
    @Input()
    transaction: Transaction;
    @Input()
    surrenderDetails: PolicyTransactionsDetailsOutput;
    @Input()
    policyId: string;

    ngOnInit(): void {
        
    }
}