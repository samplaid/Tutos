import { AbstractSearchCriteria } from "../../utils/index";

export class FundSearchCriteria extends AbstractSearchCriteria {
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
    
    constructor(pageNumber: number, pageSize: number){
        super();
        this.pageNum = pageNumber;
        this.pageSize = pageSize;
    }
}