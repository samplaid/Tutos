import { FormData } from '../../_models';
import { FundTransactionForm } from './withdrawal-fund-transaction';
import { TransferForm } from './transfer';

export class TransactionForm extends FormData {
    transactionFormId: number;
    transactionType: string;
    effectiveDate: Date;
    payments: TransferForm[];
    fundTransactionForms: FundTransactionForm[];
    securitiesTransfer: TransferForm[];
    brokerTransactionFees: number;
    transactionFees: number;
    amountType: string;
    specificAmountByFund:boolean;
    amount: number;
    paymentType:string;
    currency: string;
    status: string;
    readonly type ="TRANSACTION_FORM";
}
