import { Title } from '@angular/platform-browser';
import { EventEmitter, Output, Input, OnInit } from '@angular/core';
import { FullClient, ClientContactDetail, ClientContactType, GeneralNote, ClientUrlSearchParams, Roles, EntityType } from "../../_models";
import { Params, ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs/Subscription";
import { CountryService, UoptDetailService, OptionDetailService } from "../../_services";
import { ClientService } from "../client.service";
import { MessageService, StateMode, DateUtils, UserService } from "../../utils";
import { Observable } from "rxjs/Observable";
import { ClientContactModel } from "../components/contact/client-contact.model";
import { OptionDetail } from '../../_models/option';

export abstract class AbstractPersonComponent implements OnInit {

    ownerType =  EntityType.CLIENT;

    _clientId;
    // use for country prefix
    _correspPtCode: string;
    _corresp1PtCode: string;
    _cliCtry: any;

    mode = StateMode.readonly;
    StateMode = StateMode;
    titleMode: string;
    canSave:boolean = false;
    fc: FullClient;
    params: Params;
    inModal:boolean = false;

    // ----------- drop down list data-----------
    allCountries: any[];
    accountStatus: OptionDetail[];    
    circularLetters: any[];
    riskClassList: any[];
    titles: any[];
    professions: any[];
    profiles: any[];
    sectors: any[];
    martialStatusList: any[];
    languages: any[];
    test: any;

    // ------------ Async variables -------------
    subs: Array<Subscription> = [];
    busy: any;

    // ------------ Roles variables -------------
    hasEditRole: boolean;
    hasComplianceEditRole: boolean;


    @Output() clientIdChange = new EventEmitter<any>();
    @Input() showInModal: boolean = true;

    @Input() set clientId(clientId) {
        this.inModal = true;
        this._clientId = clientId;
        this.clientIdChange.emit(this._clientId);
    }

    get clientId() {
        return this._clientId;
    }

    public abstract getTitleMode(): string;
    public abstract getUpdateTitleMode(): string;
    public abstract getUpdateClientSuccesMsg(): string;
    public abstract getUpdateClientTitleSuccessMsg(): string;
    public abstract getCreationTitleMode(): string;
    public abstract getCreationClientSuccesMsg(): string;
    public abstract getCreationClientTitleSuccessMsg(): string;
    public abstract getPersonType(): number;
    public abstract doOnUpdateSuccess(client: FullClient, router: Router);
    public abstract doOnCreateSuccess(client: FullClient, router: Router);
    public abstract getCreateDuplicateConfirmMessage(client: FullClient);
    public abstract getCreateDuplicateConfirmMessageHeader();
    public abstract getUpdateDuplicateConfirmMessage(client: FullClient);
    public abstract getUpdateDuplicateConfirmMessageHeader();

    constructor(protected countryService: CountryService,
                protected uoptDetailService: UoptDetailService,
                protected route: ActivatedRoute, 
                protected router: Router,
                protected clientService: ClientService,
                protected messageService: MessageService,
                protected userService: UserService,
                protected titleService: Title,
                protected optionDetailService: OptionDetailService) {
                    

        // risk class
        this.subs.push(this.uoptDetailService.getFundClassifications().subscribe(t => this.riskClassList = t));
        // circular letters
        this.subs.push(this.uoptDetailService.getCircularLetters().subscribe(t => this.circularLetters = t));

        // investment risk
        this.subs.push(this.uoptDetailService.getClientProfiles().subscribe(t => this.profiles = t));
        // Sector
        this.subs.push(this.uoptDetailService.getClientActivitySectors().subscribe(t => this.sectors = t));
        // language
        this.subs.push(this.uoptDetailService.getLanguages().subscribe(t => this.languages = t));
        // load countries
        this.subs.push(this.countryService.loadAllCountries().subscribe(results => this.allCountries = results));
        // load account status
        this.subs.push(this.optionDetailService.getAccountStatus().subscribe(results => this.accountStatus = results));

        // Call from browser: find client if id is provided. Otherwise, initialize it.
        this.route.params.subscribe((urlParams: Params) => {
            if (urlParams)
                this._clientId = urlParams['id'];
        });

    }

    doUpdate() {
        return new Promise((success, error) => {
            this.busy = this.clientService.update(this.fc).subscribe(res => {
                if (res) {
                    this.initLinkedObjectIfNull(res);
                    this.fc = res;
                    this._clientId = this.fc.cliId;
                    this.doOnUpdateSuccess(this.fc, this.router);
                    //this.messageService.success(this.getUpdateClientSuccesMsg(), this.getUpdateClientTitleSuccessMsg(), "sm");
                    this.messageService.addAlertSuccess(this.getUpdateClientSuccesMsg(),true,'clientCpt');
                    success(res);
                }
            }, e => { error(e)});
        })
    }

    doCreate() {
        return new Promise((success, error) => {
            this.busy = this.clientService.create(this.fc).subscribe(res => {
                if (res) {
                    this.initLinkedObjectIfNull(res);
                    this.fc = res;
                    this._clientId = this.fc.cliId;
                    this.doOnCreateSuccess(this.fc, this.router);
                    //this.messageService.success(this.getCreationClientSuccesMsg(), this.getCreationClientTitleSuccessMsg(), "sm");
                    this.messageService.addAlertSuccess(this.getCreationClientSuccesMsg(),true,'clientCpt');
                    success(res);
                }
            }, e => { error(e)});
        })
    }

    doSave(client:FullClient, params: ClientUrlSearchParams, confirmMessage:(client:FullClient)=>string, confirmMessageHeader: ()=>string, executor: ()=> Promise<any>) {
        return new Promise(
            (success, error) => this.clientService.matchClient(params)
                .then(
                    (values: any[]) => {
                        if (values && values.length > 0) {
                            this.messageService.confirm(confirmMessage.call(this, client), confirmMessageHeader.call(this), "sm")
                                .subscribe(confirmed => {
                                    if (confirmed) {
                                        executor.call(this).then((client) => success(client), e => error(e));
                                    }
                                });
                        } else {
                            executor.call(this).then((client) => success(client), e => error(e));
                        }
                    }, e => error(e)
                )
        );
    }

    ngOnDestroy() {
        this.subs.forEach((sub: Subscription) => sub && !sub.closed && sub.unsubscribe());
    }

    ngOnInit(): void {
        this.initClient();
        // Call from other component like modal: 
        // find client with the provided id. Otherwise, initialize it.        
        this.subs.push(this.findClient(this._clientId).subscribe(client => this.subscribeClient(client) ));
        this.hasEditRole = this.userService.hasRole(Roles.CLIENT_EDIT);
        this.hasComplianceEditRole = this.userService.hasRole(Roles.CLIENT_COMPLIANCE_EDIT);
    }

    subscribeClient(client){
        if (client) {
            this.fc = client;
            // this avoid the undefined error in front end. 
            // In all case the dirty data will be removed during the save
            this.initLinkedObjectIfNull(this.fc);
            this.setMode();            
            this.setNullIfWealinsNullDate(this.fc);
            this.initDeathClaim(this.fc);
            console.log(this.fc);
        }
    }

    initDeathClaim(client: FullClient) {
        if (client.clientClaimsDetails && client.clientClaimsDetails.length === 1) {
            client.deathClaim = client.clientClaimsDetails[0];
        }
    }

    setNullIfWealinsNullDate(client:FullClient){
        client.dateOfBirth=(DateUtils.WEALINS_NULL_DATE_ISO_DATE==client.dateOfBirth) ? undefined: client.dateOfBirth;
        client.dateOfRevision=(DateUtils.WEALINS_NULL_DATE_ISO_DATE==client.dateOfRevision) ? undefined: client.dateOfRevision;
    }

    findClient(id) {
        if (id) {
            return Observable.fromPromise(this.clientService.getClient(id));
        }
        return Observable.empty();
    }

    initClient() {     
        //--------------------------
        this.fc = new FullClient();
        // Individual person
        this.fc.type = this.getPersonType();
        this.fc.status = 1;
        this.fc.homeAddress = new ClientContactDetail();
        this.fc.homeAddress.contactType = ClientContactType[ClientContactType.CORRESP];
        this.fc.correspondenceAddress = new ClientContactDetail();
        this.fc.correspondenceAddress.contactType = ClientContactType[ClientContactType.CORRESP1];
        this.fc.clientNote = new GeneralNote();
        this.setMode();
    }

    setMode(){     
       if (this.userService.hasRole(Roles.CLIENT_EDIT)){
           if (this._clientId){
                this.mode = StateMode.update;
           }else{
                this.mode = StateMode.create;
           }
           this.allowCanSave();
       } else {
           this.mode = StateMode.readonly;
       }
       this.setTitle();
    }

    allowCanSave(){
        this.canSave = true;
    }

    setTitle() {
        let newTitle = this.fc.type == 3?"Creation entity":"Creation client";
        if (this.mode == StateMode.update){
            this.titleMode = this.getUpdateTitleMode();
            newTitle = (this.fc.type == 3 ? "Entity - ": "Client - ") + this.fc.cliId;
        } else if (this.mode == StateMode.readonly){
            this.titleMode = this.getTitleMode();
            newTitle = this.fc.type == 3? "Entity": "Client";
        } else { 
            this.titleMode = this.getCreationTitleMode();
            newTitle = (this.fc.type == 3 ? "new Entity ": "new Client");
        }  
        if (!this.inModal)
            this.titleService.setTitle( newTitle );
    }

    initLinkedObjectIfNull(client: FullClient) {
        if (!client.homeAddress) {
            client.homeAddress = new ClientContactDetail();
        }
        if (!client.correspondenceAddress) {
            client.correspondenceAddress = new ClientContactDetail();
        }
        if (!client.clientNote) {
            client.clientNote = new GeneralNote();
        }
    }

    formatDates() {
        if (this.fc.dateOfBirth) {
            this.fc.dateOfBirth = DateUtils.formatToIsoDate(new Date(this.fc.dateOfBirth));
        }
        if (this.fc.dateOfRevision) {
            this.fc.dateOfRevision = DateUtils.formatToIsoDate(new Date(this.fc.dateOfRevision));
        }
    }

    clearClassificationDetails(event) {
        if (this.fc.classification == "NO") {
            this.fc.classificationDetails = undefined;
        }
    }

    public save() {
        this.formatDates();
        if (this.canSave && this.fc){
            if (this.mode != StateMode.create && this.fc.cliId) {
                let params = { name: this.fc.name, birthday: this.fc.dateOfBirth, exclude: "" + this.fc.cliId };
                return this.doSave(this.fc, params, this.getUpdateDuplicateConfirmMessage, this.getUpdateDuplicateConfirmMessageHeader, this.doUpdate);
            } else if (this.mode == StateMode.create) {
                let params = { name: this.fc.name, birthday: this.fc.dateOfBirth};
                return this.doSave(this.fc, params, this.getCreateDuplicateConfirmMessage, this.getCreateDuplicateConfirmMessageHeader, this.doCreate);           
            }
        }
    }

    public refresh() {
        this.ngOnInit();
    }

}