import { Observable } from "rxjs/Observable";
import { Observer } from "rxjs/Observer";

import { AppForm, Step, PartnerForm, AgentCategoryEnum } from "../_models";
import { HttpService, MessageService, ApiService, HandleErrorOptions, Store } from "../utils";
import { Http } from "@angular/http";
import { StoreName } from '../utils/services/store-name';

export class BasicFormDataService<T extends Step<AppForm>> extends HttpService {

    constructor(public $http: Http, messageService: MessageService, protected api: ApiService) {
        super($http, messageService);
    }

    /**
     *  Save the form data model.
     */
    save(step: T, complete?: boolean): Observable<T> {
        return new Observable(observer => { 
            this.POST(this.api.getURL(complete ? 'completeStep' : 'updateStep'), step, 'save', new HandleErrorOptions(true, true))
                .toPromise().then(step=>{observer.next(step);observer.complete();}, 
                                        e=>{observer.error(e);observer.complete();});
        });
    }

    refreshAppForm(currentStep:Step<AppForm>, reloadedStep:Step<AppForm>){
        if(reloadedStep && currentStep){                       
            currentStep = reloadedStep;
            //currentStep.checkSteps = reloadedStep.checkSteps;
        }
    }

    /**
     * Action performed when going bak to previous step.
     * */
    previousStep(appForm: AppForm) {
        // no implementation, this method is intended to be overriden
    }

}