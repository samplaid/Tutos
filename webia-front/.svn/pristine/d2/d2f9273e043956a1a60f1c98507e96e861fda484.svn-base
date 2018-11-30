import { Title } from '@angular/platform-browser';
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FullClient, ClientContactDetail, ClientContactType, GeneralNote, ClientClaimsDetail, Roles } from "../../../_models";
import { CountryService } from "../../../_services/country.service";
import { Subscription } from "rxjs/Subscription";
import { UoptDetailService } from "../../../_services/uopt-detail.service";
import { ActivatedRoute, Router, Params } from "@angular/router";
import { ClientService } from "../../client.service";
import { StateMode } from "../../../utils/mode";
import { Observable } from "rxjs/Observable";
import { MessageService, DateUtils, UserService } from "../../../utils";
import { ClientContactModel } from "../../components/contact/client-contact.model";
import { AbstractPersonComponent } from "../abstract-person.component";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ModalClientDeathNotify } from "../../index";
import { OptionDetailService } from '../../../_services';

@Component({
    selector: 'person-physical',
    templateUrl: 'person-physical.tpl.html',
    styles: [`
        .risky { color:blue; }
        .table-caption-left {
            position: relative;
            right: 10%;
        }
    `]
})

export class PersonPhysicalComponent extends AbstractPersonComponent implements OnInit {

    roles = Roles;
    clientRoles = [];
    constructor(protected countryService: CountryService,
                protected uoptDetailService: UoptDetailService,
                protected route: ActivatedRoute, 
                protected router: Router,
                protected clientService: ClientService,
                public messageService: MessageService,
                protected userService: UserService,
                protected titleService: Title,
                protected modalService: BsModalService,
                protected optionDetailService: OptionDetailService) {
        super(countryService, uoptDetailService, route, router, clientService, messageService,userService, titleService, optionDetailService);
        // titles
        this.subs.push(this.uoptDetailService.getTitles().subscribe(t => this.titles = t));
        // martial status
        this.subs.push(this.uoptDetailService.getMaritalStatus().subscribe(t => this.martialStatusList = t));
        // Function 
        this.subs.push(this.uoptDetailService.getClientProfessions().subscribe(t => this.professions = t));
        
        //getClientRoles
        this.clientService.getClientRoles(this._clientId).then(t => this.clientRoles = t);

    }

    clearMepDetails(event) {
        if (this.fc.mediaExposedPerson == "N") {
            this.fc.mepDetail = undefined;
        }
    }

    clearPepFunction(event) {
        if (this.fc.politicallyExposedPerson == "NO") {
            this.fc.pepFunction = undefined;
        }
    }

    clearRcaDetail(event) {
        if (this.fc.relativeCloseAssoc == "N") {
            this.fc.rcaDetail = undefined;
        }
    }

    clearInsiderTradingDetails(event) {
        if (this.fc.insiderTrading == "NO") {
            this.fc.insiderTradingDetails = undefined;
        }
    }

    findClient(id) {
        return super.findClient(id);
    }

    initClient() {
        // call the super method before.     
        super.initClient();
        this.fc.idNumber = new GeneralNote();
    }

    initLinkedObjectIfNull(client: FullClient) {
        // call the super method before.        
        if (!client.idNumber) {
            client.idNumber = new GeneralNote();
        }
        super.initLinkedObjectIfNull(client);
    }

    formatDates() {
        super.formatDates();
        if (this.fc.idExpiryDate) {
            this.fc.idExpiryDate = DateUtils.formatToIsoDate(new Date(this.fc.idExpiryDate));
        }
    }

    setNullIfWealinsNullDate(client: FullClient) {
        super.setNullIfWealinsNullDate(client);
        client.idExpiryDate = (DateUtils.WEALINS_NULL_DATE_ISO_DATE == client.idExpiryDate) ? undefined : client.idExpiryDate;
    }

    titleChoosen(titleCode) {
        if(titleCode === 'PP1'){
            this.fc.sex = 2;
        } else if(titleCode === 'PP2'){
            this.fc.sex = 3;
        }
    }

    notifyDeath(){
        this.clientService.canClientDeathBeNotified(this.fc.cliId).subscribe(res => {
            if (res==true)
                this.openDeathNotifyModal();
            else
                this.messageService.error("Death cannot be notify yet as some settlement have to be perfomed before.","Death cannot be notify", 'sm');
        }, (error)=>{});
    }

    openDeathNotifyModal(){
        let initialState = {client:this.fc};
        let deathNotifyModal = this.modalService.show(ModalClientDeathNotify, { initialState });

        deathNotifyModal.content.onClose.take(1).subscribe((ccd: ClientClaimsDetail) => {
            this.fc.deathClaim = ccd;
            this.fc.dead = true;
            // TODO : add a post process after death notification done
        })      
    }

    saveIt(){
        super.save().catch(e=>{})
    }

    public getTitleMode(): string {
        return "Client";
    }
    public getUpdateTitleMode(): string {
        return "Update Client";
    }
    public getUpdateClientSuccesMsg(): string {
        return "The client has been successfully updated.";
    }
    public getUpdateClientTitleSuccessMsg(): string {
        return this.getUpdateTitleMode();
    }
    public getCreationTitleMode(): string {
        return "Creation Client";
    }
    public getCreationClientSuccesMsg(): string {
        return "The new client has been successfully created.";
    }
    public getCreationClientTitleSuccessMsg(): string {
        return this.getCreationTitleMode();
    }
    public getPersonType(): number {
        return 1;
    }
    public doOnUpdateSuccess(client: FullClient, router: Router) {
       super.ngOnInit();
    }

    public doOnCreateSuccess(client: FullClient, router: Router) {
       super.ngOnInit();       
    }
    public getCreateDuplicateConfirmMessage(client: FullClient) {
        return "A client with name " + client.name + " and the date of birth " + DateUtils.formatToIsoDate(new Date(client.dateOfBirth)) + " exists already. Do you want to continue?";
    }
    public getCreateDuplicateConfirmMessageHeader() {
        return this.getCreationTitleMode();
    }
    public getUpdateDuplicateConfirmMessage(client: FullClient) {
        return this.getCreateDuplicateConfirmMessage(client);
    }
    public getUpdateDuplicateConfirmMessageHeader() {
        return this.getUpdateTitleMode();
    }

}
