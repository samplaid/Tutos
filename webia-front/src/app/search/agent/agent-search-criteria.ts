import { AbstractSearchCriteria } from "../../utils/index";

export class AgentSearchCriteria extends AbstractSearchCriteria {
    status: number;
    categories: string[];
    constructor(pageNumber: number, pageSize:number){
        super();
        this.pageNum = pageNumber;
        this.pageSize = pageSize;
    }
    
}