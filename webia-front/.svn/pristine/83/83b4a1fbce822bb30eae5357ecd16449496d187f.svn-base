import { Component, OnInit, Input, EventEmitter, Output, OnDestroy, SimpleChanges, ElementRef } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { Page, ClientStatusType, clientClassifierRegExp, defaultPageSize, Roles } from "../../_models";
import { BsModalService } from 'ngx-bootstrap/modal';
import { DateUtils } from "../../utils";
import { SearchService } from "../search.service";
import { ClientSearchCriteria } from "./client-search-criteria";
import { CountryService } from "../../_services/country.service";

const EMPTY: string = '';
const SPACE: string = ' ';

/**
 * This class represents the client search component.
 * It display a modal that will contains a list of the client.
 */
@Component({
    selector: 'client-search',
    templateUrl: './client-search.tpl.html',
    styles: [`
            .selected {
                border : solid 2px blue;
            }

            .modal-body {
                min-height : 800px;
            }
            .search-client,
            .search-box {
                min-width: 75px;
            }
            .search-box {
                line-height: 1.9rem !important;
                text-align:center;
            }
            .modal-footer-left{
                text-align:left!important;
            }
            .modal-footer-right{
                text-align:right!important;
            }
    `]
})
export class ClientSearchComponent implements OnInit, OnDestroy {
    subs: Subscription[];
    page: Page;
    noResult: boolean;
    regXp: RegExp;
    postalCodePrefix: string;
    appRoles = Roles;

    /////////////// Criteria form //////////////////
    criteriaDate: Date;
    criteriaCodeName: any;
    criteriaFirstname: string;
    criteriaMaidenName: string;
    

    constructor(
        protected modalService: BsModalService, 
        protected clientService: SearchService,
        protected countryService: CountryService) { }
    
    /**
     * An override function from angular.
     */
    ngOnInit() {        
        this.page = new Page();
        this.regXp = clientClassifierRegExp;
        this.subs = [];
    }

     
    /**
     * An function which handle a client search when the button search has been triggered.
     * @param page the page.
     */
    search(pageNumber): void {
        let criteria = new ClientSearchCriteria(pageNumber, defaultPageSize);
        if (pageNumber)
            criteria.pageNum = pageNumber;

        if (this.criteriaDate && typeof this.criteriaDate.getDate =='function')
            criteria.date = DateUtils.formatToIsoDate(this.criteriaDate);
        else
            criteria.date = undefined;
                
        criteria.filter = this.criteriaCodeName;
        criteria.firstName = this.criteriaFirstname;
        criteria.maidenName = this.criteriaMaidenName;

        this.subs.forEach(sub => sub && !sub.closed && sub.unsubscribe());
        this.subs = [this.clientService.searchClient(criteria).subscribe(pageItem => {
                this.page = pageItem;
                if(pageItem.content && pageItem.content.length > 0){
                    this.page.content.forEach((cl,i) => {
                        this.subs.push(this.countryService.getCountry(cl.country).subscribe(country => {
                            cl.ptCode = (country) ? country.ptCode : "";
                            this.page.content[i] = Object.assign({},cl);
                        }));
                    });                
                }
                this.noResult = (!pageItem.content || pageItem.content.length < 1);
            }, e => { })]
        };

    get isCriteriaSet(){
        return !this.criteriaCodeName && !this.criteriaDate && !this.criteriaFirstname && !this.criteriaMaidenName;
    }

    eraseFields(){
        this.criteriaDate = undefined;
        this.criteriaCodeName = undefined;
        this.criteriaFirstname = undefined;
        this.criteriaMaidenName = undefined;
    }

    surroundWithPercent(value: string): string {
        return value ? ("%" + value + "%") : value;
    }
   
    ngOnDestroy(): void {
        this.subs.forEach(sub => sub.unsubscribe());
    }

}
