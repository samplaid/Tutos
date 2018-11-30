import { Pipe, PipeTransform } from '@angular/core';
import * as _ from "lodash";
/**
 * custom filter pipe to apply on array of data or object
 * @author : Nabil LOUTFI
 * var array = [{a:{b:1}},{a:{b:2}}]
 * {{array | filter:'a.b=1'  }}   or  {{array | filter:'a.b!=1'  }}
 */
@Pipe({ name: 'filter'})
export class FilterPipe implements PipeTransform {

  transform(array: any[], ...args: any[]): any {    
    if (!array || !args) {
      return array;
    }
    let bool;
    args.forEach((arg) => {
      array = array.filter(item => {   
        let val = eval(arg);
        return (typeof val === 'boolean')? val:false; 
      });
    });
    return array;
  }

}