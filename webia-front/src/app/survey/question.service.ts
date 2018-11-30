import { ScoreBCFT, CheckType, CheckWorkFlow } from '../_models';
import * as _ from "lodash";import { HttpService, MessageService } from '../utils';
import { Http } from '@angular/http';
import { Injectable } from '@angular/core';

/**
 * A class that provides methods used to access the question data.
 */
@Injectable()
export class QuestionService extends HttpService{

    constructor(public $http: Http, protected messageService: MessageService) { 
        super($http, messageService);
    }

    /**
     * Retrieve the score of a question having the code and response in parameter.
     * @param checkCode the code
     * @param response  the question response
     * @param scoreBCFTs a list that contains the score that is predicated by the code and the response.
     */
    getQuestionScore(checkCode: string, response: string, scoreBCFTs?: ScoreBCFT[]): number {
        if (scoreBCFTs) {
            let bft: ScoreBCFT = scoreBCFTs.find(score => score.checkCode === checkCode && score.response === response);
            if (bft && bft.score) {
                return bft.score;
            }
        }
        return undefined;
    }

    answer(question: CheckWorkFlow, callback: (response: any) => any): any {
        let result: any = null;

        if (question && question.checkType) {
            switch (question.checkType) {
                case CheckType.yesNo.value:
                    result = callback.call(this, question.checkData.dataValueYesNoNa)
                    break;
                case CheckType.yesNoNa.value:
                    result = callback.call(this, question.checkData.dataValueYesNoNa);
                    break;
                case CheckType.text.value:
                    result = callback.call(this, question.checkData.dataValueText);
                    break;
                case CheckType.number.value:
                    result = callback.call(this, question.checkData.dataValueNumber);
                    break;
                case CheckType.date.value:
                    result = callback.call(this, question.checkData.dataValueDate);
                    break;
                case CheckType.amount.value:
                    result = callback.call(this, question.checkData.dataValueAmount);
                    break;
                default:
                    break;
            }
        }

        return result;
    }

}