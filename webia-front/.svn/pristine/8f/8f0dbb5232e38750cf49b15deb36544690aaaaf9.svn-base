import { AbstractSearchCriteria } from "../../utils/index";

export class OperationEntrySearchCriteria extends AbstractSearchCriteria {
// to search by client
    fundId: string;
    clientId: number;
    status: string[];
// to search by Agent
    partnerId: string;
    partnerCategory: string;
// to search by policy
    policyId: string;

    constructor(pageNumber: number, pageSize: number){
        super();
        this.pageNum = pageNumber;
        this.pageSize = pageSize;
    }
}