import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Page } from "../_models/page";
import { OperationEntrySearchCriteria, defaultPageSize } from "../_models/index";
import { EntityType } from "../_models/constant";
import { OperationEntryService } from '../_services';
import { PolicyService } from '../policy/services/policy.service';

@Component({
    selector: 'operation-entry',
    templateUrl: 'operation-entry.component.html'
})

export class OperationEntryComponent implements OnInit, OnChanges {    
    _ownerId;
    _ownerType;
    operationEntryBusy: any;
    page: Page;  

    @Input() set ownerId(value:string){
        this._ownerId = value;
    };
    get ownerId(){
        return this._ownerId;
    }

    @Input() set ownerType(value:string){
        this._ownerType = value;
    };
    get ownerType(){
        return this._ownerType;
    }

    constructor(protected operationService: OperationEntryService, private policyService:PolicyService) {
    }

    ngOnChanges(changes: SimpleChanges): void {   
        console.log(changes)    
        if ((changes['ownerId'] && changes['ownerId'].currentValue || this._ownerId) &&
            (changes['ownerType'] && changes['ownerType'].currentValue || this._ownerType )) {
            
            this.searchOperationEntry(1);
        }
    }

    ngOnInit() {
        this.page = new Page();
    }

     /**
     * A function which handle a fund new business entries.
     * @param page the page.
     */
    searchOperationEntry(pageNum): void {
        let searchCriteria = new OperationEntrySearchCriteria(pageNum || 1, (defaultPageSize / 2));

        if (this._ownerType === EntityType.FUND) {
            searchCriteria.fundId = this._ownerId;
        }
        if (this._ownerType ===  EntityType.CLIENT) {
            searchCriteria.clientId = parseInt(this._ownerId, 10);
        }
        
        this.operationEntryBusy = this.operationService.searchOperationEntry(searchCriteria)
        .map(page => { 
            (<any[]>page.content).forEach(item => {
                if(item) {
                    this.policyService.isExist(item.policyId).subscribe(flag => item.policyExist = (flag == true));
                }
            });

            return page;
         })
        .subscribe(pageItem => {
            this.page = (!!pageItem) ? pageItem : this.page;            
        }, e => { });
        
    } 
    
    public translateAppFormStatus(appFormStatus: string): string {
        if(appFormStatus === 'IN_FORCE') {
            return 'In force';
        }
        return 'Pending';
    }

}
