import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'mapValue'
})

export class MapValuePipe implements PipeTransform {
    transform(input: any, ...args: any[]): any {
        let returnArray = [];        
        input.forEach((entryVal, entryKey) => {
            returnArray.push({
                key: entryKey,
                value: entryVal
            });
        });
        return returnArray;
    }
}