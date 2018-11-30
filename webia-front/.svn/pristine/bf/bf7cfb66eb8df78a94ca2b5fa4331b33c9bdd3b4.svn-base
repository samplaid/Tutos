

import {TransactionsHistoryDetails} from './transactions-history-details';
import { Transaction } from './transaction';
import { TransactionsCommissions } from '.';
export class PolicyTransactionsDetails{
    commissionCurrency :string ;
    commission:number;
    agentLabel:string;
    transactionHistoryDetails:TransactionsHistoryDetails[];
}

export interface PolicyTransactionsDetailsInput {
    policyId: string;
    transaction: Transaction;
}

export interface PolicyTransactionsDetailsOutput {
    detailsOut: TransactionsHistoryDetails[]; 
    detailsIn: TransactionsHistoryDetails[];  
    commission?: TransactionsCommissions;
    isSwitch?: boolean;
}