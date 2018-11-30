export class Page {
    content: Array<any>;
    first: boolean;
    last:boolean;
    number: number; // FiXME: deprecated: to be removed in 18.3. Instead use currentPage
    numberOfElements: number; // FiXME: deprecated: to be removed in 18.3. Instead use totalRecordCount
    size:number; // FiXME: deprecated: to be removed in 18.3. Instead use pageSize
    sort: any;
    
    pageSize: number;
    totalElements:number;
    totalPages:number;
    currentPage: number = 1; // TODO to use in 18.3
    totalRecordCount:number;
    totalPageCount:number;

    [other:string] : any;

    constructor(currentPage:number=1){
        this.currentPage = currentPage;
    }

}