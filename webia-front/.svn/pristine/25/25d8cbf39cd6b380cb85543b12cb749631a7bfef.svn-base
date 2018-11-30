import { Component, OnInit, Input } from '@angular/core';
import {  BsModalRef } from 'ngx-bootstrap/modal';
import { Page, ClientStatusType, ClientLite, defaultPageSize, Roles, ClientType } from './../../_models';
import { ClientService, ClientSearchCriteria } from './../client.service';
import { DateUtils, UserService, StateMode } from "../../utils";
import { clientClassifierRegExp } from "../../_models/constant";
import { Subject } from "rxjs/Subject";

@Component({
    selector: 'modal-client-search',
    template: `
    <div>
        <div class="modal-header">
            <button type="button" class="close pull-right" aria-label="Close" (click)="dismiss(0)"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Search {{_header}}</h4>
        </div>
        <div class="modal-body">
            <form (ngSubmit)="search(1)" #searchForm="ngForm">
                <label class="col-xs-2 text-right marginTop">Name / Code</label>
                <div class="col-xs-3">
                    <input type="text" id="searchTxt" name="searchTxt" class="input-sm form-control" [(ngModel)]="_searchCriteria.filter" [autofocus]>
                </div>
                <label class="col-xs-2 text-right marginTop">Date of birth</label>
                <div class="col-xs-3">
                     <datepicker [(date)]="_date"></datepicker>
                </div>               
                <button class="btn btn-primary btn-xs" [disabled]="!_searchCriteria.filter && !_date" type="submit">
                    <span aria-hidden="true" class="glyphicon glyphicon-search"></span> 
                    Search
                </button>
                <div class="pull-right" *ngIf="_page.totalRecordCount > 0">{{_page.totalRecordCount}} Result(s)</div>
            </form>
            <div class="line-break"></div>
            <div class="relative">
              <div [ngBusy]="[_sub]">
                <table class="table dataTable table-striped table-hover nomargin fullwidth" role="grid" *ngIf="_page.content">
                    <thead>
                        <tr role="row">
                            <th>Code</th>
                            <th>Name</th>
                            <th>Address</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr *ngFor="let client of _page.content" (click)="select(client)" (dblclick)="valide(_selected)" [ngClass]="{'selected' : client?.cliId==_selected?.cliId}">
                            <td>{{client.cliId }}</td>
                            <!--<td>{{client.name | capitalize}} & {{client.firstName}} ({{ client?.dateOfBirth | date:'dd/MM/yyyy'}})</td>-->
                            <td>
                              <span highlight="text-danger" [pattern]="clientRegXp">{{ client?.displayName }}</span>
                            </td>
                            <td>{{client | address}}</td>
                        </tr>
                        <tr *ngIf="_noResult">
                            <td colspan="3" class="text-center">
                                <p style="padding: 300px">No result found </p>
                            </td>
                        </tr>
                    </tbody>
                    <tfoot *ngIf="_page.totalPageCount>1">
                        <tr>
                            <td colspan="10" class="text-center">
                            <!--<ngb-pagination *ngIf="_page.totalPageCount > 1" class="text-center" (pageChange)="search($event)" [collectionSize]="_page.totalRecordCount"
                    [           pageSize]="_page.pageSize" [(page)]="_page.currentPage" [maxSize]="10" [ellipses]="true" [boundaryLinks]="true"></ngb-pagination>-->

                            <pagination  class="text-center" [boundaryLinks]="true" 
                                [totalItems]="_page.totalRecordCount" [itemsPerPage]="_page.pageSize" [(ngModel)]="_page.currentPage" (pageChanged)="search($event.page)" [maxSize]="10"
                                previousText="&lsaquo;" nextText="&rsaquo;" firstText="&laquo;" lastText="&raquo;"></pagination>
                            </td>
                        </tr>
                    </tfoot> 
                </table>


              </div>
            </div>
        </div>
        <div class="modal-footer">
            <div class="row">
                 <div class="col-md-6 modal-footer-left" *ngIf="mode != stateMode.readonly">
                    <button *ngIf="isPhysicalClientType(type)" type="button" class="btn btn-xs btn-primary text-left" [disabled]="!(_noResult || (_page && _page.content && _page.content.length>0))" (click)="dismiss(1)">New Client</button>
                    <button *ngIf="isMoralClientType(type)" type="button" class="btn btn-xs btn-primary text-left" [disabled]="!(_noResult || (_page && _page.content && _page.content.length>0))" (click)="dismiss(3)">New Entity</button> 
                 </div>
                 <div [ngClass]="{'col-md-12' : (mode == stateMode.readonly), 'col-md-6': (mode != stateMode.readonly)}" class="modal-footer-rigth">
                    <button type="button" class="btn btn-xs btn-primary" [disabled]="!_selected" (click)="valide(_selected)">Validate</button>
                 </div>
                
            </div>
            
        </div>
    </div>
    `,
    styles : [`
        .selected {
            border : solid 2px blue;
        }

        .modal-body {
            min-height : 800px;
        }

        .modal-footer-left{
            text-align:left!important;
        }
        .modal-footer-right{
            text-align:right!important;
        }
    `]
})
export class ModalClientSearch implements OnInit {
    
    _header = "Client";
    _searchCriteria: ClientSearchCriteria;
    _page: Page;
    _noResult: boolean;
    _date;
    _sub;
    _selected: ClientLite;
    clientRegXp = clientClassifierRegExp;
    mode = StateMode.readonly;
    stateMode = StateMode;

    @Input() type: number;
    @Input() includeDeceased: boolean;

    public onClose: Subject<any>;

    constructor(public activeModal: BsModalRef, protected clientService: ClientService, protected userService: UserService) {
     }

    ngOnInit() { 
        this.onClose = new Subject();
        this._searchCriteria = new ClientSearchCriteria();
        this._searchCriteria.pageNum = 1;
        this._searchCriteria.pageSize = defaultPageSize;
        this._page = new Page();
        this. setMode();
    }

    setMode(){
        if(this.userService.hasRole(Roles.CLIENT_EDIT)) {
            this.mode = StateMode.edit
        } else {
            this.mode = StateMode.readonly;
        }
    }

        /**
     * An function which handle a client search when the button search has been triggered.
     * @param page the page.
     */
    search(page): void {
        this._searchCriteria.pageNum = page || 1;
        this._searchCriteria.includeDeceased = this.includeDeceased;

        if (this._date && typeof this._date.getDate =='function')
            this._searchCriteria.date = DateUtils.formatToIsoDate(this._date);
        else
            this._searchCriteria.date = null;
        
        if(this.type)
            this._searchCriteria.type = this.type;
        else 
            this._searchCriteria.type = undefined;
        

        this._searchCriteria.status = ClientStatusType.ENABLED;
        
        this._sub = this.clientService.searchClient(this._searchCriteria).subscribe(pageItem => {
            this._page = pageItem;
            this._noResult = (!pageItem.content || pageItem.content.length < 1);
        }, e => { });
    }

    /**
     * An function which handle a select on the table in a modal.
     * @param client the selected handle.
     */
    select(client: ClientLite): void {
        if (client) {
            this._selected = client;
        }
    } 

    close(){
        this._page = new Page();
        this._searchCriteria.date = null;
        this._searchCriteria.filter = null;    
    }

    valide(client: ClientLite){
        this.onClose.next(client);
        this.activeModal.hide();
    }

    dismiss(val:number){
        this.onClose.error(val);
        this.activeModal.hide();
    }

    isPhysicalClientType(type: number): boolean {
        return !!type ? type == ClientType.PHYSICAL : true;
    }

    isMoralClientType(type: number): boolean {
        return !!type ? type == ClientType.MORAL : true;
    }
}