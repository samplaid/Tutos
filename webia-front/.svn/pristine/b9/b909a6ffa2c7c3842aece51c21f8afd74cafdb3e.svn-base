

import { Injectable } from '@angular/core';
import { AssetManagerStrategy, CodeLabelType, AssetManagerStrategyStatus, CodeLabel, Roles, Page, CodeLabelValue } from '../../../_models/index';
import { WebiaService } from '../../../_services/index';
import { Observable } from 'rxjs/Observable';
import { UserService, StateMode, PaginationService, HttpService, MessageService, ApiService, HandleErrorOptions } from '../../../utils/index';
import { AmsStrategyRuleService } from './am-strategy-rule.service';
import { Http } from '@angular/http';
import { AgentService } from '../../agent.service';

export interface RiskTypeDescription {
    code: string;
}

@Injectable()
export class AmsStrategyService extends HttpService{
  
    constructor(private webiaService: WebiaService,
        private userService: UserService,
        private paginationService: PaginationService,
        private strategyRuleService: AmsStrategyRuleService,
        private api: ApiService,
        private agentService: AgentService,
        $http: Http, 
        public messageService: MessageService) {
            super($http, messageService);
        
    }
    
    /**
     * Save the strategy and returns the result as observable.
     * @param newStrategy 
     */
    save(newStrategy: AssetManagerStrategy): Observable<AssetManagerStrategy> {
        return this.POST(this.api.getURL('saveStrategy'), newStrategy, 'saveStrategy', new HandleErrorOptions(true, true,'agentCpt'));
    }

    /**
     * Convert the boolean to a string value that represents the status.
     * If true then it returns '1'. If false it returns '2'. Otherwise, it returns undefined because it can be null.
     * @param flag the flag.
     */
    asStatus(flag: boolean): string {
        return (flag === true) ? AssetManagerStrategyStatus.ENABLED : ((flag === false) ? AssetManagerStrategyStatus.DISABLED : undefined);
    }

    /**
     * Create a new list if the given array is undefined or null.
     * @param array a set of strategy. 
     */
    createListIfNull(array: Array<AssetManagerStrategy>): Array<AssetManagerStrategy> {
        return array ? array : new Array<AssetManagerStrategy>();
    }

    /** 
     * Retrieves the risk types of strategies from cod label and convert the code to its corresponding description.
    */
    getRiskTypeDescription(): Observable<Array<RiskTypeDescription>> {
        return this.webiaService.getCodeLabel(CodeLabelType.AM_STR)
            .map(riskTypes => (riskTypes) ? riskTypes.map(value => (value && value.code) ? JSON.parse('{ "' + value.code + '" : "' + value.label + '"}') : {})
                : new Array<RiskTypeDescription>());
    }

    /**
     * Retrieves the list of the risk type.
     */
    getRiskTypes(): Observable<Array<CodeLabel>> {
        return this.webiaService.getCodeLabel(CodeLabelType.AM_STR);
    }

    /** 
     * Determine the current mode based on the user role.
    */
    getMode(): string {
        return (this.userService.hasRole(Roles.AGENT_AM_EDIT)) ? StateMode.edit : StateMode.readonly;
    }
    
    /**
     * Deactive a strategy.
     * @param item 
     */
    deactiveItem(item: AssetManagerStrategy): void {
        if (item) {
            item.status = AssetManagerStrategyStatus.DISABLED;
        }
    }
    /**
     * Returns true if the strategy is activated and has an identifier.
     * @param item the strategy.
     */
    canDeactiveItem(item: AssetManagerStrategy): boolean {
        return item && item.amsId && this.isActiveItem(item);
    }
    
    /**
     * Returns true if the risk type, risk profil and the rsik description are filled in. Otherwise, it returns false.
     * @param item the strategy
     */
    isValidItem(item: AssetManagerStrategy): boolean {
        return item && !!item.riskType && !!item.riskProfile && !!item.riskProfileDescription;
    }

    /**
     * Indicates if the asset manager is active.
     * @param item the asset manager
     */
    isActiveItem(item: AssetManagerStrategy): boolean {
        return item && item.status === AssetManagerStrategyStatus.ENABLED;
    }

    /**
     * Indicates if the mode is read only.
     * @param mode the mode to check.
     */
    isReadOnlyMode(mode: string): boolean {
        return !mode || mode === StateMode.readonly;
    }

    /**
     * Check if the strategy has a profil risk type.
     * @param strategy the strategy.
     */
    hasProfilRiskType(strategy: AssetManagerStrategy): boolean {
        return strategy && strategy.riskType === CodeLabelValue.PROFIL;
    }
     
    /**
     * If the status in parameter is active then the function returns the active elements.
     * Otherwise, it returns the original array and an empty if the array in parameter is null or undefined.
     * @param status the strategy status
     * @param array array of strategy.
     */
    filterByStatus(status: string, array: Array<AssetManagerStrategy>): Array<AssetManagerStrategy> {
        if (array) {
            return array.filter(item => item
                && (status === AssetManagerStrategyStatus.ENABLED) ? item.status === status : true);
        } else {
            return new Array<AssetManagerStrategy>();
        }
    }

    /**
     * Returns true if the risk profile or the risk description includes the text.
     * @param text the text to search.
     * @param strategy a strategy.
     */
    searchItemPredicate(text: string, strategy: AssetManagerStrategy): boolean {
        return (strategy && strategy.riskProfile && strategy.riskProfile.toUpperCase().includes(text.toUpperCase()))
            || (strategy && strategy.riskProfileDescription && strategy.riskProfileDescription.toUpperCase().includes(text.toUpperCase()))
    }

    /**
     * Search the strategies that are matched with the text.
     * This method will filter the array based on the status and returns the search result.
     * If the search text and the array is not defined, the method returns an empty array.
     * @param status the strategy status
     * @param text the search criteria
     * @param array array of strategy.
     */
    searchItem(status: string, text: string, array: Array<AssetManagerStrategy>): Array<AssetManagerStrategy> {
        let filteredArray = this.filterByStatus(status, array);
        if (text && array) {
            filteredArray = filteredArray.filter(ams => this.searchItemPredicate(text, ams));
        }
        return filteredArray;
    }

    getFirstPage(pageSize: number, array: Array<AssetManagerStrategy>): Page {
        return this.paginationService.nextPage(array, 1, pageSize);
    }

    getPage(pageNumber: number, pageSize: number, array: Array<AssetManagerStrategy>): Page {
        return this.paginationService.nextPage(array, pageNumber, pageSize);
    }

    comparableByCode = (a1: AssetManagerStrategy, a2: AssetManagerStrategy): number => {
        if ((a1 && a2 && a1.riskProfile && !a2.riskProfile) || (a1 && !a2))
            return 1;
        if ((a1 && a2 && !a1.riskProfile && a2.riskProfile) || (!a1 && a2))
            return -1
        if (a1 && a2 && a1.riskProfile && a2.riskProfile)
            return a1.riskProfile.localeCompare(a2.riskProfile)
        return 0;
    }

    comparableByRiskType = (a1: AssetManagerStrategy, a2: AssetManagerStrategy): number => {
        if ((a1 && a2 && a1.riskType && !a2.riskType) || (a1 && !a2))
            return 1;
        if ((a1 && a2 && !a1.riskType && a2.riskType) || (!a1 && a2))
            return -1
        if (a1 && a2 && a1.riskType && a2.riskType) {
            let res = a1.riskType.localeCompare(a2.riskType)
            if (res == 0)
                return this.comparableByCode(a1, a2);
            else
                return res;
        }

        return 0;
    }

}