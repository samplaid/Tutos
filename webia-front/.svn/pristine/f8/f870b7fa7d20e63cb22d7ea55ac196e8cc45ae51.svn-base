import { Injectable } from '@angular/core';
import { Http } from "@angular/http";
import { Observable } from "rxjs/Observable";

import { MessageService, ApiService } from "../utils";
import { BasicFormDataService } from "../_services";
import { Step, AppForm } from "../_models";
import { InvestmentService } from "../investment/investment.service";
import { AgentService } from '../agent';

@Injectable()
export class RegistrationService extends BasicFormDataService<Step<AppForm>>{
    constructor(public agentService: AgentService, $http: Http, messageService: MessageService, protected api: ApiService, private investmentService: InvestmentService) {
        super($http, messageService, api);   
        this.domain = 'RegistrationService';     
    }
  
    save(step: Step<AppForm>, complete?: boolean): Observable<Step<AppForm>> { 
        if(step && step.formData){
           step.formData.fundForms = this.investmentService.removeDirtyFunds(step.formData.fundForms);
           this.investmentService.initFundCurrency(step.formData.fundForms, step.formData.contractCurrency);
           return new Observable((observer) => {
               if (!step.formData.productCd){
                   this.messageService.addAlertError("Product is mandatory !");
                    observer.complete();
               } else {
                    this.investmentService.validInvestmentFunds(step.formData.fundForms)
                                .then(ok => super.save(step, complete).subscribe((step: Step<AppForm>)=> observer.next(step), e=>observer.error(e)) , 
                                        errors => { observer.error(errors); observer.complete() });
               }   

           });
        } else {
            return super.save(step, complete);
        }   
    }
}