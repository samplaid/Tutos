import { CommissionReconciliationGroupAggregate } from './commission-reconciliation-aggregate';
import { CommissionReconciliation } from './commission-reconciliation';
import { Selectable } from './selectable';

export interface CommissionReconciliationGroup extends Selectable{
    
    /** Represents the key of the reconciliation group. */
    aggregate: CommissionReconciliationGroupAggregate;

    /** Represents the elements of the group */
	commissions?: CommissionReconciliation[];
}
