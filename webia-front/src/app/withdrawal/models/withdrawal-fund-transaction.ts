import { FundLite } from '../../_models/fund-lite';

export interface FundTransactionForm {
    fundTransactionFormId: number;
    transactionId?: number;
	currency?: string;	
    fundId: string;
    fundTp?: string;
    fund?: FundLite;
    type?: string;
    amount?: number;
    units?: number;
    percentage?: number;
    valuationAmt?: number;
    split?: number;
    inputType?: string;
    relativeShare?: number;     // this represents the relative share of the fund in the policy, this data only exists in the front-end
}
