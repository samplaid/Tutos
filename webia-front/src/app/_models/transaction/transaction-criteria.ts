import { AbstractSearchCriteria } from '../../utils';

export class TransactionSearchCriteria {
	dateEffectTo: Date;
	isCancelled: boolean;
	isAdminManagementHoldmail: boolean;
	isMortality:boolean;
}