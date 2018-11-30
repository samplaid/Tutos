import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'capitalize'})
export class CapitalizePipe implements PipeTransform {
  transform(value: string, global?: boolean): any {
    if (!value) return value;

    if (global === false)
      return value.replace(/\w\S*/, this.capitalize);
    else
      return value.replace(/\w\S*/g, this.capitalize);
  }

  capitalize(txt) {
        return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
  }

}