import { HttpService } from '../utils';
import { Http } from '@angular/http';
import { ApiService, MessageService } from '../utils';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { CheckStep, CheckData, CheckWorkFlow, BeneficiaryForm, FundForm, fundFasCode, fundFidCode } from '../_models';
import { FundFormService } from '../_services/fund-form.service';


@Injectable()
export class SurveyService extends HttpService{

    hideCodesCache: string[];
    public hideCodes: BehaviorSubject<string[]>;

    constructor($http: Http, messageService: MessageService, private api: ApiService, private fundFormService: FundFormService) {
        super($http, messageService);
        this.domain = 'SurveyService'; 

        this.hideCodes =  <BehaviorSubject<string[]>>new BehaviorSubject(undefined);
    }


    loadCheckSteps(...params): Observable<CheckStep[]> {
        return this.GET(this.api.getURL('loadCheckStep', ...params), 'loadCheckStep');
    }


/**
 * clean all invalid checkData with no value setted
 */
    prune(checkSteps:CheckStep[]):CheckStep[]{
        return checkSteps.map(checkStep=>{
            if (checkStep.check && !this._isValidCheckData(checkStep.check.checkData))
                 checkStep.check.checkData = null;
            this._cleanComment(checkStep.check);
            return checkStep;
        })
    }

/**
 * check if a checkData has a value setted not empty and not null
 */
    _isValidCheckData(checkData:CheckData):boolean {
        if (!checkData)
            return false;
        if (!checkData.checkDataId && !checkData.dataValueAmount && !checkData.dataValueDate && !checkData.dataValueNumber && !checkData.dataValueText && !checkData.dataValueYesNoNa)
            return false;
        return true;
    }

    _cleanComment(c:CheckWorkFlow):void{
        if (c && c.checkData && c.checkData.commentIf){
            if (!(c.commentIf && (c.commentIf==c.checkData.dataValueYesNoNa 
                                || +c.commentIf==c.checkData.dataValueNumber
                                || +c.commentIf==c.checkData.dataValueAmount))){
                c.checkData.commentIf = null;
            }
        }

    }

/**
 * parse a list of clients to check roles that could contextualize the survey
 */


    refreshHideCodes(clients:BeneficiaryForm[], hasFAS:boolean){
        let hideCodes = [...CheckCode.SubscriptionHiddenCodes];
        // on Policy_Agent_Relationship
        clients.forEach(c=> {
            if (c.irrevocable==true) hideCodes = hideCodes.filter(c=> c!=CheckCode.Irrevocable_Beneficiaries);
            if (c.acceptant==true) hideCodes = hideCodes.filter(c=> c!=CheckCode.Accepting_Beneficiaries);
            switch(c.clientRelationTp){
                case 15 : 
                    hideCodes = hideCodes.filter(c=> c!=CheckCode.Replacement_Policyholde); break;
                case 18 :
                case 19 :
                case 19 :
                    hideCodes = hideCodes.filter(c=> c!=CheckCode.Cession_Rights); break;
            }
        })
        // on Fund_Subtype in investment
        if (hasFAS) 
            hideCodes = hideCodes.filter(c=> c!=CheckCode.Specialised_Investment_Fund);

        this.hideCodesCache = hideCodes;
        this.hideCodes.next(this.hideCodesCache);
    }

    refreshFundCodes(fundForms: FundForm[]): void {
        if (fundForms && fundForms.some(fundForm => this.fundFormService.canShowNewFundQuestions(fundForm))) {
            this.hideCodes.next([]);
        } else {
            this.hideCodes.next(CheckCode.New_Fid_Fas_Ctx_Array);
        }
    }
}


/**
 * Static class grouping the checkCode with contextualization
 */
export class CheckCode { 
    // on Policy_Agent_Relationship
    public static Replacement_Policyholde   = 'CTX1';    //  15   ==  Owner on Death
    public static Cession_Rights  = 'CTX2';    //  18,19 or 20
    public static Irrevocable_Beneficiaries = 'CTX4';    // 17
    public static Accepting_Beneficiaries   = 'CTX3';    // 31

    // on Fund_Subtype in investment
    public static Specialised_Investment_Fund   = 'CTX5';

    public static New_Fid_Fas_Ctx_Array   = ['CTX7', 'CTX8', 'CTX9'];

    public static SubscriptionHiddenCodes = [CheckCode.Replacement_Policyholde,
                         CheckCode.Cession_Rights,
                         CheckCode.Irrevocable_Beneficiaries,
                         CheckCode.Accepting_Beneficiaries,
                         CheckCode.Specialised_Investment_Fund
                        ];
}