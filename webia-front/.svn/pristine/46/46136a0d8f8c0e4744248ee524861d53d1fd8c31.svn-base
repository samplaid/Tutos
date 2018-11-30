import { Component, OnInit, ViewChild } from '@angular/core';
import { TransferService } from '../services/transfer.service';
import { LoadableComponent } from '../../../workflow/components/loadable.component';
import { tableMessages, transferStatus } from '../../../_models';
import { SortDirection, DatatableComponent } from '@swimlane/ngx-datatable';
import { BsModalService, BsModalRef } from "ngx-bootstrap";
import { ModalRejectStep } from '../../../utils/components/modal-reject-step.component';
import { FilterService } from '../services/filter.service';
import { FilterCriteria } from '../models/filter-crtieria';
import { FormControl } from '@angular/forms';
import { Subscription } from 'rxjs';
import { transferMode } from '../models/transfer-mode';
import * as _ from "lodash";
import { MessageService } from '../../../utils';
import { TransferCompta } from '../models/transfer-compta';

const stringAndNumberFields = ["trfCurrency", "libDonOrd", "ibanDonOrd", "swiftDonOrd", "libBenef", "ibanBenef", "swiftBenef", "trfComm", "transferStatus", "transferType", "userCompta", "rejectComment", "mode", "policyId", "workflowItemId", "transferId", "trfMt"];
const dateFields = ["cps1Dt"];
const defaultQuery: FilterCriteria = {stringAndNumberFields : stringAndNumberFields, dateFields : dateFields};

@Component({
  selector: 'payments-compta',
  templateUrl: './payments-compta.component.html',
  styles: [`
    .btn-palette .btn:not(:first-child) { 
      margin-left: 6px;
    }
    .btn-palette-pull-right .btn:not(:first-child) { 
      margin-right: 6px;
    }
    .check-all-div {
      width: 30px;
    }
    .check-all-div input {
      display:block; 
      margin:0 auto;
    }    
    .lightMarginTop {
      margin-top: 4px;
    }
    .filter-buttons-wrapper {
      display: inline-block;
      margin-left: 5%;
    }
    .badge-sm {
      font-size: 0.8em;
    }
  `],
  providers: [TransferService, FilterService]
})
export class PaymentsComptaComponent extends LoadableComponent implements OnInit {

  initialPayments: TransferCompta[] = [];
  payments: TransferCompta[] = [];
  readonly defaultPageSize = 15;
  readonly defaultSort = {prop: 'transferId', dir: SortDirection.asc};
  readonly tableMessages = tableMessages;
  readonly transferStatus = transferStatus;
  readonly transferMode = transferMode;
  selected: TransferCompta[] = [];
  queryString: string;
  showStatus = {};
  isCreatingFax = false;
  @ViewChild('table') table: DatatableComponent;
  queryInputControl = new FormControl();
  formCtrlSub: Subscription;
  numberSepaSelected: number;
  totalMultiline: string;
  numberFaxSelected: number;  
  numberSwiftSelected: number;
  totalFax: string;    
  totalSWIFT: string;  
  toggleAllCheck: boolean;

  
  constructor(
    private transferService: TransferService,
    private filterService: FilterService, 
    private messageService: MessageService,
    private modalService: BsModalService) {
    super();
  }
  
  ngOnInit(): void {
    this.refresh();
    this.formCtrlSub = this.queryInputControl.valueChanges
      .debounceTime(300)
      .subscribe(value => this.filter());
  } 

  ngOnDestroy(): void {
    if(this.formCtrlSub) {
      this.formCtrlSub.unsubscribe();
    }
  }

  refresh(): void {
    this.initFilters();
    this.loadPayments();
    this.onSelect({selected: []});
  }

  clear(): void {
    this.initFilters();
    this.filter();
  }
  
  initFilters(): void  {
    this.showStatus= {[transferStatus.ACCEPTED]: true, [transferStatus.COMPTA]: true};
    this.queryString = '';
  }

  loadPayments(): void {
    this.addSubscription(this.transferService.getPayments().subscribe(result => {
      this.initialPayments = result;
      this.filter();
    }));
  }

  validate(payment: TransferCompta): void {
    const index = this.payments.indexOf(payment);
    this.addSubscription(this.transferService.validate(payment.transferId).subscribe(result => {
      let currentPayment = result.find(x => payment.transferId == x.transferId);
      this.payments[index] = currentPayment;
      result.splice(result.indexOf(currentPayment), 1);
      this.payments = [...this.payments, ...result];
      }));
  }

  refuse(payment: TransferCompta): void {    
    const rejectModal: BsModalRef =   this.modalService.show(ModalRejectStep, {class:'modal-sm'});
      rejectModal.content.onClose.take(1).subscribe((reason) => {
            this.onRefuseValidate(payment, reason);
        }); 
  }


  executeCsv(): void {
    let ids: number[] = this.selected.filter(payment => payment.mode === transferMode.SWIFT).map(transferForm => transferForm.transferId);
    const sub = this.transferService.executeCsv(ids).subscribe(transfers => {
      this.onExecutionSuccess(transfers);
    });
    this.addSubscription(sub);
  }
  executeFax(): void {
    let ids: number[] = this.selected.filter(payment => payment.mode === transferMode.FAX).map(transferForm => transferForm.transferId);
    const sub = this.transferService.executeFax(ids).subscribe(transfers => {
      this.onExecutionSuccess(transfers);
    });
    this.addSubscription(sub);
  }

  executeSepa(): void {
    let ids: number[] = this.selected.filter(payment => payment.mode === transferMode.SEPA).map(transferForm => transferForm.transferId);
    const sub = this.transferService.executeSepa(ids).subscribe(transfers => {
      this.onExecutionSuccess(transfers);
    });
    this.addSubscription(sub);
  }

  private onExecutionSuccess(transfers: TransferCompta[]): void {
    this.messageService.success("Documents generated successfully for transfers : " + transfers.map(transfer => transfer.transferId).join(", "));
    //This beautiful feature was unfortunately removed !!!!
    /*const idGroupedUpdatedTransfers = _.groupBy(transfers, transfer => transfer.transferId);    
    this.payments.forEach((payment, index) => {
      if(!!idGroupedUpdatedTransfers[payment.transferId]) {
        this.payments[index] = idGroupedUpdatedTransfers[payment.transferId][0];
      }
    });
    this.payments = [...this.payments];
    this.onSelect({selected: []});*/    
    this.refresh();
  }

  private onRefuseValidate(payment: TransferCompta, comment: string): void {
    const index = this.payments.indexOf(payment);
    this.addSubscription(this.transferService.refuse(payment.transferId, {comment: comment}).subscribe(result => {
      this.payments[index] = result;
      this.payments = [...this.payments];
    }));
  }

  onSelect(event): void {    
    this.selected = event.selected.filter(payment => this.displayCheck(payment));
    this.updateTotals();
  }

  updateTotals(): void {
    const selectedMultiline = this.selected.filter(payment => payment.mode === transferMode.SEPA);
    const selectedFax = this.selected.filter(payment => payment.mode === transferMode.FAX);
    const selectedSwift = this.selected.filter(payment => payment.mode === transferMode.SWIFT);
    this.numberFaxSelected = selectedFax.length;
    this.numberSepaSelected = selectedMultiline.length;
    this.numberSwiftSelected = selectedSwift.length ;
    this.totalMultiline = this.sumPayments(selectedMultiline);
    this.totalSWIFT = this.sumPayments(selectedSwift);
    this.totalFax = this.sumPayments(selectedFax);
  }

  sumPayments(selectedOfMode: TransferCompta[]): string {
    let messages: string[] = [];
    const paymentsMap = _.groupBy(selectedOfMode, payment => payment.trfCurrency);
    for(let currency of Object.keys(paymentsMap)) {
      let sum = 0;
      for(let payment of paymentsMap[currency]) {
        sum += payment.trfMt || 0;
      }
      messages.push(sum + " " + currency);
    }
    return messages.join(", ");
  }

  displayCheck(payment: TransferCompta): boolean {
    return payment && payment.transferStatus === transferStatus.ACCEPTED;
  }

  filter(): void {
    //navigating to the first page.
    this.table.offset = 0;
    this.payments = this.initialPayments.filter(payment => this.showPayment(payment));
  }

  showPayment(payment: TransferCompta): boolean {
    return this.filterByStatus(payment) && this.filterByQuery(payment);
  }

  filterByStatus(payment: TransferCompta): boolean {
    return (payment && this.showStatus[payment.transferStatus] === true);
  }

  filterByQuery(payment: TransferCompta): boolean {    
    return this.filterService.accepts(payment, defaultQuery, this.queryString);
  }

  toggleAll(): void {    
    if(this.toggleAllCheck) {
      this.onSelect({selected: this.payments});
    } else {
      this.onSelect({selected: []});
    }
  }
}


