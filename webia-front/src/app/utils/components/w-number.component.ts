import { Component, OnInit, Input, EventEmitter, Output, NgZone, ChangeDetectorRef, ApplicationRef, ViewChild, ElementRef } from '@angular/core';
import {FormControl} from '@angular/forms';
import { CurrencyFormatterPipe } from "../pipes/currency-formatter.pipe";
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/observable/fromEvent';

@Component({
    selector: 'w-number',
    template: `
    <input [placeholder]="placeholder" [name]="name" type='text' #input  [ngModel]='_numberTxt' (keyup)="_checkValue($event)" [disabled]='disabled' [desactive]="mode" [ngClass]="css" [class.maxValueExceeded]="maxValueExceeded" [required]="required">
    <ng-content></ng-content>
    `,
    providers : [CurrencyFormatterPipe],
    styles: [`
        .maxValueExceeded {
            color: red;
        }
    `]
})
export class WNumberComponent implements OnInit {
    _options = {precision : 2, 
                precisionFix: false, 
                decimalSeparator: ',',
                thousandSeparator:' ',
                currencySymbol:undefined,
                currencyPosition:undefined
              };
    _numberTxt;
    _number;
    txtControl = new FormControl();
    maxValueExceeded: boolean = false;
    
    constructor(private formatter:CurrencyFormatterPipe, private ngzone: NgZone, private cdref: ChangeDetectorRef) { }

    @Output() numberChange: EventEmitter<any> = new EventEmitter<any>();

    @Input() disabled;
    @Input() placeholder:string='';
    @Input() required: boolean = false;
    @Input() mode;
    @Input() name="";
    @Input() css;
    @Input() maxValue:number = 9999999999;
    @Input('Options') set options(object){
        Object.assign(this._options, object);
    };
    @Input() set precision(val:number){
        if ((val && val >= 0) || val==0)
            this._options.precision=val;
    };

    @Input() set number(value:number) {
        this._number = value;
        this._numberTxt = this._getText(value); 
        this.numberChange.emit(value);   
    };

    get number() {
        return this._number;
    }

    @ViewChild('input') inputElRef: ElementRef;

    ngOnInit() {}

    ngAfterViewInit() {
        Observable.fromEvent(this.inputElRef.nativeElement, 'keyup')
            .debounceTime(700)
            .subscribe((event:any) => {
                if ( this._isNotPartialFloat(event.target.value)){
                    this._numberTxt = this._getText(event.target.value);
                    this.emitNumber();
                    this.cdref.detectChanges();
                }
            });

        Observable.fromEvent(this.inputElRef.nativeElement, 'change')
            .subscribe((event:any) => {
                this._numberTxt = this._getText(event.target.value);
                this.emitNumber(true);
                this.cdref.detectChanges();
            });
    }

    emitNumber(force:boolean = false) {
        if(this.maxValueExceeded) {
            this.number = null;
        } else if ( this._isNotPartialFloat() || force){
            this.number = this._getNumber(this._numberTxt);
        }
    }

    _checkValue(event){
        let cleaned = this._clean(event.target.value);
        if (event.target.value != cleaned){
            this._numberTxt = cleaned;
            this._number = this._getNumber(cleaned);
            this.inputElRef.nativeElement.value = cleaned;
            this.cdref.detectChanges();
        }
    }

    _isNotPartialFloat(value=this._numberTxt){
        return (!value || !(/[\.,][0]*$/gm).test(value) );
    }

    _getText(value){
        return (value ? this.formatter.transform(value+'', this._options.decimalSeparator, this._options.thousandSeparator, this._options.currencySymbol, this._options.currencyPosition, this._options.precision, this._options.precisionFix): value)
    }

    _getNumber(value){
        return (value ? this.formatter.number(value+'', this._options.decimalSeparator, this._options.thousandSeparator, this._options.currencySymbol, this._options.currencyPosition, this._options.precision, this._options.precisionFix): null)    
    }

    _clean(value){ // remove alpha characters and limit decimal length
        if (!value) return null;
        value = (value+'').replace(new RegExp('[^0-9 \.' + this._options.decimalSeparator + this._options.thousandSeparator + ']','g'), '');
        let format = new RegExp('[0-9 ]*[\.' + this._options.decimalSeparator + ']?[0-9]{0,' + this._options.precision + '}','g');
        let test = value.match(format)[0];
        this._checkAgainstMaxValue(test);
        return (test ? test: '');
    }

    /**
     * Make sure the value in the input does not exceed to maximum value allowed.
     * 
     * @param value the value to compare
     */
    _checkAgainstMaxValue(value: string) {
        let numberValue: Number = Number(value.replace(new RegExp('\\s+', 'g'), ''));
        if(typeof numberValue == 'number') {
            this.maxValueExceeded = numberValue > this.maxValue;
        }        
    }
}