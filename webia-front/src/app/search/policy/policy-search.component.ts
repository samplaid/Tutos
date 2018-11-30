import { Component } from '@angular/core';
import { SearchService } from '../search.service';
import { Page, defaultPageSize, clientClassifierRegExp, SearchPolicyCriteria } from "../../_models";
import { Subscription } from "rxjs/Subscription";

@Component({
    templateUrl: 'policy-search.tpl.html'
})

/**
 * Home component
 * 
 * Contains :
 */
export class PolicySearchComponent  {

    busy;
    page:Page;
    noResult: boolean;
    sc:SearchPolicyCriteria;
    regXp: RegExp;
    _policies:any[];
    
    constructor( private searchService: SearchService ) {}

    ngOnInit(){
        this._policies = [];
        this.eraseFields();
        this.page = new Page();
        this.regXp = clientClassifierRegExp;
    }

  search(page) {
    if (page)
      this.sc.pageNum = page;
    let criteria = Object.assign({},this.sc);
    criteria.polId = (criteria.polId ? '%'+criteria.polId+'%':null);
    criteria.applicationForm = (criteria.applicationForm ? '%'+criteria.applicationForm+'%':null);
    criteria.brokerRefContract = (criteria.brokerRefContract ? '%'+criteria.brokerRefContract+'%':null);
    this.busy = this.searchService.searchPolicies(criteria).subscribe(page => this.setResult(page), e => { });
  }

  setResult(page) {
      if (page){
        
        this.page = page;
        this.noResult = (!page.content || page.content.length < 1);
      }
  }



  eraseFields(){
        this.sc = new SearchPolicyCriteria();
        this.sc.pageNum = 1;
        this.sc.pageSize = defaultPageSize;
  }

  ngOnDestroy() {

  }

}


