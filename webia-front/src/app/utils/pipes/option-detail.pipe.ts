import { Pipe, PipeTransform } from '@angular/core';
import { OptionDetail } from '../../_models/option';

@Pipe({name: 'optionDetail'})
export class OptionDetailPipe implements PipeTransform {
  transform(enumValue: number, allEnums: OptionDetail[]): string {
      if(!enumValue) {
        return "";
      }
      return allEnums.filter(e => e.number == enumValue)[0].description;
  }
}