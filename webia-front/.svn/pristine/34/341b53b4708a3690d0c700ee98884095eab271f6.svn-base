import { Pipe, PipeTransform } from '@angular/core';
import * as _ from "lodash";

@Pipe({name: 'groupBy'})
export class GroupByPipe implements PipeTransform {
    transform(inputs: any[], key: any): any {
        if(!_.isEmpty(inputs)){            
           return _.chain(inputs).groupBy(key).map((value, prop)=>{return { prop: prop, value: value}}).value();
        }
        return inputs;
    }
}