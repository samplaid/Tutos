import { Directive, HostListener, ElementRef, Input,Output, OnInit, SimpleChanges, EventEmitter  } from "@angular/core";
import { CurrencyFormatterPipe } from "../pipes/currency-formatter.pipe";
import {Observable} from 'rxjs/Rx';

/**
 * #Currency Formatter Directive
 * 
 * ##Params
 * currencyDecimalSeparator: String(any) DEFAULT = ",";
 * currencyThousandSeparator: String(any) DEFAULT = " ";
 * currencySymbol: String(any) DEFAULT = "€";
 * currencyPosition: String("before" || "after") DEFAULT = "after";
 * 
 * Selector = currencyFormatter
 * <input type="text" value="324234.4499" currencyFormatter> // "324234.4499" -> "324 234,44 €"
 * 
 * ##Examples
 * <input type="text" value=(:String) currencyFormatter currencyDecimalSeparator=(:String) currencyThousandSeparator=(:String) currencySymbol=(:String) currencyPosition=(:String) > 
 * <input type="text" value="324234.4499" currencyFormatter currencyDecimalSeparator="D" currencyThousandSeparator="T" currencySymbol="$" currencyPosition="before" > // "324234.4499" -> "$ 324T234D44"
 * <input type="text" value="324234.4499" currencyFormatter currencySymbol="$" currencyPosition="before" > // "324234.4499" -> "$ 324 234,44"
 * <input type="text" value="324234.4499" currencyFormatter currencySymbol="$" > // "324234.4499" -> "324 234,44 $"
 * 
 * Return string
 */
@Directive({
  selector: "[currencyFormatter]",
  providers: [CurrencyFormatterPipe] 
})
export class CurrencyFormatterDirective implements OnInit  {

  private el: HTMLInputElement;
  private _options = {precision : 2, 
                    precisionFix: false, 
                decimalSeparator: undefined,
               thousandSeparator:undefined,
                  currencySymbol:undefined,
                currencyPosition:undefined
              };
  private _model:number;

  @Input('currencyOptions') set options(object){
      Object.assign(this._options, object);
  };

  @Input() set ngModel(val){
    this._model = val ;
    this.ngModelChange.emit(val);

    if (val && !this.el.value){ // first init 
        this.el.value = this._clean(val+'');
        this._transform(this.el.value);
    }       
  };

  get ngModel(){
    return this._clean(this._model+'', true);
  }
  @Output() ngModelChange: EventEmitter<any> = new EventEmitter<any>();

  constructor(
    private elementRef: ElementRef,
    private currencyPipe: CurrencyFormatterPipe
  ) {
    this.el = this.elementRef.nativeElement;
  }

   ngOnInit() {
     this._transform(this.el.value);  // waiting for binding done
   }

   ngOnChanges(change){
     let cleanedValue = this._clean(this.el.value);
     if (this.el.value != cleanedValue){
       this.el.value = cleanedValue;
       this._afterBinding(t=>this.ngModel = this._clean(this.el.value, true));
     }  
   }

  @HostListener("focus", ["$event.target.value"])
  onFocus(value) {
    if (!this.el.classList.contains("ng-invalid") || this.el.classList.contains("ng-pristine")) {
      if (this._model)
        this._parse(this._model+'');
    }
  }

  @HostListener("blur", ["$event.target.value"])
  onBlur(value) {
    if (!this.el.classList.contains("ng-invalid") || this.el.classList.contains("ng-pristine")) {
      this.ngModel = this._clean(value, true);
      this._transform(value);
    }
  }

  @HostListener("keyup", ["$event.target.value"])
  onkeyUp(value) { // check precision on key pressing
    let cleanedValue =  this._clean(this.el.value);
    if (this.el.value != cleanedValue)
       this.el.value = cleanedValue;
  }

  _transform(value){
      value = this._clean(value);
      this._afterBinding(t=>this.el.value = this.currencyPipe.transform(value, this._options.decimalSeparator, this._options.thousandSeparator, this._options.currencySymbol, this._options.currencyPosition, this._options.precision, this._options.precisionFix));
  }

  _parse(value){
      this.el.value = this.currencyPipe.parse(value, this._options.decimalSeparator, this._options.thousandSeparator, this._options.currencySymbol, this._options.currencyPosition, this._options.precision, this._options.precisionFix); // opossite of transform
  }

  _clean(value, toNumber=false){
      let newVal = value.replace(/\./g, ',').replace(/[^0-9,]/g, '');
      let format = new RegExp('[0-9]*[,\.]?[0-9]{0,'+this._options.precision+'}');
      let test = newVal.match(format)[0];
      return (toNumber ? test.replace(/,/g, '.'): test);
  }

  _afterBinding(CB:Function){
    Observable.of({}).delay(1).toPromise().then(t=>CB(t));
  }

}