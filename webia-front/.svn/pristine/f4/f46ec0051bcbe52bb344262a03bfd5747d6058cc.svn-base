import { Component, OnInit, Output, EventEmitter, Input, Type, ViewChild, ElementRef } from '@angular/core';
import { Fund } from "../../_models/fund";
import { Page } from "../../_models/page";
import { SearchService } from "../search.service";
import { FundSearchCriteria } from "./fund-search-criteria";
import { FundType } from "../../fund/index";
import { fundClassifierRegExp, defaultPageSize, Roles, AgentLite, AgentCategoryEnum, financialAdvisorAsRoles } from "../../_models";
import { UoptDetailService } from '../../_services/uopt-detail.service';
import { FundService } from '../../fund/fund.service';

const EMPTY: string = '';
const SPACE: string = ' ';

/**
 * This class represents the fund search component.
 * It display a modal that will contains a list of the fund.
 */
@Component({
    selector: 'fund-search',
    templateUrl: './fund-search.tpl.html',
    styles: [`
            .selected {
                border : solid 2px blue;
            }

            .modal-body {
                min-height : 500px;
            } 
            .fund-search .multiple-check {
                width:50px;
            }
            a.fund-search-label:hover {
                cursor: pointer;
                color: lightslategrey;
            }
            .modal-footer-left{
                text-align:left!important;
            }
            .modal-footer-right{
                text-align:right!important;
            }

            .fund-sub-type {
                width:13.66667% !important;
            }
            .fund-input-search {
                width:29% !important;
            }            
    `]
})
export class FundSearchComponent implements OnInit {
    readonly financialAdvisorTypes = financialAdvisorAsRoles;
    fundTypes = FundType.keys;
    busy;
    page: Page;
    noResult: boolean;
    regXp: RegExp;
    fundSubType: string;
    agent: AgentLite;
    datastore:any = {};
    appRoles = Roles;   
    pricingFrequencies: any[];
        
    /////////// search form variables ///////////////
    frmFilter; // Name/code
    frmIsinCode;
    frmIbanOrAccountRoot: string;
    frmDepositBank;
    frmAssetManager;
    frmFinancialAdvisor;
    frmFundSubType;
    frmStatus;
    //////////////////////////

    constructor(private fundSearchService: SearchService,
                private uoptDetailService: UoptDetailService) { }

    statusChange(event) {
        this.frmStatus = (event.target.checked) ? 1 : undefined;
        this.search(1);
    }

    /**
     * An overriden function from angular.
     */
    ngOnInit() {
        this.frmStatus = 1;
        this.regXp = fundClassifierRegExp;
        this.frmFundSubType = FundType.FID.key;
        this.page = new Page();
        this.uoptDetailService.getPricingFrequencies().subscribe(res => this.pricingFrequencies = (!!res) ? res: []);
    }

    /**
     * Open a new tab browser for a new creation of a new fund.
     * @param fundType  the fund type
     */
    createNewFund(fundType: string) {
        switch (fundType) {
            case FundType.FID.key:
                window.open('./#/fund/FID', 'newFidTab');
                break;
            case FundType.FAS.key:
                window.open('./#/fund/FAS', 'newFasTab');
                break;
            case FundType.FE.key:
                window.open('./#/fund/FE', 'newFeTab');
                break;
            default:
                break;
        }
    }


    /**
     * An function which handle a fund search when the button search has been triggered.
     * @param page the page.
     */
    search(page): void {
        let searchCriteria = new FundSearchCriteria(page, defaultPageSize);
        if (page)
            searchCriteria.pageNum = page;

        this.prepareCriteria(searchCriteria);
        this.busy = this.fundSearchService.advanceSearch(searchCriteria).toPromise().then(pageItem => {
            this.page = pageItem;
            this.fundSubType = searchCriteria.fundSubType;
            this.noResult = (!pageItem.content || pageItem.content.length < 1);
            //this.datastore = {};
            if (pageItem.content && pageItem.content.length > 0) {
                pageItem.content.forEach((fund: Fund, i) => {
                    this.datastore = this.fillAgents(fund, this.datastore);
                });
                if (this.fundSubType == 'FE'){
                   pageItem.content.forEach((fund: Fund, i) => {
                       this.computeLimitCAA(fund);
                }); 
                }
            }

        }, e => { });
    }

    toPricingDescription(pricingNumber: number) {        
        return this.uoptDetailService.toPricingLabel(pricingNumber, this.pricingFrequencies);
    }

    fillAgents(fund: Fund, datastore){
        datastore = this.fillAgent(fund, 'depositBank', datastore);
        datastore = this.fillAgent(fund, 'assetManager', datastore);
        datastore = this.fillAgent(fund, 'financialAdvisor', datastore);
        datastore = this.fillAgent(fund, 'broker', datastore);
        return datastore;
    }


    fillAgent(fund: Fund, type:string, datastore) {
        if (fund[type]) {
            let agt = datastore[fund[type]];
            if (agt) {
                fund[type+'Name'] = agt.name;
            } else {
                datastore[fund[type]] = "loading...";
                this.fundSearchService.getAgent(fund[type]).then((a: AgentLite) => {
                    datastore[fund[type]] = a ? (a.agtId + ' - ' + a.name) : undefined;
                }).catch(e => { });
            }
        }
        return datastore;

    }

    computeLimitCAA(fund: Fund){
        let bool = false;
        if (!this.isNullor100(fund.maxAllocationA)) bool = true;
        if (!this.isNullor100(fund.maxAllocationB)) bool = true;
        if (!this.isNullor100(fund.maxAllocationC)) bool = true;
        if (!this.isNullor100(fund.maxAllocationD)) bool = true;
        if (!this.isNullor100(fund.maxAllocationN)) bool = true;
        if (!this.isNullor100(fund.maxAllocationPercent)) bool = true;
        fund['limitCAA'] = bool ? 'Limited' : 'Unlimited';
    }

    isNullor100(value:number):boolean{
        return (value==null || value ==100);
    }

    surroundWithPercent(value: string): string {
        return value ? ("%" + value + "%") : value;
    }

    ngOnDestroy(): void {
    }

    prepareCriteria(searchCriteria: FundSearchCriteria) {
        searchCriteria.fundSubType = this.frmFundSubType;
        searchCriteria.filter = this.surroundWithPercent(this.frmFilter);
        searchCriteria.depositBank = this.surroundWithPercent(this.frmDepositBank);
        searchCriteria.status = this.frmStatus;

        switch (searchCriteria.fundSubType) {
            case FundType.FID.key:
                searchCriteria.ibanOrAccountRoot = this.surroundWithPercent(this.frmIbanOrAccountRoot);
                searchCriteria.assetManager = this.surroundWithPercent(this.frmAssetManager);
                break;
            case FundType.FAS.key:
                searchCriteria.ibanOrAccountRoot = this.surroundWithPercent(this.frmIbanOrAccountRoot);
                searchCriteria.financialAdvisor = this.surroundWithPercent(this.frmFinancialAdvisor);
                break;
            case FundType.FE.key:
                searchCriteria.isinCode = this.surroundWithPercent(this.frmIsinCode);
                break;
            default:
                break;
        }
    }

    get noResultColSpan() {
        let colspan: number;
        if (this.fundSubType == FundType.FID.key)
            colspan = 10;
        else if (this.fundSubType == FundType.FAS.key)
            colspan = 10;
        else if (this.fundSubType == FundType.FE.key)
            colspan = 9;
        else
            colspan = 8;
        return colspan;
    }

    encodeUri(uri: any){
        return encodeURIComponent(uri);
    }

    decodeUri(uri: any){
        return decodeURIComponent(uri);
    }

    eraseFields(){
        this.frmFilter=undefined; // Name/code
        this.frmIsinCode=undefined;
        this.frmIbanOrAccountRoot=undefined;
        this.frmDepositBank=undefined;
        this.frmAssetManager=undefined;
        this.frmFinancialAdvisor=undefined;
        this.frmFundSubType=FundType.FID.key;
        this.frmStatus=1;
    }

}