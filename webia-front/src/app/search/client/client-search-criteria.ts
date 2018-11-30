import { AbstractSearchCriteria } from "../../utils/index";

export class ClientSearchCriteria extends AbstractSearchCriteria {
    date?: string;  // represent the client birthday
    type?: number;
    name?: string;
    code?: number;
    firstName?: string;
    maidenName?: string;    
    
    constructor(pageNumber: number, pageSize:number){
        super();
        this.pageNum = pageNumber;
        this.pageSize = pageSize;
    }
    
}