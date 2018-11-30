import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'stepName'})
export class StepNamePipe implements PipeTransform {
  transform(stepName: string): any {
      if (stepName){
        let result = stepName.replace(/(Unblocked )/gi,'<i class="fa fa-unlock" aria-hidden="true" title="Unblocked step without validation"></i> ');
        // autres regles ??
        // result = result.replace(/(Unblocked )/gi,'<sup><i class="fa fa-unlock-alt" aria-hidden="true"></i></sup> ');
        // result = result.replace(/(Awaiting )/gi,'<sup><i class="fa fa-hourglass-half" aria-hidden="true"></i></sup> ');
        return result;
      }
      
      return stepName;
  }


}