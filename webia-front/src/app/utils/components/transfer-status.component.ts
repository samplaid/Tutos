import { Component, OnInit, Input } from '@angular/core';
import { statusAnimation } from '../../workflow/models/animations';
import { transferStatus } from '../../_models';
import { TransferForm } from '../../withdrawal/models/transfer';
import { DocumentService } from '../services/document.service';

@Component({
    selector: 'transfer-status',
    template: `
        <ng-container [ngSwitch]="transfer.transferStatus">
        <span *ngSwitchCase="transferStatus.NEW" class="label label-default label-status" [@statusAnimation]>New</span>
        <span *ngSwitchCase="transferStatus.READY" class="label label-primary label-status" [@statusAnimation]>Ready</span>
        <span *ngSwitchCase="transferStatus.COMPTA" class="label label-default label-status" [@statusAnimation]>Compta</span>
        <span *ngSwitchCase="transferStatus.ACCEPTED" class="label label-success label-status" [@statusAnimation]>Accepted</span>
        <span *ngSwitchCase="transferStatus.EXECUTED" class="label label-success label-status" [@statusAnimation] >
            Executed
            <i *ngIf="!downloadInProgress && !!transfer.editingId" class="fa fa-file clickable" aria-hidden="true"  [tooltip]="'Download file'" (click)="download()"></i>
            <i *ngIf="downloadInProgress" class="fa fa-spinner fa-spin" aria-hidden="true"></i>
        </span>
        <span *ngSwitchCase="transferStatus.REFUSED" class="label label-danger label-status" [@statusAnimation] [tooltip]="'Rejected by ' + transfer.userCompta + ' : ' + transfer.rejectComment" >
            Refused<i class="fa fa-envelope" aria-hidden="true"></i>
        </span>
        
        <span *ngSwitchDefault class="label label-default label-status" [@statusAnimation]>{{transfer.transferStatus}}</span>        
        </ng-container>
    `,
    styles:[`
        .label-status {
            font-size: 11px;
        }
        .clickable {
            cursor: pointer;
        }
        .reject-title {
            font-weight: bold;
            font-size: 0.7em;
        }
        .reject-content {
            font-size: 0.7em;
            word-wrap: break-word;
        }
        .popover-div {

        }
        .popover-container {
            font-size: 0.7em;
        }
    `],
    animations: [statusAnimation]
})
export class TransferStatusComponent implements OnInit {

    @Input()
    transfer: TransferForm;

    readonly transferStatus = transferStatus;

    downloadInProgress: boolean;

    constructor(private documentService: DocumentService) {}

    ngOnInit(): void { }

    download(): void {
        this.downloadInProgress = true;
        this.documentService.getDocument(this.transfer.editingId)
        .finally(() => {
            this.downloadInProgress = false;
        }).subscribe(result => {});
    }    
}