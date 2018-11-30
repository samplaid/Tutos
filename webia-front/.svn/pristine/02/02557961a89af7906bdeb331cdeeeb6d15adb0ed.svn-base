import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'keys'})
export class KeysPipe implements PipeTransform {
  transform(value, filtreNull:boolean, args:string[]) : any {
      let result = Object.keys(value).map((key) => {return {"key": key, "value": value[key]} });
      if (filtreNull)
        result = result.filter(n => !!n.value  ); 
      return result;
  }
}