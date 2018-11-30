import { Title } from '@angular/platform-browser';
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FullClient, ClientContactDetail, ClientContactType, ClientLinkedPerson, ClientStatusType, ClientLinkedPersonType, ClientLinkedPersonStatusType, clientClassifierRegExp, ClientLite,Roles } from "../../../_models";
import { CountryService, UoptDetailService, OptionDetailService } from "../../../_services";
import { Subscription } from "rxjs/Subscription";
import { ActivatedRoute, Router, Params } from "@angular/router";
import { ClientService } from "../../client.service";
import { Observable } from "rxjs/Observable";
import { MessageService, DateUtils, UserService } from "../../../utils";
import { AbstractPersonComponent } from "../abstract-person.component";
import { TinModel } from "../../components/tin/tin.model";

@Component({
    selector: 'person-moral',
    templateUrl: './person-moral.tpl.html',
    styles: [`
        .risky { color:blue; }
        .table-caption-left {
            position: relative;
            right: 10%;
        }
    `]
})

export class PersonMoralComponent extends AbstractPersonComponent implements OnInit {
    roles = Roles;
    tin: TinModel;
    showAdditionalPRC = false;
    controlingPersonName: string;
    clientRegXp= clientClassifierRegExp;
    crsStatusList: any[];
    exactStatusList: any[];
    typeOfControlList: any[];

    constructor(protected countryService: CountryService,
                protected uoptDetailService: UoptDetailService,
                protected route: ActivatedRoute,
                protected router: Router,
                protected clientService: ClientService,
                public messageService: MessageService,
                protected userService: UserService,
                protected titleService: Title,
                protected optionDetailService: OptionDetailService) {
        super(countryService, uoptDetailService, route, router, clientService, messageService,userService, titleService, optionDetailService);       
        this.subs.push(this.uoptDetailService.getEntityType().subscribe(t => this.titles = t));
        this.subs.push(this.uoptDetailService.getCrsStatus().subscribe(crsStatusList => this.crsStatusList = crsStatusList));
        this.subs.push(this.uoptDetailService.getCrsExactStatus().subscribe(exactStatusList => this.exactStatusList = exactStatusList));
        this.subs.push(this.uoptDetailService.getTypeOfControl().subscribe(typeOfControlList => this.typeOfControlList = typeOfControlList));

    }
   
    /**
     * This method is called after the finding of a client.
     * @param client the found client
     */
    subscribeClient(client){
        super.subscribeClient(client);
        let promises = [];  
        this.fc.controllingPersons.forEach( cp => {
            if(cp.controllingPerson) {
                promises.push(this.clientService.getClient(cp.controllingPerson).then(res => {
                    if(res){
                        cp.displayName = res.displayName;
                    }
                }));
            }
        });
        Promise.all(promises).then( (cp)=>  this.fc.controllingPersons = [...this.fc.controllingPersons]);
    }

    fiduciaryChange(event) {
        if (event.target.checked) {
            this.fc.fiduciary = 1;
        } else {
            this.fc.fiduciary = 0;
        }
    }

    addPRC(clientLite: ClientLite) {
        // check if the selected client already exists.
        if (clientLite) {
            let index = this.fc.personsRepresentingCompany.findIndex(o => !!o && o.cliId == clientLite.cliId);
            if (index < 0) {
                let newRepresent = new ClientLinkedPerson();                
                newRepresent.status = ClientStatusType.ENABLED;
                newRepresent.controllingPerson = clientLite.cliId;
                newRepresent.type = ClientLinkedPersonType[ClientLinkedPersonType.DIRECTOR];                                
                // we concat the array because the its reference is not changed and therefore the filter will not work.
                this.fc.personsRepresentingCompany = this.fc.personsRepresentingCompany.concat(newRepresent);
            } else {
                this.fc.personsRepresentingCompany[index].status = ClientStatusType.ENABLED;
                // we create a new reference for the array because the its reference is not changed and therefore the filter will not work.
                this.fc.personsRepresentingCompany = this.fc.personsRepresentingCompany.slice();
            }
        }
    }

    removePRC(prc: ClientLinkedPerson) {
        // when removing the client, set the linked person status to disabled.
        if (prc && this.fc.personsRepresentingCompany && this.fc.personsRepresentingCompany.length > 0) {
            let index = -1;
            let foundPrc = this.fc.personsRepresentingCompany.find((item, i)=> {
                if(item && prc && item.clpId == prc.clpId){
                    index = i;
                    return true;
                } else {
                    return false;
                }
            });
            
            if (foundPrc && foundPrc.clpId) {
                foundPrc.status = ClientStatusType.DISABLED;
                this.fc.personsRepresentingCompany = this.fc.personsRepresentingCompany.slice();
            } else if(index > -1) {
                this.fc.personsRepresentingCompany = this.fc.personsRepresentingCompany.splice(index,1);
            }           
        }
    }

    removeCP(cpRem: ClientLinkedPerson) {
        if(this.fc.controllingPersons && this.fc.controllingPersons.length > 0){
            let index = -1;
            let foundCp = this.fc.controllingPersons.find((cp, i) => {
                if(cp && cpRem && cp.clpId == cpRem.clpId){
                    index = i;
                    return true;
                } else {
                    return false;
                }
            });
            if(foundCp && foundCp.clpId) {                
                foundCp.status = ClientStatusType.DISABLED;
            } else if(index > -1){
                this.fc.controllingPersons.splice(index, 1);
            }        
        }        
    }

    select(items) {
        if(items && items.length > 0) {
            if (!this.fc.controllingPersons) {
                this.fc.controllingPersons = [];
            }                
            items.forEach((o: ClientLite) => {
                let index = this.fc.controllingPersons.findIndex(c => c && (c.cliId == o.cliId));
                if (index < 0) {
                    let controllingPerson = new ClientLinkedPerson();
                    controllingPerson.controllingPerson = o.cliId;
                    controllingPerson.status = ClientLinkedPersonStatusType.ACTIVE;
                    controllingPerson.type = ClientLinkedPersonType[ClientLinkedPersonType.CTRL];
                    controllingPerson.displayName = o.displayName;
                    this.fc.controllingPersons = this.fc.controllingPersons.concat(controllingPerson);
                // this.clientService.getClient(o.cliId).then(fullClient => this.fc.controllingPersons.push(fullClient));
                }
            });
        }        
    }

    checkChange(input) {
        //-- Active (=1), inactive=2
        this.fc['status'] = input.checked ? ClientStatusType.ENABLED : ClientStatusType.DISABLED;
    }

    initClient() {
        super.initClient();
        this.fc.personsRepresentingCompany = new Array<ClientLinkedPerson>();
        this.fc.controllingPersons = new Array<ClientLinkedPerson>();
    }

    initLinkedObjectIfNull(client: FullClient) {
        super.initLinkedObjectIfNull(client);
        if (!client.personsRepresentingCompany) {
            client.personsRepresentingCompany = new Array<ClientLinkedPerson>();
        }
        if (!client.controllingPersons) {
            client.controllingPersons = new Array<ClientLinkedPerson>();
        }
    }

    public saveIt() {
        if (this.fc.dateOfBirth) {
            this.fc.dateOfBirth = DateUtils.formatToIsoDate(new Date(this.fc.dateOfBirth));
        }
        return super.save().catch(e=>{});
    }

    public doOnUpdateSuccess(client: FullClient, router: Router) {
        super.ngOnInit();
        //router.navigateByUrl('/client/moral/' + client.cliId);
    }
    public doOnCreateSuccess(client: FullClient, router: Router) {
        super.ngOnInit();
        // router.navigateByUrl('/client/moral/' + client.cliId);
    }

    public getTitleMode(): string {
        return "Entity";
    }
    public getUpdateTitleMode(): string {
        return "Update Entity";
    }
    public getUpdateClientSuccesMsg(): string {
        return "The entity has been successfully updated.";
    }
    public getUpdateClientTitleSuccessMsg(): string {
        return "Update Entity";
    }
    public getCreationTitleMode(): string {
        return "Creation Entity";
    }
    public getCreationClientSuccesMsg(): string {
        return "The new entity has been successfully created.";
    }
    public getCreationClientTitleSuccessMsg(): string {
        return "Creation Entity";
    }
    public getPersonType(): number {
        return 3;
    }

    public getCreateDuplicateConfirmMessage(client: FullClient) {
        return this.getUpdateDuplicateConfirmMessage(client);
    }
    public getCreateDuplicateConfirmMessageHeader() {
        return this.getCreationTitleMode();
    }
    public getUpdateDuplicateConfirmMessage(client: FullClient) {
        return "An entity with name " + client.name + " and the date of birth " + DateUtils.formatToIsoDate(new Date(client.dateOfBirth)) + " exists already. Do you want to continue?";
    }
    public getUpdateDuplicateConfirmMessageHeader() {
        return this.getUpdateTitleMode();
    }

    get specialCrsStatusClassName() {
        return this.isSpecialCrsStatus ? 'animated fadeIn' : 'animated fadeOut' ;
    }
    get bfinndclCrsStatusClassName() {
        return this.isBFINNDCLCrsStatus ? 'animated fadeIn' : 'animated fadeOut' ;
    }
    get isBFINNDCLCrsStatus(){
        return (this.fc.crsStatus == 'BFINNDCL');
    }
    get isSpecialCrsStatus(){
        return (this.fc.crsStatus == 'CFINHCRS' || this.fc.crsStatus == 'ENFINPAS' || this.fc.crsStatus == 'GTRUST');
    }
}