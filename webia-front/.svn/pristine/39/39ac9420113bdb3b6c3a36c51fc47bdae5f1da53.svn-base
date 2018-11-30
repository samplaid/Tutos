import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { CommissionService, GenerateStatementCommissionRequest } from '../../services/Commission.service';
import { CommissionReconciliationSearchCriteria } from '../../models/commission-reconciliation-search-criteria';
import { Page } from '../../../../_models';
import { PageRequest } from '../../../../_models/paging/page-request';
import { CommissionConstant } from '../../models/commission-constant';
import { CommissionReconciliationGroup } from '../../models/commission-reconciliation-group';
import { CommissionType } from '../../models/commission-type';
import { Column } from '../../models/column';
import { SortDirection } from '../../sorting/sort-direction.enum';
import { ColumnSorter } from '../../sorting/column-sorter';
import { PagerCreator } from '../../paging/pager-creator';
import { forEach } from '@angular/router/src/utils/collection';
import { ColumnMode } from '../../../../../../node_modules/@swimlane/ngx-datatable';

const defaultPageSize: number = 8;


@Component({
  selector: 'commission-table',
  templateUrl: './commission-table.component.html',
  styleUrls: ['./commission-table.component.scss'],
  encapsulation: ViewEncapsulation.None,
  providers: [CommissionService]
})
export class CommissionTableComponent implements OnInit {  
  readonly comStatus = CommissionConstant;

  columns: Column[] = [
    { title: 'Code', name: 'agentId', sort: SortDirection.NONE, className: 'width-fluid-4 text-center' },
    { title: 'Name', name: 'name', sort: SortDirection.NONE },
    { title: 'Period', name: 'period', className: 'width-fluid-5 text-nowrap text-center' },
    { title: 'Currency', name: 'currency', className: 'width-fluid-4 text-nowrap text-center' },
    { title: 'SAP Balance', name: 'sapBalance', className: 'width-fluid-7 text-nowrap text-center' },
    { title: 'Statement Balance', name: 'statementBalance', className: 'width-fluid-7 text-nowrap text-center' },
    { title: 'GAP', name: 'gap', className: 'width-fluid-7 text-nowrap text-center' },
    { title: 'Status', name: 'status', sort: SortDirection.NONE, className: 'width-fluid-10 text-nowrap text-center' },
  ];

  sub: any;
  page: Page;
  generateStatementComProcessing: boolean = false;

  /** Indicates if the Selecte all reconciled input should be checked */
  isSelectedAllReconciled: boolean = false;

  /** Show or disable the Selecte all reconciled input component */
  someReconciled: boolean = false;

  /** The commission search criteria */
  searchCriteria: CommissionReconciliationSearchCriteria;

  /** Contains all selected commissions */
  selected: CommissionReconciliationGroup[] = [];

  /** An original object which contains the result search */
  originalData: CommissionReconciliationGroup[] = [];

  /** Contains the sorted commission */
  sortedData: CommissionReconciliationGroup[] = [];

  constructor(private commissionService: CommissionService) {
  }

  ngOnInit() {
    this.page = new Page();
    this.page.size = defaultPageSize;
    this.searchCriteria = { type: CommissionType.ENTRY };
    this.searchCriteria.pageable = new PageRequest(1, defaultPageSize);
    this.onSearch(this.searchCriteria);
  }

  /**
   * Select the current page.
   * @param pageEvent 
   */
  onPageChange({ page }) {
    this.page = PagerCreator.create(page, defaultPageSize, this.sortedData);
  }

  /**
   * Fire when the checkbox column is checked.
   */
  onSelect({ selected, checked }): void {
    selected.selected = checked;
    let obj = this.originalData.find(data => data.aggregate.agentId === selected.aggregate.agentId);
    if(obj) {
      obj.selected = checked;      
    }

    this.selected = this.originalData.filter(data => data.selected);
    this.uncheckSelectAllReconciledInput(this.selected);
  }

  /**
   * Fire when the button validate selected is clicked.
   */
  onValidateSelected(): void { 
    this.commissionService.validateReconciledWithConfirmation(this.selected)
    .subscribe((result) => {
      if(result) {
        this.onSearch(this.searchCriteria)
      }
    });
  }

  /**
   * Fire when the button done is clicked.
   */
  onDone(selected: CommissionReconciliationGroup): void {
    let list: CommissionReconciliationGroup[] = [];    
    list.push(selected);
    this.commissionService.doneValidated(list)
    .subscribe(result => {
      this.originalData.splice(this.originalData.findIndex(content => content.aggregate.agentId === result[0].agentId), 1);
      let activeSortedColumn = this.columns.find((column: Column) => column.sort && column.sort != SortDirection.NONE);
      this.onSort(activeSortedColumn);
    });
  }

  /**
   * Fire when the button 'Select all reconciled' is triggered.
   */
  onSelectAllReconciled(checked: boolean): void {
    this.isSelectedAllReconciled = checked;

    // select all reconciled
    this.originalData.forEach(data => {
      data.selected = checked && this.isReconclied(data.aggregate.status);      
    });

    // update selected items
    this.selected = this.originalData.filter(data => data.selected);

    // update page
    this.originalData.forEach(data => {
      let pageEl = this.page.content.find(el =>  el.aggregate.agentId === data.aggregate.agentId);
      if(pageEl)
        pageEl.selected =  data.selected;
    });

    this.page.content = [...this.page.content];    
  }

  /**
   * Fire when the search form is submitted.
   */
  onSearch(criteria: CommissionReconciliationSearchCriteria): void {
    if (criteria) {
      criteria.pageable.page = 1;
      this.sub = this.commissionService.search(criteria).subscribe(page => {       
        // Update the new list to be selectable
        this.updateOriginalData(page.content, this.selected);
         // keep original data for sorting
        this.originalData = page.content;
        // Refresh all selected items
        this.selected = this.originalData.filter(item => item.selected);       
        // Make a copy for sorting
        this.sortedData = Object.assign([], this.originalData);
        // Set the state of the select all reconciled input check box activation
        this.someReconciled = this.originalData.some(el => this.isReconclied(el.aggregate.status));
        
        // render page    
        this.page = PagerCreator.create(page.currentPage, page.size, page.content);

        this.onSelectAllReconciled(this.isSelectedAllReconciled);
      });
    }
  }

  /**
   * Fire when the clear button is clicked.
   * This method clears the input search and load the data.
   */
  onClearInputSearch(): void {
    this.searchCriteria.text = '';
    this.onSearch(this.searchCriteria);
  }

  /**
   * Fire when the commission type select is triggered.
   * @param type the commission type
   */
  onTypeSelected(type: string): void {
    let criteria: CommissionReconciliationSearchCriteria = {};
    criteria.type = type;
    criteria.pageable = new PageRequest(1, defaultPageSize);
    this.onSearch(criteria);
  }

  /** Fire when the sorting icon is clicked */
  onSort(columnSortBy: Column): void {
    let sorter: ColumnSorter = new ColumnSorter(columnSortBy);
    this.sortedData = sorter.sort(this.originalData, this.compare);
    // render page    
    this.page = PagerCreator.create(this.page.currentPage, this.page.size, this.sortedData);
  }

  private isReconclied(status: string): boolean {
    return status == CommissionConstant.RECONCILED;
  }

  private compare(columnSortBy: Column, obj1: any, obj2: any): number {
    if (columnSortBy.name === 'status') {
      return CommissionTableComponent.compareTo(columnSortBy, CommissionTableComponent.toInStatus(obj1.aggregate[columnSortBy.name]), CommissionTableComponent.toInStatus(obj2.aggregate[columnSortBy.name]));
    } else {
      return CommissionTableComponent.compareTo(columnSortBy, obj1.aggregate[columnSortBy.name], obj2.aggregate[columnSortBy.name]);
    }
  }

  private static toInStatus(status: string): number {
    let ordinal: number;
    switch (status) {
      case CommissionConstant.RECONCILED:
        ordinal = 0;
        break;
      case CommissionConstant.NOT_RECONCILED:
        ordinal = 1;
        break;
      case CommissionConstant.VALIDATED:
        ordinal = 2;
        break;
      default:
        ordinal = 99999;
        break;
    }

    return ordinal;
  }

  private static compareTo(columnSortBy: Column, a: any, b: any): number {
    if (a > b) {
      if (columnSortBy.sort === SortDirection.DESC) {
        return -1;
      } else if (columnSortBy.sort === SortDirection.ASC) {
        return 1;
      } else if (columnSortBy.sort === SortDirection.NONE) {
        return 0;
      }
    } else if (a < b) {
      if (columnSortBy.sort === SortDirection.DESC) {
        return 1;
      } else if (columnSortBy.sort === SortDirection.ASC) {
        return -1;
      } else if (columnSortBy.sort === SortDirection.NONE) {
        return 0;
      }
    }
    return 0;
  }

  /**
   * Uncheck the all reconciled input component if the selected commission list does not contains any reconciled commissions.
   * @param selected a list of selected commissions.
   */
  private uncheckSelectAllReconciledInput(selected: CommissionReconciliationGroup[]): void {
    let hasReconciled: boolean = selected.some(commission => this.isReconclied(commission.aggregate.status));
    // Keep the previous state of the SelectAllReconciled input if there are some reconciled commissions in selected list.
    if (!hasReconciled) {
      this.isSelectedAllReconciled = false;
    }
  }

  private updateOriginalData(originalData: CommissionReconciliationGroup[], previousSelected: CommissionReconciliationGroup[]): void {
    for (let data of originalData) {
      // Update the status of the items in selected array
      previousSelected.forEach(item => {
        if(item.aggregate.agentId === data.aggregate.agentId) {
          item.aggregate.status = data.aggregate.status;
        }
      });
           
      if(data.aggregate.status !== CommissionConstant.VALIDATED) {
        let previousObj = previousSelected.find(previous => previous.aggregate.agentId === data.aggregate.agentId && previous.selected);
        if(previousObj) {
          data.selected = previousObj.selected;
        }
      }  
    }
  }

  launchGenerateStatementComJob(){
    this.generateStatementComProcessing = true;
    this.commissionService.launchGenerateStatementComJob(new GenerateStatementCommissionRequest(this.searchCriteria.type))
        .toPromise().then(s=>{this.generateStatementComProcessing=false;}, e=>{this.generateStatementComProcessing=false;})
  }

}


