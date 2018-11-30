import { ElementRef, Directive, Input, HostListener, Output, EventEmitter, AfterViewInit } from '@angular/core';

import { SortDirection } from './sort-direction.enum';
import { Column } from '../models/column';

@Directive({
  selector: '[webia-sortable]'
})
export class SortableDirective implements AfterViewInit{ 
 
  @Input() column: Column;
  @Input() columns: Column[] = [];
  @Input() sortMode: string = 'single';
  @Input() externalIcon: boolean = false;

  @Output() sortChange: EventEmitter<any> = new EventEmitter<any>();

  constructor(private el: ElementRef) { }

  ngAfterViewInit(): void {    
    this.setColumnTitle();
    this.refreshIcon(this.column);
    this.columns.forEach(col => col.sortMode = this.sortMode);
  }

  @HostListener('click', ['$event'])
  onSort(event: any): void {   
    
    if (event) {
      event.preventDefault();
    }

    if(this.sortMode === 'single') {
      this.columns.filter(col => col.name !== this.column.name && col.sort)
        .forEach(col => col.sort = SortDirection.NONE);
    }

    if (this.column && this.column.sort) {      
      switch (this.column.sort) {               
        case SortDirection.DESC:
          this.column.sort = SortDirection.NONE;
          break;
        case SortDirection.ASC:
          this.column.sort = SortDirection.DESC;
          break; 
        default:
          this.column.sort = SortDirection.ASC;
          break;
      }
      
      this.refreshAllsIcons();
      this.sortChange.emit(this.column);
    }
  }

  private setColumnTitle(): void {
    let colTitle = document.getElementById(this.column.name);
    if(!colTitle){
      this.el.nativeElement.innerHTML += `<span id="${this.column.name}">${this.column.title}</span>`;
    }    
  }

  private refreshIcon(column: Column): void {
    if(!this.externalIcon) {
      let faSort = '';
      let ariaSort = '';

      if(column.sort == SortDirection.DESC) {
        faSort = 'fa-sort-desc';
        ariaSort = 'descending';
      } else if(column.sort == SortDirection.ASC) {
        faSort = 'fa-sort-asc';
        ariaSort = 'ascending';
      } else if(column.sort == SortDirection.NONE){
        faSort = 'fa-sort';
        ariaSort = 'none';
      }

      let sortIcon = document.getElementById(`${column.name}Sort`);
      if(sortIcon){       
        sortIcon.className = '';
        sortIcon.className = sortIcon.className.concat("marginTop pull-right fa ")
        .concat(faSort);        
      } else {
        this.el.nativeElement.innerHTML += `
          <i id="${column.name}Sort" *ngIf="column.sort" aria-sorting="${ariaSort}"
            class="marginTop pull-right fa ${faSort}">
          </i>
        `;
      }
    }
    
  }
  
  private refreshAllsIcons() {
    this.columns.forEach(col => this.refreshIcon(col));
  }
}
