
import { Component, Input, OnInit, OnDestroy, ViewContainerRef, Output, EventEmitter } from '@angular/core';
import { DateUtils, MessageService } from '../../../utils';
import { TransactionsHistoryDetails, Transaction, TransactionsCommissions } from '../../../_models/transaction';
import { EditingService } from '../../../_services/editing.service';


@Component({
    selector: 'transaction-details-main',
    templateUrl: './transaction-details-main.tpl.html',
})
export class TransactionDetailsMainComponent implements OnInit, OnDestroy {

    constructor(private editingService: EditingService, private messageService: MessageService) {
    }

    ngOnInit(): void {
    }

    ngOnDestroy(): void {
    }

    closeTransactionsDetailsHistoy() {
    }

    @Input() commission: TransactionsCommissions;
    @Input() detailsIn: TransactionsHistoryDetails[] = [];
    @Input() detailsOut: TransactionsHistoryDetails[] = [];
    @Input() transaction: Transaction = null;
    @Input() policy: string = "";
    @Input() switch: boolean = false;
    @Input() commissionToDisplay: boolean = false;
    loading: boolean;

    @Output() onClose: EventEmitter<any> = new EventEmitter<any>();

    GenerateSurrender(row, policy): void {
        let effectDate = new Date(row.effectiveDate)
        let evenDate = DateUtils.formatToIsoDate(effectDate);
        let frenchTaxable = null;
        if(row.eventCanBeReported &&  row.eventDateEligible){
            frenchTaxable = row.frenchTaxable;
        }

        this.loading = true;
        this.editingService.generateSurrenderReport(policy, evenDate,frenchTaxable)
            .finally(() => this.loading = false)
            .subscribe((editionResponse: any) => {                
                if (editionResponse.request) {
                    this.messageService.addAlertSuccess(editionResponse.successMessage, false, 'transactionCpt');
                }
                else if (editionResponse == null || editionResponse.errorMessage) {
                    this.messageService.addAlertError(editionResponse.errorMessage, false, 'transactionCpt');
                }
            });
    }

    onPopinClosed(event): void {
        this.onClose.emit();
    }
}