import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';

import { TransactionsHistoryDetails } from '../../../_models/transaction/transactions-history-details';


export interface Column {
    index: number;
    name: string;
    label: string;
    title: string;
    sortable: boolean;
    hidden: boolean;
}


export const columns: Column[] = [
    { index: 0, name: 'fundSubType', label: null, title: 'Fund type', hidden: false, sortable: false },
    { index: 1, name: 'fundName', label: 'Funds', title: 'Funds', hidden: false, sortable: false },
    { index: 2, name: 'units', label: 'Units', title: 'Units', hidden: false, sortable: false },
    { index: 3, name: 'price', label: 'Price', title: 'Price', hidden: false, sortable: false },
    { index: 4, name: 'effectiveDate', label: 'Price Date', title: 'Price Date', hidden: false, sortable: false },
    { index: 5, name: 'valueFund', label: 'Value', title: 'Value', hidden: false, sortable: false },
    { index: 6, name: 'valuePol', label: 'Policy Currency Value', title: 'Policy Currency Value', hidden: false, sortable: false },
    { index: 7, name: 'split', label: 'Split', title: 'Split', hidden: false, sortable: false },
    { index: 8, name: 'status', label: 'Status', title: 'Status', hidden: false, sortable: false },
    { index: 9, name: 'transactionId', label: 'Transaction Number', title: 'Transaction Number', hidden: true, sortable: false },
    { index: 10, name: 'eventType', label: 'Transaction event type', title: 'Transaction event type', hidden: true, sortable: false },
    { index: 11, name: 'exchangeRate', label: 'Fund exchange rate', title: 'Fund exchange rate', hidden: true, sortable: false },
    { index: 12, name: 'fundId', label: 'fundId', title: 'Fund id', hidden: true, sortable: false }
];

@Component({
    selector: 'transaction-history-details',
    templateUrl: './transaction-history-details.tpl.html',
    styleUrls: ['./transaction-history-details.scss'],
    encapsulation: ViewEncapsulation.None
})
export class TransactionHistoryDetailsComponent implements OnInit {

    /**
     * Enuerates all columns
     */
    allColumns = columns;

   @Input()
    historyDetails: TransactionsHistoryDetails[] = []; 

    ngOnInit() {

    }
}