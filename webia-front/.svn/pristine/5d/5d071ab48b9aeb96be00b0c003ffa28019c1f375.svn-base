
import { Component, OnInit, Input, EventEmitter, Output, ChangeDetectorRef } from '@angular/core';
import { Observable } from "rxjs/Observable";
import { FundType } from '../../fund';
import { Fund, FundForm } from "../../_models";
import { InvestmentService } from "../investment.service";

@Component({
    selector: 'registration-invest',
    templateUrl: 'registration-invest.component.html'
})
export class RegistrationInvestComponent implements OnInit {
    _fundForms: FundForm[];

    constructor(private investmentService: InvestmentService) {}

    @Output() modelChange = new EventEmitter<any>();

    @Input() formId;    
    @Input() defaultCurrency;
    @Input() mode: "readOnly"|"waiting"|"edit"|"create";

    /**
     * Register a new model of the investment
     * @param model the model of the investment
     */
    @Input() set model(model: FundForm[]) {
        this._fundForms = model;
        if(!this._fundForms) {
            this._fundForms = [];
        } 
        this.modelChange.emit(this._fundForms);
    }

    get model(){
        return this._fundForms;
    }
         
    /**
     * Initialize ccomponent variables.
     */
    ngOnInit() {
        if(this.model){
            this.model = this.model.sort( (a,b) => { return (b.fundFormId - a.fundFormId)});
        } else {
            this.model = [];            
        }
    }

    test(a:any){
        a.currencyList = [{ "ccyId": "CAD", "name": "Canadian dollars", "isoCode": "124" }];
        return a.currencyList;
    }

    /**
     * Create and add a new registration fund having the given type into the model.
     * @param fundType the fund type to register
     */
    newFund(fundTypeKey: string): void {
        this.model.unshift(this.investmentService.createNewFund(fundTypeKey, this.formId));
        this.model = this.model.slice();
    }

    /**
    * Remove the given the fund from the model
    * @param registrationfund the fund to remove
    */
    onFundRemove(index): void {
        this.model.splice(index, 1);
        this.model = this.model.slice();
    }

    /**
     * If the fund check box is checked then create a new fund form. Otherwise delete it from the model list.
     * @param event a checkbox event
     * @param fundTypeKey the fund type
     */
    onFundChecked(event, fundTypeKey: string): void {
        let checked = event.target.checked;
        if (checked) {            
            this.newFund(fundTypeKey);
        } else {            
            this.model.splice(this.model.findIndex((value, i) => value.fund == null && value.fundTp == fundTypeKey), 1);
        }

        this.model = this.model;
    }

}