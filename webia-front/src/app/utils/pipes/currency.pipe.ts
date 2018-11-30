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
 * {{ "324234.4499" | currencyFormatter:"$":"before;"D":"T" }} // "324234.4499" -> "$ 324T234D44"
 * {{ "324234.4499" | currencyFormatter:"$":"before":"":"" }} // "324234.4499" -> "$ 324 234,44"
 * {{ "324234.4499" | currencyFormatter:"$":"":"" }} // "324234.4499" -> "324 234,44 $"
 * 
 * Return string
 */
@Pipe({ name: "currencyPipe" })
export class CurrencyPipe implements PipeTransform {

  constructor() {
  }

  transform(value: number | string, symbol: string = CURRENCY_SYMBOL, position: string = CURRENCY_POSITION, decimalS: string = DECIMAL_SEPARATOR, thousandS: string = THOUSANDS_SEPARATOR, fractionSize: number = FRACTION_SIZE, fractionFix:boolean = FRACTION_FIX): string {

    if (value === undefined || value === null) {
      return "";
    }  else if(value == 0) {
      return "0";
    }
    const decimalSeparator = decimalS.length > 0 ? decimalS : "";
    const thousandSeparator = thousandS.length > 0 ? thousandS : "";
    const currencySymbol = symbol.length > 0 ? symbol : "";
    const currencyPosition = position.length > 0 ? position : "";

    let [ integer, fraction = "" ] = (value || "").toString().replace(",", decimalSeparator).replace(".", decimalSeparator).split(decimalSeparator);

    fraction = fractionSize > 0
      ? this._getDecimal(fraction, fractionSize, fractionFix, decimalSeparator)
      : "";
    
    if(value.toString().length > 0){
      integer = integer.replace(/\B(?=(\d{3})+(?!\d))/g, thousandSeparator);
    }else{
      return "";
    }

    if(integer.length == 0) integer = "0";
    
    let res: string;
    switch (currencyPosition) {
      case "after":
        res = integer + fraction + "\u00A0" + currencySymbol;
        break;
      case "before": 
        res = currencySymbol + "\u00A0" + integer + fraction;
        break;
      default:
        res = integer + fraction;
        break;
    }
    return res;
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
    return ((decimal && decimal != '0') ? decimalSeparator + decimal : '');
  }
}