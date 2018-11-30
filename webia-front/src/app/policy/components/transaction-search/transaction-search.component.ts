import { Component, OnInit, Input,ViewEncapsulation, OnDestroy, ViewChild, ComponentFactoryResolver, ComponentRef, ViewContainerRef, OnChanges } from '@angular/core';
import { Subscription } from 'rxjs';
import { Page, defaultPageSize, tableMessages, transactionWithdrawalCode, transactionPremiumPaymentType, Transfer, PolicyChange, PolicyChangeType, PolicyChangeStatus, Product, PolicyClauses, PolicyClause, PolicyCoverage, defaultTranslationLanguage } from "../../../_models";
import { TransactionSearchCriteria, Transaction, TransactionsHistoryDetails,PolicyTransactionsDetails, TransactionsCommissions, PolicyTransactionsDetailsOutput } from '../../../_models/transaction/index';
import { DatatableComponent, SortDirection } from '@swimlane/ngx-datatable';
import { FilterService } from '../../services/filter.service';
import { EditingService } from '../../../_services/editing.service';
import {  DateUtils,MessageService } from "../../../utils";
import { LoadableComponent } from '../../../workflow/components/loadable.component';
import { trigger,state, style, animate, transition } from '@angular/animations';
import { TransactionDetailsMainComponent } from '../transaction-details/transaction-details-main.component';
import { PopinDirective } from '../../../utils/directives/popin.directive';
import { Observable } from 'rxjs/Observable';
import { PolicyService } from '../../services/policy.service';
import { BeneficiaryChange } from '../../../beneficiary-change/models/beneficiary-change';

@Component({
    selector: 'transaction-search',
    templateUrl: './transaction-search.tpl.html',
    styleUrls : ['./transaction-search.scss'],
    animations: [
        trigger(
        'enterAnimation', [
            transition(':enter', [
            style({opacity: 0}),
            animate('300ms', style({opacity: 1}))
            ])
        ]),
        trigger(
        'detailAnimation', [
            transition(':enter', [
              style({transform : 'translateY(-100%)', height: '0', opacity: 0}),
              animate('250ms', style({transform : 'translateY(0)', height: '100%', opacity: 1}))
            ])
          ]
        )
  ],
  encapsulation: ViewEncapsulation.None
})
export class TransactionSearchComponent extends LoadableComponent implements OnInit {
    readonly defaultPageSize = defaultPageSize;
    readonly tableMessages = tableMessages;
    readonly transactionWithdrawalCode = transactionWithdrawalCode;
    readonly defaultSort = {prop: 'effectiveDate', dir: SortDirection.desc};

    transactionCriteria: TransactionSearchCriteria;
    policy:string;
    selectedTransactions : Transaction;
    previousSelectedTransactions : Transaction;

    transaction : Transaction;
    detailsOut: TransactionsHistoryDetails[]; 
    detailsIn: TransactionsHistoryDetails[];  
    commission: TransactionsCommissions;
    allRows: any[];
    filteredRows: Transaction[];
    displayedTransfers: Transfer[]; 
    isSwitch:boolean;
    expanded: boolean;
    beneficiaryChange : BeneficiaryChange;
    brokerChange = {after: null, before: null};
    PolicyChangeType = PolicyChangeType;
    policyClauses : PolicyClauses;
    busyDetail:any;
    detailsTableHeigth: number;
    
 
    @Input() transfers: Transfer[];
    @Input() product: Product;
    @Input() firstPolicyCoverages: PolicyCoverage;
    @Input() set policyId(value: string) {
        if (value) {
            this.refreshData(value);
            this.policy = value;
        }
    };

    @ViewChild('myTable') table: any;
    @ViewChild(PopinDirective) popinDirective: PopinDirective;
    @ViewChild(TransactionDetailsMainComponent) transactionDetailsMainComponent: TransactionDetailsMainComponent;

    constructor( private policyService: PolicyService,
        private filterService: FilterService) {
        super();
    }

    ngOnInit() {
        this.transactionCriteria = new TransactionSearchCriteria();
        this.allRows = [];
        this.filteredRows = [];
        this.detailsIn = [];
        this.detailsOut = [];
        this.commission= new TransactionsCommissions();
        this.isSwitch =  false;
        this.expanded = false;
    }

    refreshData(polId): void{
        this.allRows = [];
        let loadingRefresh = Promise.all([this.loadTransactions(polId), 
                    this.loadPolicyChanges(polId)])
                .then(arrays =>  this.allRows = [...arrays[0], ...arrays[1] ]  )
                .then( n =>  this.filter());     
        this.addSubscription(Observable.fromPromise(loadingRefresh).subscribe());
    }

    private loadTransactions(polId) {
        return this.policyService.getTransactionsByPolicy(polId).toPromise();
    }

    private loadPolicyChanges(polId) {
        return this.policyService.getChangesByPolicy(polId).map((data:PolicyChange[]) => this.wrapToTransaction(data)).toPromise();
    }

    private wrapToTransaction(policyChanges:PolicyChange[]):Transaction[]{
        let transactions = [];
        policyChanges.forEach( c => {
            let trans = new Transaction();
            trans.lastTrnId = c.workflowItemId;
            trans.effectiveDate = this.toTimestamp(c.dateOfChange);
            trans['event'] = c.type;
            trans.eventName = PolicyChangeType[c.type];
            trans.status = PolicyChangeStatus[c.status];
            trans.statusCode = (c.cancelDate) ? 2 : 1;
            transactions.push(trans);

            if (c.cancelDate){
                let trans = new Transaction();
                trans.lastTrnId = c.workflowItemId;
                trans.effectiveDate = c.cancelDate;
                trans['event'] = c.type;
                trans.eventName = "Cancel of " + PolicyChangeType[c.type];
                trans.status = "Cancelled";
                trans.statusCode = 2;
                transactions.push(trans);
           }
        })
        return transactions;
    }

    private loadTransactionsDetails(polId,transaction) {
      
        return this.policyService.getPolicyTransactionsDetails(polId,transaction)
        .subscribe((result: PolicyTransactionsDetailsOutput) => {          
            this.commission = result.commission;            
            this.detailsIn = result.detailsIn;
            this.detailsOut = result.detailsOut;
            this.isSwitch = result.isSwitch;
        });
    }

    private loadBeneficiaryChangeDetails(polId, workflowItemId){
        this.beneficiaryChange = null;
        return this.policyService.getBeneficiaryChange(polId, workflowItemId, this.product.prdId, defaultTranslationLanguage).toPromise()
                          .then((result: BeneficiaryChange) => { 
                              this.beneficiaryChange = result;
                              let policyClauses_tmp = new PolicyClauses();
                              policyClauses_tmp.death = result.deathBenefClauses.filter(
                                clause => clause.status == 1) || []; // conaints only Textual clauses
                              policyClauses_tmp.maturity = result.lifeBenefClauses.filter(
                                clause => clause.status == 1) || []; // conaints only Textual clauses
                              this.policyClauses = policyClauses_tmp;
                           });
    }

private loadBrokerChangeDetails(workflowItemId){
    this.brokerChange = {after: null, before: null};
    let loadAfter = this.policyService.getBrokerChange(workflowItemId).toPromise().then(after => this.brokerChange.after = after, e=>{});
    let loadBefore = this.policyService.getBrokerChangeBefore(workflowItemId).toPromise().then(before => this.brokerChange.before = before, e=>{});
    return Promise.all([loadAfter, loadBefore]);
}

isSelectedTransactionCanHaveDetails(selectedTransactions:Transaction):boolean{
      if (selectedTransactions.event) return true;
      let isPremiumAllocation = selectedTransactions != null && selectedTransactions.eventType == 8;
      let isPremium = selectedTransactions != null && selectedTransactions.eventType == 2;
      let isSurrender = selectedTransactions != null && selectedTransactions.eventType == 4;
      let isDeathNotification = selectedTransactions != null && selectedTransactions.eventType == 6;
      let isWithDrawal = selectedTransactions != null && selectedTransactions.eventType == 15;
      let isDeathSettlement = selectedTransactions != null && selectedTransactions.eventType == 17;
      let isSwitchs = selectedTransactions != null && selectedTransactions.eventType == 19;
      let isCashFundSwitch = selectedTransactions != null && selectedTransactions.eventType == 23;
      let isMaturity = selectedTransactions != null && selectedTransactions.eventType == 21;
      let isWithDrawal2 = selectedTransactions != null && selectedTransactions.eventType == 30;
      let isChangeBeneFundSwitch = selectedTransactions != null && selectedTransactions.eventType == 6;

      this.isSwitch = isSwitchs || isCashFundSwitch || isChangeBeneFundSwitch;
      return isPremium || isPremiumAllocation || isSurrender || isDeathNotification || isWithDrawal || isDeathSettlement  || this.isSwitch || isMaturity  || isWithDrawal2 ;

}


    filter() : void {
        this.filteredRows = this.filterService.filterTransactions(this.allRows, this.transactionCriteria);
        //this.filteredRows = filteredRows_tmp.sort((a:any, b:any) => a.effectiveDate-b.effectiveDate);
        console.log(this.filteredRows);
        //navigating to the first page.
        this.table.offset = 0;
    }

    toTimestamp(somthing:any){
        return new Date(somthing).getTime();
    }

    compareByDate(timeStampLeft: number, timeStampRight: number, rowLeft: Transaction, rowRight: Transaction): number {
        //if two rows have the same date, we compare by event type, in this case premium payment will come before mortality change...etc.
        if(timeStampLeft === timeStampRight) {
            return rowLeft.eventType - rowRight.eventType;
        }
        return timeStampLeft - timeStampRight;
    }

    onSelect(event): void {
        const row: Transaction = event.selected[0];
        this.selectedTransactions = row;
        if(row.eventType === transactionPremiumPaymentType) {
            this.displayedTransfers = this.transfers.filter(transfer => transfer.coverage === row.coverage);
        } else {
            this.displayedTransfers = [];
        }
       
       this.toggleExpandRow( this.selectedTransactions);

    }
    
    onDetailToggle(event):void {
        //console.log('Detail Toggled', event);
        //this.onSelect(event);
    }

    toggleExpandRow(row):void {
        this.selectedTransactions = row;
        if (this.selectedTransactions.event){
            if (PolicyChangeType[this.selectedTransactions.event] == PolicyChangeType.CBN){
                this.busyDetail = this.loadBeneficiaryChangeDetails(this.policy, this.selectedTransactions.lastTrnId); // workflowItemId has been wrapped as lastTrnId
                this.ToggleRow(this.selectedTransactions);
            }
            if (PolicyChangeType[this.selectedTransactions.event] == PolicyChangeType.CAP){
                this.busyDetail = this.loadBrokerChangeDetails(this.selectedTransactions.lastTrnId); // workflowItemId has been wrapped as lastTrnId
                this.ToggleRow(this.selectedTransactions);
            }          
        } else {
            if( this.isSelectedTransactionCanHaveDetails(this.selectedTransactions)){
                this.busyDetail = this.loadTransactionsDetails(this.policy, this.selectedTransactions);
                this.ToggleRow(this.selectedTransactions);
            } 
        }        
    }

    ToggleRow(row){
        if(this.expanded){
            this.onCollapseRow(row);
        } else {
            this.onExpandRow(row);
        }
    }

    onExpandRow(row) {
        this.table.rowDetail.collapseAllRows();
       this.table.rowDetail.toggleExpandRow(row);
       this.previousSelectedTransactions =row;
       this.expanded = true;
     }

     onCollapseRow(row) {
        this.table.rowDetail.collapseAllRows();
        if(this.previousSelectedTransactions.lastTrnId!==row.lastTrnId){
            this.onExpandRow(row);
        }
        else{
            this.expanded = false;
        }
    } 	
}