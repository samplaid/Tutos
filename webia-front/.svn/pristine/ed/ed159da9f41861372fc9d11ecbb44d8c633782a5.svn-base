import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'textOfClause'})
export class TextOfClausePipe implements PipeTransform {
  transform(clause: any): string {
    if (!clause) return clause;

    let prefix:string = "";

    if (clause.textOfClause){
        if (clause.rank && !clause.textOfClause.startsWith(clause.rank)){
            prefix = clause.rank + '. ';
        }  
    } 

    return prefix + clause.textOfClause;

  }


}