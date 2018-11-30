
//criteria object for the search policy service
import { pageableCriteria } from "../_models";

export class BasicSearchCriteria extends pageableCriteria {
    public filter :string;
    constructor(pageNumber: number, pageSize: number){
        super();
        this.pageNum = pageNumber;
        this.pageSize = pageSize;
    }
}

export class SearchPolicyCriteria extends pageableCriteria {
    polId :string;
    applicationForm :string;
    brokerRefContract :string;
    brokerId :string;
    fundId :string;
    withActive:boolean;
    withPending: boolean;
    withInactive: boolean;

    constructor(){
        super();
        this.withActive=true;
        this.withPending=true;
        this.withInactive=false;
    }
}

export class FundSearchCriteria extends BasicSearchCriteria {
    fundSubType: string;
    fdsId?: string;
    name?: string;
    isinCode?: string; // for FE
    accountRoot?: string; // for FID and FAS
    iban?:string; // for FID and FAS
    depositBank?:string; // for all
    assetManager?:string;
    financialAdvisor?:string;
    ibanOrAccountRoot?: string;
    status?: number;
    excludeTerminated?: boolean;
    
    constructor(pageNumber: number, pageSize: number){
        super(pageNumber, pageSize);
    }
}

export class FundValuationSearchCriteria extends BasicSearchCriteria {
    fdsId: string;

    constructor(pageNumber: number, pageSize: number){
        super(pageNumber, pageSize);
    }
}

export class ClientSearchCriteria extends BasicSearchCriteria {
    date?: string;  // represent the client birthday
    type?: number;
    name?: string;
    code?: number;
    firstName?: string;
    maidenName?: string;    
    
    constructor(pageNumber: number, pageSize: number){
        super(pageNumber, pageSize);
    }
    
}

export class AgentSearchCriteria extends BasicSearchCriteria {
    status: number;
    category: string;
    constructor(pageNumber: number, pageSize: number){
        super(pageNumber, pageSize);
    }
    
}