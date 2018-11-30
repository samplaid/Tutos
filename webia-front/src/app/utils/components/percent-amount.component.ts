import { Component, OnInit, Input, EventEmitter, Output, SimpleChange, AfterViewChecked, SimpleChanges } from '@angular/core';


export type PercentAmount = { flatAmount: number, percent: number, type: string, currency: string };

@Component({
    selector: 'percent-amount',
    template: `
    <div class="form-inline percent-amount">
        <w-number placeholder="%" *ngIf="_unity == 'PERCENT'" [(number)]="percent" [precision]="6" [css]="'form-control input-sm text-right input-percent ' + (css?css:'')" [disabled]="disabled" [required]="required"></w-number>        

        <w-number placeholder="Amount" *ngIf="_unity=='AMOUNT'" [(number)]="flatAmount"  [css]="'form-control input-sm text-right input-amount ' + (css?css:'')" [disabled]="disabled" [required]="required"></w-number>

        <label *ngIf="exclusiveUnity=='PERCENT'"><span>%</span></label>

        <select [disabled]="disabled || disabledCurrency" *ngIf="_unity=='AMOUNT' || exclusiveUnity=='AMOUNT'" class="form-control input-sm currency-select pa_currency" [(ngModel)]="currency">
            <option *ngFor="let c of currencyList" [ngValue]="c.ccyId">{{c.ccyId}}</option>
        </select>

        <select *ngIf="!exclusiveUnity" class="form-control input-sm " 
                [(ngModel)]="_unity" 
                [disabled]="disabled" 
                (ngModelChange)="_switchAndEmit($event)" >
            <option value="PERCENT"><span>%</span></option>
            <option value="AMOUNT"><span>Amount</span></option>
        </select>

        <ng-content></ng-content>
    </div>
    `,
    styles: [`
        .percent-amount {width:290px}
        .pa_currency{margin-left: -4px}

    `]
})
export class PercentAmountComponent implements OnInit {
     _unity: string;
     _percent: number;
     _flatAmount: number;
     _currency: string;
     _defaultCurrency: string;

    constructor() { }

    ngOnInit() {
        this._selectUnityOnInit();
     }

    @Output() currencyChange: EventEmitter<string> = new EventEmitter<string>();
    @Output() percentChange: EventEmitter<any> = new EventEmitter<any>();
    @Output() flatAmountChange: EventEmitter<any> = new EventEmitter<any>();
    @Output() unityChange: EventEmitter<string> = new EventEmitter<string>();

    @Input() disabled;
    @Input() disabledCurrency: boolean = false;
    @Input() currencyList;
    @Input() css;
    @Input() required: boolean = false;
    @Input() exclusiveUnity: string;

    @Input("percent") set percent(value: number) {
        this._percent = value;
        this._emitPercent(this._percent);
    };
    get percent() {
        return this._percent;
    }

    @Input() set flatAmount(value: number) {
        this._flatAmount = value;
        this._emitAmount(this._flatAmount);
    };

    get flatAmount() {
        return this._flatAmount;
    }

    @Input() set currency(value) {
        this._currency = value;
        this._emitCurrency(this._currency);
    }

    get currency() {
        return this._currency;
    }

    @Input() set defaultCurrency(value) {
        this._defaultCurrency = value;
    }

    _emitUnity(value) {
        this.unityChange.emit(value);
    }

    _emitPercent(value) {
        this.percentChange.emit(value);
    }

    _emitAmount(value) {
        this.flatAmountChange.emit(value);
    }

    _emitCurrency(value) {
        this.currencyChange.emit(value);
    }

    _selectUnityOnInit() {        
        const flatFlag = ((!isNaN(parseFloat('' + this.flatAmount)) && (this.currency || isNaN(parseFloat('' + this.percent)))) || this.exclusiveUnity == 'AMOUNT');
        if (flatFlag) {
            this._unity = 'AMOUNT';
        } else {
            this._unity = 'PERCENT';
        }     
        this._emitUnity(this._unity);
    }

    _setCurrencyToDefaultIfNan() {
        if (!this.currency) {
            this.currency = this._defaultCurrency;
        }
    }

    _storeCurrentCurrency() {
        if (this.currency) {
            this._defaultCurrency = this.currency;
        }
    }

    _switchAndEmit(unityValue) {
        this._emitUnity(unityValue);
        if (unityValue == 'PERCENT' && (this.flatAmount != null || this.currency)) {
            this.percent = this.flatAmount;
            this.flatAmount = undefined;
            this._storeCurrentCurrency();
            this.currency = undefined;
        } else if (unityValue == 'AMOUNT') {
            if (this.percent) {
                this.flatAmount = +((+this._percent).toFixed(2));
                this.percent = undefined;
            }
            this._setCurrencyToDefaultIfNan();
        }

    }

    ngOnChanges(changes: SimpleChanges) {
        if ((changes['flatAmount'] && changes['flatAmount'].currentValue!=undefined) || (changes['percent'] && changes['percent'].currentValue!=undefined) )
            this._selectUnityOnInit();
    }
}



