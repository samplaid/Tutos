import { Page } from '../../../_models';

export class PagerCreator {

    /**
     * Create a page of items based on the data array.
     * @param page the page number
     * @param data the data array to create page to.
     * @returns a page.
     */
    public static create(page: number, size: number, data: Array<any>): Page {
        let newPage = new Page();
        let localPage = page;

        if(page <= 0 || !page) {
            localPage = 1;
        }      

        let start = (localPage - 1) * size;
        let end = size > -1 ? (start + size) : data.length;

        newPage.content = data.slice(start, end);
        newPage.currentPage = localPage;
        newPage.size = size;
        newPage.totalRecordCount = data.length;
        newPage.totalPages = Math.ceil(data.length / size);
        
        newPage.number = newPage.currentPagege
        newPage.pageSize = newPage.size;
        newPage.totalElements = newPage.totalRecordCount;
        newPage.totalPageCount = newPage.totalPages;
        return newPage;
    }
}
