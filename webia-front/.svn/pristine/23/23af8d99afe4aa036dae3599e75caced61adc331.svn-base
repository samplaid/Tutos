import { Injectable } from '@angular/core';
import { defaultPageSize } from "../../_models/constant";
import { Page } from "../../_models/page";

@Injectable()
export class PaginationService {
    constructor(){}
    
    nextPage(array:any[], pageNumber, pageSize = defaultPageSize){
        let newPage: Page = new Page();
        this.initPage(newPage, pageSize);
        const pageCount = Math.ceil(array.length / pageSize);

        if(array && array.length > 0 && pageNumber > 0 && !isNaN(pageCount)) {             
            const start = (pageNumber - 1) * pageSize;
            const end = start + pageSize;
            newPage.totalRecordCount = array.length;
            newPage.totalPageCount = pageCount;
            newPage.number = pageNumber;                
            newPage.content = array.slice(start, end);         
        } 

        return newPage;
    }

    private initPage(page: Page, pageSize: number): void {
        if(page) {
            page.totalPageCount = 0;
            page.totalRecordCount = 0;
            page.number = null;
            page.content = [];
            page.size = pageSize;
        }
    }
}