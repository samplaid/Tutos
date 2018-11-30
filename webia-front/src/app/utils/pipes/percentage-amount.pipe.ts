import { isNullOrUndefined } from 'util';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'percentageAmount'})
export class PercentageAmountPipe implements PipeTransform {
  transform(value: string, isPercentage: boolean, currency: string) : string {
      if(isNullOrUndefined(value)) {
        return "";
      } else if(isPercentage) {
        return value + " %";
      } else {
        return value + " " + currency;
      }
  }
}