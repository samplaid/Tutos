/**
 * Represents the data nodel of the commission statement
 */
export interface Commission {
    
    /** Contains the commission id */
    comId?: number;
    
    /** Contains the agent id */
    agentId?: string;
    
    /** Contains the payment source name */
    origin?: string;

    /** Contains the payment source id */
	originId? : number;
    
    /** Contains The commission type */
    comType?: string;

    /** Contains the payment period*/
	accountingMonth?: string;
    
    /** Contains the payment date */
    comDate?: Date;

    /** contains the payment amount */
    comAmount?: number;
    
    /** Contains the currency */
	comCurrency?: string;
    
    /** Contains the commission rate */
    comRate?: number;

    /** Contains the commission base amount */
    comBase?: number;
    
    /** Contains the product id */
    productCd?: string;
    
    /** Contains the policy id */
    policyId?: string;
    
    /** Contains the fund name */
    fundName?: string;
    
    /** Contains the commission print date */
    comPrintDate?: Date;
    
    /** Contains the sub agent id */
	subAgentId?: string;
    
    /** Contains the transfer id */
    transferId?: number;

    /** Contains the statement id */
	statementId?: number;
    
    /** Contains the code isin */
    codeIsin?: string;

    /** Contains the transaction */
    transaction0?: number;
    
    /** Contains the commission reconciliation status */
    status?: string;
}
