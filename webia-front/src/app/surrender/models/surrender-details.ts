import { Transaction, PolicyTransactionsDetails } from '@models/transaction';

export interface SurrenderTransactionDetailsDTO {
    transaction: Transaction;   
    details: PolicyTransactionsDetails;
}