import { Component, Injectable, Input, Output, EventEmitter, ElementRef, HostListener, Inject } from '@angular/core';
import {  BsLocaleService, BsDatepickerConfig  } from 'ngx-bootstrap/datepicker';
import { DateUtils } from "../date-utils";



@Component({
  selector: 'input-datepicker',
  template: `
        <div class="input-group" *ngIf="!disabled" style="width: 110px;">
          <input type="text" class="input-sm form-control " #dp="bsDatepicker" bsDatepicker [bsConfig]="bsConfig"
                  [outsideClick]="true" [(ngModel)]="_date" (ngModelChange)="updateDate($event)" [isDisabled]="disabled" placement="{{placement}}"
                  (click)="toggle(dp)" triggers="keydown:click" [isOpen]="isOpen">
          <div class="input-group-btn btn-group-sm">
            <button class="btn btn-sm btn-primary" type="button" (click)="toggle(dp)" [attr.aria-expanded]="dp.isOpen"><i class="fa fa-calendar" aria-hidden="true"></i></button>
          </div>
        </div>
  `
})
export class InputDatepicker {
    _date: Date;
    openUp:boolean = false;
    placement = "bottom";

    @Input() date:Date;
    @Input() disabled:boolean;
    @Input() bsConfig: Partial<BsDatepickerConfig>;
    @Input() set dateIn(date:any){ // this can be a Date, Number or String
        this.date = date;
        if (!!date && typeof date.getDate != 'function'){
            try {
              let date_tmp = new Date(date);
              date = new Date(DateUtils.formatToIsoDate(date_tmp));  //WARING : this should remove time and add timeZone
              this.date = date;
            } catch (e){ }
        }
        if (!!date && date.getFullYear() > DateUtils.WEALINS_NULL_YEAR) {
          this._date = date;
        } else {
          this._date = null;
        }
    };

    @Output() onDateChange = new EventEmitter<Date>();

  constructor( private elRef:ElementRef) {}

  ngOnInit(){}

  ngAfterContentInit() {
    this.updatePLacement();  
  }

  updateDate(event){
    let previousDate = this.date;
    if(event){
      this.date = event;  // new Date(Date.UTC(event.getFullYear(), event.getMonth(), event.getDate())) //WARING : this should remove timeZone
    } else {
      this.date = null;
    }
    if (!this.date || !previousDate || typeof previousDate.toLocaleDateString != 'function' || this.date.toLocaleDateString()!=previousDate.toLocaleDateString() ){
      this.onDateChange.emit(this.date);
    }
      
  }

  toggle(dp){
    this.updatePLacement();
    if (dp.isOpen) //check now if it has to be show or hide in next digest
      setTimeout(()=>dp.hide(),0);
    else
      setTimeout(()=>dp.show(),0);
  }

  updatePLacement(){
       let height = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
       let posFromTop = this.elRef.nativeElement.getBoundingClientRect().bottom;
       let posToBottom =  height - posFromTop;
       this.placement = (posFromTop > 230 && posToBottom < 230)? "top":"bottom" ;
  }

}




@Component({
  selector: 'datepicker',
  template: `
        <input-datepicker *ngIf="!disabled" [(date)]="_currentDate" (onDateChange)="updateDate($event)" [dateIn]="_currentDate" [disabled]="disabled" [bsConfig]="bsConfig"></input-datepicker>
        <input *ngIf="disabled" [ngClass]="css" [required]="required" style="width: 95px;" type="text" class="datepicker form-control input-sm" value="{{_currentDate | wdate}}" disabled >
  `

})
export class MyDatepicker {
  _currentDate: Date;
  locale = 'en';
  readonly defaultminDate= new Date(1900, 1, 1);
  readonly defaultMaxDate= new Date(2050, 12, 31);

  bsConfig: Partial<BsDatepickerConfig> = { dateInputFormat: 'DD/MM/YYYY', 
                                            containerClass: 'theme-default', 
                                            showWeekNumbers : false, 
                                            minDate: this.defaultminDate, 
                                            maxDate: this.defaultMaxDate };

  @Output() dateChange: EventEmitter<Date> = new EventEmitter<Date>();

  @Input() disabled:boolean = false;
  @Input() required:boolean = false;  
  @Input() css;
  @Input() set date(date: Date) {   
    this._currentDate = date;
  }
  
  @Input() set minDate(date){
    if (date){
      if (typeof date.getDate != "function"){
        try {
          date = new Date(date);
        } catch (e){
          return;
        }
      }
      date.setHours(0,0,0,0);
      this.bsConfig.minDate = date;
    } else {
      this.bsConfig.minDate = this.defaultminDate;
    }
  }
  
  @Input() set maxDate(value: Date) {
    if(value && this.bsConfig) {
      this.bsConfig.maxDate = value;
    }
  }
  

  get date(): Date {
      return this._currentDate; 
  }

  constructor(private _localeService: BsLocaleService) {
    this._localeService.use(this.locale);
  }

  updateDate(event){
        this.date = event;
        this.dateChange.emit(this.date);
  }

}
   

   