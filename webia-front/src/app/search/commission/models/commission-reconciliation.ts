import { Commission } from './commission';

/**
 * This interface contains information about commission reconcialiation result.
 */
export interface CommissionReconciliation {

    /** Contains the agent id */
    agentId?: string;

    /** Contains the CRM identifier */
    crmId?: string;

    /** Contains the name of the agent */
    name?: string;

    /** Contains the commission type */
    comType?: string;

    /** Contains the commission period */
    period?: string;

    /** Contains commission currency */
    currency?: string;

    /** Contains commission extracted from sap system */
    sapBalance?: number;

    /** Contains commission extracted from webia system */
    statementBalance?: number;

    /** Contains the reconcilation value*/
    gap?: number;

    /** Contains the reconcilation staus*/
    status?: string;

    /** Contains the sap commission */
    sapCommissionsToPay?: Commission[];
    
    /** Contains the webia commission */
    webiaCommissionToPay?: Commission[];
}
