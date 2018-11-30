import { Component, OnInit, Input, EventEmitter, Output, OnDestroy, SimpleChanges } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ClientService, ClientSearchCriteria } from "../client.service";
import { Page, ClientLite, ClientStatusType, ClientType, clientClassifierRegExp } from "../../_models";
import { DateUtils } from "../../utils";
import { ModalClientContent } from './../modal/modal-client-content.component';
import { ModalClientSearch } from './../modal/modal-client-search.component';
import * as _ from "lodash";

const EMPTY: string = '';
const SPACE: string = ' ';

/**
 * This class represents the client search component.
 * It display a modal that will contains a list of the client.
 */
@Component({
    selector: 'search-client',
    templateUrl: './search-client.tpl.html',
    styleUrls: ['search-client.style.css']
})
export class SearchClientComponent implements OnInit, OnDestroy {
    
    _labelFn: Function;
    _label: string;
    _title: string;
    _clientId: number;
    _sub;
    _href;
    private _searchModal: BsModalRef;
    private _createModal;

    @Output() clientIdChange = new EventEmitter<any>();
    @Output() onChange = new EventEmitter<any>();
    @Output() onClear = new EventEmitter<any>();
    
    @Input() allowClear: boolean = true;
    @Input() disabled: boolean = false;
    @Input() required: boolean = false;
    @Input() css;
    @Input() type: number;
    @Input() includeDeceased: boolean=true;

    @Input() set labelFn(labelFn: Function) {
        this._labelFn = labelFn;
    }

    @Input() set clientId(value: number) {
        this._clientId = value;
        this.clientIdChange.emit(value);
    }
    get clientId() {
       return this._clientId;
    }

    constructor(protected modalService: BsModalService, protected clientService: ClientService) {

    }

    /**
     * Set the client label with the value in parameter.
     * In case of the a function callback represented by the input <code>labelFn</code> 
     * is given, then it will override the value in parameter and use the function in prior.
     * @param value the value to set.
     */
    protected setclientLabel(client: ClientLite) {        
        if(!client || _.isEmpty(client)) {
            this._label = '';
        } else {
            this._href = ((client.type == ClientType.MORAL)? "./#/client/moral/"+ client.cliId : "./#/client/physical/"+ client.cliId);
            if (client.displayName){
                this._label = client.displayName.replace(clientClassifierRegExp," <span class='text-danger'>$1</span>") + " - <small><i>"+client.cliId+"</i></small>";
            } else
                this._label = "";
            if (this._labelFn)
                this._label = this._labelFn.call(null, client.cliId);
        }
        this._title = this._label.replace(/<[^>]+>/gm, '');        
    }

    /**
     * An override function from angular.
     */
    ngOnInit() {
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes.clientId && changes.clientId.currentValue){
            this.clientService.getClientLight(changes.clientId.currentValue).then((client: ClientLite) =>  this.select(client));
        }
    }

    /**
     * An event handler which open a modal to display a content.
     */
    modalOpen(): void {
        if (!this.disabled) {
            let initialState = { 
                type: this.type,
                includeDeceased: this.includeDeceased
            };
            this._searchModal = this.modalService.show(ModalClientSearch, { class: 'modal-lg',  initialState});
            this._searchModal.content.onClose.take(1).subscribe((client: ClientLite) => {
                this.select(client);      
            }, (reason) => {
                if (reason > 0){
                    this.createClient(reason);
                }
            });
        }
    }

    tryOpen(){
        if (!this.disabled && !this.clientId && (!this._label ||  this._label=='') ) {
            this.modalOpen();
        }
    } 

    /**
     * An function which handle a select on the table in a modal.
     * @param client the selected handle.
     */
    select(client: ClientLite): void {        
        if (client && !_.isEmpty(client)) {
            this.clientId = client.cliId;
            this.setclientLabel(client);
            this.onChange.emit(client);
        } else {
            this.clientId = null;
            this.setclientLabel(null);
            this.onChange.emit(null);
        }        
    } 
   
    ngOnDestroy(): void {
        if (this._sub)
            this._sub.unsubscribe();
    }

    clear() {
        if (this.allowClear){
            this.clientId = null;
            this.setclientLabel(null);
            this.onChange.emit(null);
            this.onClear.emit(null);
        }
    }

    /**
     * Open a new tab browser for a new creation of a new fund.
     * @param fundType  the fund type
     */
    createClient(type: number) {
        switch (type) {
            case ClientType.PHYSICAL:
                this.modalCreationOpen(ModalClientContent, ClientType.PHYSICAL);
                break;
            case ClientType.MORAL:
                this.modalCreationOpen(ModalClientContent, ClientType.MORAL);
                break;
            default:
                break;
        }
    }

    modalCreationOpen(content, clientType: number): void {
        if (!this.disabled) {
            let initialState = { 
                clientType: clientType, 
                cliId: null 
            };
            this._createModal = this.modalService.show(content, {class : 'modal-xlg', initialState });  // xlg is a custom size set to 1600px
            this._createModal.content.onClose.take(1).subscribe(clientId => this.clientId = clientId, error => {});
        }
    }

}

