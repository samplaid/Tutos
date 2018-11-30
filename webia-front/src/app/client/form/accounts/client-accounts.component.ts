import { Title } from '@angular/platform-browser';
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FullClient, ClientContactDetail, ClientContactType, GeneralNote, ClientClaimsDetail, Roles, ClientAccount, accountClosedStatus, accountSuspendedStatus, accountActiveStatus } from "../../../_models";
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
import { OptionDetail } from '../../../_models/option';

@Component({
    selector: 'client-accounts',
    templateUrl: 'client-accounts.tpl.html'
})

export class AccountsComponent implements OnInit {

    @Input() accounts: ClientAccount[] = [];
    @Input() status: OptionDetail[] = [];
    @Input() hasEditRoles: boolean;
    readonly accountActiveStatus = accountActiveStatus;
    readonly accountSuspendedStatus = accountSuspendedStatus;
    
    ngOnInit(): void {

    }

    suspend(account : ClientAccount): void {
        account.status = accountSuspendedStatus;
    }

    activate(account : ClientAccount): void {
        account.status = accountActiveStatus;
    }
}
