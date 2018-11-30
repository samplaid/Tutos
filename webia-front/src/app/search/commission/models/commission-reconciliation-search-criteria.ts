import { PageRequest } from '../../../_models/paging/page-request';

/**
 * This interface that exposes commission search criteria: 
 * the commission type and the code or name of the agent.
 */
export interface CommissionReconciliationSearchCriteria{
    
    /** Contains the pagination request*/
    pageable?: PageRequest;

    /** Constains the type of the commission */
    type?: string;

    /** Contains the search text. By default the code or the name of the agent. */
    text?: string;

}
