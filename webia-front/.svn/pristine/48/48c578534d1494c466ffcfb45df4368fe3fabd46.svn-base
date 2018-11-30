import { NgModule } from '@angular/core';

import { OperationEntryComponent } from './operation-entry.component';
import { FormsModule } from "@angular/forms";
import { HttpModule } from "@angular/http";

import { busyConfig } from "../utils/index";
import { CommonModule } from "@angular/common";
import { NgBusyModule } from "ng-busy";
import { PaginationModule } from "ngx-bootstrap";
import { PolicyService } from '../policy/services/policy.service';
import { OperationEntryService } from '../_services';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        HttpModule, 
        NgBusyModule.forRoot(busyConfig),
        PaginationModule.forRoot(),
    ],
    exports: [OperationEntryComponent],
    declarations: [OperationEntryComponent],
    providers: [OperationEntryService, PolicyService],
})

export class OperationEntryModule { }
