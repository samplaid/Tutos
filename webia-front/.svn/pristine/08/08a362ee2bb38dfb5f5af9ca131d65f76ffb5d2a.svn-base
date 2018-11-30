import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'clientDisplayName'})
export class ClientDisplayNamePipe implements PipeTransform {
  transform(displayName: string): any {
      if (displayName)
        return displayName.replace(/(PEP|DAP|Insider Trading|MEP|FACTA|STR)/g,"<span class='text-danger'>$1</span>");
      else
        return displayName;
  }


}