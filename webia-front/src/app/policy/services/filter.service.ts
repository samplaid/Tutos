import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { isNullOrUndefined } from 'util';
import { HttpService, MessageService, ApiService, DateUtils } from '../../utils';
import { Transaction, TransactionSearchCriteria, PolicyTransactionsDetails} from '../../_models/transaction';
import { transactionAdminManagementHoldmailCodes, transactionCancelledStatuses, transactionMortalityCodes } from '../../_models';

@Injectable()
export class FilterService  {

    constructor() {}

    filterTransactions(transactions: Transaction[], transactionCriteria: TransactionSearchCriteria): Transaction[] {
        return transactions.filter(transaction => {
            const isDateInRange = this.isDateInRange(transaction.effectiveDate, transactionCriteria.dateEffectTo);
            const isTypeInFilter = this.isTypeInFilter(transaction.eventType, transactionCriteria.isAdminManagementHoldmail);
            const isStatusInFilter =  this.isStatusInFilter(transaction.statusCode, transactionCriteria.isCancelled);
            const isMortalitySelected = this.isMortalitySelected(transaction.eventType, transactionCriteria.isMortality);
            return isDateInRange && isTypeInFilter && isStatusInFilter && isMortalitySelected;
        });
    } 

    private isDateInRange(dateTimestamp: any, maxDate: Date): boolean {
        if (isNullOrUndefined(maxDate)) {
            return true;
        }
        return maxDate.getTime() >= +dateTimestamp;
    }

    private isTypeInFilter(typeCode: number, isAdminManagementHoldmail: boolean): boolean {
        if (isAdminManagementHoldmail) {
            return true;
        }
        return transactionAdminManagementHoldmailCodes.indexOf(typeCode) == -1;
    }

    private isStatusInFilter(statusCode: number, isCancelled: boolean): boolean {
        if (isCancelled) {
            return true;
        }
        return transactionCancelledStatuses.indexOf(statusCode) == -1;
    }

    private isMortalitySelected(typeCode: number, isMortality: boolean): boolean {
        if (isMortality) {
            return true;
        }
        return transactionMortalityCodes.indexOf(typeCode) == -1;
    }
}