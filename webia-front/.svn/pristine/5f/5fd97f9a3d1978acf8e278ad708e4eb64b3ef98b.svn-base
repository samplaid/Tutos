import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { isNullOrUndefined } from 'util';
import { HttpService, MessageService, ApiService, DateUtils } from '../../utils';
import { Transaction, TransactionSearchCriteria, PolicyTransactionsDetails} from '../../_models/transaction';
import { transactionAdminManagementHoldmailCodes, transactionCancelledStatuses, transactionMortalityCodes } from '../../_models';

@Injectable()
export class TransactionService extends HttpService {

    constructor($http: Http, messageService: MessageService, private api: ApiService) {
        super($http, messageService);
        this.domain = 'TransactionService';
    }

    getTransactionsByPolicy(policyId: string): Observable<Transaction[]> {
        return this.GET(this.api.getURL('getPoliciesTransactions', policyId), 'getPoliciesTransactions');
    }

    filterTransactions(transactions: Transaction[], transactionCriteria: TransactionSearchCriteria): Transaction[] {
        return transactions.filter(transaction => {
            const isDateInRange = this.isDateInRange(transaction.effectiveDate, transactionCriteria.dateEffectTo);
            const isTypeInFilter = this.isTypeInFilter(transaction.eventType, transactionCriteria.isAdminManagementHoldmail);
            const isStatusInFilter =  this.isStatusInFilter(transaction.statusCode, transactionCriteria.isCancelled);
            const isMortalitySelected = this.isMortalitySelected(transaction.eventType, transactionCriteria.isMortality);
            return isDateInRange && isTypeInFilter && isStatusInFilter && isMortalitySelected;
        });
    }

    private isDateInRange(dateTimestamp: Date, maxDate: Date): boolean {
        if (isNullOrUndefined(maxDate) || isNullOrUndefined(dateTimestamp) ) {
            return true;
        }
        return maxDate.getTime() >= dateTimestamp.getTime();
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