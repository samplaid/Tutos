import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Page, defaultPageSize, OperationEntrySearchCriteria } from '../../../_models';
import { OperationEntryService } from '../../../_services';


@Component({
    selector: 'policy-operation',
    templateUrl: 'policy-operation.tpl.html'
})

export class PolicyOperationComponent implements OnInit {    
    operationEntryBusy: any;
    page: Page;  

    @Input() policyId:string;


    constructor(protected operationService: OperationEntryService) {}


    ngOnInit() {
        this.page = new Page();
        this.page.pageSize=10;
        this.page.currentPage = 1;
        this.searchOperationEntry(1);
    }

     /**
     * A function which handle a fund new business entries.
     * @param page the page.
     */
    searchOperationEntry(pageNum): void {
        this.operationEntryBusy = this.operationService.getOperationForPolicy(this.policyId)
                                                       .subscribe(data => this.buildPage(data), e => { });
        
    } 

    buildPage(data){
        if (!data) data = []; 
        this.page.content = data;
        this.page.totalRecordCount = data.length;    
        //console.log(this.page);
    }
    
}