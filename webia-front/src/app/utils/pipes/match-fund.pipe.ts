import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'matchFund'
})

export class MatchFundPipe implements PipeTransform {
    transform(items: Array<any>, ...args: any[]): any {
        return items.filter( (item) => args.findIndex((value,i) => value == item.fundTp) != -1);
    }
}