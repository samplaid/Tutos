import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'address'})
export class AddressPipe implements PipeTransform {
  transform(agent: any): string {
    if (!agent) return agent;

    let address:string = "";

    if (agent.addressLine1) 
        address+=agent.addressLine1.trim();
    if (agent.addressLine2) 
        address+=" "+agent.addressLine2.trim();
    if (agent.addressLine3) 
        address+=" "+agent.addressLine3.trim();
    if (agent.addressLine4) 
        address+=" "+agent.addressLine4.trim();
    if (agent.ptCode)
        address+=" "+agent.ptCode.trim()+"-"; 
    else 
        address+=" ";
    if (agent.postcode) 
        address+=agent.postcode.trim();
    if (agent.postCode) 
        address+=agent.postCode.trim();

    if (agent.town) 
        address+=" "+agent.town.trim();
    if (agent.country && agent.country.trim()!="") 
        address+=" ("+agent.country.trim()+")";

      return address;

  }


}