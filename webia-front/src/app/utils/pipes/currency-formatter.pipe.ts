import { Pipe, PipeTransform } from "@angular/core";

const PADDING = "00000000000000000000000000000000";
const DECIMAL_SEPARATOR = ",";
const THOUSANDS_SEPARATOR = "\u00A0";
const CURRENCY_SYMBOL = "€";
const CURRENCY_POSITION = ""; // "after"
const FRACTION_FIX = false; 
const FRACTION_SIZE = 2;
/**
 * #Currency Formatter Pipe
 * 
 * ##Params
 * currencyDecimalSeparator: String(any) DEFAULT = ",";
 * currencyThousandSeparator: String(any) DEFAULT = " ";
 * currencySymbol: String(any) DEFAULT = "€";
 * currencyPosition: String("before" || "after") DEFAULT = "after";
 * currencyFractionSize: number DEFAULT = 2;
 * currencyFractionFixe: boolean DEFAULT = false;
 *
 * Selector: currencyFormatter
 * {{ "324234.4499" | currencyFormatter }} // "324234.4499" -> "324 234,44 €"
 *  
 * ##Examples
 * {{ (:String) | currencyFormatter:(currencyDecimalSeparator:String):(currencyThousandSeparator:String):(currencySymbol:String):(currencyPosition:String):(currencyFractionSize):(currencyFractionFixe) }}
 * {{ "324234.4499" | currencyFormatter:"D":"T":"$":"before" }} // "324234.4499" -> "$ 324T234D44"
 * {{ "324234.4499" | currencyFormatter:"":"":"$":"before" }} // "324234.4499" -> "$ 324 234,44"
 * {{ "324234.4499" | currencyFormatter:"":"":"$" }} // "324234.4499" -> "324 234,44 $"
 * 
 * Return string
 */
/**
 * @Deprecated : too many stateful behaviors that cause bugs, use CurrencyPipe instead.

*/
@Pipe({ name: "currencyFormatter" })
export class CurrencyFormatterPipe implements PipeTransform {
  private decimalSeparator: string;
  private thousandSeparator: string;
  private currencySymbol: string;
  private currencyPosition: string;
  private currencyFractionSize: number;
  private currencyFractionFixe: boolean;

  constructor() {
  }

  transform(value: number | string, decimalS: string = DECIMAL_SEPARATOR, thousandS: string = THOUSANDS_SEPARATOR, symbol: string = CURRENCY_SYMBOL, position: string = CURRENCY_POSITION, fractionSize: number = FRACTION_SIZE, fractionFix:boolean = FRACTION_FIX): string {

    if (value === undefined || value === null) return "";
    decimalS.length > 0 ? this.decimalSeparator = decimalS : "";
    thousandS.length > 0 ? this.thousandSeparator = thousandS : "";
    symbol.length > 0 ? this.currencySymbol = symbol : "";
    position.length > 0 ? this.currencyPosition = position : "";

    let [ integer, fraction = "" ] = (value || "").toString().replace(",", this.decimalSeparator).replace(".", this.decimalSeparator)
      .split(this.decimalSeparator);

    fraction = fractionSize > 0
      ? this._getDecimal(fraction, fractionSize, fractionFix, this.decimalSeparator)
      : "";
    
    if(value.toString().length > 0){
      integer = integer.replace(/\B(?=(\d{3})+(?!\d))/g, this.thousandSeparator);
    }else{
      return "";
    }

    if(integer.length == 0) integer = "0";
    
    let res: string;
    switch (this.currencyPosition) {
      case "after":
        res = integer + fraction + "\u00A0" + this.currencySymbol;
        break;
      case "before": 
        res = this.currencySymbol + "\u00A0" + integer + fraction;
        break;
      default:
        res = integer + fraction;
        break;
    }
    return res;
  }

  parse(value: string, decimalS: string = "", thousandS: string = "", symbol: string = "", position: string = "", fractionSize: number = FRACTION_SIZE, fractionFix:boolean = FRACTION_FIX): string {
    decimalS.length > 0 ? this.decimalSeparator = decimalS : "";
    thousandS.length > 0 ? this.thousandSeparator = thousandS : "";
    symbol.length > 0 ? this.currencySymbol = symbol : "";

    let [ integer, fraction = "" ] = (value || "").replace(this.decimalSeparator,".").split(".");

    integer = integer.replace(new RegExp(this.thousandSeparator, "g"), "");
    integer = integer.replace(new RegExp(this.currencySymbol, "g"), "");
    integer = integer.replace(new RegExp("\u00A0", "g"), "");

    fraction = parseInt(fraction, 20) > 0 && fractionSize > 0
      ? this._getDecimal(fraction, fractionSize, fractionFix, ".")
      : "";
    return integer + fraction;
  }

  number(value: string, decimalS: string = "", thousandS: string = "", symbol: string = "", position: string = "", fractionSize: number = FRACTION_SIZE, fractionFix:boolean = FRACTION_FIX): number {
    if (!value) return null;
    if (decimalS != '.') value = value.replace(new RegExp(decimalS,'g'),'.');
    value = value.replace(/[^0-9\.]/g, '');
    let format = new RegExp('[0-9]*[\.]?[[0-9]{0,'+fractionSize+'}');
    let test = value.match(format)[0];
    return (test ? +test: 0);
  }

  _getDecimal(fraction, fractionSize, fractionFix, decimalSeparator):string{
    let decimal = (fraction + PADDING).substring(0, fractionSize);
    if (!fractionFix)
      decimal=  ((+('0.'+decimal))+'').substring(2);
    return ((decimal && decimal != '0') ? this.decimalSeparator + decimal : '');
  }


}