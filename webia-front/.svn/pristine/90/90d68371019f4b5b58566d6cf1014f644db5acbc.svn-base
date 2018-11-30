import { Pipe, PipeTransform } from '@angular/core';
import {DateUtils} from '../date-utils'

@Pipe({name: 'wdate'})
export class WDatePipe implements PipeTransform {
  transform(value: any): any {
    if (!value) return value;

    if (typeof value.getTime !='function')
         value = new Date(value);

    if (value.getFullYear() < DateUtils.WEALINS_NULL_YEAR)   // date like 01/01/1753 or 02/03/1753 are null date in MSSql
        return null;   

    return DateUtils.formatToddMMyyyy(value, "/");
  }
}