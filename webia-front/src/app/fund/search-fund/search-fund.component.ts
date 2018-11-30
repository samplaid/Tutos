import { ModalFundContent } from './../modal/modal-fund-content.component';
import { Component, OnInit, Output, EventEmitter, Input, Type, ViewChild, ElementRef } from '@angular/core';
import { FundService, FundSearchCriteria } from "../fund.service";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { Page } from "../../_models/page";
import { fundClassifierRegExp, defaultPageSize, Roles } from "../../_models";
import { FundType } from "../index";
import { UserService, StateMode } from "../../utils/index";
import { UoptDetailService } from '../../_services/uopt-detail.service';
import { FundLite } from '../../_models/fund-lite';

const EMPTY: string = '';
const SPACE: string = ' ';

/**
 * This class represents the fund search component.
 * It display a modal that will contains a list of the fund.
 */
@Component({
    selector: 'search-fund',
    templateUrl: './search-fund.tpl.html',
    styles: [`
            .selected {
                border : solid 2px blue;
            }

            .modal-body {
                min-height : 500px;
            } 
            .search-fund .multiple-check {
                width:50px;
            }
            a.search-fund-label:hover {
                cursor: pointer;
                color: lightslategrey;
            }
            .modal-footer-left{
                text-align:left!important;
            }
            .modal-footer-right{
                text-align:right!important;
            }
    `]
})
export class SearchFundComponent implements OnInit {
    readonly _fundTypes = FundType.keys;
    header = "Fund";
    busy;
    searchCriteria: FundSearchCriteria = new FundSearchCriteria();
    page: Page;
    noResult: boolean;
    selectedItems: Array<FundLite> = [];
    disabledNewFund: boolean = false;
    mode = StateMode.readonly;
    stateMode = StateMode;
    public searchModal: BsModalRef;
    public createModal: BsModalRef;
    brokerStatus:boolean = false;
    appRoles = Roles;
    pricingFrequencies: any[];
    currentFundType: string;

    regXp: RegExp;

    @Output() selectItemsChange = new EventEmitter<Array<FundLite>>();
    @Input() disabled: boolean = false;
    @Input() brokerId: string  // if defined, this will filter search result on 'FE' funds linked to this brokerId or null


    constructor(private modalService: BsModalService, 
                private fundService: FundService, 
                protected userService: UserService,
                private uoptDetailService: UoptDetailService) {
        this.searchCriteria.pageNum = 1;
        this.searchCriteria.type = 'FID';
        this.currentFundType = this.searchCriteria.type;
        this.searchCriteria.pageSize = defaultPageSize;
        this.page = new Page();
        this.uoptDetailService.getPricingFrequencies().subscribe(res => this.pricingFrequencies = (!!res) ? res: []);
    }

    /**
     * An overriden function from angular.
     */
    ngOnInit() { 
        this.regXp = fundClassifierRegExp;
        this.setMode();
    }

    setMode(): void{
        this.mode = this.userService.hasRole(Roles.FUND_FID_FAS_EDIT) ? StateMode.edit : StateMode.readonly;
    }

    /**
     * An event handler which open a modal to display a content.
     * @param content the content to display
     */
    private openModal(content, size?) {
        if (!this.disabled)
            return this.modalService.show(content, { class: size });
        return null;
    }

    /**
     * This method is called on click of the 'add fund button' and open a modal that 
     * will contain the content in parameter. 
     * It can be an template reference or a component.
     * @param content the template to be show in the modal.
     */
    open(content): void {
        if (!this.disabled)
            this.searchModal = this.openModal(content, 'modal-lg');
    }

    valide(funds: FundLite[]){
        this.selectItemsChange.emit(this.selectedItems);
        setTimeout(()=>this.clearSelection(),0);
        this.searchModal.hide();
    }

    /**
     * Emit the selected fund to the parent and enable the new fund buttons in order to create a new.
     */
    addToStack() {
        // Now emit the items to the parent.
        this.selectItemsChange.emit(this.selectedItems);
        setTimeout(()=>this.clearSelection(),0);
    }

    /**
     * Check if the provided fund is belong to the selected funds.
     * @param fund the fund to be checked.
     */
    isSelected(fund: FundLite): boolean {
        return (this.selectedItems.findIndex((item: FundLite) => item.fdsId == fund.fdsId) > -1);
    }

    /**
     * Handle the check box click event. If the list of selected fund already contains the provided fund,
     * it will be removed. Otherwise, it will be added to the list.
     * @param fund the new selected fund.
     */
    fundChecked(fund: FundLite): void {
        if (this.isSelected(fund)) //if exists then remove it
            this.selectedItems = this.selectedItems.filter((item: FundLite) => item.fdsId != fund.fdsId);
        else
            this.selectedItems.push(fund);

        this.enableNewFidButton();
    }

    /**
     * Enable the fund button
     */
    enableNewFidButton(): void {
        this.disabledNewFund = this.selectedItems.length > 0;
    }
    
    /**
     * An function which handle a fund search when the button search has been triggered.
     * @param page the page.
     */
    search(page): void {
        this.currentFundType = this.searchCriteria.type;
        this.searchCriteria.pageNum = page || 1;
        this.searchCriteria.brokerId = (this.searchCriteria.type == 'FE') ? this.brokerId : null;
        this.searchCriteria.onlyBroker = this.brokerStatus;
        this.busy = this.fundService.searchFund(this.searchCriteria).subscribe(pageItem => {
            this.page = pageItem;
            this.noResult = (!pageItem.content || pageItem.content.length < 1);
        }, e => { });

    }

    /** 
     * Clear the list of selected items and 
     * enable the new fond creation button.
    */
    clearSelection(): void {
        this.selectedItems = new Array<FundLite>();
        this.enableNewFidButton();
    }

    fundTypeChange(fundType: string): void {
        if(fundType !== this.currentFundType){
            this.initializeTable();
            if(!!this.searchCriteria.filter || this.isSpecificFeSearch(fundType, this.brokerStatus)){
                this.search(1);
            }
        } 
    }
    
    /** 
     * Clear the page, initialize the current selected type with 
     * the last fund search type and enable the new fond creation button.
    */
    initializeTable(): void {
        this.page = new Page();
        this.currentFundType = this.searchCriteria.type;
        this.clearSelection();
    }

    /**
     * Do a search if 'FE' type and the specific broker is selected. 
     * Otherwise, it initialize the table.
     * @param specificBroker true if the specific broker is selected.
     */
    brokerChange(specificBroker: boolean): void{
        this.initializeTable();
        if(this.isSpecificFeSearch(this.searchCriteria.type, specificBroker)) {
            this.search(1);
        }
    }

    /**
     * Indicates if it is a specific 'FE' search.
     * @param fundType a fund type.
     */
    isSpecificFeSearch(fundType: string, specificBroker: boolean): boolean {
        return fundType === FundType.FE.key && specificBroker;
    }

    ngOnDestroy(): void {
        if (this.busy)
            this.busy.unsubscribe();
    }

    /**
     * Open a new tab browser for a new creation of a new fund.
     * @param fundType  the fund type
     */
    createNewFund( subType:'FID'|'FAS'|'FE'|'FIC'): void {
        this.searchModal.hide();
        if (!this.disabled) {
            let initialState = {subType};
            this.createModal = this.modalService.show(ModalFundContent, { class: 'modal-xlg', ignoreBackdropClick: true, initialState });  // xlg is a custom size set to 1600px          
            this.createModal.content.onClose.take(1).subscribe(fund => {
                                            if(fund){
                                                this.selectedItems.push(fund);
                                                this.selectItemsChange.emit(this.selectedItems);
                                                setTimeout(() => this.clearSelection(), 0);
                                            }    
                                        }, e => {});
        }
    }

    toPricingDescription(pricingNumber: number) {        
        return this.uoptDetailService.toPricingLabel(pricingNumber, this.pricingFrequencies);
    }
}