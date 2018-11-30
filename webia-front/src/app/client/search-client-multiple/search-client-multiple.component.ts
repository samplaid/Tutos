import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ClientService, ClientSearchCriteria } from "../client.service";
import { SearchClientComponent } from "../search-client/search-client.component";
import { DatePipe } from "@angular/common";
import { DateUtils } from "../../utils/date-utils";
import { ClientLite, Page, defaultPageSize, ClientStatusType, clientClassifierRegExp, Roles, ClientType } from "../../_models";
import { ModalClientContent } from '../modal/modal-client-content.component';

@Component({
    selector: 'search-client-multiple',
    templateUrl: './search-client-multiple.tpl.html',
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
export class SearchClientMultipleComponent implements OnInit {
    
    protected _header = "Client";
    protected _sub;
    protected _searchCriteria: ClientSearchCriteria;
    protected _page: Page;
    protected _noResult: boolean;
    protected _selectedRowIndex: number;
    protected _date;
    protected _selectedItems: Array<ClientLite> = [];
    _createModal:any;
    public selectModal: BsModalRef;
    clientRegXp = clientClassifierRegExp;
    appRoles = Roles;

    @Input() disabled: boolean = false;
    @Input() required: boolean = false;
    @Input() css;
    @Input() buttonCaption: string;
    @Input() type: number;

    @Output() selectItemsChange = new EventEmitter<Array<ClientLite>>();

    

    constructor(protected modalService: BsModalService, protected clientService: ClientService) {
        this._searchCriteria = new ClientSearchCriteria();
        this._searchCriteria.status = ClientStatusType.ENABLED;
        this._searchCriteria.pageNum = 1;
        this._searchCriteria.pageSize = defaultPageSize;
        this._page = new Page();
     }

    ngOnInit() { }
    ngOnDestroy(): void {
        if (this._sub)
            this._sub.unsubscribe();
    }
   
    /**
     * Check if the client is belong to the selected client list.
     * @param client the fund to be checked.
     */
    isSelected(client): boolean {
        return (this._selectedItems.findIndex((o) => o.cliId == client.cliId) > -1);
    }

    /**
     * Handle the check box click event. If the selected client list contains already the new selected client,
     * it will be removed. Otherwise, it will be added to the list.
     * @param fund the new selected fund.
     */
    select(client: ClientLite): void {
        if (this.isSelected(client)) //if exists then remove it
            this._selectedItems = this._selectedItems.filter(o => o.cliId != client.cliId);
        else
            this._selectedItems.push(client);

        //this.enableNewFidButton();
    }

   
    addToStack() {
        // Now emit the items to the parent.
        this.selectItemsChange.emit(this._selectedItems);
        this.clearSelection();
    }

     clearSelection(){
        // Once added, uncheck items
        this._selectedItems = new Array<ClientLite>();
        //this.enableNewFidButton();
    }

    /**
     * An function which handle a client search when the button search has been triggered.
     * @param page the page.
     */
    search(page): void {
        this._searchCriteria.pageNum = page || 1;       
        this._searchCriteria.date = (this._date && typeof this._date.getDate =='function') ? DateUtils.formatToIsoDate(this._date): null;       
        this._searchCriteria.type = (this.type) ? this.type: undefined;
        
        this._sub = this.clientService.searchClient(this._searchCriteria).map((p:Page) => {
            if(this.type) {
                p.content = p.content.filter((c:ClientLite)=> c.type == this.type);
            } 
            return p;
        }).subscribe(pageItem => {
            this._page = pageItem;
            this._noResult = (!pageItem.content || pageItem.content.length < 1);
        }, e => { });
    }

    /**
     * An event handler which open a modal to display a content.
     * @param content the content to display
     */
    modalOpen(content): void {
        if (!this.disabled) {
            this.selectModal = this.modalService.show(content, { class: 'modal-lg' });
        }
    }

    valide(selectedItems){
        this.selectItemsChange.emit(selectedItems);  
        this.clearSelection();
        this.selectModal.hide();
    }

    /**
     * Open a new tab browser for a new creation of a new fund.
     * @param fundType  the fund type
     */
    createClient(type:number) {
        this.modalCreationOpen(ModalClientContent, ClientType.PHYSICAL);
    }

    modalCreationOpen(content, clientType: number): void {
        if (!this.disabled) {
            let initialState = {clientType};
            this._createModal = this.modalService.show(content, {class : 'modal-xlg', initialState });  // xlg is a custom size set to 1600px

            this._createModal.content.onClose.take(1).subscribe(clientId => {
                                            //TODO : get the Client created, close the modal and add to the parent
                                        this.clientService.getClientLight(clientId).then(client => {
                                            this._selectedItems.push(client);
                                            this.selectItemsChange.emit(this._selectedItems);  
                                            this.clearSelection();   
                                        });                                            
                                    }
                                        , error => {});
        }
    }

}